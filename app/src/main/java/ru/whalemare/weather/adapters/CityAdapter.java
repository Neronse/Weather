package ru.whalemare.weather.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.Activity.ForecastActivity;
import ru.whalemare.weather.ItemClickListener;
import ru.whalemare.weather.R;
import ru.whalemare.weather.objects.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private static final String TAG = "WHALETAG";
    private List<City> cities;

    public CityAdapter(List<City> cities) {
        this.cities = new ArrayList<>(cities);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;

        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.itemCity_name);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.OnClick(view, getPosition(), false);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(cities.get(position).getCityName());

        final String KEY_GISMETEO = holder.name.getContext().getResources().getString(R.string.KEY_GISMETEO);
        holder.setClickListener(new ItemClickListener() {

            @Override
            public void OnClick(View view, int position, boolean IsLongClick) {
                Toast.makeText(view.getContext(), "Выбран город " + cities.get(position).getCityName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), ForecastActivity.class)
                    .putExtra(KEY_GISMETEO, cities.get(position).getGismeteoCode());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void animateTo(List<City> cities) {
        applyAndAnimateRemovals(cities);
        applyAndAnimateAdditions(cities);
        applyAndAnimateMoveditems(cities);
    }

    private void applyAndAnimateAdditions(List<City> newCities) {
        for (int i = 0, count = newCities.size(); i<count; i++) {
            final City city = newCities.get(i);
            if (!cities.contains(city)) {
                addItem(i, city);
            }
        }
        Log.d(TAG, "applyAndAnimateAdditions: " + cities.size());
    }

    private void applyAndAnimateMoveditems(List<City> newCities) {
        for (int toPosition = newCities.size() - 1; toPosition >= 0; toPosition--) {
            final City city = newCities.get(toPosition);
            final int fromPosition = cities.indexOf(city);

            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
        Log.d(TAG, "applyAndAnimateMoveditems: " + cities.size());
    }

    private void applyAndAnimateRemovals(List<City> newCities) {
        for (int i = cities.size() - 1; i >= 0; i--) {
            final City city = cities.get(i);
            if (!newCities.contains(city)) {
                removeItem(i);
            }
        }
        Log.d(TAG, "applyAndAnimateRemovals: " + cities.size());
    }

    public void addItem(int position, City city) {
        cities.add(position, city);
        notifyItemInserted(position);
    }

    private void moveItem(int from, int to) {
        final City city = cities.remove(from);
        cities.add(to, city);
        notifyItemMoved(from, to);
    }

    public City removeItem(int position) {
        final City city = cities.remove(position);
        notifyItemRemoved(position);
        return city;
    }

}
