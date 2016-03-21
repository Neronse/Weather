package ru.whalemare.weather.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.whalemare.weather.CitiesCallback;
import ru.whalemare.weather.R;
import ru.whalemare.weather.adapters.CityAdapter;
import ru.whalemare.weather.objects.City;
import ru.whalemare.weather.tasks.CityTask;

public class CityFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String TAG = "WHALETAG";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private CitiesCallback citiesCallback = new CitiesCallback() {
        @Override
        public void onCitiesRetrieved(List<City> cities) {
            adapter = new CityAdapter(cities);
            recyclerView.setAdapter(adapter);
        }
    };

    public CityFragment() {}

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
        View view = inflater.inflate(R.layout.fragment_city, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_city);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_city);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        CityTask cityTask = new CityTask(citiesCallback, getContext());
        cityTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sw, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        // filter logic
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
