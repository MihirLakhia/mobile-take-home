package home.mihir.project.guestlogix.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import home.mihir.project.guestlogix.Model.Airlines;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;

import static home.mihir.project.guestlogix.Local.AirlinesDatabase.DATABASE_VERSION;

@Database(entities = {Airlines.class, Airports.class, Routes.class}, version =DATABASE_VERSION)
public abstract class AirlinesDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="AirlinesDatabase";

    public abstract DatabaseDAO databaseDAO();

    private static AirlinesDatabase mInstance;

    public static AirlinesDatabase getmInstance(Context context) {
        if (mInstance == null) {
            mInstance =
                    Room.databaseBuilder(context.getApplicationContext(), AirlinesDatabase.class, "user-database")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return mInstance;
    }

}

