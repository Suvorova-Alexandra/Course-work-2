package database;

import database.Connection.ConnectionDB;
import model.CompanyInfo;

import java.util.ArrayList;

public class TableCompanyInfo implements DBRequests {
    private static TableCompanyInfo instance;
    private ConnectionDB dbConnection;
    private TableCompanyInfo() {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized TableCompanyInfo getInstance() {
        if (instance == null) {
            instance = new TableCompanyInfo();
        }
        return instance;
    }

    @Override
    public Object find(Object o) {
        return null;
    }

    @Override
    public void insert(Object o) {
        CompanyInfo obj=(CompanyInfo)o;
        String str = "INSERT INTO  companyinformation (idCompany, idUser) VALUES('" +
                obj.getIdCompany() +
                "', '" + obj.getIdUser() +"')";
        dbConnection.execute(str);
    }

    @Override
    public void delete(Object o) {
        int idcompany=(int)o;
        String str = "DELETE FROM companyinformation WHERE idCompany = '" + idcompany + "'";
        dbConnection.execute(str);
    }

    @Override
    public void update(Object o) {

    }

    @Override
    public ArrayList<CompanyInfo> selectAll() {
        String str = "SELECT * From companyinformation";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<CompanyInfo> listInfo = new ArrayList<>();
        for (String[] items : result) {
            CompanyInfo info = new CompanyInfo();
info.setIdCompany(Integer.parseInt(items[1]));
info.setIdUser(Integer.parseInt(items[0]));
            listInfo.add(info);
        }
        return listInfo;
    }

    @Override
    public void create() {
        String str = "CREATE TABLE if not exists companyinformation (\n" +
                "  idUser int unsigned NOT NULL,\n" +
                "  idCompany int unsigned NOT NULL,\n" +
                "  KEY fk_companyinformation_users_idx (idUser),\n" +
                "  KEY fk_companyinformation_company1_idx (idCompany),\n" +
                "  CONSTRAINT fk_companyinformation_company1 FOREIGN KEY (idCompany) REFERENCES company (idCompany),\n" +
                "  CONSTRAINT idUser FOREIGN KEY (idUser) references users (idUser)\n" +
                ") ;";
        dbConnection.execute(str);
    }

    @Override
    public Object select(Object type) {
        return null;
    }

    public void deleteUserInfo(int iduser) {
        String str = "DELETE FROM companyinformation WHERE idUser = '" + iduser + "'";
        dbConnection.execute(str);
    }

}
