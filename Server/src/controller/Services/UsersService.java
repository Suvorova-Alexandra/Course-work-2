package controller.Services;

import database.TableFactory;
import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class UsersService {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;


    public UsersService(String message, ObjectInputStream sois, ObjectOutputStream soos, Socket clientSocket) throws IOException, ClassNotFoundException {
        this.sois = sois;
        this.soos = soos;
        this.clientSocket = clientSocket;
        switch (message) {
            case "registrationUser": {
                registration();
            }
            break;
            case "forbidAccess": {
                forbidAccess();
            }
            break;
            case "allowAccess": {
                allowAccess();
            }
            break;
            case "deleteUser": {
                deleteUser();
            }
            break;
            case "showInfoUser": {
                showInfoUser();
            }
            break;
            case "createListCommand": {
                showListCommand();
            }
            break;
            case "addAnalyst": {
                addAnalyst();
            }
            break;
            case "createListAllUsers": {
                createListAllUsers();
            }
            break;
            case "enter": {
                enter();
            }
            break;

        }
    }

    public void registration() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на проверку пользователя(таблица users), клиент: " + clientSocket.getInetAddress().toString());
        Users user = (Users) sois.readObject();

        TableFactory sqlFactory = new TableFactory();

        if (sqlFactory.getUsers().find(user).equals("")) {
            soos.writeObject("OK");
            sqlFactory.getUsers().insert(user);
        } else {
            soos.writeObject("This user is already existed");
        }
    }

    public void deleteUser() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на удаление пользователя(таблица users), клиент: " + clientSocket.getInetAddress().toString());
        String[] value = (String[]) sois.readObject();
        TableFactory sqlFactory = new TableFactory();
        ArrayList<Users> listUsers = sqlFactory.getUsers().selectAll();
        ArrayList<CompanyInfo> listInfo = sqlFactory.getInfo().selectAll();
        ArrayList<EvaluatedCompany> listCompanies = sqlFactory.getCompanies().selectAll();
        ArrayList<FinParameter> listParameters = sqlFactory.getParameters().selectAll();
        ArrayList<Users> listDeleted = new ArrayList<>();
        String nameCompany = new String();
        for (int i = 0; i < value.length; i++) {
            Users curUser = new Users();
            curUser.setLogin(value[i]);
            Users newUser = sqlFactory.getUsers().select(curUser);
            if (newUser.isValid()) {
                if (newUser.getRole().equals("Владелец")) {
                    for (CompanyInfo info : listInfo) {
                        if (newUser.getIdUser() == info.getIdUser()) {
                            int idCompany = info.getIdCompany();
                            for (EvaluatedCompany evCompany : listCompanies) {
                                if (idCompany == evCompany.getIdCompany()) {
                                    nameCompany = evCompany.getNameCompany();
                                    for (FinParameter parameter : listParameters) {
                                        if (idCompany == parameter.getIdCompany())
                                            sqlFactory.getParameters().delete(parameter.getIdParameter());
                                    }
                                    ArrayList<Multiplier> listMults = sqlFactory.getMultipliers().selectAll();
                                    for (Multiplier mult : listMults) {
                                        if (idCompany == mult.getIdCompany()) {
                                            sqlFactory.getMultipliers().delete(mult.getIdMult());
                                        }
                                    }
                                    ArrayList<Deposit> listDeps = sqlFactory.getDeposits().selectAll(idCompany);
                                    for (Deposit dep : listDeps) {
                                        sqlFactory.getDeposits().delete(dep.getIdDeposit());
                                    }

                                }

                            }
                            for (CompanyInfo info1 : listInfo) {
                                if (idCompany == info1.getIdCompany()) {
                                    int idUser = info1.getIdUser();
                                    for (Users analyst : listUsers) {
                                        if (idUser == analyst.getIdUser()) {
                                            listDeleted.add(analyst);
                                        }
                                    }
                                }
                            }
                            sqlFactory.getInfo().delete(idCompany);
                            sqlFactory.getCompanies().delete(nameCompany);
                            for (Users nalyst : listDeleted) {
                                sqlFactory.getUsers().delete(nalyst.getLogin());
                            }


                        }
                    }
                }
                if (newUser.getRole().equals("Аналитик")) {
                    for (CompanyInfo info : listInfo) {
                        if (info.getIdUser() == newUser.getIdUser()) {
                            sqlFactory.getInfo().deleteUserInfo(info.getIdUser());
                        }
                    }
                    sqlFactory.getUsers().delete(value[i]);
                }
            }

        }


    }

    public void showInfoUser() throws IOException, ClassNotFoundException {
        Users user = (Users) sois.readObject();

        TableFactory sqlFactory = new TableFactory();
        user = sqlFactory.getUsers().select(user);
        soos.writeObject(user);
    }

    public void showListCommand() throws IOException, ClassNotFoundException {
        TableFactory sqlFactory = new TableFactory();

        ArrayList<Users> listUsers = sqlFactory.getUsers().selectAll();
        ArrayList<EvaluatedCompany> listCompanies = sqlFactory.getCompanies().selectAll();
        ArrayList<CompanyInfo> listInfo = sqlFactory.getInfo().selectAll();

        ArrayList<EvaluatedCompany> listOwnerCompanies = new ArrayList<>();
        ArrayList<Users> listAnalysts = new ArrayList<>();

        Users owner = (Users) sois.readObject();
        owner = sqlFactory.getUsers().select(owner);
        int idUser = owner.getIdUser();
        for (CompanyInfo company : listInfo) {
            if (idUser == company.getIdUser()) {
                int idCompany = company.getIdCompany();
                for (EvaluatedCompany evcompany : listCompanies) {
                    if (idCompany == evcompany.getIdCompany()) {
                        listOwnerCompanies.add(evcompany);
                    }
                }
            }
        }
        ArrayList<String> names = new ArrayList<>();

        for (EvaluatedCompany evcompany : listOwnerCompanies) {
            for (CompanyInfo company : listInfo) {
                if (evcompany.getIdCompany() == company.getIdCompany()) {

                    int idAnalyst = company.getIdUser();

                    for (Users usre : listUsers) {
                        if (idAnalyst == usre.getIdUser() && usre.getRole().equals("Аналитик")) {
                            listAnalysts.add(usre);
                            names.add(evcompany.getNameCompany());
                        }
                    }
                }
            }
        }
        String[][] analysts = new String[listAnalysts.size()][7];
        int count = 0;
        for (Users nal : listAnalysts) {
            analysts[count][0] = names.get(count);
            analysts[count][1] = nal.getSurname();
            analysts[count][2] = nal.getName();
            analysts[count][3] = nal.getPhone();
            analysts[count][4] = nal.getRole();
            analysts[count][5] = nal.getLogin();
            analysts[count][6] = nal.getPassword();
            count++;
        }
        soos.writeObject(analysts);
    }

    public void addAnalyst() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на добавление бизнес-аналитика (таблица users), клиент: " + clientSocket.getInetAddress().toString());
        Users user = (Users) sois.readObject();
        String companyName = (String) sois.readObject();
        TableFactory sqlFactory = new TableFactory();

        if (sqlFactory.getUsers().find(user).equals("")) {
            soos.writeObject("OK");
            sqlFactory.getUsers().insert(user);
            CompanyInfo info = new CompanyInfo();
            EvaluatedCompany company = new EvaluatedCompany();
            company.setNameCompany(companyName);
            EvaluatedCompany newCompany = sqlFactory.getCompanies().select(company);
            Users newUser = sqlFactory.getUsers().select(user);
            info.setIdCompany(newCompany.getIdCompany());
            info.setIdUser(newUser.getIdUser());
            sqlFactory.getInfo().insert(info);
        } else {
            soos.writeObject("This user is already existed");
        }

    }

    public void createListAllUsers() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД для получения списка пользователей(таблица users), клиент: " + clientSocket.getInetAddress().toString());
        TableFactory sqlFactory = new TableFactory();
        ArrayList<Users> listUsers = sqlFactory.getUsers().selectAll();

        int size = 0;
        for (Users user : listUsers) {
            String str = user.getRole();
            if (str.equals("admin")) {

            } else size++;
        }

        String[][] data = new String[size][6];
        int count = 0;
        for (Users user : listUsers) {
            String str = user.getRole();
            if (str.equals("admin")) {

            } else {
                data[count][0] = user.getSurname();
                data[count][1] = user.getName();
                data[count][2] = user.getPhone();
                data[count][3] = user.getRole();
                data[count][4] = user.getLogin();
                data[count][5] = user.getPassword();

                count++;
            }
        }
        soos.writeObject(data);
    }

    public void enter() throws IOException, ClassNotFoundException {
        Users user = (Users) sois.readObject();
        TableFactory sqlFactory = new TableFactory();
        String status = sqlFactory.getUsers().find(user);
        if (status == ""){
                soos.writeObject("error");
        }
        else if (status.contains("-")) {
            soos.writeObject("Access denied!");}
        else {
            soos.writeObject("ok");
            soos.writeObject(status);

            System.out.println("Подключение клиента: " + clientSocket.getInetAddress().toString());
        }
    }

    public void forbidAccess() throws IOException, ClassNotFoundException {
        String[] logs = (String[]) sois.readObject();
        TableFactory sqlFactory = new TableFactory();
        for (int i = 0; i < logs.length; i++) {
            Users curUser = new Users();
            curUser.setLogin(logs[i]);
            Users newUser = sqlFactory.getUsers().select(curUser);
            if (newUser.getRole().indexOf("-") != -1) {
                System.out.println("sdfs");
                soos.writeObject(logs[i]);
            } else {
                sqlFactory.getUsers().update(newUser);
                soos.writeObject("ok");
            }
        }
    }

    public void allowAccess() throws IOException, ClassNotFoundException {
        String[] logs = (String[]) sois.readObject();
        TableFactory sqlFactory = new TableFactory();
        for (int i = 0; i < logs.length; i++) {
            Users curUser = new Users();
            curUser.setLogin(logs[i]);
            Users newUser = sqlFactory.getUsers().select(curUser);
            if (newUser.getRole().indexOf("-") != -1) {
                String role = newUser.getRole().substring(0,newUser.getRole().indexOf("-"));
                newUser.setRole(role);
                sqlFactory.getUsers().updateAllow(newUser);
                soos.writeObject("ok");
            } else {
                soos.writeObject(logs[i]);

            }
        }
    }
}
