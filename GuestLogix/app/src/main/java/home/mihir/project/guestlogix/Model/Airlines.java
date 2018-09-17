package home.mihir.project.guestlogix.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

//database table AIRLINES


@Entity(tableName = "airlines")
public class Airlines {

    @ColumnInfo(name = "name")
    private String name;

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "digit_code2")
    private String digit_code2;

    @ColumnInfo(name = "digit_code3")
    private String digit_code3;

    @ColumnInfo(name = "country")
    private String country;

    public Airlines(String name, String digit_code2, String digit_code3, String country) {
        this.name = name;
        this.digit_code2 = digit_code2;
        this.digit_code3 = digit_code3;
        this.country = country;
    }

    public Airlines(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDigit_code2() {
        return digit_code2;
    }

    public void setDigit_code2(String digit_code2) {
        this.digit_code2 = digit_code2;
    }

    public String getDigit_code3() {
        return digit_code3;
    }

    public void setDigit_code3(String digit_code3) {
        this.digit_code3 = digit_code3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
