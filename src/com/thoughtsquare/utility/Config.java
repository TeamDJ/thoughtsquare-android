package com.thoughtsquare.utility;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private Properties properties;

    public Config(Properties properties){
        this.properties = properties;
    }

    public String getServerBaseURL(){
        return properties.getProperty("server.base.url");
    }
    
}
