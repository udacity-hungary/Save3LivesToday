package hu.intellicode.savethreelivestoday.ui.user;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import hu.intellicode.savethreelivestoday.R;

public class UserDetailsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        addPreferencesFromResource(R.xml.user_details);
    }
}
