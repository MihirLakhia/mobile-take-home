package home.mihir.project.guestlogix.Database;

import java.util.List;

import home.mihir.project.guestlogix.Local.DatabaseDAO;
import home.mihir.project.guestlogix.Model.Airlines;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;

public class DataSource implements IDataSource {

    private DatabaseDAO databaseDAO;
    private static DataSource mInstance;

    public DataSource(DatabaseDAO databaseDAO) {
        this.databaseDAO = databaseDAO;
    }

    public static  DataSource getInstance(DatabaseDAO databaseDAO){

        if(mInstance == null)
        {
            mInstance = new DataSource(databaseDAO);
        }

        return mInstance;
    }


    @Override
    public List<Airports> getAirports() { return databaseDAO.getAirport(); }

    @Override
    public List<Airlines> getAirlines() {
        return databaseDAO.getAirlines();
    }

    @Override
    public List<Routes> getRoutes() { return databaseDAO.getroutes(); }

    @Override
    public List<Airports> getIata(String query) {return databaseDAO.getIata(query); }

    @Override
    public List<Routes> findRouts(String origin, String Destination) { return databaseDAO.findRouts(origin,Destination);   }

    @Override
    public List<Routes> findViaRouts(String origin, String Destination) { return databaseDAO.findViaRouts(origin,Destination);   }


    @Override
    public void insertAirports(Airports... airports) {
        databaseDAO.insertAirports(airports);
    }

    @Override
    public void insertAirlines(Airlines... Airlines) {
        databaseDAO.insertAirlines(Airlines);
    }

    @Override
    public void insertRoutes(Routes... routes) {
        databaseDAO.insertRoutes(routes);
    }


}
