package ru.whalemare.weather.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.whalemare.weather.database.DatabaseHandler;
import ru.whalemare.weather.database.DatabaseHandlerImpl;

/**
 * @author Anton Vlasov
 *         Developed by Magora Team (magora-systems.com). 2016.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    DatabaseHandler provideDatabaseHandler(){
        return new DatabaseHandlerImpl(context);
    }

}
