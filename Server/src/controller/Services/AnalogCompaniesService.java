package controller.Services;

import database.TableFactory;
import model.AnalogCompany;
import model.Country;
import model.FinParameter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class AnalogCompaniesService {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public AnalogCompaniesService(String message, ObjectInputStream sois, ObjectOutputStream soos, Socket clientSocket) throws IOException, ClassNotFoundException {
        this.sois = sois;
        this.soos = soos;
        this.clientSocket = clientSocket;
        switch (message) {
            case "addAnalogCompany": {
                add();
            }
            break;
            case "deleteAnalogCompany": {
                deleteAnalogCompany();
            }
            break;
            case "editAnalogCompany": {
                edit();
            }
            break;
            case "showAnalogCompanies": {
                showAnalogCompanies();
            }
            break;
            case "FilterAnalogsIndustry": {
                filterAnalogsByIndustry();
            }
            break;


        }
    }

    public void add() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на добавление компании-аналога(таблица company), клиент: " + clientSocket.getInetAddress().toString());
        AnalogCompany company = (AnalogCompany) sois.readObject();
        TableFactory sqlFactory2 = new TableFactory();
        if (sqlFactory2.getAnalogCompanies().find(company).equals("")) {
            soos.writeObject("OK");
            sqlFactory2.getAnalogCompanies().insert(company);
            AnalogCompany newCompany = sqlFactory2.getAnalogCompanies().select(company);
            ArrayList<FinParameter> companyPars = company.getParameters();
            for (FinParameter par : companyPars) {
                par.setIdCompany(newCompany.getIdCompany());
                sqlFactory2.getParameters().insert(par);
            }
        } else {
            soos.writeObject("This company is already existed");
        }
    }

    public void deleteAnalogCompany() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на удаление компании-аналога(таблица company), клиент: " + clientSocket.getInetAddress().toString());
        String[] value = (String[]) sois.readObject();
        TableFactory sqlFactory = new TableFactory();
        ArrayList<AnalogCompany> list = sqlFactory.getAnalogCompanies().selectAll();
        ArrayList<FinParameter> listPars = sqlFactory.getParameters().selectAll();
        //int id = sqlFactory.getCompanies().findAnalogCompany(value);
        for (int count = 0; count < value.length; count++) {
            for (AnalogCompany an : list) {
                if (value[count].equals(an.getNameCompany())) {
                    int id = an.getIdCompany();
                    for (FinParameter finParameter : listPars) {
                        if (id == finParameter.getIdCompany()) {
                            sqlFactory.getParameters().delete(finParameter.getIdParameter());
                        }
                    }
                    sqlFactory.getAnalogCompanies().delete(value[count]);
                }
            }
        }

    }

    public void edit() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на обновление компании-аналога(таблица company), клиент: " + clientSocket.getInetAddress().toString());
        AnalogCompany company = (AnalogCompany) sois.readObject();
        String oldName = (String) sois.readObject();
        TableFactory sqlFactory2 = new TableFactory();
        ArrayList<FinParameter> listPars = sqlFactory2.getParameters().selectAll();
        if (sqlFactory2.getCompanies().findEditedCompany(company, oldName).equals("")) {
            soos.writeObject("OK");
            sqlFactory2.getAnalogCompanies().updateAnalogCompany(company, oldName);
            AnalogCompany newCompany = sqlFactory2.getAnalogCompanies().select(company);
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

    public void showAnalogCompanies() throws IOException {
        String[][] names = listAnalogCompanies();
        soos.writeObject(names);


    }

    public void filterAnalogsByIndustry() throws IOException {
        TableFactory factory = new TableFactory();
        ArrayList<String> industries = factory.getAnalogCompanies().selectIndustriesAnalogs();
        String[][] industry = new String[industries.size()][1];
        for (int i = 0; i < industries.size(); i++) {
            industry[i][0] = industries.get(i);
        }
        soos.writeObject(industry);

    }

    public static String[][] listAnalogCompanies(){
        TableFactory companyFactory = new TableFactory();
        ArrayList<AnalogCompany> listCompanies = companyFactory.getAnalogCompanies().selectAll();
        ArrayList<FinParameter> finParameters = companyFactory.getParameters().selectAll();
        ArrayList<Country> listCountries = companyFactory.getCountries().selectAll();
        String[][] names = new String[listCompanies.size()][11];
        int count = 0;

        for (AnalogCompany company : listCompanies) {
            names[count][0] = company.getNameCompany();
            names[count][1] = String.valueOf(company.getSizeCompany());
            names[count][2] = company.getIndustryCompany();
            for (Country con : listCountries) {
                if (company.getIdCountry() == con.getIdCountry()) {
                    names[count][3] = con.getNameCountry();
                }
            }
            names[count][4] = String.valueOf(company.getCostCompany());
            for (FinParameter parameter : finParameters) {
                if (parameter.getIdCompany() == company.getIdCompany()) {
                    if (parameter.getNameParameter().equals("Выручка")) {
                        names[count][5] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Чистая прибыль")) {
                        names[count][6] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Прибыль до уплаты налогов")) {
                        names[count][7] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Денежный поток")) {
                        names[count][8] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Денежный поток до налогообложения")) {
                        names[count][9] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Дивиденды")) {
                        names[count][10] = String.valueOf(parameter.getValue());
                    }
                }
            }
            count++;
        }
        return names;
    }
}
