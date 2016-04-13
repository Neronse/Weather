package ru.whalemare.weather.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import ru.whalemare.weather.interfaces.ItemClickListener;
import ru.whalemare.weather.models.City;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
public class CityCursorAdapter extends CursorRecyclerViewAdapter<CityCursorAdapter.ViewHolder> {

    private final String TAG = getClass().getSimpleName();
    Cursor cursor;

    public CityCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.cursor = cursor;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        City city = getCityFromCursor(cursor);

        final String KEY_GISMETEO = viewHolder.name.getContext().getResources().getString(R.string.KEY_GISMETEO);
        final String KEY_CITYNAME = viewHolder.name.getContext().getResources().getString(R.string.KEY_CITYNAME);

        viewHolder.name.setText(city.getCityName());

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean IsLongClick) {
                Intent intent = new Intent(view.getContext(), ForecastActivity.class)
                        .putExtra(KEY_GISMETEO, city.getGismeteoCode())
                        .putExtra(KEY_CITYNAME, city.getCityName());
                view.getContext().startActivity(intent);
            }
        });
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

    final String KEY_GISMETEO_CODE = "gismeteo_code";
    final String KEY_CITY_NAME = "city_name";
    private City getCityFromCursor(Cursor cursor) {

        final int gismeteoCodeIndex = cursor.getColumnIndex(KEY_GISMETEO_CODE);
        final int cityNameIndex = cursor.getColumnIndex(KEY_CITY_NAME);

        if (cityNameIndex != -1 && gismeteoCodeIndex != -1) {
//            Log.d(TAG, "getCityFromCursor: city_name = " + cursor.getString(cityNameIndex));
//            Log.d(TAG, "getCityFromCursor: gismeteo_code = " + cursor.getString(gismeteoCodeIndex));
            return new City(cursor.getString(cityNameIndex), cursor.getString(gismeteoCodeIndex));
        } else {
            Log.w(TAG, "getCityFromCursor: WARNING! cursor have is " + cursor.getColumnCount() + " column.");
            throw new IllegalArgumentException("Item of the city not found name and gismeteo_code columns");
        }
    }

}
