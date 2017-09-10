package com.example.gplotnikov.reddittestapp.data.backend;

import com.example.gplotnikov.reddittestapp.domain.backend.Backend;
import com.example.gplotnikov.reddittestapp.domain.backend.SomethingWrongException;
import com.example.gplotnikov.reddittestapp.domain.model.Order;
import com.example.gplotnikov.reddittestapp.domain.model.Page;
import com.example.gplotnikov.reddittestapp.domain.model.Topic;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.Credentials;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitBackend implements Backend {
    private static final String OAUTH_BASE_URL = "https://oauth.reddit.com";
    private final RedditService redditService;
    private final AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
    private final HeaderValue basicAuth;

    RetrofitBackend(String userName, String password) {
        basicAuth = new BasicAuthHeaderValue(userName, password);
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .dispatcher(new Dispatcher(Executors.newSingleThreadExecutor()))
                        .addInterceptor(authenticationInterceptor)
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(OAUTH_BASE_URL)
                .build();
        authenticationInterceptor.setHeaderValue(basicAuth);
        redditService = retrofit.create(RedditService.class);
    }

    @Override
    public Page<Topic> getTop(Order order, int limit) throws IOException, SomethingWrongException {
        Call<ApiListingResponse<ApiTopic>> call = redditService.getTop(limit,
                order != null && order.getValue().equals(Order.AFTER) ? order.getHash() : null,
                order != null && order.getValue().equals(Order.BEFORE) ? order.getHash() : null);
        retrofit2.Response<ApiListingResponse<ApiTopic>> response = call.execute();
        if (response.isSuccessful()) {
            return Mapper.map(response.body());
        } else if (response.code() == 401 || response.code() == 403) {
            authenticationInterceptor.setHeaderValue(basicAuth);
            retrofit2.Response<ApiTokenResponse> tokenResponse = redditService.getToken("https://oauth.reddit.com/grants/installed_client", "345467a6-da4f-49db-be3b-03b0ca7833ba").execute();
            if (tokenResponse.isSuccessful()) {
                if (tokenResponse.body() != null) {
                    authenticationInterceptor.setHeaderValue(new BearerAuthHeaderValue(tokenResponse.body().getAccessToken()));
                }
                response = call.clone().execute();
                return Mapper.map(response.body());
            } else {
                throw new SomethingWrongException();
            }
        } else {
            throw new SomethingWrongException();
        }
    }

    private interface HeaderValue {
        String getValue();
    }

    private static class AuthenticationInterceptor implements Interceptor {
        private static final String HEADER_AUTHORIZATION = "Authorization";

        private HeaderValue headerValue;

        public HeaderValue getHeaderValue() {
            return headerValue;
        }

        public void setHeaderValue(HeaderValue headerValue) {
            this.headerValue = headerValue;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (headerValue != null) {
                return chain.proceed(request.newBuilder().addHeader(HEADER_AUTHORIZATION, headerValue.getValue()).build());
            } else {
                return chain.proceed(request);
            }
        }
    }

    private static class BearerAuthHeaderValue implements HeaderValue {
        private static final String PREFIX = "bearer";
        private final String hash;

        BearerAuthHeaderValue(String hash) {
            this.hash = hash;
        }

        public String getHash() {
            return hash;
        }

        @Override
        public String toString() {
            return "BearerAuthHeaderValue{" +
                    "hash='" + hash + '\'' +
                    '}';
        }

        @Override
        public String getValue() {
            return PREFIX + " " + hash;
        }
    }

    private static class BasicAuthHeaderValue implements HeaderValue {
        private final String userName;
        private final String password;

        BasicAuthHeaderValue(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        String getUserName() {
            return userName;
        }

        String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return "BasicAuthHeaderValue{" +
                    "userName='" + userName + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        @Override
        public String getValue() {
            return Credentials.basic(userName, password);
        }
    }
}