package app.cominjohnsawickitrkhp_identity_photo.linkedin.httpswww.phoneandtabletweather;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

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
        url= "http://api.openweathermap.org/data/2.5/forecast/daily?zip=77020,us&units=imperial&cnt=7&APPID=36ef9d0845139b59cdc1be9d83142b39";
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.zip, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.refresh_item){
            FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
            fetchWeatherTask.execute(url);
        }
        return true;
    }
    class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
        public String[] formattedString = new String[11];   //index 0-4 for detail and index 5-9 for list
        @Override
        protected String[] doInBackground(String... strings) {
            String backGroundURL = strings[0];
            try {
                URL theURL = new URL(backGroundURL);
                BufferedReader reader = new BufferedReader(new InputStreamReader(theURL.openConnection().getInputStream(), "UTF-8"));
                String jsonString = reader.readLine();
                Log.d("raw json", jsonString);
                JSONObject weather = new JSONObject(jsonString);
                JSONObject city = weather.getJSONObject("city");
                formattedString[0]=city.getString("name");
                Log.d("city name", formattedString[0]);//Houston
                JSONArray days = weather.getJSONArray("list");
                Date date = new Date();
                String dayOfWeek, main, max, min, composite, icon, description;
                for (int i = 0; i < 5; i++) {
                    date.setTime(date.getTime()+i*24*60*60*1000);
                    dayOfWeek =String.format("%ta %<tb %<td", date);
                    JSONObject dayInfo = days.getJSONObject(i); //info for the first day
                    JSONObject temp = dayInfo.getJSONObject("temp");
                    max = temp.getString("max");
                    min=temp.getString("min");
                    JSONArray weatherArray = dayInfo.getJSONArray("weather");
                    JSONObject weatherInfo = weatherArray.getJSONObject(0);
                    main = weatherInfo.getString("main");
                    icon = ""+weatherInfo.getInt("icon");
                    description = weatherInfo.getString("description");
                    composite =dayOfWeek+" - "+main+" - "+ Math.round(Double.parseDouble(max))+"/"+ Math.round(Double.parseDouble(min)) ;
                    if(i==0){//save values for detail activity
                        formattedString[1] = dayOfWeek; formattedString[2] = max; formattedString[3] = min;  formattedString[4] = description; formattedString[5] = icon;
                    }
                    formattedString[i+6]= composite;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return formattedString;
        }
        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            Log.d("city name", formattedString[0]+" onPostExecute");
            //for(int i = 0; i<formattedString.length; i++){
            //     Log.d(i+" ",formattedString[i] );   }
            mListFragment.updateList(formattedString);
            mLocationFragment.updateLocation(formattedString);
            mDetailFragment.updateDetail(formattedString);   //detail activity for phone vert updated, but fragment size set to 0

        }
    }
}
