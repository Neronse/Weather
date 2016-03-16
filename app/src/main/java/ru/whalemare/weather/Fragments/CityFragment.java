package ru.whalemare.weather.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.whalemare.weather.R;

public class CityFragment extends Fragment {

    public CityFragment() {
        // Required empty public constructor
    }

    public static CityFragment newInstance(String param1) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
