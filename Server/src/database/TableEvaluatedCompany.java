package database;

import database.Connection.ConnectionDB;
import model.Company;
import model.EvaluatedCompany;

import java.util.ArrayList;

public class TableEvaluatedCompany implements DBRequests {

    private static TableEvaluatedCompany instance;
    private ConnectionDB dbConnection;
    private TableEvaluatedCompany() {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized TableEvaluatedCompany getInstance() {
        if (instance == null) {
            instance = new TableEvaluatedCompany();
        }
        return instance;
    }


    public String findEditedCompany(Company obj, String oldName) {
        String str = "SELECT * FROM company where nameCompany = '"+ obj.getNameCompany()+"' AND nameCompany != '"+oldName+"'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String status = "";
        for (String[] item : result)
            status = item[0];
        return status;
    }

    public void updateOwnerCompany(EvaluatedCompany obj,String oldName) {
        String str = "UPDATE company SET nameCompany='" +
                obj.getNameCompany() +
                "', sizeCompany = '" + obj.getSizeCompany() +
                "', industryCompany ='" + obj.getIndustryCompany()+
                "', idCountry= '" + obj.getIdCountry() +
                "',typeCompany='" + obj.getTypeCompany() +"' where nameCompany='"+oldName+"'";
        dbConnection.execute(str);
    }


    @Override
    public Object find(Object o) {
        Company obj=(Company)o;
        String str = "SELECT * FROM company where nameCompany = '"+ obj.getNameCompany()+"'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String status = "";
        for (String[] item : result)
            status = item[0];
        return status;
    }

    @Override
    public void insert(Object o) {
        EvaluatedCompany obj=(EvaluatedCompany)o;
        String str = "INSERT INTO  company (nameCompany, sizeCompany, industryCompany, idCountry, costCompany,typeCompany) VALUES('" +
                obj.getNameCompany() +
                "', '" + obj.getSizeCompany() +
                "', '" + obj.getIndustryCompany()+
                "', '" + obj.getIdCountry() +
                "', '" + "0" +
                "', '" + obj.getTypeCompany() +"')";
        dbConnection.execute(str);
    }

    @Override
    public void delete(Object o) {
        String log=(String)o;
        String str = "DELETE FROM company WHERE nameCompany = '" + log + "'";
        dbConnection.execute(str);
    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void create() {
        String str = "CREATE TABLE if not exists company (\n" +
                "  idCompany int unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  nameCompany varchar(45) DEFAULT NULL,\n" +
                "  sizeCompany int DEFAULT NULL,\n" +
                "  industryCompany varchar(45) DEFAULT NULL,\n" +
                "  idCountry int DEFAULT NULL,\n" +
                "  costCompany float DEFAULT NULL,\n" +
                "  typeCompany varchar(45) DEFAULT NULL,\n" +
                "  PRIMARY KEY (idCompany),\n" +
                "  CONSTRAINT country_fk1 FOREIGN KEY (idCountry) REFERENCES country (idCountry)\n" +
                ");";
        dbConnection.execute(str);
    }

    @Override
    public EvaluatedCompany select(Object type) {
        EvaluatedCompany obj=(EvaluatedCompany)type;
        String str = "SELECT * From company WHERE nameCompany = '" + obj.getNameCompany() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<EvaluatedCompany> listInfo = new ArrayList<>();
        EvaluatedCompany info = new EvaluatedCompany();
        for (String[] items : result) {
            info.setIdCompany(Integer.parseInt(items[0]));
            info.setNameCompany(items[1]);
            info.setSizeCompany(Integer.parseInt(items[2]));
            info.setIndustryCompany(items[3]);
            info.setIdCountry(Integer.parseInt(items[4]));
            info.setTypeCompany(items[6]);
            listInfo.add(info);
        }
        return info;
    }


    @Override
    public ArrayList<EvaluatedCompany> selectAll() {
        String str = "SELECT * From company Where typeCompany='owner'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<EvaluatedCompany> listInfo = new ArrayList<>();
        for (String[] items : result) {
            EvaluatedCompany info = new EvaluatedCompany();
            info.setIdCompany(Integer.parseInt(items[0]));
            info.setNameCompany(items[1]);
            info.setSizeCompany(Integer.parseInt(items[2]));
            info.setIndustryCompany(items[3]);
            info.setIdCountry(Integer.parseInt(items[4]));
            info.setTypeCompany(items[6]);
            listInfo.add(info);
        }
        return listInfo;
    }


}
