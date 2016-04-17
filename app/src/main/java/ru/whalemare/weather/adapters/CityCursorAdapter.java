package ru.whalemare.weather.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.whalemare.weather.R;
import ru.whalemare.weather.activity.ForecastActivity;
import ru.whalemare.weather.database.CitiesProvider;
import ru.whalemare.weather.interfaces.ItemClickListener;
import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityCursorAdapter extends CursorRecyclerViewAdapter<CityCursorAdapter.ViewHolder> {

    private final String TAG = getClass().getSimpleName();
    Cursor cursor;
    Context context;

    public CityCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.cursor = cursor;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        City city = getCityFromCursor(cursor);

        viewHolder.name.setText(city.getCityName());

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean IsLongClick) {
                if (checkInternet()) {
                    Intent intent = new Intent(view.getContext(), ForecastActivity.class)
                            .putExtra(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE, city.getGismeteoCode())
                            .putExtra(CitiesProvider.CitiesMetaData.KEY_CITY_NAME, city.getCityName());
                    view.getContext().startActivity(intent);
                } else {
                    Snackbar snackbar = Snackbar.make(view, "Подключитесь к интернету", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK", v -> {
                                view.getContext().startActivity(new Intent(Settings.ACTION_SETTINGS));
                            });
                    snackbar.show();
                }
            }
        });
    }

    private boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo internetInfo = cm.getActiveNetworkInfo();
        return !(internetInfo == null || !internetInfo.isConnectedOrConnecting());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.itemCity_name)
        public TextView name;

        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

    private City getCityFromCursor(Cursor cursor) {

        final int gismeteoCodeIndex = cursor.getColumnIndex(CitiesProvider.CitiesMetaData.KEY_GISMETEO_CODE);
        final int cityNameIndex = cursor.getColumnIndex(CitiesProvider.CitiesMetaData.KEY_CITY_NAME);

        if (cityNameIndex != -1 && gismeteoCodeIndex != -1) {
            return new City(cursor.getString(cityNameIndex), cursor.getString(gismeteoCodeIndex));
        } else {
            Log.w(TAG, "getCityFromCursor: WARNING! cursor have is " + cursor.getColumnCount() + " column.");
            throw new IllegalArgumentException("Item of the city not found name and gismeteoCode columns");
        }
    }

}
