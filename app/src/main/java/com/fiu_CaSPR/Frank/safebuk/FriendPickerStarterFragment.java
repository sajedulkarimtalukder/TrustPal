package com.fiu_CaSPR.Frank.safebuk;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fiu_CaSPR.Frank.safebuk.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FriendPickerStarterFragment extends Fragment {

    public FriendPickerStarterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_picker_starter, container, false);
    }
}
