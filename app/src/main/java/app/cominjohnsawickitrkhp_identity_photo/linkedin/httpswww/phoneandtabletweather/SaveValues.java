package app.cominjohnsawickitrkhp_identity_photo.linkedin.httpswww.phoneandtabletweather;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveValues extends MainActivity{
    SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(this);
    SharedPreferences.Editor editor;
    public String[] formattedValues = new String[11];
    SaveValues(){
    }
    public void saveFormattedStrings(String[] formattedValues){
        this.formattedValues = formattedValues;
        editor.putString("saved 0", formattedValues[0]); // here string is the value you want to save
        editor.putString("saved 1", formattedValues[1]);
        editor.putString("saved 2", formattedValues[2]);
        editor.putString("saved 3", formattedValues[3]);
        editor.putString("saved 4", formattedValues[4]);
        editor.putString("saved 5", formattedValues[5]);
        editor.putString("saved 6", formattedValues[6]);
        editor.putString("saved 7", formattedValues[7]);
        editor.putString("saved 8", formattedValues[8]);
        editor.putString("saved 9", formattedValues[9]);
        editor.putString("saved 10", formattedValues[10]);
        editor.commit();
    }
    public String[] retrieveFormattedStrings(){
        formattedValues[0]=settings.getString("saved 0", "Houston");
        formattedValues[1]=settings.getString("saved 1", "Today");
        formattedValues[2]=settings.getString("saved 2", "70");
        formattedValues[3]=settings.getString("saved 3", "50");
        formattedValues[4]=settings.getString("saved 4", "main");
        formattedValues[5]=settings.getString("saved 5", "icon");
        formattedValues[6]=settings.getString("saved 6", "Today - Sunny - 88/63");
        formattedValues[7]=settings.getString("saved 7", "Tomorrow - Foggy - 70/46");
        formattedValues[8]=settings.getString("saved 8", "Weds - Cloudy - 72/63");
        formattedValues[9]=settings.getString("saved 9", "Weds - Cloudy - 72/63");
        formattedValues[10]=settings.getString("saved 10", "Fri - Foggy - 70/46");
        formattedValues[11]=settings.getString("saved 11", "Sat - Sunny 76/68");
        return formattedValues;
    }
}
