package home.mihir.project.guestlogix.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import home.mihir.project.guestlogix.Model.Airlines;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;

@Dao
public interface DatabaseDAO {
    @Query("Select * from airports")
    List<Airports> getAirport();

    @Query("Select * from airlines")
    List<Airlines> getAirlines();

    @Query("Select * from routes")
    List<Routes> getroutes();

    @Query("Select * from airports where IATA_3 like :query limit 5")
    List<Airports> getIata(String query);

    @Query("Select * from routes where origine like :origin AND destination like :Destination")
    List<Routes> findRouts(String origin,String Destination);

    @Query("Select * from routes where origine like :origin AND destination in( select origine from routes where destination like :Destination) ")
    List<Routes> findViaRouts(String origin,String Destination);

    @Insert
    void insertAirports(Airports... airports);
    @Insert
    void insertAirlines(Airlines... Airlines);
    @Insert
    void insertRoutes(Routes... routes);

}
