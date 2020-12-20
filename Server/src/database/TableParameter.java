package database;

import database.Connection.ConnectionDB;
import model.FinParameter;

import java.util.ArrayList;

public class TableParameter implements DBRequests {
    private static TableParameter instance;
    private ConnectionDB dbConnection;
    private TableParameter() {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized TableParameter getInstance() {
        if (instance == null) {
            instance = new TableParameter();
        }
        return instance;
    }

    @Override
    public String find(Object o) {
        return null;
    }

    @Override
    public void insert(Object o) {
        FinParameter obj=(FinParameter)o;
        String str = "INSERT INTO  finparameter (value, nameParameter, idCompany) VALUES('" +
                obj.getValue() +
                "', '" + obj.getNameParameter() +
                "', '" + obj.getIdCompany() +"')";
        dbConnection.execute(str);
    }

    @Override
    public void delete(Object o) {
        int id=(int)o;
        String str = "DELETE FROM finparameter WHERE idParameter = '" + id + "'";
        dbConnection.execute(str);
    }

    @Override
    public void update(Object o) {
        FinParameter obj=(FinParameter)o;
        String str = "UPDATE  finparameter SET value='" +
                obj.getValue() +
                "', nameParameter='" + obj.getNameParameter() +
                "', idCompany ='" + obj.getIdCompany() +"' where idParameter='"+obj.getIdParameter()+"'";
        dbConnection.execute(str);
    }

    @Override
    public ArrayList<FinParameter> selectAll() {
        String str = "SELECT * From finparameter";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<FinParameter> listInfo = new ArrayList<>();
        for (String[] items : result) {
            FinParameter info = new FinParameter();
            info.setIdCompany(Integer.parseInt(items[3]));
            info.setIdParameter(Integer.parseInt(items[0]));
            info.setNameParameter(items[2]);
            info.setValue(Float.parseFloat(items[1]));
            listInfo.add(info);
        }
        return listInfo;
    }

    @Override
    public void create() {
        String str = "CREATE TABLE if not exists finparameter (\n" +
                "  idParameter int NOT NULL AUTO_INCREMENT,\n" +
                " value float DEFAULT NULL,\n" +
                "  nameParameter varchar(45) DEFAULT NULL,\n" +
                "  idCompany int unsigned NOT NULL,\n" +
                "  PRIMARY KEY (idParameter),\n" +
                "  KEY fk_finparameter_company1_idx (idCompany),\n" +
                "  CONSTRAINT fk_finparameter_company1 FOREIGN KEY (idCompany) REFERENCES company (idCompany)\n" +
                ") ;";
        dbConnection.execute(str);
    }

    @Override
    public Object select(Object type) {
        return null;
    }



}
