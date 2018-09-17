package home.mihir.project.guestlogix;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import home.mihir.project.guestlogix.Local.AirlinesDatabase;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;
import home.mihir.project.guestlogix.Service.DatabaseCreator;

import static home.mihir.project.guestlogix.R.id;
import static home.mihir.project.guestlogix.R.layout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName().toString();
    private static final int ORIGIN = 1;
    private static final int DESTINATION = 2;


    @BindView(id.txt_origin)
    EditText txt_origin;
    @BindView(id.txt_destination)
    EditText txt_destination;
    GoogleMap map;
    AirlinesDatabase db;
    List<String> data;

    Airports Origin,Destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        db = AirlinesDatabase.getmInstance(getApplicationContext());

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
            }
        });


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                        sleep(1000);
                        checkdata();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

//        thread.start();
        txt_origin.addTextChangedListener(tc1);
        txt_destination.addTextChangedListener(tc);


    }
    TextWatcher tc = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>=3)
                new AutoFill(txt_destination.getText().toString(),Integer.valueOf(txt_destination.getTag().toString())).execute();
        }
    };
    TextWatcher tc1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>=3)
                new AutoFill(txt_origin.getText().toString(),Integer.valueOf(txt_origin.getTag().toString())).execute();
        }
    };




    public class AutoFill extends AsyncTask<String,String,List<Airports>> {
        String query;
        int error;
        List<Airports> data;
        int type;

        public AutoFill(String query, Integer type) {
            this.query = query;
            this.type = type;
        }

        @Override
        protected List<Airports> doInBackground(String... strings) {

            data = db.databaseDAO().getIata(query);
            if(type == ORIGIN)
               if(data.isEmpty())
                error = ORIGIN;
               else
                   Origin = data.get(0);
            else if(type == DESTINATION)
                if(data.isEmpty())
                    error = DESTINATION;
                else
                    Destination = data.get(0);
            return data;
        }

        @Override
        protected void onPostExecute(List<Airports> airportsdata) {
            super.onPostExecute(airportsdata);

            if(error == ORIGIN)
            { txt_origin.setError("Origin is invalid");
                Toast.makeText(getApplicationContext(),"Data is invalid!",Toast.LENGTH_LONG).show();
            }else if (error == DESTINATION) {
                txt_destination.setError("Destination is invalid");
                Toast.makeText(getApplicationContext(), "Data is invalid!", Toast.LENGTH_LONG).show();
            }
            else{
                for(Airports data : airportsdata){
                    Log.d("Airport Data--------", data.getName());
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng l = new LatLng(Double.valueOf(data.getLatitude()),Double.valueOf(data.getLongitude()));
                    // Setting the position for the marker
                    markerOptions.position(l);
                    markerOptions.title(data.getName());
                    markerOptions.snippet(data.getCity()+", "+data.getCountry());
                    map.animateCamera(CameraUpdateFactory.newLatLng(l));
                    map.addMarker(markerOptions);
                }
            }


        }
    }
    public class FindRoutes extends AsyncTask<String,String,List<Routes>> {
        String origin;
        String destination;
        List<Routes> data;
        ProgressDialog dialogue;
        ArrayList<LatLng> points;
        List<Airports> midPoint;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogue = new ProgressDialog(MainActivity.this);
            dialogue.setTitle("Finding routes..");
            dialogue.show();
            points = new ArrayList<LatLng>();
            midPoint = new ArrayList<Airports>();
        }

        public FindRoutes(String origin, String destination) {
            this.origin = origin;
            this.destination = destination;
        }

        @Override
        protected List<Routes> doInBackground(String... strings) {

            data = db.databaseDAO().findRouts(origin,destination);
            if(data.isEmpty()) {
                data = db.databaseDAO().findViaRouts(origin, destination);
                if(!data.isEmpty()) {
                    midPoint = db.databaseDAO().getIata(data.get(0).getDestination().toString());

                }
            }return data;
        }

        @Override
        protected void onPostExecute(List<Routes> routes) {
            super.onPostExecute(routes);

            if(data.isEmpty())
            {    Toast.makeText(getApplicationContext(),"Sorry! No Route Found.",Toast.LENGTH_LONG).show();
                map.clear();
            }
            else {
                Routes data = routes.get(0);
                Toast.makeText(getApplicationContext(), "Congratulation", Toast.LENGTH_LONG).show();
                Log.d("HERE Data--------", data.getOrigine() + " \n " + data.getDestination());

                PolylineOptions polyLineOptions = new PolylineOptions();
                points.add(new LatLng(Double.valueOf(Origin.getLatitude()), Double.valueOf(Origin.getLongitude())));
                points.add(new LatLng(Double.valueOf(Destination.getLatitude()), Double.valueOf(Destination.getLongitude())));
                if (!midPoint.isEmpty()) {
                    points.add(new LatLng(Double.valueOf(midPoint.get(0).getLatitude()), Double.valueOf(midPoint.get(0).getLongitude())));

                    if (checkDistance(points)) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng l = new LatLng(Double.valueOf(midPoint.get(0).getLatitude()), Double.valueOf(midPoint.get(0).getLongitude()));
                        // Setting the position for the marker
                        markerOptions.position(l);
                        markerOptions.title(midPoint.get(0).getName());
                        markerOptions.snippet(midPoint.get(0).getCity() + ", " + midPoint.get(0).getCountry());
                        map.animateCamera(CameraUpdateFactory.newLatLng(l));
                        map.addMarker(markerOptions);
                    } else
                        points.remove(2);
                }
                polyLineOptions.width(2 * 4);
                polyLineOptions.geodesic(true);
                polyLineOptions.color(getResources().getColor(R.color.colorPrimaryDark));
                polyLineOptions.addAll(points);
                Polyline polyline = map.addPolyline(polyLineOptions);
                polyline.setGeodesic(true);
                polyline.setJointType(0);

            }
        dialogue.dismiss();
        }

    }

    public void findRoutes(View v)
    {
        String origin = txt_origin.getText().toString();
        String destination = txt_destination.getText().toString();
        if(origin.equals(destination))
            Toast.makeText(getApplicationContext(),"Origin and Destination both are same!",Toast.LENGTH_LONG).show();
        else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            new FindRoutes(origin, destination).execute();
        }
    }
    public boolean checkDistance(List<LatLng> points) {

        Location origin = new Location("");
        origin.setLatitude(points.get(0).latitude);
        origin.setLatitude(points.get(0).longitude);
        Location middlePoint= new Location("");
        middlePoint.setLatitude(points.get(2).latitude);
        middlePoint.setLatitude(points.get(2).longitude);
        Location destination= new Location("");
        destination.setLatitude(points.get(1).latitude);
        destination.setLatitude(points.get(1).longitude);
        float mainDistance = origin.distanceTo(destination);
        float middleDistance = origin.distanceTo(middlePoint);
        if(mainDistance > middleDistance)
            return true;
        else
        return false;
    }
    public void checkdata(){


//        List<Airports>  airportsdata = db.databaseDAO().getAirport();
//        for(Airports data : airportsdata){
//           Log.d("Airports Data", data.getName());
//        }
//        List<Airlines>  airlinesdata = db.databaseDAO().getAirlines();
//        for(Airlines data : airlinesdata){
//           Log.d("Airline Data", data.getName());
//        }
//        List<Routes>  routesdata = db.databaseDAO().getroutes();
//        for(Routes data : routesdata){
//           Log.d("Routes Data", data.getAirline_id());
//        }

    }
}
