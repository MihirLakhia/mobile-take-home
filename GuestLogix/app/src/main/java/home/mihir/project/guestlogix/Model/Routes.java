package home.mihir.project.guestlogix.Model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

//database table ROUTES

@Entity(tableName = "routes")
public class Routes {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ForeignKey(entity = Airlines.class,
            parentColumns = "digit_code2",
            childColumns = "Airline_id")

    @ColumnInfo(name = "Airline_id")
    private String Airline_id;

    @ColumnInfo(name = "origine")
    private String origine;

    @ColumnInfo(name = "destination")
    private String destination;

    public Routes(String airline_id, String origine, String destination) {
        Airline_id = airline_id;
        this.origine = origine;
        this.destination = destination;
    }
    public Routes(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirline_id() {
        return Airline_id;
    }

    public void setAirline_id(String airline_id) {
        Airline_id = airline_id;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
