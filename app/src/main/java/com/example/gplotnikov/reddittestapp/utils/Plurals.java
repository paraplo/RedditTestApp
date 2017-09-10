package com.example.gplotnikov.reddittestapp.utils;

import android.content.Context;
import android.content.res.Resources;

import com.seppius.i18n.plurals.PluralResources;

public class Plurals {
    private final Resources resources;
    private final PluralResources pluralResources;

    private Plurals(Resources resources) {
        this.resources = resources;
        PluralResources pluralResourcesTemp;
        try {
            pluralResourcesTemp = new PluralResources(resources);
        } catch (NoSuchMethodException e) {
            pluralResourcesTemp = null;
        }
        pluralResources = pluralResourcesTemp;
    }

    public static Plurals with(Context context) {
        return new Plurals(context.getResources());
    }

    public String getQuantityString(int id, int quantity, Object... formatArgs) throws Resources.NotFoundException {
        return pluralResources != null ? pluralResources.getQuantityString(id, quantity, formatArgs) : resources.getQuantityString(id, quantity, formatArgs);
    }

    public String getFormattedQuantityString(int id, int quantity) throws Resources.NotFoundException {
        return pluralResources != null ? pluralResources.getQuantityString(id, quantity, quantity) : resources.getQuantityString(id, quantity, quantity);
    }

    public String getQuantityString(int id, int quantity) throws Resources.NotFoundException {
        return pluralResources != null ? pluralResources.getQuantityString(id, quantity) : resources.getQuantityString(id, quantity);
    }
}
