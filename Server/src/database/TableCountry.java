package database;

import database.Connection.ConnectionDB;
import model.Country;

import java.util.ArrayList;

public class TableCountry implements DBRequests {
    private static TableCountry instance;
    private ConnectionDB dbConnection;

    private TableCountry() {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized TableCountry getInstance() {
        if (instance == null) {
            instance = new TableCountry();
        }
        return instance;
    }


    @Override
    public Object find(Object o) {
        return null;
    }

    @Override
    public void insert(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public ArrayList<Country> selectAll() {
        String str = "SELECT * From country";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Country> listCountries = new ArrayList<>();
        for (String[] items : result) {
            Country country = new Country();
            country.setIdCountry(Integer.parseInt(items[0]));
            country.setNameCountry(items[1]);
            country.setCodeCountry(items[2]);
            listCountries.add(country);
        }
        return listCountries;
    }

    @Override
    public void create() {
        String str =                "CREATE TABLE if not exists country (\n" +
                "  idCountry int NOT null,\n" +
                "  nameCountry varchar(45) DEFAULT NULL,\n" +
                "  PRIMARY KEY (idCountry)\n" +
                ");";
        dbConnection.execute(str);
    }

    @Override
    public Object select(Object type) {
        return null;
    }

}
