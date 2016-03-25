package ru.whalemare.weather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.whalemare.weather.R;
import ru.whalemare.weather.fragments.ForecastFragment;
import ru.whalemare.weather.interfaces.ItemClickListener;
import ru.whalemare.weather.models.Weather;

public class WeathersAdapter extends RecyclerView.Adapter<WeathersAdapter.ViewHolder> {

    private List<Weather> weathers;
    private ForecastFragment.OnChooseForecastListener listener;

    public WeathersAdapter(List<Weather> weathers, ForecastFragment.OnChooseForecastListener listener) {
        this.weathers = weathers;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tod;
        public TextView nowTemperature;

        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            tod = (TextView) itemView.findViewById(R.id.tod);
            nowTemperature = (TextView) itemView.findViewById(R.id.now_temperature);

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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeathersAdapter.ViewHolder holder, int position) {

        String tod = weathers.get(position).getHumanTod();
        String nowTemperature = weathers.get(position).getTemperature_max() + holder.tod.getContext().getString(R.string.celcium);

        holder.tod.setText(tod);
        holder.nowTemperature.setText(nowTemperature); // TODO среднее арифметическое

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
