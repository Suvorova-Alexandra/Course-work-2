package database;

import database.Connection.ConnectionDB;
import model.AnalogCompany;
import model.Company;

import java.util.ArrayList;

public class TableAnalogCompany implements DBRequests {
    private static TableAnalogCompany instance;
    private ConnectionDB dbConnection;
    private TableAnalogCompany() {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized TableAnalogCompany getInstance() {
        if (instance == null) {
            instance = new TableAnalogCompany();
        }
        return instance;
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

    public String findEditedCompany(Company obj, String oldName) {
        String str = "SELECT * FROM company where nameCompany = '"+ obj.getNameCompany()+"' AND nameCompany != '"+oldName+"'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String status = "";
        for (String[] item : result)
            status = item[0];
        return status;
    }
    public void updateAnalogCompany(AnalogCompany obj, String oldName) {
        String str = "UPDATE company SET nameCompany='" +
                obj.getNameCompany() +
                "', sizeCompany = '" + obj.getSizeCompany() +
                "', industryCompany ='" + obj.getIndustryCompany()+
                "', idCountry= '" + obj.getIdCountry() +
                "', costCompany= '" + obj.getCostCompany() +
                "',typeCompany='" + obj.getTypeCompany() +"' where nameCompany='"+oldName+"'";
        dbConnection.execute(str);
    }

    @Override
    public void insert(Object o) {
        AnalogCompany obj=(AnalogCompany)o;
        String str = "INSERT INTO  company (nameCompany, sizeCompany, industryCompany, idCountry, costCompany,typeCompany) VALUES('" +
                obj.getNameCompany() +
                "', '" + obj.getSizeCompany() +
                "', '" + obj.getIndustryCompany()+
                "', '" + obj.getIdCountry() +
                "', '" + obj.getCostCompany() +
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

    }

    @Override
    public AnalogCompany select(Object type) {
        AnalogCompany obj=(AnalogCompany)type;
        String str = "SELECT * From company WHERE nameCompany = '" + obj.getNameCompany() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<AnalogCompany> listInfo = new ArrayList<>();
        AnalogCompany info = new AnalogCompany();
        for (String[] items : result) {
            info.setIdCompany(Integer.parseInt(items[0]));
            info.setNameCompany(items[1]);
            info.setSizeCompany(Integer.parseInt(items[2]));
            info.setIndustryCompany(items[3]);
            info.setIdCountry(Integer.parseInt(items[4]));
            info.setCostCompany(Float.parseFloat(items[5]));
            info.setTypeCompany(items[6]);
            listInfo.add(info);
        }
        return info;
    }

    public  ArrayList<String> selectIndustriesAnalogs() {
        String str = "SELECT distinct industryCompany from company WHERE typeCompany = 'analog'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<String> listInfo = new ArrayList<>();
        for (String[] items : result) {
            listInfo.add(items[0]);
        }
        return listInfo;
    }

    @Override
    public ArrayList<AnalogCompany> selectAll() {
        String str = "SELECT * From company Where typeCompany='analog'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<AnalogCompany> listInfo = new ArrayList<>();
        for (String[] items : result) {
            AnalogCompany info = new AnalogCompany();
            info.setIdCompany(Integer.parseInt(items[0]));
            info.setNameCompany(items[1]);
            info.setSizeCompany(Integer.parseInt(items[2]));
            info.setIndustryCompany(items[3]);
            info.setIdCountry(Integer.parseInt(items[4]));
            info.setCostCompany(Float.parseFloat(items[5]));
            info.setTypeCompany(items[6]);
            listInfo.add(info);
        }
        return listInfo;
    }
}
