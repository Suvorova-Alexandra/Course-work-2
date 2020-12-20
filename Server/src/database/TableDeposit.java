package database;

import database.Connection.ConnectionDB;
import model.Deposit;

import java.util.ArrayList;

public class TableDeposit implements DBRequests {
    private static TableDeposit instance;
    private ConnectionDB dbConnection;
    private TableDeposit() {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized TableDeposit getInstance() {
        if (instance == null) {
            instance = new TableDeposit();
        }
        return instance;
    }


    public ArrayList<Deposit> selectAll(int id) {
        String str = "SELECT * From deposit Where idCompany='"+id+"'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Deposit> listInfo = new ArrayList<>();
        for (String[] items : result) {
            Deposit info = new Deposit();
            info.setIdDeposit(Integer.parseInt(items[0]));
            info.setNameDeposit(items[2]);
            info.setValue(Float.parseFloat(items[1]));
            info.setIdCompany(Integer.parseInt(items[3]));
            listInfo.add(info);
        }
        return listInfo;
    }


    @Override
    public Object find(Object o) {
        return null;
    }

    @Override
    public void insert(Object o) {
        Deposit obj=(Deposit)o;
        String str = "INSERT INTO  deposit (nameDeposit, value, idCompany) VALUES('" +
                obj.getNameDeposit() +
                "', '" + obj.getValue() +
                "', '" + obj.getIdCompany()+ "')";
        dbConnection.execute(str);
    }

    @Override
    public void delete(Object o) {
        int id=(int)o;
        String str = "DELETE FROM deposit WHERE idDeposit = '" + id + "'";
        dbConnection.execute(str);
    }

    @Override
    public void update(Object o) {
        Deposit obj=(Deposit)o;
        String str = "UPDATE  deposit SET value='" +
                obj.getValue() +
                "', nameDeposit='" + obj.getNameDeposit() +
                "', idCompany ='" + obj.getIdCompany() +"' where idDeposit='"+obj.getIdDeposit()+"'";
        dbConnection.execute(str);
    }

    @Override
    public Object selectAll() {
        String str = "SELECT * From deposit";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Deposit> listInfo = new ArrayList<>();
        for (String[] items : result) {
            Deposit info = new Deposit();
            info.setIdDeposit(Integer.parseInt(items[0]));
            info.setNameDeposit(items[2]);
            info.setValue(Float.parseFloat(items[1]));
            info.setIdCompany(Integer.parseInt(items[3]));
            listInfo.add(info);
        }
        return listInfo;
    }

    @Override
    public void create() {
        String str = "CREATE TABLE if not exists deposit (\n" +
                "  idDeposit int NOT NULL AUTO_INCREMENT,\n" +
                "  value float DEFAULT NULL,\n" +
                "  nameDeposit varchar(45) DEFAULT NULL,\n" +
                " idCompany int unsigned NOT NULL,\n" +
                "  PRIMARY KEY (idDeposit),\n" +
                "  KEY fk_deposit_company1_idx (idCompany),\n" +
                "  CONSTRAINT fk_deposit_company1 FOREIGN KEY (idCompany) REFERENCES company (idCompany)\n" +
                ") ;";
        dbConnection.execute(str);
    }

    @Override
    public Object select(Object type) {
        return null;
    }
}
