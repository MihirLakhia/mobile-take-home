package home.mihir.project.guestlogix.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.InputStream;
import java.util.List;

import home.mihir.project.guestlogix.Local.AirlinesDatabase;
import home.mihir.project.guestlogix.Model.Airlines;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;
import home.mihir.project.guestlogix.R;
import home.mihir.project.guestlogix.utils.CSVFile;

public class DatabaseCreator extends IntentService{

    public static final int AIRPORTS = 1;
    public static final int AIRLINES = 2;
    public static final int ROUTES = 3;
    public static final String TAG = "Database Services";

    AirlinesDatabase database;

    public DatabaseCreator() {
        super("Service");
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG,"Service Started");

        database = AirlinesDatabase.getmInstance(getApplicationContext());
        List<Airports> list= database.databaseDAO().getAirport();
        if(list.isEmpty())
            new ReadData().execute();
    }



    public class ReadData extends AsyncTask<String,Void, String>{

            @Override
            protected String doInBackground(String... strings) {
                fill_Airports();
                fill_Airlines();
                fill_Routes();
                return null;
            }
        }

        public void fill_Airports(){
            InputStream inputStream = getResources().openRawResource(R.raw.airports);
            CSVFile csvFile = new CSVFile(inputStream);
            List myList = csvFile.read(AIRPORTS);
            for(int i =0;i<myList.size();i++)
            {
                Airports airports = (Airports) myList.get(i);
                database.databaseDAO().insertAirports(airports);
                Log.d("Airport Data", airports.getName());
            }

        }
        public void fill_Airlines(){
            InputStream inputStream = getResources().openRawResource(R.raw.airlines);
            CSVFile csvFile = new CSVFile(inputStream);
            List myList = csvFile.read(AIRLINES);
            for(int i =0;i<myList.size();i++)
            {
                Airlines airlines = (Airlines) myList.get(i);
                database.databaseDAO().insertAirlines(airlines);
                Log.d("Airlines Data", airlines.getName());
            }
        }
        public void fill_Routes(){
            InputStream inputStream = getResources().openRawResource(R.raw.routes);
            CSVFile csvFile = new CSVFile(inputStream);
            List myList = csvFile.read(ROUTES);
            for(int i =0;i<myList.size();i++)
            {
                Routes routes = (Routes) myList.get(i);
                database.databaseDAO().insertRoutes(routes);
                Log.d("Routes Data", routes.getAirline_id());
            }
        }
}
