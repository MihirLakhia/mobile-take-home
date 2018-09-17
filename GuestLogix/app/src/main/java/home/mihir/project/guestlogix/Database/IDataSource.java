package home.mihir.project.guestlogix.Database;
import java.util.List;

import home.mihir.project.guestlogix.Model.Airlines;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;


//interface for implementation of sql methods.

public interface IDataSource {

    List<Airports> getAirports();
    List<Airlines> getAirlines();
    List<Routes> getRoutes();
    List<Airports> getIata(String query);
    List<Routes> findRouts(String origin,String Destination);
    List<Routes> findViaRouts(String origin,String Destination);
    void insertAirports(Airports... airports);
    void insertAirlines(Airlines... Airlines);
    void insertRoutes(Routes... routes);

}
