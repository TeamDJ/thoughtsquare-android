package teamdj.thoughtsquare.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import teamdj.thoughtsquare.R;
import teamdj.thoughtsquare.domain.Location;
import teamdj.thoughtsquare.service.LocationService;
import teamdj.thoughtsquare.service.MockLocationService;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by IntelliJ IDEA.
 * User: jottaway
 * Date: Aug 11, 2010
 * Time: 4:27:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateLocationActivity extends ListActivity {
    private LocationService locationService;
    private List<Location> locations;

    public UpdateLocationActivity() {
        locationService = new MockLocationService();
        
        locations = locationService.getLocations();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.update_location);

        setListAdapter(new LocationAdapter());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Bundle extras = new Bundle();
        extras.putParcelable("location", locations.get(position));

        Intent mIntent = new Intent();
        mIntent.putExtras(extras);
        setResult(RESULT_OK, mIntent);
        finish();
    }

    class LocationAdapter extends ArrayAdapter<Location> {
        LocationAdapter() {
            super(UpdateLocationActivity.this, android.R.layout.simple_list_item_1, locations);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            TextView text = (TextView)row.findViewById(android.R.id.text1);
            text.setText(locations.get(position).getTitle());

            return row;
        }
    }
}
