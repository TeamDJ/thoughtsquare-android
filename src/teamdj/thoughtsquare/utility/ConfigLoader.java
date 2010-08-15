package teamdj.thoughtsquare.utility;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    public Config getConfig(Context context){
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
