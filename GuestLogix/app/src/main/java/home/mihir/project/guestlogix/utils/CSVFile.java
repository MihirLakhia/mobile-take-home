package home.mihir.project.guestlogix.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import home.mihir.project.guestlogix.Model.Airlines;
import home.mihir.project.guestlogix.Model.Airports;
import home.mihir.project.guestlogix.Model.Routes;


/*
* CSV file reader utilies
* */
public class CSVFile {
    InputStream inputStream;

    public static final int AIRPORTS = 1;
    public static final int AIRLINES = 2;
    public static final int ROUTES = 3;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    /*
     * CSV file reader @TableType is for table name
     * */
    public List read(int TableType){

        List resultList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            Airports airports;
            Airlines airlines;
            Routes routes;

            switch(TableType){
                case  AIRPORTS :
                resultList = new ArrayList<Airports>();
                while ((csvLine = reader.readLine()) != null) {
                    String[] row = csvLine.split(",");
                    airports = new Airports(row[0],row[1],row[2],row[3],row[4],row[5]);
                    resultList.add(airports);
                    Log.d("AIRPORTS DATA", row[0].toString());
                }
                break;

                case  AIRLINES :
                resultList = new ArrayList<Airlines>();
                while ((csvLine = reader.readLine()) != null) {
                    String[] row = csvLine.split(",");
                    airlines = new Airlines(row[0],row[1],row[2],row[3]);
                    resultList.add(airlines);
                    Log.d("AIRLINES DATA", row[0].toString());
                }
                break;

                case  ROUTES :
                resultList = new ArrayList<Routes>();
                while ((csvLine = reader.readLine()) != null) {
                    String[] row = csvLine.split(",");
                    routes = new Routes(row[0],row[1],row[2]);
                    resultList.add(routes);
                    Log.d("ROUTES DATA", row[0].toString());
                }
                break;
             }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }
}
