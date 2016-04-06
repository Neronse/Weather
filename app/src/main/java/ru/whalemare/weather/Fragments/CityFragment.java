package ru.whalemare.weather.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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

import ru.whalemare.weather.R;
import ru.whalemare.weather.adapters.CityAdapter;
import ru.whalemare.weather.models.City;
import ru.whalemare.weather.tasks.CityLoader;

public class CityFragment extends Fragment implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<City>> {

    private final String TAG = getClass().getSimpleName();

    SearchView searchView;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CityAdapter adapter;

    private List<City> cities;

    public CityFragment() {
    }

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(1, null, this).forceLoad();
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
        getActivity().invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sw, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (!cities.isEmpty()) {
            final List<City> filteredList = filter(cities, query);
            adapter.animateTo(filteredList);
            recyclerView.scrollToPosition(0);
            return true;
        } else {
            Log.d(TAG, "onQueryTextChange: cities is empty");
            return false;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.setQuery("", false);
        searchView.setIconified(true);
        searchView.clearFocus();
        return true;
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

    @Override
    public Loader<List<City>> onCreateLoader(int id, Bundle args) {
        return new CityLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<City>> loader, List<City> data) {
        if (!data.isEmpty()) {
            this.cities = data;
            adapter = new CityAdapter(data);
            recyclerView.setAdapter(adapter);
        } else {
            Log.d(TAG, "onLoadFinished: cities is empty");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<City>> loader) {
        Log.d(TAG, "onLoaderReset");
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.setData(cities);
        adapter.notifyDataSetChanged();
    }
}
