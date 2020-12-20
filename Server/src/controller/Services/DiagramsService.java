package controller.Services;

import database.TableFactory;
import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class DiagramsService {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public DiagramsService (String message, ObjectInputStream sois, ObjectOutputStream soos, Socket clientSocket) throws IOException, ClassNotFoundException {
        this.sois = sois;
        this.soos = soos;
        this.clientSocket = clientSocket;
        switch (message) {
            case "formRoundDiagram": {
               formRoundDiagram();
            }
            break;
            case "formGrafic": {
                formGrafic();
            }
            break;
        }
    }
    public void formRoundDiagram() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на просмотр компаний-аналогов в виде диаграммы (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        Users owner = (Users) sois.readObject();
        TableFactory companyFactory = new TableFactory();
        Users newUser = companyFactory.getUsers().select(owner);
        ArrayList<AnalogCompany> listCompanies = companyFactory.getAnalogCompanies().selectAll();
        ArrayList<FinParameter> finParameters = companyFactory.getParameters().selectAll();
        ArrayList<Country> listCountries = companyFactory.getCountries().selectAll();
        String[][] names = new String[listCompanies.size()][12];
        int count = 0;
        ArrayList<EvaluatedCompany> list = companyFactory.getCompanies().selectAll();
        ArrayList<CompanyInfo> info = companyFactory.getInfo().selectAll();
        ArrayList<EvaluatedCompany> listOwner = new ArrayList<>();
        for (CompanyInfo inf : info) {
            if (newUser.getIdUser() == inf.getIdUser()) {
                int idCompany = inf.getIdCompany();
                for (EvaluatedCompany ev : list) {
                    if (idCompany == ev.getIdCompany()) {
                        listOwner.add(ev);
                    }
                }
            }
        }
        String[][] ownerCompanies = new String[listOwner.size()][10];
        int counter = 0;
        for (EvaluatedCompany eva : listOwner) {
            ownerCompanies[counter][0] = eva.getNameCompany();
            ownerCompanies[counter][1] = String.valueOf(eva.getSizeCompany());
            ownerCompanies[counter][2] = eva.getIndustryCompany();
            for (Country con : listCountries) {
                if (eva.getIdCountry() == con.getIdCountry()) {
                    ownerCompanies[counter][3] = con.getNameCountry();
                }
            }
            for (FinParameter parameter : finParameters) {
                if (parameter.getIdCompany() == eva.getIdCompany()) {
                    if (parameter.getNameParameter().equals("Выручка")) {
                        ownerCompanies[counter][4] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Чистая прибыль")) {
                        ownerCompanies[counter][5] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Прибыль до уплаты налогов")) {
                        ownerCompanies[counter][6] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Денежный поток")) {
                        ownerCompanies[counter][7] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Денежный поток до налогообложения")) {
                        ownerCompanies[counter][8] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Дивиденды")) {
                        ownerCompanies[counter][9] = String.valueOf(parameter.getValue());
                    }
                }

            }
            counter++;
        }
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

        ArrayList<String[]> res = new ArrayList<>();
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (int j = 0; j < ownerCompanies.length; j++) {
            count = 0;
            for (int i = 0; i < names.length; i++) {
                int size1 = Integer.parseInt(names[i][1]);
                int size2 = Integer.parseInt(ownerCompanies[j][1]);
                if (names[i][2].equals(ownerCompanies[j][2]) && names[i][3].equals(ownerCompanies[j][3]) && ((size2 < 15 && size1 < 15) || (size2 < 100 && size2 > 15 && size1 < 100 && size1 > 15) || (size2 < 250 && size2 > 100 && size1 < 250 && size1 > 100) || (size2 > 250 && size1 > 250))) {
                    int flag = 0;
                    int aflag = 0;

                    if (ownerCompanies[j][4] != null) {
                        flag++;
                        if (names[i][5] != null) {
                            aflag++;
                        }
                    }

                    if (ownerCompanies[j][5] != null) {
                        flag++;
                        if (names[i][6] != null) {
                            aflag++;
                        }
                    }

                    if (ownerCompanies[j][6] != null) {
                        flag++;
                        if (names[i][7] != null) {
                            aflag++;
                        }
                    }
                    if (ownerCompanies[j][7] != null) {
                        flag++;
                        if (names[i][8] != null) {
                            aflag++;
                        }
                    }

                    if (ownerCompanies[j][8] != null) {
                        flag++;
                        if (names[i][9] != null) {
                            aflag++;
                        }
                    }
                    if (ownerCompanies[j][9] != null) {
                        flag++;
                        if (names[i][10] != null) {
                            aflag++;
                        }
                    }
                    if (aflag>1) {
                        count++;
                        names[i][11] = ownerCompanies[j][0];

                        res.add(names[i]);
                    }
                }
            }

        }
        if (!res.isEmpty()) {
            soos.writeObject("OK");
            soos.writeObject(res);
        } else soos.writeObject("No companies!");
    }
    public void formGrafic()throws IOException, ClassNotFoundException{
        System.out.println("Запрос к БД на просмотр анализа стоимости компании в виде графика (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        Users owner = (Users) sois.readObject();
        TableFactory companyFactory = new TableFactory();
        Users newUser = companyFactory.getUsers().select(owner);
        ArrayList<FinParameter> finParameters = companyFactory.getParameters().selectAll();
        ArrayList<Country> listCountries = companyFactory.getCountries().selectAll();
        float cost = 0;
        ArrayList<EvaluatedCompany> list = companyFactory.getCompanies().selectAll();
        ArrayList<CompanyInfo> info = companyFactory.getInfo().selectAll();
        ArrayList<EvaluatedCompany> listOwner = new ArrayList<>();
        for (CompanyInfo inf : info) {
            if (newUser.getIdUser() == inf.getIdUser()) {
                int idCompany = inf.getIdCompany();
                for (EvaluatedCompany ev : list) {
                    if (idCompany == ev.getIdCompany()) {
                        listOwner.add(ev);
                    }
                }
            }
        }
        String[][] ownerCompanies = new String[listOwner.size()][11];
        int counter = 0;
        for (EvaluatedCompany eva : listOwner) {
            ownerCompanies[counter][0] = eva.getNameCompany();
            ownerCompanies[counter][1] = String.valueOf(eva.getSizeCompany());
            ownerCompanies[counter][2] = eva.getIndustryCompany();
            for (Country con : listCountries) {
                if (eva.getIdCountry() == con.getIdCountry()) {
                    ownerCompanies[counter][3] = con.getNameCountry();
                }
            }
            for (FinParameter parameter : finParameters) {
                if (parameter.getIdCompany() == eva.getIdCompany()) {
                    if (parameter.getNameParameter().equals("Выручка")) {
                        ownerCompanies[counter][4] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Чистая прибыль")) {
                        ownerCompanies[counter][5] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Прибыль до уплаты налогов")) {
                        ownerCompanies[counter][6] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Денежный поток")) {
                        ownerCompanies[counter][7] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Денежный поток до налогообложения")) {
                        ownerCompanies[counter][8] = String.valueOf(parameter.getValue());
                    }
                    if (parameter.getNameParameter().equals("Дивиденды")) {
                        ownerCompanies[counter][9] = String.valueOf(parameter.getValue());
                    }
                }

            }
            ArrayList<Deposit> listDeposit = companyFactory.getDeposits().selectAll(eva.getIdCompany());
            for (Deposit dep : listDeposit) {
                cost = cost + dep.getValue();
            }
            ownerCompanies[counter][10] = String.valueOf(cost);
            counter++;
        }
        ArrayList<String[]> data = new ArrayList<>();
        ArrayList<Report> reps=companyFactory.getReports().selectAll();
        for (int i = 0; i < ownerCompanies.length; i++) {
            int id=0;
            for (EvaluatedCompany eva : listOwner) {
                if(eva.getNameCompany().equals(ownerCompanies[i][0])){
                    id=eva.getIdCompany();
                }
            }
           for(Report rep:reps){
               if(rep.getIdCompany()==id){
                   String[] mas = {ownerCompanies[i][0], rep.getDateReport(), String.valueOf(rep.getCostReport())};
                   data.add(mas);
               }
           }
//
        }
        System.out.println("sds"+data.size());
        if (!data.isEmpty()) {
            soos.writeObject("OK");
            soos.writeObject(data);
        } else soos.writeObject("No companies!");

    }

}
