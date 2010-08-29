package com.thoughtsquare.utility;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Config config = null;

    public Config getConfig(Context context) {
        if (config == null) {
            config = getConfigFromAndroid(context);
        }
        return config;
    }

    private Config getConfigFromAndroid(Context context) {
        Resources resources = context.getResources();
        AssetManager assetManager = resources.getAssets();

        // Read from the /assets directory
        try {
            InputStream inputStream = assetManager.open("application.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            System.out.println("The properties are now loaded");
            return new Config(properties);

        } catch (IOException e) {
            throw new RuntimeException("Failed to open application.properties", e);
        }
    }
}
