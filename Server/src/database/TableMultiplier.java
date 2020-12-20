package database;

import database.Connection.ConnectionDB;
import model.Multiplier;

import java.util.ArrayList;

public class TableMultiplier implements DBRequests {
    private static TableMultiplier instance;
    private ConnectionDB dbConnection;
    private TableMultiplier() {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized TableMultiplier getInstance() {
        if (instance == null) {
            instance = new TableMultiplier();
        }
        return instance;
    }




    @Override
    public Object find(Object o) {
        return null;
    }

    @Override
    public void insert(Object o) {
        Multiplier obj=(Multiplier)o;
        String str = "INSERT INTO  multiplier (value, nameMult, idCompany) VALUES('" +
                obj.getValue() +
                "', '" + obj.getNameMult() +
                "', '" + obj.getIdCompany() +"')";
        dbConnection.execute(str);
    }

    @Override
    public void delete(Object o) {
        int id=(int)o;
        String str = "DELETE FROM multiplier WHERE idMult = '" + id + "'";
        dbConnection.execute(str);
    }

    @Override
    public void update(Object o) {
        Multiplier obj=(Multiplier)o;
        String str = "UPDATE multiplier SET value='" +
                obj.getValue() +
                "', nameMult ='" + obj.getNameMult() +
                "', idCompany ='" + obj.getIdCompany() +"' where idMult ='"+obj.getIdMult()+"'";
        dbConnection.execute(str);
    }

    @Override
    public ArrayList<Multiplier> selectAll() {
        String str = "SELECT * From multiplier";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Multiplier> listInfo = new ArrayList<>();
        for (String[] items : result) {
            Multiplier info = new Multiplier();
            info.setIdCompany(Integer.parseInt(items[3]));
            info.setIdMult(Integer.parseInt(items[0]));
            info.setValue(Float.parseFloat(items[1]));
            info.setNameMult(items[2]);
            listInfo.add(info);
        }
        return listInfo;
    }

    @Override
    public void create() {
        String str = "CREATE TABLE if not exists multiplier (\n" +
                "  idMult int NOT NULL AUTO_INCREMENT,\n" +
                "  value float DEFAULT NULL,\n" +
                "  nameMult varchar(45) DEFAULT NULL,\n" +
                "  idCompany int unsigned NOT NULL,\n" +
                "  PRIMARY KEY (idMult),\n" +
                "  KEY fk_multiplier_company1_idx (idCompany),\n" +
                "  CONSTRAINT fk_multiplier_company1 FOREIGN KEY (idCompany) REFERENCES company (idCompany)\n" +
                ") ;";
        dbConnection.execute(str);
    }

    @Override
    public Object select(Object type) {
        return null;
    }



}
