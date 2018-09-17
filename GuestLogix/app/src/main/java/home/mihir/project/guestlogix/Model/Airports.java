package home.mihir.project.guestlogix.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//database table AIRPORTS

@Entity(tableName = "airports")
public class Airports {

    @PrimaryKey(autoGenerate = true)
    private int airport_id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "IATA_3")
    private String IATA_3;
    @ColumnInfo(name = "latitude")
    private String latitude;
    @ColumnInfo(name = "longitude")
    private String longitude;

    public String getName() {
        return name;
    }

    public int getAirport_id() {
        return airport_id;
    }

    public void setAirport_id(int airport_id) {
        this.airport_id = airport_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIATA_3() {
        return IATA_3;
    }

    public void setIATA_3(String IATA_3) {
        this.IATA_3 = IATA_3;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Airports(String name, String city, String country, String IATA_3, String latitude, String longitude) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.IATA_3 = IATA_3;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
