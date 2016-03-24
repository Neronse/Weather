package ru.whalemare.weather.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.DatabaseHandlerImpl;
import ru.whalemare.weather.R;
import ru.whalemare.weather.adapters.CityAdapter;
import ru.whalemare.weather.interfaces.CitiesCallback;
import ru.whalemare.weather.objects.City;
import ru.whalemare.weather.tasks.CityTask;

public class CityFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String TAG = "WHALETAG";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CityAdapter adapter;

    private List<City> cities;

    private CitiesCallback citiesCallback = new CitiesCallback() {
        @Override
        public void onCitiesRetrieved(List<City> cities) {
            setCities(cities);
            adapter = new CityAdapter(cities);
            recyclerView.setAdapter(adapter);
        }
    };

    void setCities(List<City> cities) {
        this.cities = new ArrayList<>(cities);
    }

    public CityFragment() {}

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_city, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_city);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
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
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<City> filteredList = filter(cities, query);
        adapter.animateTo(filteredList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private boolean show = true; // flag for showing message
    private List<City> filter(List<City> cities, String query) {
        query = query.toLowerCase();
        final List<City> filteredList = new ArrayList<>();
        for (City city : cities) {
            final String text = city.getCityName().toLowerCase();
            if (text.contains(query)) {
                filteredList.add(city);
            }
        }

        if (filteredList.size() <= 0) {
            if (show) {
                Toast.makeText(getContext(), "Города по запросу не найдено", Toast.LENGTH_SHORT).show();
                show = false;
            }
        } else {
            show = true;
        }

        return filteredList;
    }

    void loadCitiesIntoDatabase(List<City> cities){
        final DatabaseHandlerImpl database = new DatabaseHandlerImpl(getContext());
        if (cities.size() > 0)
            database.setAllData(cities);
        else
            Log.d(TAG, "cities.size() = " + cities.size());
    }



    List<City> getCitiesFromDatabase(){
        return new DatabaseHandlerImpl(getContext()).getAllData();
    }

    boolean checkTableInDatabase(){
        return new DatabaseHandlerImpl(getContext()).checkTable();
    }
}
