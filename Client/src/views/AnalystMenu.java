package views;

import Controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AnalystMenu extends JFrame {
    private JPanel contentPane;
    private JButton exit;
    private JTable tableAnalogs;
    private JButton add;
    private JButton delete;
    private JButton edit;
    private JTextField surname;
    private JTextField analystName;
    private JTextField title;
    private JTextField industry;
    private JTextField proceeds;
    private JTextField size;
    private JTextField phone;
    private JTextField login;
    private JTextField pass;
    private JTextField income;
    private JButton searchAnalogs;
    private JTable analogs;
    private JButton countMults;
    private JTable mults;
    private JTable avgMults;
    private JButton countCost;
    private JTextField companyCost;
    private JTextField country;
    private JTextField par1;
    private JTextField par2;
    private JTextField par3;
    private JTextField par4;
    private JComboBox boxIndustry;
    private JButton showAnalogs;
    private JButton report1;
    private JButton cancelCost;
    private JTabbedPane tabbedPane1;
    private EnterDialog dialog;
    private EditCompany company;

    public AnalystMenu() {
        setContentPane(contentPane);
        //setUndecorated(true);

        setDefaultCloseOperation(AnalystMenu.EXIT_ON_CLOSE);
        //getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
        dialog = new EnterDialog();
        Controller.getInstance().initialize(this);
        add.setActionCommand("showAddCompanyAnalogFrame");
        add.addActionListener(Controller.getInstance());
        tableAnalogs.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(tableAnalogs.getSelectedRow());
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
        delete.setActionCommand("deleteAnalogCompany");
        delete.addActionListener(Controller.getInstance());
        delete.setEnabled(false);
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
        report1.setActionCommand("formReportForCompanyCost");
        report1.addActionListener(Controller.getInstance());
        showAnalogs.setActionCommand("formReportForFoundAnalogs");
        showAnalogs.addActionListener(Controller.getInstance());
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditButtonActionPerformed(e);

            }
        });
        edit.setEnabled(false);
        searchAnalogs.setActionCommand("SearchSuitableAnalogs");
        searchAnalogs.addActionListener(Controller.getInstance());
        countMults.setActionCommand("CountMultipliers");
        countMults.addActionListener(Controller.getInstance());
        searchAnalogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countMults.setEnabled(true);
                searchAnalogs.setEnabled(false);
            }
        });
        cancelCost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countCost.setEnabled(false);
                searchAnalogs.setEnabled(true);
                countMults.setEnabled(false);
                DefaultTableModel model = (DefaultTableModel) getFoundAnalogs().getModel();
                model.setRowCount(0);
                DefaultTableModel model1 = (DefaultTableModel) getMults().getModel();
                model1.setRowCount(0);
                DefaultTableModel model2 = (DefaultTableModel) getAvgMults().getModel();
                model2.setRowCount(0);
                companyCost.setText("");
            }
        });
        countMults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countCost.setEnabled(true);
                countMults.setEnabled(false);
            }
        });
        countCost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countCost.setEnabled(false);
                searchAnalogs.setEnabled(true);
            }
        });
        countCost.setActionCommand("CountCost");
        countCost.addActionListener(Controller.getInstance());
        boxIndustry.setActionCommand("filterIndustries");
        boxIndustry.addActionListener(Controller.getInstance());
        countMults.setEnabled(false);
        countCost.setEnabled(false);
    }

    public void setSurname(String surname) {
        this.surname.setText(surname);
    }

    public void setAnalystName(String name) {
        this.analystName.setText(name);
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

    public void setCompanyTitle(String title) {
        this.title.setText(title);
    }

    public void setIndustry(String industry) {
        this.industry.setText(industry);
    }

    public void setSize(String size) {
        this.size.setText(size);
    }

    public void setProceeds(String proceeds) {
        this.proceeds.setText(proceeds);
    }

    public void setIncome(String income) {
        this.income.setText(income);
    }

    public void setCountry(String country) {
        this.country.setText(country);
    }

    public void setPar1(String par1) {
        this.par1.setText(par1);
    }

    public void setPar2(String par2) {
        this.par2.setText(par2);
    }

    public void setPar3(String par3) {
        this.par3.setText(par3);
    }

    public void setPar4(String par4) {
        this.par4.setText(par4);
    }


    public JTable getTableAnalogs() {
        return tableAnalogs;
    }

    public JTable getFoundAnalogs() {
        return analogs;
    }

    public JTable getMults() {
        return mults;
    }

    public JTable getAvgMults() {
        return avgMults;
    }

    public JTextField getCompanyCost() {
        return companyCost;
    }

    public String[] getSelected() {
        int[] selectedRows = tableAnalogs.getSelectedRows();
        String[] title = new String[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            int selIndex = selectedRows[i];
            title[i] = (String) tableAnalogs.getValueAt(selIndex, 0);
        }
        return title;
    }

    public String getSelectedIndustry() {
        String title;
        title = boxIndustry.getSelectedItem().toString();
        return title;
    }

    public JComboBox getBoxIndustry() {
        return boxIndustry;
    }

    public JButton getDeleteButton() {
        return delete;
    }

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (tableAnalogs.getSelectedRowCount() > 0) {
            company = new EditCompany();
            Controller con = Controller.getInstance();
            con.repaintEditCountries();
            company.setCompanyName((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 0));
            company.setCompanySize((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 1));
            company.setCompanyIndustry((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 2));
            company.setCountry((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 3));
            if (tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 5) != null) {
                company.setPar1((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 5));
            }
            if (tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 6) != null) {
                company.setPar2((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 6));
            }
            if (tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 7) != null) {
                company.setPar3((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 7));
            }
            if (tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 8) != null) {
                company.setPar4((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 8));
            }
            if (tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 9) != null) {
                company.setPar5((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 9));
            }
            if (tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 10) != null) {
                company.setPar6((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 10));
            }
            company.setCost((String) tableAnalogs.getValueAt(tableAnalogs.getSelectedRow(), 4));

            company.setTitle("Редактировать компанию");
            company.pack();
            company.setLocationRelativeTo(null);
            company.setVisible(true);

        }
    }

    public JButton getCountMults() {
        return countMults;
    }

    public JButton getSearchAnalogs() {
        return searchAnalogs;
    }

    public String getCompanyTitle() {
        return title.getText();
    }
}
