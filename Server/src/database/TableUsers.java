package database;

import database.Connection.ConnectionDB;
import model.Users;

import java.util.ArrayList;

public class TableUsers implements DBRequests {
    private static TableUsers instance;
    private ConnectionDB dbConnection;

    private TableUsers() {
        dbConnection = ConnectionDB.getInstance();
    }

    public static synchronized TableUsers getInstance() {
        if (instance == null) {
            instance = new TableUsers();
        }
        return instance;
    }

    @Override
    public String find(Object o) {
        Users obj=(Users)o;
        String str = "SELECT * FROM users WHERE loginUser = '" + obj.getLogin() +
                "' and passUser = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        String status = "";
        for (String[] item : result)
            status = item[3];
        return status;
    }

    @Override
    public void insert(Object o) {
        Users obj=(Users)o;
        String str = "INSERT INTO users (loginUser, passUser, roleUser, surname, name, phone) VALUES('" + obj.getLogin()
                + "', '" + obj.getPassword() + "', '" + obj.getRole()+ "', '" + obj.getSurname() + "', '" + obj.getName() + "', '" + obj.getPhone() +  "')";
        dbConnection.execute(str);
    }

    @Override
    public void delete(Object o) {
        String log=(String)o;
        String str = "DELETE FROM users WHERE loginUser = '" + log + "'";
        dbConnection.execute(str);
    }

    @Override
    public void update(Object o) {
        Users obj=(Users)o;
        String str = "UPDATE users SET roleUser='"+obj.getRole()+"-' Where idUser = '" + obj.getIdUser() + "'";
        dbConnection.execute(str);
    }
    public void updateAllow(Object o) {
        Users obj=(Users)o;
        String str = "UPDATE users SET roleUser='"+obj.getRole()+"' Where idUser = '" + obj.getIdUser() + "'";
        dbConnection.execute(str);
    }
    @Override
    public ArrayList<Users> selectAll() {
        String str = "SELECT * From users";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        ArrayList<Users> listUsers = new ArrayList<>();
        for (String[] items : result) {
            Users user = new Users();
            user.setIdUser(Integer.parseInt(items[0]));
            user.setLogin(items[1]);
            user.setPassword(items[2]);
            user.setRole(items[3]);
            user.setSurname(items[4]);
            user.setName(items[5]);
            user.setPhone(items[6]);
            listUsers.add(user);
        }
        return listUsers;
    }

    @Override
    public void create() {
        String str = "CREATE TABLE if not exists users (\n" +
                "  idUser int unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  loginUser varchar(45) NOT NULL,\n" +
                "  passUser varchar(45) NOT NULL,\n" +
                "  roleUser varchar(45) NOT NULL,\n" +
                "  surname varchar(45) DEFAULT NULL,\n" +
                "  name varchar(45) DEFAULT NULL,\n" +
                "  phone varchar(45) DEFAULT NULL,\n" +
                "  PRIMARY KEY (idUser)\n" +
                ");";
        dbConnection.execute(str);
    }

    @Override
    public Users select(Object type) {
        Users obj=(Users)type;
        String str = "SELECT * From users Where loginUser = '" + obj.getLogin() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(str);
        Users user = new Users();
        for (String[] items : result) {
            user.setIdUser(Integer.parseInt(items[0]));
            user.setLogin(items[1]);
            user.setPassword(items[2]);
            user.setRole(items[3]);
            user.setSurname(items[4]);
            user.setName(items[5]);
            user.setPhone(items[6]);
        }
        return user;
    }


}
