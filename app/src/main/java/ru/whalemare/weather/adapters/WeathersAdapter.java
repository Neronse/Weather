package ru.whalemare.weather.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.whalemare.weather.Fragments.ForecastFragment;
import ru.whalemare.weather.ItemClickListener;
import ru.whalemare.weather.R;
import ru.whalemare.weather.objects.Weather;

public class WeathersAdapter extends RecyclerView.Adapter<WeathersAdapter.ViewHolder> {

    private final String TAG = "WHALETAG";
    private List<Weather> weathers;
    private ForecastFragment.OnChooseForecastListener listener;

    public WeathersAdapter(List<Weather> weathers, ForecastFragment.OnChooseForecastListener listener) {
        Log.d(TAG, "Пришло прогнозов: " + weathers.size());
        this.weathers = weathers;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tod;
        public TextView aboutWeather;
        public TextView wind;
        public TextView pressure;
        public TextView relwet;

        public TextView nowTemperature;
        public TextView minMaxTemperature;

        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            tod = (TextView) itemView.findViewById(R.id.tod);
            aboutWeather = (TextView) itemView.findViewById(R.id.aboutWeather);
            wind = (TextView) itemView.findViewById(R.id.wind);
            pressure = (TextView) itemView.findViewById(R.id.pressure);
            relwet = (TextView) itemView.findViewById(R.id.relwet);
            nowTemperature = (TextView) itemView.findViewById(R.id.now_temperature);
            minMaxTemperature = (TextView) itemView.findViewById(R.id.min_max_Temperature);

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
    public WeathersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_cardview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeathersAdapter.ViewHolder holder, int position) {



        String wind = holder.wind.getContext().getResources().getString(R.string.wind) + weathers.get(position).getWind_min() + " - " + weathers.get(position).getWind_max() + " м/с"; // 2-4 м/с
        String pressure = holder.wind.getContext().getResources().getString(R.string.pressure) + weathers.get(position).getPressure_min() + " - " + weathers.get(position).getPressure_max() + " мм"; // 776 | 780 мм
        String relwet = holder.wind.getContext().getResources().getString(R.string.relwet) + weathers.get(position).getRelwet_min() + "-" + weathers.get(position).getRelwet_max() + "%"; // 88-90%

        String nowTemperature = weathers.get(position).getTemperature_max() + "°C";
        String minMaxTemperature = weathers.get(position).getTemperature_min() + " | " + weathers.get(position).getTemperature_max(); // -20 | -27
        String tod = weathers.get(position).getHumanTod();

        String aboutWeather = weathers.get(position).getHumanAboutWeather();

        holder.aboutWeather.setText(aboutWeather);
        holder.wind.setText(wind);
        holder.pressure.setText(pressure);
        holder.relwet.setText(relwet);
        holder.nowTemperature.setText(nowTemperature); // TODO среднее арифметическое
        holder.minMaxTemperature.setText(minMaxTemperature);
        holder.tod.setText(tod);

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean IsLongClick) {
                listener.sendForecast(weathers.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }
}
