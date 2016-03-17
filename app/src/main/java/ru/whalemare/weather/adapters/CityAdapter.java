package ru.whalemare.weather.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.whalemare.weather.Activity.MainActivity;
import ru.whalemare.weather.ItemClickListener;
import ru.whalemare.weather.R;
import ru.whalemare.weather.objects.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private static final String TAG = "WHALETAG";
    private List<City> cities = new ArrayList<>();
    Context context;

    public CityAdapter(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
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

        holder.setClickListener(new ItemClickListener() {
            public static final String KEY_GISMETEO = "KEY_GISMETEO";

            @Override
            public void OnClick(View view, int position, boolean IsLongClick) {
                Toast.makeText(view.getContext(), "Выбран город " + cities.get(position).getCityName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra(KEY_GISMETEO, cities.get(position).getGismeteoCode());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
