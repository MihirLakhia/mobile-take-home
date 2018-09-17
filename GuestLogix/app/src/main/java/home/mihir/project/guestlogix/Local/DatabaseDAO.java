package home.mihir.project.guestlogix.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import home.mihir.project.guestlogix.Model.Airlines;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;

// DAO file for sql queries and data fatching mathods
@Dao
public interface DatabaseDAO {
    @Query("Select * from airports")
    List<Airports> getAirport();

    @Query("Select * from airlines")
    List<Airlines> getAirlines();

    @Query("Select * from routes")
    List<Routes> getroutes();

    //searching airports data
    @Query("Select * from airports where IATA_3 like :query limit 5")
    List<Airports> getIata(String query);

    //finding routes for 2 points
    @Query("Select * from routes where origine like :origin AND destination like :Destination")
    List<Routes> findRouts(String origin,String Destination);

    //finding via points routes
    @Query("Select * from routes where origine like :origin AND destination in( select origine from routes where destination like :Destination) limit 1 ")
    List<Routes> findViaRouts(String origin,String Destination);


    //insertion methods for databse creation
    @Insert
    void insertAirports(Airports... airports);
    @Insert
    void insertAirlines(Airlines... Airlines);
    @Insert
    void insertRoutes(Routes... routes);

}
