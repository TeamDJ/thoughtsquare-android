package teamdj.thoughtsquare.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import teamdj.thoughtsquare.R;

import java.util.ArrayList;
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
}
