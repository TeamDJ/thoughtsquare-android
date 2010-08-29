package com.thoughtsquare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.thoughtsquare.R;
import com.thoughtsquare.async.AddLocationTask;
import com.thoughtsquare.domain.Location;

import java.util.concurrent.ExecutionException;

import static java.lang.Double.valueOf;

public class AddLocationActivity extends Activity {
    private AsyncTask<Location, Void, Location> addLocationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location);

        addLocationTask = new AddLocationTask(this);

        Button addLocation = (Button) findViewById(R.id.addLocation);

        addLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title = ((EditText) findViewById(R.id.title)).getText().toString();
                String latitude = ((EditText) findViewById(R.id.latitude)).getText().toString();
                String longitude = ((EditText) findViewById(R.id.longitude)).getText().toString();

                Location location = new Location(0, title, valueOf(latitude), valueOf(longitude), 0);

                addLocationTask.execute(location);
            }
        });
    }

    public void onFinishTask() {
        Location location = null;
        try {
            location = addLocationTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (location != null) {
            Bundle extras = new Bundle();
            extras.putParcelable("location", location);

            Intent mIntent = new Intent();
            mIntent.putExtras(extras);
            setResult(RESULT_OK, mIntent);
            finish();
        }
    }
}
