package app.cominjohnsawickitrkhp_identity_photo.linkedin.httpswww.phoneandtabletweather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocationFragment extends Fragment {
    TextView time, location;
    String userTime=" ";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_fragment,container, false);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM-dd-yyyy");
        time = (TextView)view.findViewById(R.id.time_textView);
        location = (TextView)view.findViewById(R.id.location_textView);
        userTime = dateFormat.format(date);
        time.setText(userTime);
        return view;
    }

    public void updateLocation(String[] formattedString){
        location.setText(formattedString[0]);
    }
    /*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("userTime",userTime );
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        time.setText(savedInstanceState.getString("userTime"));
        super.onViewStateRestored(savedInstanceState);
    }
*/
}
