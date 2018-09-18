package misiont.mision7;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;



/**
 * Created by Bernardo_NoAdmin on 17/02/2018.
 */

public class AccountConfigFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener  {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.account_preferences);
        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        ((ContentActivity)getActivity()).setUserName();
    }
}
