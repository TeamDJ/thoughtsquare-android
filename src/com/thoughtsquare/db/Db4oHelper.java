package com.thoughtsquare.db;

import android.content.Context;
import android.util.Log;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.thoughtsquare.domain.Location;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class Db4oHelper {
    private static ObjectContainer oc = null;
    private Context context;

    public Db4oHelper(Context context) {
        this.context = context;
    }

    public ObjectContainer db() {
        try {
            if (oc == null || oc.ext().isClosed()) {
                oc = Db4oEmbedded.openFile(dbConfig(), context.getDir("data", MODE_PRIVATE) + "/" + "thoughtsquare.db4o");
            }

            return oc;
        } catch (Exception ie) {                                                    
            Log.e(Db4oHelper.class.getName(), ie.toString());
            return null;
        }
    }

    private EmbeddedConfiguration dbConfig() throws IOException {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().objectClass(Location.class).objectField("name").indexed(true);
        configuration.common().objectClass(Location.class).cascadeOnUpdate(true);
        configuration.common().objectClass(Location.class).cascadeOnActivate(true);
        return configuration;
    }

    public void close() {
        if (oc != null) {
            oc.close();
        }
    }
}
