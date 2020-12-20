package views;

import Controller.Controller;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class OwnerMenu extends JFrame {
    private JPanel contentPane;
    private JButton exit;
    private JTabbedPane tabbedPane1;
    private JTable tableCompanies;
    private JTable tableCommand;
    private JButton addCompany;
    private JButton addAnalyst;
    private JButton delete;
    private JButton edit;
    private JButton deleteAnalysts;
    private JComboBox comboBoxFilter;
    private JButton showReport;
    private JPanel diagram;
    private JComboBox comboBoxCompaies;
    private JButton but;
    private JPanel forDiagram;
    private JPanel forGrafic;
    private JComboBox comboBoxGrafics;
    private JPanel tb1;
    private JTextField Surname;
    private JTextField nameTf;
    private JTextField phone;
    private JTextField login;
    private JTextField pass;
    private EnterDialog dialog;
    private EditCompany company;
    private ChartPanel pan;
    private JFileChooser fileChooser = null;
    private String answerFile;

    public OwnerMenu() {
        setContentPane(contentPane);
        diagram = new JPanel();
        fileChooser = new JFileChooser("D:/My Documents/Курсовой 5 сем/курсач/reports/");
        // Подключение слушателей к кнопкам
        Component[] comp = fileChooser.getComponents();
        comp[0].setVisible(false);
        fileChooser.setAcceptAllFileFilterUsed(false);

        addFileChooserListeners();
        UIManager.put(tb1,Color.MAGENTA);
        dialog = new EnterDialog();
        Controller.getInstance().initialize(this);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                dialog.setTitle("Авторизация");
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        addCompany.setActionCommand("showAddCompanyFrame");
        addCompany.addActionListener(Controller.getInstance());
        deleteAnalysts.setEnabled(false);
        delete.setEnabled(false);
        edit.setEnabled(false);
        tableCommand.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteAnalysts.setEnabled(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
        tableCompanies.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delete.setEnabled(true);
                edit.setEnabled(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
        deleteAnalysts.setActionCommand("deleteAnalyst");
        deleteAnalysts.addActionListener(Controller.getInstance());
        addAnalyst.setActionCommand("ShowAddAnalystFrame");
        addAnalyst.addActionListener(Controller.getInstance());
        delete.setActionCommand("deleteOwnerCompany");
        delete.addActionListener(Controller.getInstance());
        comboBoxFilter.setActionCommand("filterAnalysts");
        comboBoxFilter.addActionListener(Controller.getInstance());
        comboBoxCompaies.addActionListener(Controller.getInstance());
        comboBoxCompaies.setActionCommand("formRoundDiagram");

        comboBoxGrafics.addActionListener(Controller.getInstance());
        comboBoxGrafics.setActionCommand("formGrafic");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditButtonActionPerformed(e);
            }
        });
        setDefaultCloseOperation(OwnerMenu.EXIT_ON_CLOSE);


    }

    public void setSurname(String surname) {
        Surname.setText(surname);
    }

    public void setNameTf(String nameTf) {
        this.nameTf.setText(nameTf);
    }

    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    public void setLogin(String login) {
        this.login.setText(login);
    }

    public void setPass(String pass) {
        this.pass.setText(pass);
    }

    public JTable getTableCommand() {
        return tableCommand;
    }

    public JTable getTableCompanies() {
        return tableCompanies;
    }

    public void setTableCommand(JTable tableCommand) {
        this.tableCommand = tableCommand;
    }

    public String[] getSelected() {
        int[] selectedRows = tableCompanies.getSelectedRows();
        String[] title = new String[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            int selIndex = selectedRows[i];
            title[i] = (String) tableCompanies.getValueAt(selIndex, 0);
        }
        return title;
    }

    public String[] getSelectedAnalyst() {
        int[] selectedRows = tableCommand.getSelectedRows();
        String[] title = new String[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            int selIndex = selectedRows[i];
            title[i] = (String) tableCommand.getValueAt(selIndex, 5);
        }
        return title;
    }

    public String getSelectedName() {
        String title;
        title = comboBoxFilter.getSelectedItem().toString();
        return title;
    }

    public JButton getDeleteButton() {
        return delete;
    }

    public JComboBox getComboBoxFilter() {
        return comboBoxFilter;
    }

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (tableCompanies.getSelectedRowCount() > 0) {
            company = new EditCompany();
            Controller con = Controller.getInstance();
            con.repaintEditCountries();
            company.setCompanyName((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 0));
            company.setCompanySize((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 1));
            company.setCompanyIndustry((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 2));
            company.setCountry((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 3));
            if (tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 4) != null) {
                company.setPar1((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 4));
                company.setLbpar1("Выручка");
            }
            if (tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 5) != null) {
                company.setPar2((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 5));
                company.setLbpar2("Чистая прибыль");
            }
            if (tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 6) != null) {
                company.setPar3((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 6));
                company.setLbpar3("Прибыль до уплаты налогов");
            }
            if (tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 7) != null) {
                company.setPar4((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 7));
                company.setLbpar4("Денежный поток");
            }
            if (tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 8) != null) {
                company.setPar5((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 8));
                company.setLbpar5("Денежный поток до налогообложения");
            }
            if (tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 9) != null) {
                company.setPar6((String) tableCompanies.getValueAt(tableCompanies.getSelectedRow(), 9));
                company.setLbpar6("Дивиденды");
            }

            company.setTitle("Редактировать компанию");
            company.pack();
            company.setLocationRelativeTo(null);
            company.setVisibleForOwner();
            company.setVisible(true);

        }
    }

    public JButton getDeleteAnalysts() {
        return deleteAnalysts;
    }

    public JButton getEdit() {
        return edit;
    }

    public void addFileChooserListeners() {


        showReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller controller = Controller.getInstance();
                answerFile=controller.addFilters(fileChooser);
                if(answerFile.equals("ok")) {
                    fileChooser.setDialogTitle("Выбор директории");
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    int result = fileChooser.showOpenDialog(OwnerMenu.this);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        try {
                            Desktop.getDesktop().open(new File(fileChooser.getSelectedFile().getPath()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                else  JOptionPane.showMessageDialog(OwnerMenu.this, "У вас еще нет компаний!", "Ошибка открытия отчета", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getDiagram() {
        return forDiagram;
    }

    public JComboBox getComboBoxCompaies() {
        return comboBoxCompaies;
    }
    public String getSelectedCompanyDiagram(){
        String title;
        title = comboBoxCompaies.getSelectedItem().toString();
        return title;
    }

    public JComboBox getComboBoxGrafics() {
        return comboBoxGrafics;
    }

    public JPanel getForGrafic() {
        return forGrafic;
    }
    public String getSelectedCompanyGrafic(){
        String title;
        title = comboBoxGrafics.getSelectedItem().toString();
        return title;
    }

}
