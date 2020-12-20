package controller.Services;

import database.TableFactory;
import model.*;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportsService {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public ReportsService(String message, ObjectInputStream sois, ObjectOutputStream soos, Socket clientSocket) throws IOException, ClassNotFoundException {
        this.sois = sois;
        this.soos = soos;
        this.clientSocket = clientSocket;
        switch (message) {
            case "formReportForCompanyCost": {
                formCostReport();
            }
            break;
            case "formReportForFoundAnalogs": {
                formAnalogsReport();
            }
            break;
        }
    }

    public void formCostReport() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на формирование отчета по результатам оценки стоимости компании (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        Users owner = (Users) sois.readObject();
        TableFactory companyFactory = new TableFactory();
        Users newUser = companyFactory.getUsers().select(owner);
        ArrayList<CompanyInfo> infoCompanies = companyFactory.getInfo().selectAll();
        ArrayList<Multiplier> listMults = companyFactory.getMultipliers().selectAll();
        ArrayList<EvaluatedCompany> listCompany = companyFactory.getCompanies().selectAll();
        ArrayList<FinParameter> finParameters = companyFactory.getParameters().selectAll();
        String[] data = new String[18];
        int idCompany = 0;
        String nameCompany = "";
        String name = newUser.getLogin();
        float cost = 0;

        for (CompanyInfo companyInf : infoCompanies) {
            int id = newUser.getIdUser();
            if (id == companyInf.getIdUser()) {
                idCompany = companyInf.getIdCompany();
            }
        }


        for (EvaluatedCompany company : listCompany) {
            if (company.getIdCompany() == idCompany) {
                nameCompany = company.getNameCompany();

                for (Multiplier multiplier : listMults) {
                    if (idCompany == multiplier.getIdCompany() && multiplier.getNameMult().equals("Выручка")) {
                        data[0] = String.valueOf(multiplier.getValue());
                    }
                    if (idCompany == multiplier.getIdCompany() && multiplier.getNameMult().equals("Чистая прибыль")) {
                        data[1] = String.valueOf(multiplier.getValue());
                    }
                    if (idCompany == multiplier.getIdCompany() && multiplier.getNameMult().equals("Прибыль до уплаты налогов")) {
                        data[2] = String.valueOf(multiplier.getValue());
                    }
                    if (idCompany == multiplier.getIdCompany() && multiplier.getNameMult().equals("Денежный поток")) {
                        data[3] = String.valueOf(multiplier.getValue());
                    }
                    if (idCompany == multiplier.getIdCompany() && multiplier.getNameMult().equals("Денежный поток до налогообложения")) {
                        data[4] = String.valueOf(multiplier.getValue());
                    }
                    if (idCompany == multiplier.getIdCompany() && multiplier.getNameMult().equals("Дивиденды")) {
                        data[5] = String.valueOf(multiplier.getValue());
                    }

                }
                ArrayList<Deposit> listDeposit = companyFactory.getDeposits().selectAll(idCompany);
                for (Deposit dep : listDeposit) {
                    if (dep.getNameDeposit().equals("Выручка")) {
                        data[6] = String.valueOf(dep.getValue());
                    }
                    if (dep.getNameDeposit().equals("Чистая прибыль")) {
                        data[7] = String.valueOf(dep.getValue());
                    }
                    if (dep.getNameDeposit().equals("Прибыль до уплаты налогов")) {
                        data[8] = String.valueOf(dep.getValue());
                    }
                    if (dep.getNameDeposit().equals("Денежный поток")) {
                        data[9] = String.valueOf(dep.getValue());
                    }
                    if (dep.getNameDeposit().equals("Денежный поток до налогообложения")) {
                        data[10] = String.valueOf(dep.getValue());
                    }
                    if (dep.getNameDeposit().equals("Дивиденды")) {
                        data[11] = String.valueOf(dep.getValue());
                    }
                    cost = cost + dep.getValue();

                }
                for (FinParameter par : finParameters) {
                    if (idCompany == par.getIdCompany() && par.getNameParameter().equals("Выручка")) {
                        data[12] = String.valueOf(par.getValue());
                    }
                    if (idCompany == par.getIdCompany() && par.getNameParameter().equals("Чистая прибыль")) {
                        data[13] = String.valueOf(par.getValue());
                    }
                    if (idCompany == par.getIdCompany() && par.getNameParameter().equals("Прибыль до уплаты налогов")) {
                        data[14] = String.valueOf(par.getValue());
                    }
                    if (idCompany == par.getIdCompany() && par.getNameParameter().equals("Денежный поток")) {
                        data[15] = String.valueOf(par.getValue());
                    }
                    if (idCompany == par.getIdCompany() && par.getNameParameter().equals("Денежный поток до налогообложения")) {
                        data[16] = String.valueOf(par.getValue());
                    }
                    if (idCompany == par.getIdCompany() && par.getNameParameter().equals("Дивиденды")) {
                        data[17] = String.valueOf(par.getValue());
                    }

                }

            }
        }
        EvaluatedCompany evaluatedCompany = new EvaluatedCompany();
        evaluatedCompany.setNameCompany(nameCompany);
        if (cost != 0) {
            soos.writeObject("OK");
            Date date = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
            try {
                XWPFDocument docxModel = new XWPFDocument();
                XWPFParagraph bodyParagraph = docxModel.createParagraph();
                bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun paragraphConfig = bodyParagraph.createRun();
                XWPFRun paragraph = bodyParagraph.createRun();
                paragraphConfig.setText(
                        "Отчет о рассчитанной стоимости компании \"" + nameCompany + "\""
                );
                paragraphConfig.setFontSize(22);
                paragraphConfig.addBreak();
                paragraph.setText("Стоимость: " + cost + ";" + "Аналитик: " + name + ";" + "Дата формирования:" + formatForDateNow.format(date) + ".");

                XWPFTable table = docxModel.createTable(4, 7);
                XWPFTableRow firstRow = table.getRow(0);
                XWPFTableRow secondRow = table.getRow(1);
                XWPFTableRow thirdRow = table.getRow(2);
                XWPFTableRow fourRow = table.getRow(3);
                firstRow.getCell(0).addParagraph().createRun().setText(nameCompany);
                firstRow.getCell(1).addParagraph().createRun().setText("Выручка");
                firstRow.getCell(2).addParagraph().createRun().setText("Чистая прибыль");
                firstRow.getCell(3).addParagraph().createRun().setText("Прибыль до уплаты налогов");
                firstRow.getCell(4).addParagraph().createRun().setText("Денежный поток");
                firstRow.getCell(5).addParagraph().createRun().setText("Денежный поток до налогообложения");
                firstRow.getCell(6).addParagraph().createRun().setText("Дивиденды");
                secondRow.getCell(0).addParagraph().createRun().setText("Мультипликаторы");
                thirdRow.getCell(0).addParagraph().createRun().setText("Вклады");
                fourRow.getCell(0).addParagraph().createRun().setText("Параметры");
                int count = 0;
                for (int i = 1; i < 4; i++) {
                    XWPFTableRow row = table.getRow(i);
                    for (int j = 1; j < 7; j++) {
                        XWPFTableCell cell = row.getCell(j);
                        cell.addParagraph().createRun().setText(data[count]);
                        count++;
                    }
                }
                XWPFRun end = bodyParagraph.createRun();
                end.setText(
                        "В результате проведенного анализа оценки стоимости компании сравнительным методом (методом компаний аналогов) использовались следующие мультипликаторы: "
                );

                if (data[0] != null) {
                    end.setText("Выручки, ");
                }
                if (data[1] != null) {
                    end.setText("Чистой прибыли, ");
                }
                if (data[2] != null) {
                    end.setText("Прибыли до уплаты налогов, ");
                }
                if (data[3] != null) {
                    end.setText("Денежный поток, ");
                }
                if (data[4] != null) {
                    end.setText("Денежного потока до налогообложения, ");
                }
                if (data[5] != null) {
                   end.setText("Дивидендов.");
                }

                File file = new File("D:/My Documents/Курсовой 5 сем/курсач/reports/" + name + "+" + nameCompany + "+Cost.docx");
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream outputStream = new FileOutputStream("D:/My Documents/Курсовой 5 сем/курсач/reports/" + name + "+" + nameCompany + "+Cost.docx");
                docxModel.write(outputStream);
                outputStream.close();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();

            }
              Report rep=new Report();
            rep.setCostReport(cost);
                rep.setDateReport(formatForDateNow.format(date));

            rep.setIdCompany(idCompany);
            if(companyFactory.getReports().find(rep).equals(rep.getDateReport())){
                companyFactory.getReports().update(rep);
            }
            else {
                companyFactory.getReports().insert(rep);
            }



        } else {
            soos.writeObject("This company is already existed");
        }

    }

    public void formAnalogsReport() throws IOException, ClassNotFoundException {
        System.out.println("Запрос к БД на формирование отчета по результатам поиска подходящих компаний аналогов (таблица company), клиент: " + clientSocket.getInetAddress().toString());
        String companyName = (String) sois.readObject();
        Users owner = (Users) sois.readObject();
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
        EvaluatedCompany curCompany = new EvaluatedCompany();
        curCompany.setNameCompany(companyName);
        EvaluatedCompany newCom = companyFactory.getCompanies().select(curCompany);
        Users newUs = companyFactory.getUsers().select(owner);
        String country = "";
        for (Country con : listCountries) {
            if (newCom.getIdCountry() == con.getIdCountry()) {
                country = con.getNameCountry();
            }
        }
        ArrayList<String[]> suitAnalogs = new ArrayList<>();//
        count = 0;
        for (int i = 0; i < names.length; i++) {
            int size1 = Integer.parseInt(names[i][1]);
            int size2 = newCom.getSizeCompany();
            if (names[i][2].equals(newCom.getIndustryCompany()) && names[i][3].equals(country) && ((size2 < 15 && size1 < 15) || (size2 < 100 && size2 > 15 && size1 < 100 && size1 > 15) || (size2 < 250 && size2 > 100 && size1 < 250 && size1 > 100) || (size2 > 250 && size1 > 250))) {
                int flag = 0;
                int aflag = 0;
                for (FinParameter parameter : finParameters) {
                    if (parameter.getIdCompany() == newCom.getIdCompany() && parameter.getNameParameter().equals("Выручка")) {
                        flag++;
                        if (names[i][5] != null) {
                            aflag++;
                        }
                    }
                }
                for (FinParameter parameter : finParameters) {
                    if (parameter.getIdCompany() == newCom.getIdCompany() && parameter.getNameParameter().equals("Чистая прибыль")) {
                        flag++;
                        if (names[i][6] != null) {
                            aflag++;
                        }
                    }
                }
                for (FinParameter parameter : finParameters) {
                    if (parameter.getIdCompany() == newCom.getIdCompany() && parameter.getNameParameter().equals("Прибыль до уплаты налогов")) {
                        flag++;
                        if (names[i][7] != null) {
                            aflag++;
                        }
                    }
                }
                for (FinParameter parameter : finParameters) {
                    if (parameter.getIdCompany() == newCom.getIdCompany() && parameter.getNameParameter().equals("Денежный поток")) {
                        flag++;
                        if (names[i][8] != null) {
                            aflag++;
                        }
                    }
                }
                for (FinParameter parameter : finParameters) {
                    if (parameter.getIdCompany() == newCom.getIdCompany() && parameter.getNameParameter().equals("Денежный поток до налогообложения")) {
                        flag++;
                        if (names[i][9] != null) {
                            aflag++;
                        }
                    }
                }
                for (FinParameter parameter : finParameters) {
                    if (parameter.getIdCompany() == newCom.getIdCompany() && parameter.getNameParameter().equals("Дивиденды")) {
                        flag++;
                        if (names[i][10] != null) {
                            aflag++;
                        }
                    }
                }

                if (aflag>1) {
                    count++;
                    suitAnalogs.add(names[i]);
                }
            }
        }

        if (count != 0) {
            soos.writeObject("OK");
            Date date = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd ");
            try {
                XWPFDocument docxModel = new XWPFDocument();
                XWPFParagraph bodyParagraph = docxModel.createParagraph();
                bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun paragraphConfig = bodyParagraph.createRun();
                XWPFRun paragraph = bodyParagraph.createRun();
                paragraphConfig.setFontSize(25);
                paragraphConfig.setText(
                        "Отчет о найденных компаниях-аналогах для компании \"" + companyName + "\""
                );
                paragraphConfig.addBreak();
                paragraph.setText("Аналитик: " + newUs.getLogin() + ";" + "Дата формирования:" + formatForDateNow.format(date) + ".");
                XWPFTable table = docxModel.createTable(count + 1, 11); //Здесь всё просто, создаем таблицу в документе и работаем с ней.
                // сохраняем модель docx документа в файл
                XWPFTableRow firstRow = table.getRow(0);
                firstRow.getCell(0).addParagraph().createRun().setText("Название компании");
                firstRow.getCell(1).addParagraph().createRun().setText("Отрасль");
                firstRow.getCell(2).addParagraph().createRun().setText("Размер компании");
                firstRow.getCell(3).addParagraph().createRun().setText("Страна");
                firstRow.getCell(4).addParagraph().createRun().setText("Стоимость компании");
                firstRow.getCell(5).addParagraph().createRun().setText("Выручка");
                firstRow.getCell(6).addParagraph().createRun().setText("Чистая прибыль");
                firstRow.getCell(7).addParagraph().createRun().setText("Прибыль до уплаты налогов");
                firstRow.getCell(8).addParagraph().createRun().setText("Денежный поток");
                firstRow.getCell(9).addParagraph().createRun().setText("Денежный поток до налогообложения");
                firstRow.getCell(10).addParagraph().createRun().setText("Дивиденды");
                for (int i = 1; i < count + 1; i++) {
                    XWPFTableRow row = table.getRow(i);
                    for (int j = 0; j < 11; j++) {
                        XWPFTableCell cell = row.getCell(j);
                        cell.addParagraph().createRun().setText(suitAnalogs.get(i - 1)[j]);
                        System.out.println("res" + suitAnalogs.get(i - 1)[j]);
                    }
                }
                XWPFRun end = bodyParagraph.createRun();
                end.setText(
                        "В результате проведенного поиска подходящих компаний-аналогов (для оценки стоимости компании методом компаний аналогов) были найдены следующие компании: "
                );
                end.addBreak();
                for (int i = 0; i < suitAnalogs.size(); i++) {
                    end.setText((i + 1) + ". " + suitAnalogs.get(i)[0]);
                    end.addBreak();
                }

                File file = new File("D:/My Documents/Курсовой 5 сем/курсач/reports/" + newUs.getLogin() + "+" + companyName + "+Analogs.docx");
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream outputStream = new FileOutputStream("D:/My Documents/Курсовой 5 сем/курсач/reports/" + newUs.getLogin() + "+" + companyName + "+Analogs.docx");
                docxModel.write(outputStream);
                outputStream.close();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();

            }
        } else {
            soos.writeObject("This company is already existed");
        }
    }
}
