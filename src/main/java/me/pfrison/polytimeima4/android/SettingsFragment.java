package me.pfrison.polytimeima4.android;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import me.pfrison.polytimeima4.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_fragment);
    }
}
