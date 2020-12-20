package controller.Services;

import database.TableFactory;
import model.Deposit;
import model.EvaluatedCompany;
import model.FinParameter;
import model.Multiplier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MethodService {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public MethodService(String message, ObjectInputStream sois, ObjectOutputStream soos, Socket clientSocket) throws IOException, ClassNotFoundException {
        this.sois = sois;
        this.soos = soos;
        this.clientSocket = clientSocket;
        switch (message) {
            case "saveMults": {
                saveMultipliers();
            }
            break;
            case "CountCost": {
                countCost();
            }
            break;
            case "searchAnalogs": {
                searchAnalogs();
            }
            break;
        }
    }

    public void saveMultipliers() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на добавление мультипликаторов (таблица multiplier), клиент: " + clientSocket.getInetAddress().toString());

        String[] proceeds = (String[]) sois.readObject();
        String[] income = (String[]) sois.readObject();
        String[] incomeBeforeTax = (String[]) sois.readObject();
        String[] cashFlow = (String[]) sois.readObject();
        String[] cashFlowBeforeTax = (String[]) sois.readObject();
        String[] dividend = (String[]) sois.readObject();
        String[] cost = (String[]) sois.readObject();
        String[] names = (String[]) sois.readObject();
        String companyName = (String) sois.readObject();
        int size = proceeds.length;
        float[] multProceeds = new float[size];
        float[] multIncome = new float[size];
        float[] multIncomeBeforeTax = new float[size];
        float[] multCashFlow = new float[size];
        float[] multCashFlowBeforeTax = new float[size];
        float[] multDividend = new float[size];
        int[] num=new int[6];

        for (int i = 0; i < size; i++) {
            if (proceeds[i] != null) {
                multProceeds[i] = Float.parseFloat(cost[i]) / Float.parseFloat(proceeds[i]);
            num[0]++;
            }
            if (income[i] != null) {
                multIncome[i] = Float.parseFloat(cost[i]) / Float.parseFloat(income[i]);
                num[1]++;
            }
            if (incomeBeforeTax[i] != null) {
                multIncomeBeforeTax[i] = Float.parseFloat(cost[i]) / Float.parseFloat(incomeBeforeTax[i]);
                num[2]++;
            }
            if (cashFlow[i] != null){
                multCashFlow[i] = Float.parseFloat(cost[i]) / Float.parseFloat(cashFlow[i]);
            num[3]++;
            }
            if (cashFlowBeforeTax[i] != null) {
                multCashFlowBeforeTax[i] = Float.parseFloat(cost[i]) / Float.parseFloat(cashFlowBeforeTax[i]);
            num[4]++;
            }
            if (dividend[i] != null){
                multDividend[i] = Float.parseFloat(cost[i]) / Float.parseFloat(dividend[i]);
            num[5]++;
            }

        }
        String[][] data = new String[size][7];
        for (int i = 0; i < size; i++) {
            data[i][0] = names[i];
            data[i][1] = String.valueOf(multProceeds[i]);
            data[i][2] = String.valueOf(multIncome[i]);
            data[i][3] = String.valueOf(multIncomeBeforeTax[i]);
            data[i][4] = String.valueOf(multCashFlow[i]);
            data[i][5] = String.valueOf(multCashFlowBeforeTax[i]);
            data[i][6] = String.valueOf(multDividend[i]);

        }
        soos.writeObject(data);
        String[][] avgMults = new String[1][7];
        float[] sum = new float[6];
        for (int i = 0; i < size; i++) {
            sum[0] = sum[0] + multProceeds[i];
            sum[1] = sum[1] + multIncome[i];
            sum[2] = sum[2] + multIncomeBeforeTax[i];
            sum[3] = sum[3] + multCashFlow[i];
            sum[4] = sum[4] + multCashFlowBeforeTax[i];
            sum[5] = sum[5] + multDividend[i];
        }
        for (int j = 0; j < 6; j++) {
            if(num[j]!=0)
            avgMults[0][j] = String.valueOf(sum[j] / (num[j]));
            else avgMults[0][j]=String.valueOf(0);
        }
        soos.writeObject(avgMults);
        int idCompany = 0;
        TableFactory companyFactory = new TableFactory();
        ArrayList<EvaluatedCompany> listCompanies = companyFactory.getCompanies().selectAll();
        ArrayList<Multiplier> listMults = companyFactory.getMultipliers().selectAll();
        for (EvaluatedCompany company : listCompanies) {
            if (companyName.equals(company.getNameCompany())) {
                idCompany = company.getIdCompany();
            }
        }
        for (int i = 0; i < 6; i++) {
            Multiplier mult = new Multiplier();
            mult.setIdCompany(idCompany);
            mult.setValue(Float.parseFloat(avgMults[0][i]));
            if (mult.getValue() != 0) {
                switch (i) {
                    case 0:
                        mult.setNameMult("Выручка");
                        break;
                    case 1:
                        mult.setNameMult("Чистая прибыль");
                        break;
                    case 2:
                        mult.setNameMult("Прибыль до уплаты налогов");
                        break;
                    case 3:
                        mult.setNameMult("Денежный поток");
                        break;
                    case 4:
                        mult.setNameMult("Денежный поток до налогообложения");
                        break;
                    case 5:
                        mult.setNameMult("Дивиденды");
                        break;
                }
                int flag = 0;
                for (Multiplier multiplier : listMults) {
                    if (idCompany == multiplier.getIdCompany() && mult.getNameMult().equals(multiplier.getNameMult())) {
                        mult.setIdMult(multiplier.getIdMult());
                        flag++;
                    }
                }
                if (flag == 0)
                    companyFactory.getMultipliers().insert(mult);
                else {
                    companyFactory.getMultipliers().update(mult);
                }
            }
        }


    }

    public void countCost() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на расчет стоимости компании (таблица multiplier), клиент: " + clientSocket.getInetAddress().toString());
        TableFactory factory = new TableFactory();
        String title = (String) sois.readObject();
        EvaluatedCompany curCompany = new EvaluatedCompany();
        curCompany.setNameCompany(title);
        ArrayList<Multiplier> multipliers = factory.getMultipliers().selectAll();
        EvaluatedCompany company = factory.getCompanies().select(curCompany);
        ArrayList<Deposit> listDeposits1 = factory.getDeposits().selectAll(company.getIdCompany());
        ArrayList<FinParameter> listParameters = factory.getParameters().selectAll();
        for (Multiplier mult : multipliers) {
            if (company.getIdCompany() == mult.getIdCompany()) {
                switch (mult.getNameMult()) {
                    case "Выручка": {
                        Deposit deposit = new Deposit();
                        deposit.setNameDeposit(mult.getNameMult());
                        deposit.setIdCompany(mult.getIdCompany());
                        float weight = 0.3f;
                        float value = weight * mult.getValue();
                        for (FinParameter parameter : listParameters) {
                            if (parameter.getIdCompany() == mult.getIdCompany() && parameter.getNameParameter().equals("Выручка")) {
                                value = value * parameter.getValue();
                                deposit.setValue(value);
                                if (!listDeposits1.isEmpty()) {
                                    int flag = 0;
                                    for (Deposit dep : listDeposits1) {
                                        if (dep.getNameDeposit().equals("Выручка")) {
                                            deposit.setIdDeposit(dep.getIdDeposit());
                                            flag++;
                                        }

                                    }
                                    if (flag == 0) factory.getDeposits().insert(deposit);
                                    else {
                                        factory.getDeposits().update(deposit);
                                    }
                                } else factory.getDeposits().insert(deposit);
                            }
                        }

                    }
                    break;
                    case "Чистая прибыль": {
                        Deposit deposit = new Deposit();
                        deposit.setNameDeposit(mult.getNameMult());
                        deposit.setIdCompany(mult.getIdCompany());
                        float weight = 0.37f;
                        float value = weight * mult.getValue();
                        for (FinParameter parameter : listParameters) {
                            if (parameter.getIdCompany() == mult.getIdCompany() && parameter.getNameParameter().equals("Чистая прибыль")) {
                                value = value * parameter.getValue();
                                deposit.setValue(value);
                                if (!listDeposits1.isEmpty()) {
                                    int flag = 0;
                                    for (Deposit dep : listDeposits1) {
                                        if (dep.getNameDeposit().equals("Чистая прибыль")) {
                                            deposit.setIdDeposit(dep.getIdDeposit());
                                            flag++;
                                        }

                                    }
                                    if (flag == 0) factory.getDeposits().insert(deposit);
                                    else {
                                        factory.getDeposits().update(deposit);
                                    }
                                } else factory.getDeposits().insert(deposit);
                            }
                        }
                    }
                    break;
                    case "Прибыль до уплаты налогов": {
                        Deposit deposit = new Deposit();
                        deposit.setNameDeposit(mult.getNameMult());
                        deposit.setIdCompany(mult.getIdCompany());
                        float weight = 0.3f;
                        float value = weight * mult.getValue();
                        for (FinParameter parameter : listParameters) {
                            if (parameter.getIdCompany() == mult.getIdCompany() && parameter.getNameParameter().equals("Прибыль до уплаты налогов")) {
                                value = value * parameter.getValue();
                                deposit.setValue(value);
                                if (!listDeposits1.isEmpty()) {
                                    int flag = 0;
                                    for (Deposit dep : listDeposits1) {
                                        if (dep.getNameDeposit().equals("Прибыль до уплаты налогов")) {
                                            deposit.setIdDeposit(dep.getIdDeposit());
                                            flag++;
                                        }

                                    }
                                    if (flag == 0) factory.getDeposits().insert(deposit);
                                    else {
                                        factory.getDeposits().update(deposit);
                                    }
                                } else factory.getDeposits().insert(deposit);
                            }
                        }
                    }
                    break;
                    case "Денежный поток": {
                        Deposit deposit = new Deposit();
                        deposit.setNameDeposit(mult.getNameMult());
                        deposit.setIdCompany(mult.getIdCompany());
                        float weight = 0.3f;
                        float value = weight * mult.getValue();
                        for (FinParameter parameter : listParameters) {
                            if (parameter.getIdCompany() == mult.getIdCompany() && parameter.getNameParameter().equals("Денежный поток")) {
                                value = value * parameter.getValue();
                                deposit.setValue(value);
                                if (!listDeposits1.isEmpty()) {
                                    int flag = 0;
                                    for (Deposit dep : listDeposits1) {
                                        if (dep.getNameDeposit().equals("Денежный поток")) {
                                            deposit.setIdDeposit(dep.getIdDeposit());
                                            flag++;
                                        }

                                    }
                                    if (flag == 0) factory.getDeposits().insert(deposit);
                                    else {
                                        factory.getDeposits().update(deposit);
                                    }
                                } else factory.getDeposits().insert(deposit);
                            }
                        }
                    }
                    break;
                    case "Денежный поток до налогообложения": {
                        Deposit deposit = new Deposit();
                        deposit.setNameDeposit(mult.getNameMult());
                        deposit.setIdCompany(mult.getIdCompany());
                        float weight = 0.3f;
                        float value = weight * mult.getValue();
                        for (FinParameter parameter : listParameters) {
                            if (parameter.getIdCompany() == mult.getIdCompany() && parameter.getNameParameter().equals("Денежный поток до налогообложения")) {
                                value = value * parameter.getValue();
                                deposit.setValue(value);
                                if (!listDeposits1.isEmpty()) {
                                    int flag = 0;
                                    for (Deposit dep : listDeposits1) {
                                        if (dep.getNameDeposit().equals("Денежный поток до налогообложения")) {
                                            deposit.setIdDeposit(dep.getIdDeposit());
                                            flag++;
                                        }

                                    }
                                    if (flag == 0) factory.getDeposits().insert(deposit);
                                    else {
                                        factory.getDeposits().update(deposit);
                                    }
                                } else factory.getDeposits().insert(deposit);
                            }
                        }
                    }
                    break;
                    case "Дивиденды": {
                        Deposit deposit = new Deposit();
                        deposit.setNameDeposit(mult.getNameMult());
                        deposit.setIdCompany(mult.getIdCompany());
                        float weight = 0.3f;
                        float value = weight * mult.getValue();
                        for (FinParameter parameter : listParameters) {
                            if (parameter.getIdCompany() == mult.getIdCompany() && parameter.getNameParameter().equals("Дивиденды")) {
                                value = value * parameter.getValue();
                                deposit.setValue(value);
                                if (!listDeposits1.isEmpty()) {
                                    int flag = 0;
                                    for (Deposit dep : listDeposits1) {
                                        if (dep.getNameDeposit().equals("Дивиденды")) {
                                            deposit.setIdDeposit(dep.getIdDeposit());
                                            flag++;
                                        }
                                    }
                                    if (flag == 0) factory.getDeposits().insert(deposit);
                                    else {
                                        factory.getDeposits().update(deposit);
                                    }
                                } else factory.getDeposits().insert(deposit);
                            }
                        }
                    }
                    break;
                }
            }
        }
        ArrayList<Deposit> listDeposits = factory.getDeposits().selectAll(company.getIdCompany());
        float costCompany = 0;
        int flag = 0;
        for (Deposit deposit : listDeposits) {
            if (company.getIdCompany() == deposit.getIdCompany()) {
                flag++;
            }
            costCompany = costCompany + deposit.getValue();
        }
        soos.writeObject(costCompany);

    }

    public void searchAnalogs() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на поиск подходящих компаний-аналогов (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        ArrayList<String[]> result = new ArrayList<>();
        int count = 0;
        String[][] analogs = AnalogCompaniesService.listAnalogCompanies();
        String[][] company = (String[][]) sois.readObject();
        for (int i = 0; i < analogs.length; i++) {
            int size1 = Integer.parseInt(analogs[i][1]);
            int size2 = Integer.parseInt(company[0][1]);
            if (analogs[i][2].equals(company[0][2]) && analogs[i][3].equals(company[0][3]) && ((size2 < 15 && size1 < 15) || (size2 < 100 && size2 > 15 && size1 < 100 && size1 > 15) || (size2 < 250 && size2 > 100 && size1 < 250 && size1 > 100) || (size2 > 250 && size1 > 250))) {
                int flag = 0;
                int aflag = 0;
                if (company[0][9] != null) {
                    flag++;
                    if (analogs[i][10] != null) {
                        aflag++;
                    }
                }
                if (company[0][4] != null) {
                    flag++;
                    if (analogs[i][5] != null) {

                        aflag++;
                    }
                }
                if (company[0][5] != null) {
                    flag++;
                    if (analogs[i][6] != null) {

                        aflag++;
                    }

                }
                if (company[0][6] != null) {
                    flag++;
                    if (analogs[i][7] != null) {

                        aflag++;
                    }
                }
                if (company[0][7] != null) {
                    flag++;
                    if (analogs[i][8] != null) {

                        aflag++;
                    }
                }
                if (company[0][8] != null) {
                    flag++;
                    if (analogs[i][9] != null) {

                        aflag++;
                    }
                }
              //  System.out.println("flag" + flag + "; aflag" + aflag);
                if (aflag > 1) {
                    count++;
                    result.add(analogs[i]);

                }
            }
        }
        String[][] res = new String[result.size()][11];
        int size = 0;
        for (String[] resul : result) {
            res[size][0] = resul[0];
            res[size][1] = resul[1];
            res[size][2] = resul[2];
            res[size][3] = resul[3];
            res[size][4] = resul[4];
            res[size][5] = resul[5];
            res[size][6] = resul[6];
            res[size][7] = resul[7];
            res[size][8] = resul[8];
            res[size][9] = resul[9];
            res[size][10] = resul[10];
            size++;
        }
        if (count == 0) {
            soos.writeObject("No companies!");
        } else {
            soos.writeObject("ok");
            soos.writeObject(res);

        }

    }

}
