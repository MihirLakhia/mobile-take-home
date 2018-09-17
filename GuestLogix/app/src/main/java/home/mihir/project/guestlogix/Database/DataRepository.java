package home.mihir.project.guestlogix.Database;

import java.util.List;

import home.mihir.project.guestlogix.Model.Airlines;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;
//data sources created for data faching using RXJava methods.

public class DataRepository implements IDataSource {
    private IDataSource mlocalDataSource;

    private static DataRepository mInstance;

    public DataRepository(IDataSource mlocalDataSource) {
        this.mlocalDataSource = mlocalDataSource;
    }

    public static DataRepository  getmInstance(IDataSource mlocalDataSource){
        if(mInstance == null)
        {
            mInstance = new DataRepository(mlocalDataSource);
        }
        return mInstance;
    }

    @Override
    public List<Airports> getAirports() {
        return mlocalDataSource.getAirports();
    }

    @Override
    public List<Airlines> getAirlines() {
        return mlocalDataSource.getAirlines();
    }

    @Override
    public List<Routes> getRoutes() {
        return mlocalDataSource.getRoutes();
    }

    @Override
    public List<Airports> getIata(String query) {return mlocalDataSource.getIata(query);}

    @Override
    public List<Routes> findRouts(String origin, String Destination) { return mlocalDataSource.findRouts(origin,Destination);   }

    @Override
    public List<Routes> findViaRouts(String origin, String Destination) { return mlocalDataSource.findViaRouts(origin,Destination);   }

    @Override
    public void insertAirports(Airports... airports) {
        mlocalDataSource.insertAirports(airports);
    }

    @Override
    public void insertAirlines(Airlines... airlines) {
        mlocalDataSource.insertAirlines(airlines);
    }

    @Override
    public void insertRoutes(Routes... routes) {
        mlocalDataSource.insertRoutes(routes);
    }
}
