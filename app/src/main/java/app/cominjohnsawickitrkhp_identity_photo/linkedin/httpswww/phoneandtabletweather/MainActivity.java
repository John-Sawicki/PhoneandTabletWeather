package app.cominjohnsawickitrkhp_identity_photo.linkedin.httpswww.phoneandtabletweather;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    LocationFragment mLocationFragment = new LocationFragment();
    DetailFragment mDetailFragment = new DetailFragment();
    ListFragment mListFragment = new ListFragment();
    FragmentManager mFragmentManager;
    Button updateButton;
    String url, zipCode ="77020", units="imperial";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("start", "start");
        mFragmentManager = getFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.location_time_fragment, mLocationFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.list_fragment, mListFragment).commit();
        mFragmentManager.beginTransaction().add(R.id.detail_fragment, mDetailFragment).commit();
        setContentView(R.layout.activity_main);
        url ="http://api.openweathermap.org/data/2.5/forecast/daily?zip="+zipCode+",us&units="+units+"&cnt=7&APPID=36ef9d0845139b59cdc1be9d83142b39";

    }
}
