package app.cominjohnsawickitrkhp_identity_photo.linkedin.httpswww.phoneandtabletweather;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment{
    TextView maxTemp, minTemp, main;
    ImageView iconImageView;
    String[] formattedString = new String[11];
    String imageIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        maxTemp = (TextView) view.findViewById(R.id.max_temp);
        minTemp = (TextView) view.findViewById(R.id.min_temp);
        main = (TextView) view.findViewById(R.id.main_description);
        iconImageView = (ImageView) view.findViewById(R.id.icon);
        return view;
    }

    public DetailFragment() {
    }
}
