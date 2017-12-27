package app.cominjohnsawickitrkhp_identity_photo.linkedin.httpswww.phoneandtabletweather;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

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
    LinearLayout background;
    public String[] formattedString = new String[11];   //index 0-4 for detail and index 5-9 for list
    public SharedPreferences settings;
    public SharedPreferences.Editor editor;
    SaveValues mSaveValues;
    public boolean refreshed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = settings.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("start", "start");
        background =(LinearLayout)findViewById(R.id.background);
        mFragmentManager = getFragmentManager();
        if(!refreshed) {  //only add the fragments the first time. after data is refreshed
            Log.d("added  fragments", "onCreate");
            mFragmentManager.beginTransaction().add(R.id.location_time_fragment, mLocationFragment).commit();
            mFragmentManager.beginTransaction().add(R.id.list_fragment, mListFragment).commit();
            mFragmentManager.beginTransaction().add(R.id.detail_fragment, mDetailFragment).commit();
            setContentView(R.layout.activity_main);
        }
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
        //
        @Override
        protected String[] doInBackground(String... strings) {
            String backGroundURL = strings[0];
            refreshed= true;    //use formatted value strings
            try {
                URL theURL = new URL(backGroundURL);
                BufferedReader reader = new BufferedReader(new InputStreamReader(theURL.openConnection().getInputStream(), "UTF-8"));
                String jsonString = reader.readLine();
                //Log.d("raw json", jsonString);
                JSONObject weather = new JSONObject(jsonString);
                JSONObject city = weather.getJSONObject("city");
                formattedString[0]=city.getString("name");
                Log.d("city name index 0", formattedString[0]);//Houston
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
                    icon = weatherInfo.getString("icon");
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
            //Log.d("city name", formattedString[0]+" onPostExecute");
           mListFragment.updateList(formattedString);
           mLocationFragment.updateLocation(formattedString);
           mDetailFragment.updateDetail(formattedString);   //detail activity for phone vert updated, but fragment size set to 0
            double highTemp = Double.parseDouble(formattedString[2]);
            int intHighTemp = (int)highTemp;
            if(intHighTemp<34)   //TODO change font to white
                background.setBackgroundColor(0x1565C0);
            else if(intHighTemp<60)
                background.setBackgroundColor(0xBBDEFB);
            else if(intHighTemp<80)
                background.setBackgroundColor(0xFFCDD2);
            else
                background.setBackgroundColor(0xF44336);    //TODO change font to white
        }
    }

    @Override
    protected void onPause() {  //save formatted string before the rotation
        super.onPause();
        Log.d("onPause", "onPause" );
        if(refreshed) {
            //mSaveValues.saveFormattedStrings(formattedString);  //saves values in shared pref
            editor.putString("saved 0", formattedString[0]); // here string is the value you want to save
            editor.putString("saved 1", formattedString[1]);
            editor.putString("saved 2", formattedString[2]);
            editor.putString("saved 3", formattedString[3]);
            editor.putString("saved 4", formattedString[4]);
            editor.putString("saved 5", formattedString[5]);
            editor.putString("saved 6", formattedString[6]);
            editor.putString("saved 7", formattedString[7]);
            editor.putString("saved 8", formattedString[8]);
            editor.putString("saved 9", formattedString[9]);
            editor.putString("saved 10", formattedString[10]);
            editor.putBoolean("refreshed",refreshed);
            editor.commit();
            Log.d("onPause index 0", settings.getString("saved 0", "Houston"));
        }
    }
    @Override
    protected void onResume() {//clear arrayList and add values from formattedString
        super.onResume();
        //update if values are not null. when the program is first started array is empty and listFragment values are used
        Log.d("onResume", "onResume" );
        //Log.d("resume value 6", settings.getString("saved 6", "no shared pref"));
        //formattedString[6]=settings.getString("saved 6", "no shared pref");
        //Log.d("test for null", "index 0 is not null" );
        refreshed = settings.getBoolean("refreshed", false);
        Log.d("refreshed value",""+refreshed );
        if(refreshed) {//use share pref values if async task is used
            formattedString[0]=settings.getString("saved 0", "Houston");
            formattedString[1]=settings.getString("saved 1", "Today");
            formattedString[2]=settings.getString("saved 2", "70");
            formattedString[3]=settings.getString("saved 3", "50");
            formattedString[4]=settings.getString("saved 4", "main");
            formattedString[5]=settings.getString("saved 5", "icon");
            formattedString[6]=settings.getString("saved 6", "Today - Sunny - 88/63");
            formattedString[7]=settings.getString("saved 7", "Tomorrow - Foggy - 70/46");
            formattedString[8]=settings.getString("saved 8", "Weds - Cloudy - 72/63");
            formattedString[9]=settings.getString("saved 9", "Weds - Cloudy - 72/63");
            formattedString[10]=settings.getString("saved 10", "Fri - Foggy - 70/46");
            Log.d("formatted string 6",  formattedString[6]);
            mListFragment.updateList(formattedString);
            mLocationFragment.updateLocation(formattedString);
            mDetailFragment.updateDetail(formattedString);
        }
    }
}
