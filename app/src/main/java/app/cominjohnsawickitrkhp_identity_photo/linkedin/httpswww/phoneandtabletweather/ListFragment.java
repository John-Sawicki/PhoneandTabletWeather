package app.cominjohnsawickitrkhp_identity_photo.linkedin.httpswww.phoneandtabletweather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFragment extends Fragment {
    public ListFragment(){}
    TextView mTextView;
    public ArrayAdapter<String> mArrayAdapter;
    String[] weather, formattedString;
    String todayForecast;
    ListView listView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment,container, false);
        mTextView =(TextView)view.findViewById(R.id.detail_update);
        weather = new String[] {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/46",
                "Weds - Cloudy - 72/63",
                "Thurs - Rainy - 64-51",
                "Fri - Foggy - 70/46",
                "Sat - Sunny 76/68"};
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(weather));
        mArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.weather_listview, weekForecast);
         listView = (ListView)view.findViewById(R.id.arrayView);
        View emptyView = view.findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        listView.setAdapter(mArrayAdapter);
        return view;
    }

    public void updateList(String[] formattedString){
        this.formattedString = formattedString;
        //location.setText(formattedString[0]);
        if(formattedString!=null){
            mArrayAdapter.clear();
            Log.d("clear array", "clear array");
            for(int i = 6;i<11;i++){        //copy updated dated from async to the weather string
                Log.d(i+" i ",formattedString[i]);
                todayForecast = formattedString[i];
                mArrayAdapter.add(todayForecast);
            }
        }
    }
}