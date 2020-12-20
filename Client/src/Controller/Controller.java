package Controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import views.*;
import model.*;
import model.Client.Client;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller implements ActionListener {
    private static Controller instance;
    private EnterDialog objEnterDialog;
    private UsersRegistration objRegistrationUsers;
    private Client client;
    private AdminMenu objAdminMenu;
    private OwnerMenu objOwnerMenu;
    private AddOwnerCompany objOwnerCompany;
    private EditCompany objEditCompany;
    private AnalystMenu objAnalyst;
    public String loginUser;
    public String passUser;

    private Controller() {
        client = new Client("127.0.0.1", "1234");
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void initialize(EnterDialog obj) {
        objEnterDialog = obj;
    }

    public void initialize(UsersRegistration obj) {
        objRegistrationUsers = obj;
    }

    public void initialize(AnalystMenu obj) {
        objAnalyst = obj;
    }

    public void initialize(AdminMenu obj) {
        objAdminMenu = obj;
    }

    public void initialize(OwnerMenu obj) {
        objOwnerMenu = obj;
    }

    public void initialize(AddOwnerCompany obj) {
        objOwnerCompany = obj;
    }

    public void initialize(EditCompany obj) {
        objEditCompany = obj;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Вход")) {
            autorization();
        }
        if (e.getActionCommand().equals("filterAnalysts")) {
            Users owner = new Users();
            owner.setLogin(loginUser);
            owner.setPassword(passUser);
            DefaultTableModel model = (DefaultTableModel) objOwnerMenu.getTableCommand().getModel();
            model.setRowCount(0);
            repaintInfoCommandFilter(owner);
        }
        if (e.getActionCommand().equals("filterIndustries")) {

            DefaultTableModel model = (DefaultTableModel) objAnalyst.getTableAnalogs().getModel();
            model.setRowCount(0);
            repaintFilterIndustries();
        }
        if (e.getActionCommand().equals("FilterRolesAdmin")) {
            Users owner = new Users();
            owner.setLogin(loginUser);
            owner.setPassword(passUser);
            DefaultTableModel model = (DefaultTableModel) objAdminMenu.getTableUsers().getModel();
            model.setRowCount(0);
            repaintAdminUserFilter();
        }
        if (e.getActionCommand().equals("SortCompaniesAdmin")) {
            Users owner = new Users();
            owner.setLogin(loginUser);
            owner.setPassword(passUser);
            DefaultTableModel model = (DefaultTableModel) objAdminMenu.getTableCompanies().getModel();
            model.setRowCount(0);
            repaintAdminCompaniesSort();
        }
        if (e.getActionCommand().equals("showRegistrationFrame")) {
            objEnterDialog.dispose();
            UsersRegistration formReg = null;

            formReg = new UsersRegistration();
            repaintRegistrationCountries();
            try {
                countriesPhone();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            formReg.setTitle("Регистрация пользователя");
            formReg.pack();
            formReg.setLocationRelativeTo(null);
            formReg.setVisible(true);
        }
        if (e.getActionCommand().equals("backToAutorization")) {
            objRegistrationUsers.dispose();
            EnterDialog dialog = new EnterDialog();
            dialog.setTitle("Авторизация");
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
        if (e.getActionCommand().equals("registrationUsers")) {
            int error = 0;
            if (
                    objRegistrationUsers.getLogin().getText().equals("") ||
                            objRegistrationUsers.getPassword().getText().equals("") ||
                            objRegistrationUsers.getSurnameField().getText().equals("") ||
                            objRegistrationUsers.getNameField().getText().equals("") ||
                            objRegistrationUsers.getPhoneField().getText().indexOf("_")!=-1) {
                JOptionPane.showMessageDialog(objRegistrationUsers, "Заполните поля!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                error++;
            } else {
                Users user = new Users();

                user.setLogin(objRegistrationUsers.getLogin().getText());
                user.setPassword(objRegistrationUsers.getPassword().getText());
                user.setRole("Владелец");
                user.setSurname(objRegistrationUsers.getSurnameField().getText());
                user.setName(objRegistrationUsers.getNameField().getText());
                user.setPhone(objRegistrationUsers.getPhoneField().getText());
                if (error == 0) {
                    client.sendMessage("Users");
                    client.sendMessage("registrationUser");
                    client.sendObject(user);
                    String mes = "";
                    try {
                        mes = client.readMessage();
                    } catch (IOException ex) {
                        System.out.println("Error in reading");
                    }
                    if (mes.equals("This user is already existed"))
                        JOptionPane.showMessageDialog(objRegistrationUsers, "Такой пользователь уже существует!", "Ошибка регистрации", JOptionPane.ERROR_MESSAGE);

                    else {
                        JOptionPane.showMessageDialog(objRegistrationUsers, "Пользователь успешно зарегистрирован", "Регистрация пользователя", JOptionPane.PLAIN_MESSAGE);
                        objRegistrationUsers.dispose();
                        EnterDialog dialog = new EnterDialog();
                        dialog.setTitle("Авторизация");
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                }
            }
        }
        if (e.getActionCommand().equals("exitUser")) {
            client.sendMessage("exit");
            try {
                if (client.readMessage().equals("OK")) {
                    client.close();
                    objOwnerMenu.dispose();
                    EnterDialog dialog = new EnterDialog();
                    dialog.setTitle("Авторизация");
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);

                }
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }
        }
        if (e.getActionCommand().equals("deleteUser")) {
            client.sendMessage("Users");
            String[] sel = objAdminMenu.getSelected();
            client.sendMessage("deleteUser");
            client.sendObject(sel);
            JOptionPane.showMessageDialog(objAdminMenu, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintPassenger();
            objAdminMenu.getDeleteButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("countriesPhone")) {
            try {
                countriesPhone();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }
        if (e.getActionCommand().equals("deleteAnalyst")) {
            String sel[] = objOwnerMenu.getSelectedAnalyst();
            client.sendMessage("Users");
            Users usr = new Users();
            usr.setLogin(loginUser);
            usr.setPassword(passUser);
            client.sendMessage("deleteUser");
            client.sendObject(sel);
            JOptionPane.showMessageDialog(objOwnerMenu, "Удаление выполнено успешно!", "Удаление пользователя", JOptionPane.PLAIN_MESSAGE);
            repaintInfoCommand(usr);
            objOwnerMenu.getDeleteAnalysts().setEnabled(false);
        }
        if (e.getActionCommand().equals("showAddCompanyFrame")) {

            AddOwnerCompany addcomp = new AddOwnerCompany();
            addcomp.setTitle("Добавление компании");
            addcomp.pack();
            addcomp.setLocationRelativeTo(null);
            addcomp.setVisible(true);
            repaintCountries();
            System.out.println(loginUser + "     ////" + passUser);
        }
        if (e.getActionCommand().equals("showAddCompanyAnalogFrame")) {

            AddOwnerCompany addcomp = new AddOwnerCompany();
            addcomp.setTitle("Добавление компании-аналога");
            addcomp.pack();
            addcomp.setLocationRelativeTo(null);
            addcomp.setVisible(true);
            addcomp.visibleCost();
            repaintCountries();
        }
        if (e.getActionCommand().equals("addOwnerCompany")) {
            if (
                            objOwnerCompany.getNameCompany().getText().equals("") ||
                            objOwnerCompany.getIndustry().getSelectedItem().equals("Не выбрано") ||
                            objOwnerCompany.getCountryBox().getSelectedItem().equals("Не выбрано") ||
                            objOwnerCompany.getSizeCompany().getText().equals("")||
                                    (!objOwnerCompany.getTf1().equals("no") && objOwnerCompany.getTf1().equals("")) ||
                                    (!objOwnerCompany.getTf2().equals("no") && objOwnerCompany.getTf2().equals("")) ||
                                    (!objOwnerCompany.getTf3().equals("no") && objOwnerCompany.getTf3().equals("")) ||
                                    (!objOwnerCompany.getTf4().equals("no") && objOwnerCompany.getTf4().equals("")) ||
                                    (!objOwnerCompany.getTf5().equals("no") && objOwnerCompany.getTf5().equals("")) ||
                                    (!objOwnerCompany.getTf6().equals("no") && objOwnerCompany.getTf6().equals("")))
                JOptionPane.showMessageDialog(objOwnerCompany, "Заполните поля!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else if(objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs2()) && objOwnerCompany.getLb2().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs3()) && objOwnerCompany.getLb3().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs4()) && objOwnerCompany.getLb4().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs5()) && objOwnerCompany.getLb5().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs2().equals(objOwnerCompany.getComboBoxParametrs3()) && objOwnerCompany.getLb3().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs2().equals(objOwnerCompany.getComboBoxParametrs4()) && objOwnerCompany.getLb4().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs2().equals(objOwnerCompany.getComboBoxParametrs5()) && objOwnerCompany.getLb5().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs2().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs3().equals(objOwnerCompany.getComboBoxParametrs4()) && objOwnerCompany.getLb4().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs3().equals(objOwnerCompany.getComboBoxParametrs5()) && objOwnerCompany.getLb5().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs3().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs4().equals(objOwnerCompany.getComboBoxParametrs5()) && objOwnerCompany.getLb5().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs4().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs5().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible()

            )
                JOptionPane.showMessageDialog(objOwnerCompany, "Названия параметров должны быть разными", "Ошибка добавления", JOptionPane.ERROR_MESSAGE);

            else {
                EvaluatedCompany company = new EvaluatedCompany();
                int error = 0;

                company.setNameCompany(objOwnerCompany.getNameCompany().getText());
                company.setSizeCompany(Integer.parseInt(objOwnerCompany.getSizeCompany().getText()));
                company.setTypeCompany("owner");
                company.setIdCountry(objOwnerCompany.getCountryBox().getSelectedIndex());
                ArrayList<FinParameter> pars = new ArrayList<>();
                if (!objOwnerCompany.getTf1().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf1()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs1());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf2().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf2()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs2());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf3().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf3()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs3());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf4().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf4()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs4());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf5().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf5()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs5());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf6().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf6()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs6());
                    pars.add(par);
                }
                System.out.println("dfgdfg" + pars.get(0).getNameParameter());
                company.setParameters(pars);
                company.setIndustryCompany(objOwnerCompany.getIndustry().getSelectedItem().toString());
                Users user = new Users();
                user.setLogin(loginUser);
                user.setPassword(passUser);
                if (error == 0) {
                    client.sendMessage("Companies");
                    client.sendMessage("addOwnerCompany");
                    client.sendObject(company);
                    client.sendObject(user);
                    String mes = "";
                    try {
                        mes = client.readMessage();
                    } catch (IOException ex) {
                        System.out.println("Error in reading");
                    }
                    if (mes.equals("This company is already existed"))
                        JOptionPane.showMessageDialog(objOwnerCompany, "Такая компания уже существует!", "Ошибка добавления", JOptionPane.ERROR_MESSAGE);
                    else {
                        JOptionPane.showMessageDialog(objOwnerCompany, "Компания успешно добавлена", "Добавление", JOptionPane.PLAIN_MESSAGE);
                        objOwnerCompany.dispose();
                    }
                }
                repaintInfoCompanies(user);
                repaintCompanies(0);
                repaintCompanies(1);
            }

        }
        if (e.getActionCommand().equals("ShowAddAnalystFrame")) {
            UsersRegistration formReg = new UsersRegistration();

            formReg.setTitle("Регистрация бизнес-аналитика");
            formReg.pack();
            repaintRegistrationCountries();
            try {
                countriesPhone();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            formReg.setLocationRelativeTo(null);
            formReg.companyVisible();
            String answer = repaintOwnerCompanies();
            if (answer == "error") {
                JOptionPane.showMessageDialog(objOwnerMenu, "У вас еще нет компаний!", "Ошибка добавления бизнес-аналитика", JOptionPane.ERROR_MESSAGE);
            } else
                formReg.setVisible(true);
        }
        if (e.getActionCommand().equals("addAnalogCompany")) {

            if (
                    objOwnerCompany.getNameCompany().getText().equals("") ||
                            objOwnerCompany.getSizeCompany().getText().equals("") ||
                            objOwnerCompany.getIndustry().getSelectedItem().equals("Не выбрано") ||
                            objOwnerCompany.getCost().getText().equals("") ||
                            objOwnerCompany.getCountryBox().getSelectedItem().equals("Не выбрано") ||
                            (!objOwnerCompany.getTf1().equals("no") && objOwnerCompany.getTf1().equals("")) ||
                            (!objOwnerCompany.getTf2().equals("no") && objOwnerCompany.getTf2().equals("")) ||
                            (!objOwnerCompany.getTf3().equals("no") && objOwnerCompany.getTf3().equals("")) ||
                            (!objOwnerCompany.getTf4().equals("no") && objOwnerCompany.getTf4().equals("")) ||
                            (!objOwnerCompany.getTf5().equals("no") && objOwnerCompany.getTf5().equals("")) ||
                            (!objOwnerCompany.getTf6().equals("no") && objOwnerCompany.getTf6().equals("")))
                JOptionPane.showMessageDialog(objOwnerCompany, "Заполните поля!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else if (objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs2()) && objOwnerCompany.getLb2().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs3()) && objOwnerCompany.getLb3().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs4()) && objOwnerCompany.getLb4().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs5()) && objOwnerCompany.getLb5().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs1().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs2().equals(objOwnerCompany.getComboBoxParametrs3()) && objOwnerCompany.getLb3().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs2().equals(objOwnerCompany.getComboBoxParametrs4()) && objOwnerCompany.getLb4().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs2().equals(objOwnerCompany.getComboBoxParametrs5()) && objOwnerCompany.getLb5().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs2().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs3().equals(objOwnerCompany.getComboBoxParametrs4()) && objOwnerCompany.getLb4().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs3().equals(objOwnerCompany.getComboBoxParametrs5()) && objOwnerCompany.getLb5().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs3().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs4().equals(objOwnerCompany.getComboBoxParametrs5()) && objOwnerCompany.getLb5().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs4().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible() ||
                    objOwnerCompany.getComboBoxParametrs5().equals(objOwnerCompany.getComboBoxParametrs6()) && objOwnerCompany.getLb6().isVisible()

            )
                JOptionPane.showMessageDialog(objOwnerCompany, "Названия параметров должны быть разными", "Ошибка добавления", JOptionPane.ERROR_MESSAGE);

            else {
                AnalogCompany company = new AnalogCompany();
                int error = 0;

                company.setNameCompany(objOwnerCompany.getNameCompany().getText());
                company.setSizeCompany(Integer.parseInt(objOwnerCompany.getSizeCompany().getText()));
                company.setTypeCompany("analog");
                company.setIdCountry(objOwnerCompany.getCountryBox().getSelectedIndex());
                company.setIndustryCompany(objOwnerCompany.getIndustry().getSelectedItem().toString());
                company.setCostCompany(Float.parseFloat(objOwnerCompany.getCost().getText()));
                ArrayList<FinParameter> pars = new ArrayList<>();
                if (!objOwnerCompany.getTf1().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf1()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs1());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf2().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf2()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs2());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf3().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf3()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs3());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf4().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf4()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs4());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf5().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf5()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs5());
                    pars.add(par);
                }
                if (!objOwnerCompany.getTf6().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objOwnerCompany.getTf6()));
                    par.setNameParameter(objOwnerCompany.getComboBoxParametrs6());
                    pars.add(par);
                }

                company.setParameters(pars);
                if (error == 0) {
                    client.sendMessage("AnalogCompanies");
                    client.sendMessage("addAnalogCompany");
                    client.sendObject(company);
                    String mes = "";
                    try {
                        mes = client.readMessage();
                    } catch (IOException ex) {
                        System.out.println("Error in reading");
                    }
                    if (mes.equals("This company is already existed"))
                        JOptionPane.showMessageDialog(objOwnerCompany, "Такая компания уже существует!", "Ошибка добавления", JOptionPane.ERROR_MESSAGE);
                    else {
                        JOptionPane.showMessageDialog(objOwnerCompany, "Компания успешно добавлена", "Добавление", JOptionPane.PLAIN_MESSAGE);
                        objOwnerCompany.dispose();
                    }
                }
                repaintAnalogCompanies();
                repaintIndustries();
            }
        }
        if (e.getActionCommand().equals("deleteAnalogCompany")) {
            String[] sel = objAnalyst.getSelected();
            client.sendMessage("AnalogCompanies");
            client.sendMessage("deleteAnalogCompany");
            client.sendObject(sel);
            JOptionPane.showMessageDialog(objAdminMenu, "Удаление выполнено успешно!", "Удаление компании-аналога", JOptionPane.PLAIN_MESSAGE);
            repaintAnalogCompanies();
            objAnalyst.getDeleteButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("deleteOwnerCompany")) {
            Users owner = new Users();
            owner.setLogin(loginUser);
            owner.setPassword(passUser);
            String[] sel = objOwnerMenu.getSelected();
            client.sendMessage("Companies");
            client.sendMessage("deleteOwnerCompany");
            client.sendObject(sel);
            JOptionPane.showMessageDialog(objOwnerMenu, "Удаление выполнено успешно!", "Удаление компании", JOptionPane.PLAIN_MESSAGE);
            repaintInfoCommand(owner);
            repaintInfoCompanies(owner);
            objOwnerMenu.getDeleteButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("registrationAnalyst")) {
            if (
                    objRegistrationUsers.getLogin().getText().equals("") ||
                            objRegistrationUsers.getPassword().getText().equals("") ||
                            objRegistrationUsers.getSurnameField().getText().equals("") ||
                            objRegistrationUsers.getNameField().getText().equals("") ||
                            objRegistrationUsers.getPhoneField().getText().equals("") ||
                            objRegistrationUsers.getBoxCompany().isEmpty())
                JOptionPane.showMessageDialog(objRegistrationUsers, "Заполните поля!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                Users user = new Users();
                Users owner = new Users();
                owner.setLogin(loginUser);
                owner.setPassword(passUser);
                int error = 0;
                String nameCompany = objRegistrationUsers.getBoxCompany();
                user.setLogin(objRegistrationUsers.getLogin().getText());
                user.setPassword(objRegistrationUsers.getPassword().getText());
                user.setRole("Аналитик");
                user.setSurname(objRegistrationUsers.getSurnameField().getText());
                user.setName(objRegistrationUsers.getNameField().getText());
                user.setPhone(objRegistrationUsers.getPhoneField().getText());
                if (error == 0) {
                    client.sendMessage("Users");
                    client.sendMessage("addAnalyst");
                    client.sendObject(user);
                    client.sendMessage(nameCompany);
                    String mes = "";
                    try {
                        mes = client.readMessage();
                    } catch (IOException ex) {
                        System.out.println("Error in reading");
                    }
                    if (mes.equals("This user is already existed"))
                        JOptionPane.showMessageDialog(objRegistrationUsers, "Такой пользователь уже существует!", "Ошибка регистрации", JOptionPane.ERROR_MESSAGE);
                    else {
                        JOptionPane.showMessageDialog(objRegistrationUsers, "Пользователь успешно зарегистрирован", "Регистрация пользователя", JOptionPane.PLAIN_MESSAGE);
                        objRegistrationUsers.dispose();
                        repaintInfoCommand(owner);
                    }
                }
            }
        }
        if (e.getActionCommand().equals("SearchSuitableAnalogs")) {
            String[][] company = repaintAnalystCompany();
            client.sendMessage("Method");
            client.sendMessage("searchAnalogs");
            client.sendObject(company);
            String mes = "";
            try {
                mes = client.readMessage();
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }
            if (mes.equals("No companies!")) {
                JOptionPane.showMessageDialog(objAnalyst, "Подходящих компаний аналогов не найдено!", "Поиск компаний-аналогов", JOptionPane.PLAIN_MESSAGE);
                objAnalyst.getCountMults().setEnabled(false);
                objAnalyst.getSearchAnalogs().setEnabled(true);
            }
            else {
                String[][] res=(String[][])client.readObject();
                String[] columnNames = {
                        "Название компании",
                        "Размер компании",
                        "Отрасль",
                        "Страна",
                        "Стоимость",
                        "Выручка",
                        "Чистая прибыль",
                        "Прибыль до уплаты налогов",
                        "Денежный поток",
                        "Денежный поток до налогообложения",
                        "Дивиденды"
                };
                TableModel tableModel1 = new DefaultTableModel(res, columnNames);
                objAnalyst.getFoundAnalogs().setModel(tableModel1);
            }
        }
        if (e.getActionCommand().equals("CountMultipliers")) {
            client.sendMessage("Method");
            client.sendMessage("saveMults");
            String company = objAnalyst.getCompanyTitle();
            int size = objAnalyst.getFoundAnalogs().getRowCount();
            int sizeC = objAnalyst.getFoundAnalogs().getColumnCount();
            System.out.println("dsfdf" + sizeC);
            String[] proceeds = new String[size];
            String[] income = new String[size];
            String[] incomeBeforeTax = new String[size];
            String[] cashFlow = new String[size];
            String[] cashFlowBeforeTax = new String[size];
            String[] dividend = new String[size];
            String[] cost = new String[size];
            String[] names=new String[size];
            for (int i = 0; i < size; i++) {
                proceeds[i] = (String) objAnalyst.getFoundAnalogs().getValueAt(i, 5);
                income[i] = (String) objAnalyst.getFoundAnalogs().getValueAt(i, 6);
                incomeBeforeTax[i] = (String) objAnalyst.getFoundAnalogs().getValueAt(i, 7);
                cashFlow[i] = (String) objAnalyst.getFoundAnalogs().getValueAt(i, 8);
                cashFlowBeforeTax[i] = (String) objAnalyst.getFoundAnalogs().getValueAt(i, 9);
                dividend[i] = (String) objAnalyst.getFoundAnalogs().getValueAt(i, 10);
                cost[i] = (String) objAnalyst.getFoundAnalogs().getValueAt(i, 4);
                names[i] = (String) objAnalyst.getFoundAnalogs().getValueAt(i, 0);
            }
            client.sendObject(proceeds);
            client.sendObject(income);
            client.sendObject(incomeBeforeTax);
            client.sendObject(cashFlow);
            client.sendObject(cashFlowBeforeTax);
            client.sendObject(dividend);
            client.sendObject(cost);
            client.sendObject(names);
            client.sendObject(company);
            String[] columnNames = {
                    "Название компании",
                    "Выручка",
                    "Чистая прибыль",
                    "Прибыль до уплаты налогов",
                    "Денежный поток",
                    "Денежный поток до налогообложения",
                    "Дивиденды"
            };
            String[] columnNames1 = {
                    "Выручка",
                    "Чистая прибыль",
                    "Прибыль до уплаты налогов",
                    "Денежный поток",
                    "Денежный поток до налогообложения",
                    "Дивиденды"
            };
           String[][] data=(String[][])client.readObject();
            String[][] avgMults = (String[][]) client.readObject();
            TableModel tableModel1 = new DefaultTableModel(data, columnNames);
            objAnalyst.getMults().setModel(tableModel1);
            TableModel tableModel2 = new DefaultTableModel(avgMults, columnNames1);
            objAnalyst.getAvgMults().setModel(tableModel2);



        }
        if (e.getActionCommand().equals("CountCost")) {
            String[][] company = repaintAnalystCompany();
            client.sendMessage("Method");
            client.sendMessage("CountCost");
            client.sendMessage(company[0][0]);
            float cost = (float) client.readObject();
            objAnalyst.getCompanyCost().setText(String.valueOf(cost));
        }
        if (e.getActionCommand().equals("deleteOwnerCompanyAdmin")) {
            String[] sel = objAdminMenu.getSelectedCompanies();
            client.sendMessage("Companies");
            client.sendMessage("deleteOwnerCompanyAdmin");
            client.sendObject(sel);
            JOptionPane.showMessageDialog(objAdminMenu, "Удаление выполнено успешно!", "Удаление компании", JOptionPane.PLAIN_MESSAGE);
            repaintAllOwnerCompanies();
            objAdminMenu.getDeleteCompanyButton().setEnabled(false);
        }
        if (e.getActionCommand().equals("ShowEditAnalogCompanyFrame")) {

            objEditCompany.setTitle("Регистрация бизнес-аналитика");
            objEditCompany.pack();
            objEditCompany.setLocationRelativeTo(null);
            repaintEditCountries();
            objEditCompany.setVisible(true);

        }
        if (e.getActionCommand().equals("editAnalogCompany")) {
            if (
                    objEditCompany.getCompanyName().getText().equals("") ||
                            objEditCompany.getCompanySize().getText().equals("") ||
                            objEditCompany.getIndustry().getSelectedItem().equals("Не выбрано") ||
                            objEditCompany.getCost().getText().equals("") ||
                            objEditCompany.getCountry().getSelectedItem().equals("Не выбрано") ||
                            (!objEditCompany.getPar1().equals("no") && objEditCompany.getPar1().equals("")) ||
                            (!objEditCompany.getPar2().equals("no") && objEditCompany.getPar2().equals("")) ||
                            (!objEditCompany.getPar3().equals("no") && objEditCompany.getPar3().equals("")) ||
                            (!objEditCompany.getPar4().equals("no") && objEditCompany.getPar4().equals("")) ||
                            (!objEditCompany.getPar5().equals("no") && objEditCompany.getPar5().equals("")) ||
                            (!objEditCompany.getPar6().equals("no") && objEditCompany.getPar6().equals("")))
                JOptionPane.showMessageDialog(objEditCompany, "Заполните поля!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
      else {
                AnalogCompany company = new AnalogCompany();
                int error = 0;
                company.setNameCompany(objEditCompany.getCompanyName().getText());
                company.setSizeCompany(Integer.parseInt(objEditCompany.getCompanySize().getText()));
                company.setTypeCompany("analog");
                company.setIdCountry(objEditCompany.getIndustry().getSelectedIndex());
                company.setIndustryCompany(objEditCompany.getCompanyIndustry().getSelectedItem().toString());
                company.setCostCompany(Float.parseFloat(objEditCompany.getCost().getText()));
                ArrayList<FinParameter> pars = new ArrayList<>();
                if (!objEditCompany.getPar1().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar1()));
                    par.setNameParameter("Выручка");
                    pars.add(par);
                }
                if (!objEditCompany.getPar2().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar2()));
                    par.setNameParameter("Чистая прибыль");
                    pars.add(par);
                }
                if (!objEditCompany.getPar3().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar3()));
                    par.setNameParameter("Прибыль до уплаты налогов");
                    pars.add(par);
                }
                if (!objEditCompany.getPar4().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar4()));
                    par.setNameParameter("Денежный поток");
                    pars.add(par);
                }
                if (!objEditCompany.getPar5().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar5()));
                    par.setNameParameter("Денежный поток до налогообложения");
                    pars.add(par);
                }
                if (!objEditCompany.getPar6().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar6()));
                    par.setNameParameter("Дивиденды");
                    pars.add(par);
                }

                company.setParameters(pars);

                String[] oldName = objAnalyst.getSelected();
                if (error == 0) {
                    client.sendMessage("AnalogCompanies");
                    client.sendMessage("editAnalogCompany");
                    client.sendObject(company);
                    client.sendObject(oldName[0]);
                    String mes = "";
                    try {
                        mes = client.readMessage();
                    } catch (IOException ex) {
                        System.out.println("Error in reading");
                    }
                    if (mes.equals("This company is already existed"))
                        JOptionPane.showMessageDialog(objEditCompany, "Такая компания уже существует!", "Ошибка обновления", JOptionPane.ERROR_MESSAGE);
                    else {
                        JOptionPane.showMessageDialog(objEditCompany, "Компания успешно обновлена", "Обновление", JOptionPane.PLAIN_MESSAGE);
                        objEditCompany.dispose();
                    }
                }
                repaintAnalogCompanies();
                repaintIndustries();
            }
        }
        if (e.getActionCommand().equals("editOwnerCompany")) {
            if (
                    objEditCompany.getCompanyName().getText().equals("") ||
                            objEditCompany.getCompanySize().getText().equals("") ||
                            objEditCompany.getIndustry().getSelectedItem().equals("Не выбрано") ||
                            objEditCompany.getCountry().getSelectedItem().equals("Не выбрано") ||
                            (!objEditCompany.getPar1().equals("no") && objEditCompany.getPar1().equals("")) ||
                            (!objEditCompany.getPar2().equals("no") && objEditCompany.getPar2().equals("")) ||
                            (!objEditCompany.getPar3().equals("no") && objEditCompany.getPar3().equals("")) ||
                            (!objEditCompany.getPar4().equals("no") && objEditCompany.getPar4().equals("")) ||
                            (!objEditCompany.getPar5().equals("no") && objEditCompany.getPar5().equals("")) ||
                            (!objEditCompany.getPar6().equals("no") && objEditCompany.getPar6().equals("")))
                JOptionPane.showMessageDialog(objEditCompany, "Заполните поля!", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            else {
                EvaluatedCompany company = new EvaluatedCompany();
                int error = 0;
                company.setNameCompany(objEditCompany.getCompanyName().getText());
                company.setSizeCompany(Integer.parseInt(objEditCompany.getCompanySize().getText()));
                company.setTypeCompany("owner");
                company.setIdCountry(objEditCompany.getIndustry().getSelectedIndex());
                company.setIndustryCompany(objEditCompany.getCompanyIndustry().getSelectedItem().toString());
                ArrayList<FinParameter> pars = new ArrayList<>();
                if (!objEditCompany.getPar1().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar1()));
                    par.setNameParameter("Выручка");
                    pars.add(par);
                }
                if (!objEditCompany.getPar2().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar2()));
                    par.setNameParameter("Чистая прибыль");
                    pars.add(par);
                }
                if (!objEditCompany.getPar3().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar3()));
                    par.setNameParameter("Прибыль до уплаты налогов");
                    pars.add(par);
                }
                if (!objEditCompany.getPar4().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar4()));
                    par.setNameParameter("Денежный поток");
                    pars.add(par);
                }
                if (!objEditCompany.getPar5().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar5()));
                    par.setNameParameter("Денежный поток до налогообложения");
                    pars.add(par);
                }
                if (!objEditCompany.getPar6().equals("no")) {
                    FinParameter par = new FinParameter();
                    par.setValue(Float.parseFloat(objEditCompany.getPar6()));
                    par.setNameParameter("Дивиденды");
                    pars.add(par);
                }

                company.setParameters(pars);

                String[] oldName = objOwnerMenu.getSelected();
                if (error == 0) {
                    client.sendMessage("Companies");
                    client.sendMessage("editOwnerCompany");
                    client.sendObject(company);
                    client.sendObject(oldName[0]);
                    String mes = "";
                    try {
                        mes = client.readMessage();
                    } catch (IOException ex) {
                        System.out.println("Error in reading");
                    }
                    if (mes.equals("This company is already existed"))
                        JOptionPane.showMessageDialog(objEditCompany, "Такая компания уже существует!", "Ошибка обновления", JOptionPane.ERROR_MESSAGE);
                    else {
                        JOptionPane.showMessageDialog(objEditCompany, "Компания успешно обновлена", "Обновление", JOptionPane.PLAIN_MESSAGE);
                        objEditCompany.dispose();
                    }
                }
                Users owner = new Users();
                owner.setLogin(loginUser);
                owner.setPassword(passUser);
                repaintInfoCompanies(owner);
                objOwnerMenu.getEdit().setEnabled(true);
            }
        }
        if (e.getActionCommand().equals("formReportForCompanyCost")) {
            int error = 0;

            if (error == 0) {
                client.sendMessage("Reports");
                client.sendMessage("formReportForCompanyCost");
                Users owner = new Users();
                owner.setLogin(loginUser);
                owner.setPassword(passUser);
                client.sendObject(owner);
                String mes = "";
                try {
                    mes = client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("This company is already existed"))
                    JOptionPane.showMessageDialog(objAnalyst, "Стоимость этой компании еще не посчитана!", "Ошибка создания отчета", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAnalyst, "Отчет успешно создан", "Создание отчета", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        if (e.getActionCommand().equals("formReportForFoundAnalogs")) {
            int error = 0;
            if (error == 0) {
                client.sendMessage("Reports");
                client.sendMessage("formReportForFoundAnalogs");
                Users owner = new Users();
                owner.setLogin(loginUser);
                owner.setPassword(passUser);
                String title = objAnalyst.getCompanyTitle();
                client.sendObject(title);
                client.sendObject(owner);
                String mes = "";
                try {
                    mes = client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals("This company is already existed"))
                    JOptionPane.showMessageDialog(objAnalyst, "Подходящих компаний-аналогов нет!", "Ошибка создания отчета", JOptionPane.ERROR_MESSAGE);
                else {
                    JOptionPane.showMessageDialog(objAnalyst, "Отчет успешно создан", "Создание отчета", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        if (e.getActionCommand().equals("formGrafic")) {
            formGrafic();
        }
        if (e.getActionCommand().equals("formRoundDiagram")) {
            formDiagram();
        }
        if (e.getActionCommand().equals("forbidAccess")) {
            String[] log = objAdminMenu.getSelected();
            client.sendMessage("Users");
            client.sendMessage("forbidAccess");
            client.sendObject(log);
            String mes = "";
            for (int i = 0; i < log.length; i++) {
                try {
                    mes = client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals(log[i])) {
                    JOptionPane.showMessageDialog(objAdminMenu, "У этого пользователя: " + log[i] + " уже отсутствует доступ к системе!", "Ошибка ограничения доступа", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(objAdminMenu, "Доступ успешно запрещен!", "Доступ запрещен", JOptionPane.OK_CANCEL_OPTION);
                }
            }
            repaintPassenger();
        }
        if (e.getActionCommand().equals("allowAccess")) {
            String[] log = objAdminMenu.getSelected();
            client.sendMessage("Users");
            client.sendMessage("allowAccess");
            client.sendObject(log);
            String mes = "";
            for (int i = 0; i < log.length; i++) {
                try {
                    mes = client.readMessage();
                } catch (IOException ex) {
                    System.out.println("Error in reading");
                }
                if (mes.equals(log[i])) {
                    JOptionPane.showMessageDialog(objAdminMenu, "У этого пользователя: " + log[i] + " уже присутствует доступ к системе!", "Ошибка возвращения доступа", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(objAdminMenu, "Доступ успешно разрешен!", "Доступ разрешен", JOptionPane.OK_CANCEL_OPTION);
                }
            }
            repaintPassenger();
        }
    }

    public void autorization() {
        try {
            String msgLogin = objEnterDialog.getTextLogin().getText();
            String msgPassword = objEnterDialog.getPasswordField1().getText();
            if (msgLogin.equals("") || msgPassword.equals(""))
                JOptionPane.showMessageDialog(objEnterDialog,
                        "Заполните все поля!",
                        "Ошибка ввода",
                        JOptionPane.ERROR_MESSAGE);

            Users user = new Users();
            user.setLogin(msgLogin);
            user.setPassword(msgPassword);
            client.sendMessage("Users");
            client.sendMessage("enter");
            client.sendObject(user);

            String servMsg = client.readMessage();
            switch (servMsg) {
                case "error": {
                    JOptionPane.showMessageDialog(objEnterDialog,
                            "Такой пользователь не зарегистрирован",
                            "Ошибка авторизации",
                            JOptionPane.ERROR_MESSAGE);
                    objEnterDialog.getPasswordField1().setText("");
                    objEnterDialog.getTextLogin().setText("");
                }
                break;
                case "errorInput": {
                    JOptionPane.showMessageDialog(objEnterDialog,
                            "Проверьте введенные данные",
                            "Ошибка ввода",
                            JOptionPane.ERROR_MESSAGE);
                    objEnterDialog.getPasswordField1().setText("");
                    objEnterDialog.getTextLogin().setText("");
                }
                break;
                case "Access denied!": {
                    JOptionPane.showMessageDialog(objRegistrationUsers, "Администратор запретил вам доступ", "Ошибка входа", JOptionPane.ERROR_MESSAGE);
                    objEnterDialog.getPasswordField1().setText("");
                    objEnterDialog.getTextLogin().setText("");
                }
                break;

                case "ok": {
                    loginUser = user.getLogin();
                    passUser = user.getPassword();
                    Users usr = new Users();
                    usr.setLogin(loginUser);
                    usr.setPassword(passUser);
                    String status = client.readMessage();
                    System.out.println(status);
                    if (status.equals("admin")) {
                        objEnterDialog.setVisible(false);
                        AdminMenu form = new AdminMenu();
                        repaintPassenger();
                        form.setTitle("Меню администратора");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);
                        repaintAllOwnerCompanies();

                        JMenuBar menu = new JMenuBar();
                        JMenu item = new JMenu("Выход");
                        item.setActionCommand("exit");
                        item.addActionListener(Controller.getInstance());
                        menu.add(item);
                        form.setJMenuBar(menu);
                    }
                    if (status.equals("Владелец")) {
                        objEnterDialog.setVisible(false);
                        OwnerMenu menuOwner = new OwnerMenu();
                        menuOwner.setTitle("Личный кабинет");
                        menuOwner.pack();
                        menuOwner.setLocationRelativeTo(null);
                        menuOwner.setVisible(true);
                        repaintInfoUser(usr);
                        repaintInfoCompanies(usr);
                        repaintCompanies(0);
                        repaintCompanies(1);
                        repaintInfoCommand(usr);
                        formDiagram();
                        formGrafic();

                    }
                    if (status.equals("Аналитик")) {
                        System.out.println(user.getLogin());
                        objEnterDialog.setVisible(false);
                        AnalystMenu form = new AnalystMenu();
                        form.setTitle("Личный кабинет");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);
                        repaintInfoAnalyst(usr);
                        repaintAnalystCompany();
                        repaintIndustries();
                        repaintAnalogCompanies();

                    }
                }
                break;
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void repaintPassenger() {
        client.sendMessage("Users");
        client.sendMessage("createListAllUsers");
        String[][] data = (String[][]) client.readObject();
        String[] columnNames = {
                "Фамилия",
                "Имя",
                "Телефон",
                "Роль",
                "Логин",
                "Пароль"
        };

        TableModel tableModel = new DefaultTableModel(data, columnNames);
        objAdminMenu.getTableUsers().setModel(tableModel);
        objAdminMenu.getComboBoxFilterRole().setSelectedItem("Не выбрано");
    }

    public void repaintInfoUser(Users user) {
        client.sendMessage("Users");
        client.sendMessage("showInfoUser");
        client.sendObject(user);
        Users info = (Users) client.readObject();
        objOwnerMenu.setSurname(info.getSurname());
        objOwnerMenu.setNameTf(info.getName());
        objOwnerMenu.setPhone(info.getPhone());
        objOwnerMenu.setLogin(info.getLogin());
        objOwnerMenu.setPass(info.getPassword());

    }

    public void repaintInfoAnalyst(Users user) {
        client.sendMessage("Users");
        client.sendMessage("showInfoUser");
        client.sendObject(user);
        Users info = (Users) client.readObject();
        objAnalyst.setSurname(info.getSurname());
        objAnalyst.setAnalystName(info.getName());
        objAnalyst.setPhone(info.getPhone());
        objAnalyst.setLogin(info.getLogin());
        objAnalyst.setPass(info.getPassword());

    }

    public void repaintInfoCommand(Users user) {
        client.sendMessage("Users");
        client.sendMessage("createListCommand");

        client.sendObject(user);
        String[][] data = (String[][]) client.readObject();
        String[] columnNames = {
                "Название компании",
                "Фамилия",
                "Имя",
                "Телефон",
                "Роль",
                "Логин",
                "Пароль"
        };

        TableModel tableModel1 = new DefaultTableModel(data, columnNames);
        objOwnerMenu.getTableCommand().setModel(tableModel1);
objOwnerMenu.getComboBoxFilter().setSelectedItem("Не выбрано");
    }

    public void repaintInfoCommandFilter(Users user) {
        client.sendMessage("Users");
        client.sendMessage("createListCommand");

        client.sendObject(user);
        String[][] data = (String[][]) client.readObject();
        int size = 0;

        for (int j = 0; j < data.length; j++) {
            if (objOwnerMenu.getSelectedName().equals(data[j][0])) {
                size++;
            }
        }
        String[][] filterCompany = new String[size][7];
        int count = 0;

        for (int i = 0; i < data.length; i++) {
            if (objOwnerMenu.getSelectedName().equals(data[i][0])) {
                filterCompany[count][0] = data[i][0];
                filterCompany[count][1] = data[i][1];
                filterCompany[count][2] = data[i][2];
                filterCompany[count][3] = data[i][3];
                filterCompany[count][4] = data[i][4];
                filterCompany[count][5] = data[i][5];
                filterCompany[count][6] = data[i][6];
                count++;

            }

        }
        String[] columnNames = {
                "Название компании",
                "Фамилия",
                "Имя",
                "Телефон",
                "Роль",
                "Логин",
                "Пароль"
        };
        if (count != 0) {
            TableModel tableModel1 = new DefaultTableModel(filterCompany, columnNames);
            objOwnerMenu.getTableCommand().setModel(tableModel1);
        } else if (objOwnerMenu.getSelectedName().equals("Не выбрано")) {
            TableModel tableModel1 = new DefaultTableModel(data, columnNames);
            objOwnerMenu.getTableCommand().setModel(tableModel1);
        } else {

        }
    }

    public String repaintOwnerCompanies() {
        client.sendMessage("Companies");
        client.sendMessage("showOwnerCompanies");
        Users user = new Users();
        user.setLogin(loginUser);
        user.setPassword(passUser);
        client.sendObject(user);
        String[] data = (String[]) client.readObject();
        if (data.length == 0) {
            return "error";
        } else {
            ComboBoxModel tableModel = new DefaultComboBoxModel(data);
            objRegistrationUsers.getBoxCompany2().setModel(tableModel);
            return "ok";
        }
    }

    public void repaintCompanies(int flag) {
        client.sendMessage("Companies");
        client.sendMessage("showOwnerCompanies");
        Users user = new Users();
        user.setLogin(loginUser);
        user.setPassword(passUser);
        client.sendObject(user);
        String[] data = (String[]) client.readObject();
        String[] data1 = new String[data.length + 1];
        data1[0] = "Не выбрано";
        int count = 1;
        for (int i = 0; i < data.length; i++) {
            data1[count] = data[i];
            count++;
        }
        if (flag == 0) {
            ComboBoxModel tableModel = new DefaultComboBoxModel(data1);

            objOwnerMenu.getComboBoxFilter().setModel(tableModel);
        } else if (flag == 1) {
            ComboBoxModel tableModel = new DefaultComboBoxModel(data);
            ComboBoxModel tableModel1 = new DefaultComboBoxModel(data);
            objOwnerMenu.getComboBoxCompaies().setModel(tableModel);
            objOwnerMenu.getComboBoxGrafics().setModel(tableModel1);
        }
    }

    public void repaintInfoCompanies(Users user) {
        client.sendMessage("Companies");
        client.sendMessage("createOwnerCompanies");
        System.out.println(user.getLogin());
        client.sendObject(user);
        String[][] data = (String[][]) client.readObject();
        String[] columnNames = {
                "Название компании",
                "Размер компании",
                "Отрасль",
                "Страна",
                "Выручка",
                "Чистая прибыль",
                "Прибыль до уплаты налогов",
                "Денежный поток",
                "Денежный поток до налогообложения",
                "Дивиденды"
        };

        TableModel tableModel1 = new DefaultTableModel(data, columnNames);
        objOwnerMenu.getTableCompanies().setModel(tableModel1);


    }

    public String[][] repaintAnalystCompany() {
        System.out.println("Title1234");
        client.sendMessage("Companies");
        client.sendMessage("showAnalystCompany");

        Users user = new Users();
        user.setLogin(loginUser);
        user.setPassword(passUser);
        client.sendObject(user);
        String[][] data = (String[][]) client.readObject();
        System.out.println("Title"+data[0][0]);
        objAnalyst.setCompanyTitle(data[0][0]);
        objAnalyst.setIndustry(data[0][2]);
        objAnalyst.setSize(data[0][1]);
        objAnalyst.setCountry(data[0][3]);
        objAnalyst.setProceeds(data[0][4]);
        objAnalyst.setIncome(data[0][5]);
        objAnalyst.setPar1(data[0][6]);
        objAnalyst.setPar2(data[0][7]);
        objAnalyst.setPar3(data[0][8]);
        objAnalyst.setPar4(data[0][9]);
        return data;
    }

    public String[][] repaintAnalogCompanies() {
        client.sendMessage("AnalogCompanies");
        client.sendMessage("showAnalogCompanies");

        String[][] data = (String[][]) client.readObject();
        String[] columnNames = {
                "Название компании",
                "Размер компании",
                "Отрасль",
                "Страна",
                "Стоимость",
                "Выручка",
                "Чистая прибыль",
                "Прибыль до уплаты налогов",
                "Денежный поток",
                "Денежный поток до налогообложения",
                "Дивиденды"
        };
        TableModel tableModel1 = new DefaultTableModel(data, columnNames);
        objAnalyst.getTableAnalogs().setModel(tableModel1);
        objAnalyst.getBoxIndustry().setSelectedItem("Не выбрано");
        return data;
    }

    public void repaintAllOwnerCompanies() {
        client.sendMessage("Companies");
        client.sendMessage("showAllOwnerCompanies");
        String[][] data = (String[][]) client.readObject();
        String[] columnNames = {
                "Название компании",
                "Размер компании",
                "Отрасль",
                "Страна",
                "Выручка",
                "Чистая прибыль",
                "Прибыль до уплаты налогов",
                "Денежный поток",
                "Денежный поток до налогообложения",
                "Дивиденды"
        };

        TableModel tableModel1 = new DefaultTableModel(data, columnNames);
        objAdminMenu.getTableCompanies().setModel(tableModel1);
    }

    public void repaintAdminUserFilter() {
        client.sendMessage("Users");
        client.sendMessage("createListAllUsers");
        String[][] data = (String[][]) client.readObject();
        int size = 0;
        String selRole=objAdminMenu.getSelectedRole();
        String selRole1=objAdminMenu.getSelectedRole()+"-";
        for (int j = 0; j < data.length; j++) {
            if (selRole.equals(data[j][3])|| selRole1.equals(data[j][3])) {
                size++;
            }
        }
        String[][] data1 = new String[size][6];
        int count = 0;

        for (int i = 0; i < data.length; i++) {
            if (selRole.equals(data[i][3])|| selRole1.equals(data[i][3])) {
                data1[count][0] = data[i][0];
                data1[count][1] = data[i][1];
                data1[count][2] = data[i][2];
                data1[count][3] = data[i][3];
                data1[count][4] = data[i][4];
                data1[count][5] = data[i][5];
                count++;

            }

        }
        String[] columnNames = {
                "Фамилия",
                "Имя",
                "Телефон",
                "Роль",
                "Логин",
                "Пароль"
        };
        if (count != 0) {
            TableModel tableModel = new DefaultTableModel(data1, columnNames);
            objAdminMenu.getTableUsers().setModel(tableModel);
        } else {
            TableModel tableModel = new DefaultTableModel(data, columnNames);
            objAdminMenu.getTableUsers().setModel(tableModel);
        }
    }

    public void repaintAdminCompaniesSort() {
        client.sendMessage("Companies");
        client.sendMessage("showAllOwnerCompanies");
        String[][] data = (String[][]) client.readObject();
        String[] titles = new String[data.length];
        int[] sizes = new int[data.length];
        int size = 0;
        for (int j = 0; j < data.length; j++) {
            titles[j] = data[j][0];
        }
        for (int j = 0; j < data.length; j++) {
            sizes[j] = Integer.parseInt(data[j][1]);
        }
        Arrays.sort(titles);
        Arrays.sort(sizes);
        int[] reversed = new int[sizes.length];
        Arrays.setAll(reversed, i -> sizes[sizes.length - i - 1]);

        // System.out.println(titles[0]);
        String[][] data1 = new String[data.length][10];


        if (objAdminMenu.getSelectedSort().equals("По алфавиту")) {
            int count = 0;
            for (int i = 0; i < data.length; i++) {
                for (int k = 0; k < data.length; k++) {
                    if (titles[i].equals(data[k][0])) {
                        data1[count][0] = data[k][0];
                        data1[count][1] = data[k][1];
                        data1[count][2] = data[k][2];
                        data1[count][3] = data[k][3];
                        data1[count][4] = data[k][4];
                        data1[count][5] = data[k][5];
                        data1[count][6] = data[k][6];
                        data1[count][7] = data[k][7];
                        data1[count][8] = data[k][8];
                        data1[count][9] = data[k][9];
                        count++;
                    }
                }
            }
        } else if (objAdminMenu.getSelectedSort().equals("По дате добавления")) {
            int count = 0;

            for (int k = 0; k < data.length; k++) {
                data1[count][0] = data[k][0];
                data1[count][1] = data[k][1];
                data1[count][2] = data[k][2];
                data1[count][3] = data[k][3];
                data1[count][4] = data[k][4];
                data1[count][5] = data[k][5];
                data1[count][6] = data[k][6];
                data1[count][7] = data[k][7];
                data1[count][8] = data[k][8];
                data1[count][9] = data[k][9];
                count++;
            }

        } else if (objAdminMenu.getSelectedSort().equals("По размеру компании(по убыванию)")) {
            int count = 0;
            for (int i = 0; i < data.length; i++) {
                for (int k = 0; k < data.length; k++) {
                    if (reversed[i] == Integer.parseInt(data[k][1])) {
                        data1[count][0] = data[k][0];
                        data1[count][1] = data[k][1];
                        data1[count][2] = data[k][2];
                        data1[count][3] = data[k][3];
                        data1[count][4] = data[k][4];
                        data1[count][5] = data[k][5];
                        data1[count][6] = data[k][6];
                        data1[count][7] = data[k][7];
                        data1[count][8] = data[k][8];
                        data1[count][9] = data[k][9];
                        count++;
                    }
                }
            }
        }


        String[] columnNames = {
                "Название компании",
                "Размер компании",
                "Отрасль",
                "Страна",
                "Выручка",
                "Чистая прибыль",
                "Прибыль до уплаты налогов",
                "Денежный поток",
                "Денежный поток до налогообложения",
                "Дивиденды"
        };
        TableModel tableModel = new DefaultTableModel(data1, columnNames);
        objAdminMenu.getTableCompanies().setModel(tableModel);

    }

    public void repaintCountries() {
        client.sendMessage("Companies");
        client.sendMessage("createListCountries");
        String[][] data = (String[][]) client.readObject();
        String[] data1 = new String[data.length + 1];
        data1[0]="Не выбрано";
        int count = 1;
        for (int i = 0; i < data.length; i++) {
            data1[count] = data[i][0];
            count++;
        }
        ComboBoxModel tableModel = new DefaultComboBoxModel(data1);

        objOwnerCompany.getCountryBox().setModel(tableModel);
    }

    public void repaintRegistrationCountries() {
        client.sendMessage("Companies");
        client.sendMessage("createListCountries");
        String[][] data = (String[][]) client.readObject();
        String[] data1 = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            data1[i] = data[i][0];
        }
        ComboBoxModel tableModel = new DefaultComboBoxModel(data1);
        objRegistrationUsers.getCountriesCB().setModel(tableModel);
    }

    public void repaintEditCountries() {
        client.sendMessage("Companies");
        client.sendMessage("createListCountries");
        String[][] data = (String[][]) client.readObject();
        String[] data1 = new String[data.length + 1];
        data1[0] = "Не выбрано";
        int count = 1;
        for (int i = 0; i < data.length; i++) {
            data1[count] = data[i][0];
            count++;
        }
        ComboBoxModel tableModel = new DefaultComboBoxModel(data1);

        objEditCompany.getIndustry().setModel(tableModel);
    }

    public void repaintIndustries() {
        client.sendMessage("AnalogCompanies");
        client.sendMessage("FilterAnalogsIndustry");
        String[][] data = (String[][]) client.readObject();
        String[] data1 = new String[data.length + 1];
        data1[0] = "Не выбрано";
        int count = 1;
        for (int i = 0; i < data.length; i++) {
            data1[count] = data[i][0];
            count++;
        }
        ComboBoxModel tableModel = new DefaultComboBoxModel(data1);

        objAnalyst.getBoxIndustry().setModel(tableModel);
    }

    public void repaintFilterIndustries() {
        client.sendMessage("AnalogCompanies");
        client.sendMessage("showAnalogCompanies");
        String[][] dataC = (String[][]) client.readObject();
        int size = 0;
        for (int i = 0; i < dataC.length; i++) {
            if (objAnalyst.getSelectedIndustry().equals(dataC[i][2])) {
                size++;
            }
        }
        String[][] data1 = new String[size][11];
        int count = 0;
        System.out.println("voahe;");
        for (int i = 0; i < dataC.length; i++) {
            if (objAnalyst.getSelectedIndustry().equals(dataC[i][2])) {
                data1[count][0] = dataC[i][0];
                data1[count][1] = dataC[i][1];
                data1[count][2] = dataC[i][2];
                data1[count][3] = dataC[i][3];
                data1[count][4] = dataC[i][4];
                data1[count][5] = dataC[i][5];
                data1[count][6] = dataC[i][6];
                data1[count][7] = dataC[i][7];
                data1[count][8] = dataC[i][8];
                data1[count][9] = dataC[i][9];
                data1[count][10] = dataC[i][10];
                count++;

            }

        }
        String[] columnNames = {
                "Название компании",
                "Размер компании",
                "Отрасль",
                "Страна",
                "Стоимость",
                "Выручка",
                "Чистая прибыль",
                "Прибыль до уплаты налогов",
                "Денежный поток",
                "Денежный поток до налогообложения",
                "Дивиденды"
        };
        if (count != 0) {
            TableModel tableModel = new DefaultTableModel(data1, columnNames);
            objAnalyst.getTableAnalogs().setModel(tableModel);
        } else {
            TableModel tableModel = new DefaultTableModel(dataC, columnNames);
            objAnalyst.getTableAnalogs().setModel(tableModel);
        }
    }

    public String addFilters(JFileChooser fileChooser) {
        Users owner = new Users();
        owner.setLogin(loginUser);
        owner.setPassword(passUser);
        client.sendMessage("Companies");
        client.sendMessage("showOwnerCompanies");
        client.sendObject(owner);
        String[] data = (String[]) client.readObject();
        fileChooser.resetChoosableFileFilters();
        if (data.length == 0) {
            return "error";
        } else {
            for (int i = 0; i < data.length; i++) {
                String nam = data[i];
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    public String getDescription() {
                        return nam + " (*.docx)";
                    }

                    public boolean accept(File f) {
                        if (f.isDirectory()) {
                            return true;
                        } else {
                            return f.getName().endsWith(nam + "+Cost.docx") || f.getName().endsWith(nam + "+Analogs.docx");
                        }
                    }

                });
            }
            return "ok";
        }


    }

    public void formDiagram() {
        client.sendMessage("Diagrams");
        client.sendMessage("formRoundDiagram");
        Users owner = new Users();
        owner.setLogin(loginUser);
        owner.setPassword(passUser);
        client.sendObject(owner);
        String mes = "";
        try {
            mes = client.readMessage();
        } catch (IOException ex) {
            System.out.println("Error in reading");
        }
        if (mes.equals("No companies!")) {
            //  JOptionPane.showMessageDialog(objAnalyst, "Для всех ваших компаний не найдено данных для диаграммы!", "Ошибка создания диаграммы", JOptionPane.ERROR_MESSAGE);

        } else {
            System.out.println("point3");
            ArrayList<String[]> data = (ArrayList<String[]>) client.readObject();
            System.out.println("kdsl" + data.size());
            String company = objOwnerMenu.getSelectedCompanyDiagram();
            if (company != null) {
                int i = 0;
                DefaultPieDataset dataset = new DefaultPieDataset();
                for (String[] set : data) {
                    if (set[11] != null) {
                        if (company.equals(set[11])) {

                            dataset.setValue(set[0], new Float(Float.parseFloat(set[4])));
                            System.out.println("sizeDataset" + dataset.getItemCount());

                            i++;
                        }

                    }
                }
                if (i == 0) {
                    JOptionPane.showMessageDialog(objAnalyst, "Для компании не найдено подходящих компаний-аналогов!", "Ошибка создания диаграммы", JOptionPane.ERROR_MESSAGE);
                    objOwnerMenu.getDiagram().removeAll();
                    objOwnerMenu.getDiagram().repaint();
                } else {
                    PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                            "{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat("0.0%"));
                    JFreeChart chart = ChartFactory.createPieChart(
                            "Диаграмма компаний-аналогов",
                            dataset,
                            true,
                            true,
                            false);
                    PiePlot plot = (PiePlot) chart.getPlot();
                    //chart.setBackgroundPaint(Color.getHSBColor(52,21,98));
                    java.awt.Font font = new java.awt.Font(Font.SERIF, Font.PLAIN, 10);
                    plot.setSimpleLabels(true);
                    plot.setLabelGenerator(gen);
                    plot.setBackgroundPaint(Color.WHITE);
                    plot.setLabelBackgroundPaint(Color.white);
                    plot.setLabelFont(font);
                    chart.getTitle().setPaint(Color.blue);
                    objOwnerMenu.getDiagram().removeAll();
                    objOwnerMenu.getDiagram().add(new ChartPanel(chart));
                    objOwnerMenu.getDiagram().repaint();
                }
            }
        }
    }

    public void formGrafic() {
        client.sendMessage("Diagrams");
        client.sendMessage("formGrafic");
        Users owner = new Users();
        owner.setLogin(loginUser);
        owner.setPassword(passUser);
        client.sendObject(owner);
        String mes = "";
        try {
            mes = client.readMessage();
        } catch (IOException ex) {
            System.out.println("Error in reading");
        }
        if (mes.equals("No companies!")) {
            //  JOptionPane.showMessageDialog(objAnalyst, "Для всех ваших компаний не найдено данных для графика!", "Ошибка создания графика", JOptionPane.ERROR_MESSAGE);
        } else {
            ArrayList<String[]> data = (ArrayList<String[]>) client.readObject();
            System.out.println("kdsl" + data.size());
            String company = objOwnerMenu.getSelectedCompanyGrafic();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            if (company != null) {
                int i = 0;
                System.out.println("df" + data.size());
                System.out.println("Company   " + company);
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j)[0] != null) {
                        if (data.get(j)[0].equals(company)) {
                            dataset.addValue(Float.parseFloat(data.get(j)[2]), data.get(j)[0], data.get(j)[1]);
                            System.out.println("val1" + Float.parseFloat(data.get(j)[2]));
                            System.out.println("val2" + data.get(j)[0]);
                            System.out.println("val3" + data.get(j)[1]);
                            i++;
                        }
                    }
                }
                if (i == 0) {
                    JOptionPane.showMessageDialog(objAnalyst, "Для компании данных для графика не найдено!", "Ошибка создания графика", JOptionPane.ERROR_MESSAGE);
                    objOwnerMenu.getForGrafic().removeAll();
                    objOwnerMenu.getForGrafic().repaint();
                } else {
                    JFreeChart chart = ChartFactory.createLineChart(
                            "Анализ стоимости компании",       // chart title
                            null,                      // domain axis label
                            "Значение",                // range axis label
                            dataset,                   // data
                            PlotOrientation.VERTICAL,  // orientation
                            true,                      // include legend
                            true,                      // tooltips
                            false                      // urls
                    );
                    chart.setBackgroundPaint(Color.white);

                    final CategoryPlot plot = (CategoryPlot) chart.getPlot();
                    plot.setBackgroundPaint(Color.WHITE);
                    plot.setRangeGridlinePaint(Color.white);
                    LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
                    renderer.setShapesVisible(true);
                    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
                    rangeAxis.setStandardTickUnits(
                            NumberAxis.createIntegerTickUnits());
                    rangeAxis.setAutoRangeIncludesZero(true);
                    objOwnerMenu.getForGrafic().removeAll();
                    objOwnerMenu.getForGrafic().add(new ChartPanel(chart));
                    objOwnerMenu.getForGrafic().repaint();
                }
            }
        }
    }

    public void countriesPhone() throws ParseException {
        client.sendMessage("Companies");
        client.sendMessage("createListCountries");
        String[][] mas = (String[][]) client.readObject();
        for (int i = 0; i < mas.length; i++) {

                if (objRegistrationUsers.getCountriesCB().getSelectedItem().toString().equals(mas[i][0])) {
                    objRegistrationUsers.getPhoneFormatter().setMask(mas[i][1]+"-###-###-##-##");
                    objRegistrationUsers.change();
                }
            }

    }
}
