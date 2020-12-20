package database;

import database.Connection.ConnectionDB;
import model.Report;

import java.util.ArrayList;

public class TableReport implements DBRequests {
    private static TableReport instance;
    private ConnectionDB dbConnection;
    private TableReport() {
        dbConnection = ConnectionDB.getInstance();
    }
    public static synchronized TableReport getInstance() {
        if (instance == null) {
            instance = new TableReport();
        }
        return instance;
    }
    @Override
    public Object find(Object o) {
        Report obj=(Report)o;
        String str = "SELECT * FROM report where dateReport = '"+ obj.getDateReport()+"' and idCompany='"+obj.getIdCompany()+"'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String status="";

        for (String[] item : result)
            status = item[2];
        System.out.println("sf"+status);
        return status;
    }

    @Override
    public void insert(Object o) {
       Report obj=(Report) o;
        String str = "INSERT INTO  report (costReport, dateReport, idCompany) VALUES('" +
                obj.getCostReport() +
                "', '" + obj.getDateReport() +
                "', '" + obj.getIdCompany() +"')";
        dbConnection.execute(str);
    }

    @Override
    public void delete(Object o) {
        int id=(int)o;
        String str = "DELETE FROM report WHERE idCompany = '" + id + "'";
        dbConnection.execute(str);
    }

    @Override
    public void update(Object o) {
       Report obj=(Report) o;
        String str = "UPDATE report SET costReport='" +
                obj.getCostReport() +"' where idReport='"+obj.getIdReport()+"'";
        dbConnection.execute(str);
    }

    @Override
    public ArrayList<Report> selectAll() {
        String str = "SELECT * From report";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Report> listReps = new ArrayList<>();
        for (String[] items : result) {
            Report report = new Report();
            report.setIdCompany(Integer.parseInt(items[0]));
            report.setCostReport(Float.parseFloat(items[1]));
            report.setDateReport(items[2]);
            report.setIdCompany(Integer.parseInt(items[3]));
            listReps.add(report);
        }
        return listReps;
    }

    @Override
    public void create() {

    }

    @Override
    public Object select(Object type) {
        return null;
    }
}
