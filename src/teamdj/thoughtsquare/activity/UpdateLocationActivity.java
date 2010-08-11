package teamdj.thoughtsquare.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import teamdj.thoughtsquare.R;

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
    private List<String> cities = asList("Brisbane", "Sydney", "Melbourne", "Perth");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.update_location);

        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Bundle extras = new Bundle();
        extras.putString("cityName", cities.get(position));

        Intent mIntent = new Intent();
        mIntent.putExtras(extras);
        setResult(RESULT_OK, mIntent);
        finish();
    }
}
