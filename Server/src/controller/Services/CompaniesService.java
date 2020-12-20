package controller.Services;

import database.TableFactory;
import model.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CompaniesService {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public CompaniesService(String message, ObjectInputStream sois, ObjectOutputStream soos, Socket clientSocket) throws IOException, ClassNotFoundException {
        this.sois = sois;
        this.soos = soos;
        this.clientSocket = clientSocket;
        switch (message) {
            case "addOwnerCompany": {
                add();
            }
            break;
            case "deleteOwnerCompany": {
                deleteOwnerCompany();
            }
            break;
            case "editOwnerCompany": {
                editOwnerCompany();
            }
            break;
            case "showOwnerCompanies": {
                showOwnerCompanies();
            }
            break;
            case "showAllOwnerCompanies": {
                showAllOwnerCompanies();
            }
            break;
            case "showAnalystCompany": {
                showCompanyToAnalist();
            }
            break;
            case "createOwnerCompanies": {
                createOwnerCompanies();
            }
            break;
            case "deleteOwnerCompanyAdmin": {
                deleteOwnerCompanyAdmin();
            }
            break;
            case "createListCountries": {
                createListCountries();
            }
            break;


        }
    }

    public void add() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на добавление компании (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        EvaluatedCompany company = (EvaluatedCompany) sois.readObject();
        Users user = (Users) sois.readObject();
        TableFactory sqlFactory2 = new TableFactory();

        if (sqlFactory2.getCompanies().find(company).equals("")) {
            soos.writeObject("OK");
            CompanyInfo info = new CompanyInfo();
            sqlFactory2.getCompanies().insert(company);
            EvaluatedCompany newCompany = sqlFactory2.getCompanies().select(company);
            System.out.println(newCompany.getIdCompany());
            Users newUser = sqlFactory2.getUsers().select(user);
            info.setIdCompany(newCompany.getIdCompany());
            info.setIdUser(newUser.getIdUser());
            sqlFactory2.getInfo().insert(info);
            ArrayList<FinParameter> companyPars = company.getParameters();
            for (FinParameter par : companyPars) {
                par.setIdCompany(newCompany.getIdCompany());
                sqlFactory2.getParameters().insert(par);
            }


        } else {
            soos.writeObject("This company is already existed");
        }
    }

    public void deleteOwnerCompany() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на удаление компании (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        String[] value1 = (String[]) sois.readObject();
        TableFactory companyFactory = new TableFactory();
        ArrayList<EvaluatedCompany> listCompanies = companyFactory.getCompanies().selectAll();
        ArrayList<CompanyInfo> listInfo = companyFactory.getInfo().selectAll();
        ArrayList<Users> listUsers = companyFactory.getUsers().selectAll();
        ArrayList<FinParameter> listPars = companyFactory.getParameters().selectAll();
        ArrayList<Users> list = new ArrayList<>();
        int id, idUser;
        for (int j = 0; j < value1.length; j++) {
            for (int i = 0; i < listCompanies.size(); i++) {
                if (value1[j].equals(listCompanies.get(i).getNameCompany())) {
                    id = listCompanies.get(i).getIdCompany();
                    for (CompanyInfo info : listInfo) {
                        if (id == info.getIdCompany()) {
                            idUser = info.getIdUser();
                            for (Users user : listUsers) {
                                String str = user.getRole();
                                if (str.equals("Владелец") || str.equals("admin")) {
                                } else list.add(user);
                            }
                            companyFactory.getInfo().delete(id);
                            for (int k = 0; k < list.size(); k++) {
                                if (idUser == list.get(k).getIdUser()) {
                                    String login = list.get(k).getLogin();
                                    File file1=new File("D:/My Documents/Курсовой 5 сем/курсач/reports/"+login+"+"+value1[j]+"+Cost.docx");
                                    File file2=new File("D:/My Documents/Курсовой 5 сем/курсач/reports/"+login+"+"+value1[j]+"+Analogs.docx");
                                    if(file1.exists()){
                                        file1.delete();
                                    }
                                    if(file2.exists()){
                                        file2.delete();
                                    }
                                    companyFactory.getUsers().delete(login);
                                }
                            }
                            for (FinParameter finParameter : listPars) {
                                if (id == finParameter.getIdCompany()) {
                                    companyFactory.getParameters().delete(finParameter.getIdParameter());
                                }
                            }
                            ArrayList<Multiplier> listMults = companyFactory.getMultipliers().selectAll();
                            for (Multiplier mult : listMults) {
                                if (id == mult.getIdCompany()) {
                                    companyFactory.getMultipliers().delete(mult.getIdMult());
                                }
                            }
                            ArrayList<Deposit> listDeps = companyFactory.getDeposits().selectAll(id);
                            for (Deposit dep : listDeps) {
                                companyFactory.getDeposits().delete(dep.getIdDeposit());

                            }
                            companyFactory.getReports().delete(id);
                        }
                    }

                    companyFactory.getCompanies().delete(value1[j]);
                }
            }
        }


    }

    public void editOwnerCompany() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на обновление компании (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        EvaluatedCompany company = (EvaluatedCompany) sois.readObject();
        String oldName = (String) sois.readObject();
        TableFactory sqlFactory2 = new TableFactory();
        ArrayList<FinParameter> listPars = sqlFactory2.getParameters().selectAll();
        if (sqlFactory2.getCompanies().findEditedCompany(company, oldName).equals("")) {
            soos.writeObject("OK");
            sqlFactory2.getCompanies().updateOwnerCompany(company, oldName);
            EvaluatedCompany newCompany = sqlFactory2.getCompanies().select(company);
            ArrayList<FinParameter> companyPars = company.getParameters();
            for (FinParameter par : companyPars) {
                int flag = 0;
                par.setIdCompany(newCompany.getIdCompany());
                for (FinParameter param : listPars) {
                    if (par.getIdCompany() == param.getIdCompany() && par.getNameParameter().equals(param.getNameParameter())) {
                        par.setIdParameter(param.getIdParameter());
                        flag++;
                    }
                }
                if (flag != 0) {

                    sqlFactory2.getParameters().update(par);
                } else {
                    sqlFactory2.getParameters().insert(par);
                }

            }
        } else {
            soos.writeObject("This company is already existed");
        }
    }

    public void showOwnerCompanies() throws IOException, ClassNotFoundException {
        Users user = (Users) sois.readObject();
        TableFactory companyFactory = new TableFactory();
        Users newUser = companyFactory.getUsers().select(user);
        ArrayList<CompanyInfo> infoCompanies = companyFactory.getInfo().selectAll();
        ArrayList<EvaluatedCompany> listCompanies = companyFactory.getCompanies().selectAll();

        ArrayList<CompanyInfo> list2 = new ArrayList<>();

        for (CompanyInfo companyInf : infoCompanies) {
            int id = newUser.getIdUser();
            if (id == companyInf.getIdUser()) {
                list2.add(companyInf);

            }
        }
        String[] names = new String[list2.size()];
        int count = 0;
        for (CompanyInfo infoC : list2) {
            for (EvaluatedCompany company : listCompanies) {
                if (infoC.getIdCompany() == company.getIdCompany()) {
                    names[count] = company.getNameCompany();

                }
            }
            count++;
        }
        soos.writeObject(names);
    }

    public void showAllOwnerCompanies() throws IOException {
        TableFactory companyFactory = new TableFactory();
        ArrayList<EvaluatedCompany> listCompanies = companyFactory.getCompanies().selectAll();
        ArrayList<FinParameter> finParameters = companyFactory.getParameters().selectAll();
        ArrayList<Country> listCountries = companyFactory.getCountries().selectAll();
        String[][] companies = new String[listCompanies.size()][10];
        int count = 0;

        for (EvaluatedCompany company : listCompanies) {
            companies[count][0] = company.getNameCompany();
            companies[count][1] = String.valueOf(company.getSizeCompany());
            companies[count][2] = company.getIndustryCompany();
            for (Country con : listCountries) {
                if (company.getIdCountry() == con.getIdCountry()) {
                    companies[count][3] = con.getNameCountry();
                }
            }
            for (FinParameter parameter : finParameters) {
                if (parameter.getIdCompany() == company.getIdCompany()) {
                    if (parameter.getNameParameter().equals("Выручка")) {
                        companies[count][4] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Чистая прибыль")) {
                        companies[count][5] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Прибыль до уплаты налогов")) {
                        companies[count][6] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Денежный поток")) {
                        companies[count][7] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Денежный поток до налогообложения")) {
                        companies[count][8] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Дивиденды")) {
                        companies[count][9] = String.valueOf(parameter.getValue());
                    }
                }
            }
            count++;
        }
        soos.writeObject(companies);
    }

    public void showCompanyToAnalist() throws IOException, ClassNotFoundException {

        Users user = (Users) sois.readObject();
        TableFactory companyFactory = new TableFactory();
        Users newUser = companyFactory.getUsers().select(user);
        ArrayList<CompanyInfo> infoCompanies = companyFactory.getInfo().selectAll();
        ArrayList<EvaluatedCompany> listCompanies = companyFactory.getCompanies().selectAll();
        ArrayList<FinParameter> finParameters = companyFactory.getParameters().selectAll();
        int idComp = 0;

        for (CompanyInfo companyInf : infoCompanies) {
            int id = newUser.getIdUser();
            if (id == companyInf.getIdUser()) {
                idComp = companyInf.getIdCompany();

            }
        }
        String[][] names = new String[listCompanies.size()][10];
        ArrayList<Country> listCountries = companyFactory.getCountries().selectAll();
        int count = 0;
        for (EvaluatedCompany company : listCompanies) {
            if (idComp == company.getIdCompany()) {
                names[count][0] = company.getNameCompany();
                names[count][1] = String.valueOf(company.getSizeCompany());
                names[count][2] = company.getIndustryCompany();
                for (Country con : listCountries) {
                    if (company.getIdCountry() == con.getIdCountry()) {
                        names[count][3] = con.getNameCountry();
                    }
                }
                for (FinParameter parameter : finParameters) {
                    if (parameter.getIdCompany() == company.getIdCompany()) {
                        if (parameter.getNameParameter().equals("Выручка")) {
                            names[count][4] = String.valueOf(parameter.getValue());
                        }
                        if (parameter.getNameParameter().equals("Чистая прибыль")) {
                            names[count][5] = String.valueOf(parameter.getValue());
                        }
                        if (parameter.getNameParameter().equals("Прибыль до уплаты налогов")) {
                            names[count][6] = String.valueOf(parameter.getValue());
                        }
                        if (parameter.getNameParameter().equals("Денежный поток")) {
                            names[count][7] = String.valueOf(parameter.getValue());
                        }
                        if (parameter.getNameParameter().equals("Денежный поток до налогообложения")) {
                            names[count][8] = String.valueOf(parameter.getValue());
                        }
                        if (parameter.getNameParameter().equals("Дивиденды")) {
                            names[count][9] = String.valueOf(parameter.getValue());
                        }
                    }
                }
                count++;

            }
        }
System.out.println("dsf"+names[0][0]);
        soos.writeObject(names);
    }

    public void createOwnerCompanies() throws IOException, ClassNotFoundException {
        Users user = (Users) sois.readObject();
        TableFactory companyFactory = new TableFactory();
        Users newUser = companyFactory.getUsers().select(user);
        ArrayList<CompanyInfo> infoCompanies = companyFactory.getInfo().selectAll();
        ArrayList<EvaluatedCompany> listCompanies = companyFactory.getCompanies().selectAll();
        ArrayList<CompanyInfo> list2 = new ArrayList<>();
        ArrayList<FinParameter> finParameters = companyFactory.getParameters().selectAll();
        for (CompanyInfo companyInf : infoCompanies) {
            int id = newUser.getIdUser();
            if (id == companyInf.getIdUser()) {
                list2.add(companyInf);
            }
        }
        String[][] companies = new String[list2.size()][10];
        ArrayList<Country> listCountries = companyFactory.getCountries().selectAll();
        int count = 0;
        for (CompanyInfo infoC : list2) {
            for (EvaluatedCompany company : listCompanies) {
                if (infoC.getIdCompany() == company.getIdCompany()) {
                    companies[count][0] = company.getNameCompany();
                    companies[count][1] = String.valueOf(company.getSizeCompany());
                    companies[count][2] = company.getIndustryCompany();
                    for (Country con : listCountries) {
                        if (company.getIdCountry() == con.getIdCountry()) {
                            companies[count][3] = con.getNameCountry();
                        }
                    }
                    for (FinParameter parameter : finParameters) {
                        if (parameter.getIdCompany() == company.getIdCompany()) {
                            if (parameter.getNameParameter().equals("Выручка")) {
                                companies[count][4] = String.valueOf(parameter.getValue());
                            }
                            if (parameter.getNameParameter().equals("Чистая прибыль")) {
                                companies[count][5] = String.valueOf(parameter.getValue());
                            }
                            if (parameter.getNameParameter().equals("Прибыль до уплаты налогов")) {
                                companies[count][6] = String.valueOf(parameter.getValue());
                            }
                            if (parameter.getNameParameter().equals("Денежный поток")) {
                                companies[count][7] = String.valueOf(parameter.getValue());
                            }
                            if (parameter.getNameParameter().equals("Денежный поток до налогообложения")) {
                                companies[count][8] = String.valueOf(parameter.getValue());
                            }
                            if (parameter.getNameParameter().equals("Дивиденды")) {
                                companies[count][9] = String.valueOf(parameter.getValue());
                            }
                        }
                    }
                }
            }
            count++;
        }
        soos.writeObject(companies);

    }

    public void createListCountries() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на создание списка стран (таблица country), клиент: " + clientSocket.getInetAddress().toString());
        TableFactory factory = new TableFactory();
        ArrayList<Country> listCountries = factory.getCountries().selectAll();

        String[][] names = new String[listCountries.size()][2];
        int count = 0;
        for (Country com : listCountries) {
            names[count][0] = com.getNameCountry();
            names[count][1] = com.getCodeCountry();
            count++;
        }
        soos.writeObject(names);
    }

    public void deleteOwnerCompanyAdmin() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на удаление компании (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        String[] value1 = (String[]) sois.readObject();
        TableFactory companyFactory1 = new TableFactory();
        ArrayList<EvaluatedCompany> listCompanies = companyFactory1.getCompanies().selectAll();
        ArrayList<CompanyInfo> listInfo = companyFactory1.getInfo().selectAll();
        ArrayList<Users> listUsers = companyFactory1.getUsers().selectAll();
        ArrayList<FinParameter> listPars = companyFactory1.getParameters().selectAll();
        ArrayList<Users> list = new ArrayList<>();
        int id, idUser;
        for (int j = 0; j < value1.length; j++) {
            for (int i = 0; i < listCompanies.size(); i++) {
                if (value1[j].equals(listCompanies.get(i).getNameCompany())) {
                    id = listCompanies.get(i).getIdCompany();
                    for (CompanyInfo info : listInfo) {
                        if (id == info.getIdCompany()) {
                            idUser = info.getIdUser();
                            for (Users user : listUsers) {
                                String str = user.getRole();
                                if (str.equals("admin")) {
                                } else list.add(user);
                            }
                            companyFactory1.getInfo().delete(id);
                            System.out.println(list.size());
                            for (int k = 0; k < list.size(); k++) {
                                if (idUser == list.get(k).getIdUser()) {
                                    String login = list.get(k).getLogin();
                                    File file1=new File("D:/My Documents/Курсовой 5 сем/курсач/reports/"+login+"+"+value1[j]+"+Cost.docx");
                                    File file2=new File("D:/My Documents/Курсовой 5 сем/курсач/reports/"+login+"+"+value1[j]+"+Analogs.docx");
                                    if(file1.exists()){
                                        file1.delete();
                                    }
                                    if(file2.exists()){
                                        file2.delete();
                                    }
                                    companyFactory1.getUsers().delete(login);
                                }
                            }
                            for (FinParameter finParameter : listPars) {
                                if (id == finParameter.getIdCompany()) {
                                    companyFactory1.getParameters().delete(finParameter.getIdParameter());
                                }
                            }
                            ArrayList<Multiplier> listMults = companyFactory1.getMultipliers().selectAll();
                            for (Multiplier mult : listMults) {
                                if (id == mult.getIdCompany()) {
                                    companyFactory1.getMultipliers().delete(mult.getIdMult());
                                }
                            }
                            ArrayList<Deposit> listDeps = companyFactory1.getDeposits().selectAll(id);
                            for (Deposit dep : listDeps) {
                                companyFactory1.getDeposits().delete(dep.getIdDeposit());

                            }
                            companyFactory1.getReports().delete(id);
                        }
                    }
                    companyFactory1.getCompanies().delete(value1[j]);


                }
            }
        }
    }
}

