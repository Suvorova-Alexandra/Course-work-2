package views;

import Controller.Controller;
import model.FloatException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class EditCompany extends JFrame {
    private JPanel contentPane;
    private JButton saveBtn;
    private JButton cancel;
    private JTextField companyName;
    private JTextField companySize;
    private JComboBox companyIndustry;
    private JTextField par1;
    private JTextField par2;
    private JTextField par3;
    private JTextField par4;
    private JTextField par5;
    private JTextField par6;
    private JTextField cost;
    private JLabel lbpar1;
    private JLabel lbpar2;
    private JLabel lbpar3;
    private JLabel lbpar4;
    private JLabel lbpar5;
    private JLabel lbpar6;
    private JComboBox country;
    private JLabel lebCost;
    private JButton saveOwner;
    private JLabel par1Error;
    private JLabel par2Error;
    private JLabel par3Error;
    private JLabel par4Error;
    private JLabel par5Error;
    private JLabel par6Error;
    private JLabel costError;
    private JLabel sizeError;
    private boolean pr1=false;
    private boolean pr2=false;
    private boolean pr3=false;
    private boolean pr4=false;
    private boolean pr5=false;
    private boolean pr6=false;
    private boolean pr7=false;
    private boolean pr8=false;
    private String[] industries = {"Не выбрано","Промышленность", "Сельское хозяйство", "Лесное хозяйство", "Строительство", "Прочие виды деятельности сферы материального производства","Обслуживание сельского хозяйства","Транспорт","Связь","Торговля и общественное питание",
            "Материально-техническое снабжение и сбыт","Информационно-вычислительное обслуживание","Операции с недвижимым имуществом","Общая коммерческая деятельность по обеспечению функционирования рынка","Геология и разведка недр, геодезическая и гидрометеорологическая службы","Жилищное хозяйство","Коммунальное хозяйство","Непроизводственные виды бытового обслуживания населения","Здравоохранение, физическая культура и социальное обеспечение",
            "Народное образование","Культура и искусство","Наука и научное обслуживание","Финансы, кредит, страхование, пенсионное обеспечение","Управление", "Общественные объединения"};


    public EditCompany() {
        setContentPane(contentPane);
        Controller.getInstance().initialize(this);
        costError.setVisible(false);
        sizeError.setVisible(false);
        par1Error.setVisible(false);
        par2Error.setVisible(false);
        par3Error.setVisible(false);
        par4Error.setVisible(false);
        par5Error.setVisible(false);
        par6Error.setVisible(false);
        lbpar1.setText("Выручка (тыс, $):");
        lbpar2.setText("Чистая прибыль (тыс, $):");
        lbpar3.setText("Прибыль до уплаты налогов (тыс, $):");
        lbpar4.setText("Денежный поток (тыс, $):");
        lbpar5.setText("Денежный поток до налогообложения (тыс, $):");
        lbpar6.setText("Дивиденды (тыс, $):");

        DefaultComboBoxModel industries1=new DefaultComboBoxModel(industries);
        companyIndustry.setModel(industries1);
        saveOwner.setVisible(false);
        saveOwner.setActionCommand("editOwnerCompany");
        saveOwner.addActionListener(Controller.getInstance());
        saveBtn.setActionCommand("editAnalogCompany");
        saveBtn.addActionListener(Controller.getInstance());
        companyName.setEditable(false);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });
        DocumentListener listener1 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(par1,par1Error,pr1);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(par1,par1Error,pr1);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener2 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(par2,par2Error,pr2);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(par2,par2Error,pr2);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener3 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(par3,par3Error,pr3);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(par3,par3Error,pr3);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener4 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(par4,par4Error,pr4);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(par4,par4Error,pr4);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener5 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(par5,par5Error,pr5);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(par5,par5Error,pr5);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener6 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(par6,par6Error,pr6);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(par6,par6Error,pr6);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener7 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(cost,costError,pr7);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(cost,costError,pr7);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener8 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert1(companySize,sizeError,pr8);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update1(companySize,sizeError,pr8);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        par1.getDocument().addDocumentListener(listener1);
        par2.getDocument().addDocumentListener(listener2);
        par3.getDocument().addDocumentListener(listener3);
        par4.getDocument().addDocumentListener(listener4);
        par5.getDocument().addDocumentListener(listener5);
        par6.getDocument().addDocumentListener(listener6);
        cost.getDocument().addDocumentListener(listener7);
        companySize.getDocument().addDocumentListener(listener8);

    }

    public void setCompanyName(String companyName) {
        this.companyName.setText(companyName);
    }

    public void setCompanySize(String companySize) {
        this.companySize.setText(companySize);
    }

    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry.setSelectedItem(companyIndustry);
    }

    public void setCountry(String country) {
        this.country.setSelectedItem(country);
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

    public void setPar5(String par5) {
        this.par5.setText(par5);
    }

    public void setPar6(String par6) {
        this.par6.setText(par6);
    }

    public void setLbpar1(String lbpar1) {
        this.lbpar1.setText(lbpar1);
    }

    public void setLbpar2(String lbpar2) {
        this.lbpar2.setText(lbpar2);
    }

    public void setLbpar3(String lbpar3) {
        this.lbpar3.setText(lbpar3);
    }

    public void setLbpar4(String lbpar4) {
        this.lbpar4.setText(lbpar4);
    }

    public void setLbpar5(String lbpar5) {
        this.lbpar5.setText(lbpar5);
    }

    public void setLbpar6(String lbpar6) {
        this.lbpar6.setText(lbpar6);
    }

    public JComboBox getIndustry() {
        return country;
    }

    public void setCost(String cost) {
        this.cost.setText(cost);
    }


    public JTextField getCost() {
        return cost;
    }

    public JTextField getCompanyName() {
        return companyName;
    }

    public JComboBox getCompanyIndustry() {
        return companyIndustry;
    }

    public JLabel getLbpar1() {
        return lbpar1;
    }

    public JLabel getLbpar2() {
        return lbpar2;
    }

    public JLabel getLbpar3() {
        return lbpar3;
    }

    public JLabel getLbpar4() {
        return lbpar4;
    }

    public JLabel getLbpar5() {
        return lbpar5;
    }

    public JLabel getLbpar6() {
        return lbpar6;
    }

    public String getPar1() {
        if (!par1.getText().isEmpty()) {
            return par1.getText();
        } else
            return "no";
    }

    public String getPar2() {
        if (!par2.getText().isEmpty()) {
            return par2.getText();
        } else
            return "no";
    }

    public String getPar3() {
        if (!par3.getText().isEmpty()) {
            return par3.getText();
        } else
            return "no";
    }

    public String getPar4() {
        if (!par4.getText().isEmpty()) {
            return par4.getText();
        } else
            return "no";
    }

    public String getPar5() {
        if (!par5.getText().isEmpty()) {
            return par5.getText();
        } else
            return "no";
    }

    public String getPar6() {
        if (!par6.getText().isEmpty()) {
            return par6.getText();
        } else
            return "no";
    }

    public JTextField getCompanySize() {
        return companySize;
    }

    public JComboBox getCountry() {
        return country;
    }

    public void setVisibleForOwner() {
        cost.setVisible(false);
        lebCost.setVisible(false);
        saveBtn.setVisible(false);
        saveOwner.setVisible(true);
    }

    private void addButtonEnable() {
        if (!par1Error.isVisible() && !par2Error.isVisible() && !par3Error.isVisible() && !par4Error.isVisible() && !par5Error.isVisible() && !par6Error.isVisible()
                && !costError.isVisible() && !sizeError.isVisible()) {
            saveOwner.setEnabled(true);
            saveBtn.setEnabled(true);
        } else {
            saveOwner.setEnabled(false);
            saveBtn.setEnabled(false);
        }
    }

    public void insert(JTextField text, JLabel label,boolean pat){
        label.setForeground(Color.red);
        try {
            try {
                float x = Float.parseFloat(text.getText().trim());
                if (x <= 0) {
                    label.setText("Параметр не должен быть меньше 1");
                    label.setVisible(true);
                    pat = false;
                } else if (x > 10000000) {
                    label.setText("Параметр не должен быть больше 10000000");
                    label.setVisible(true);
                    pat = false;
                } else {
                    label.setVisible(false);
                    pat = true;
                }
            } catch (NumberFormatException ev) {
                throw new FloatException();
            }
        }
        catch (FloatException e)
        {
            label.setText(e.toString());
            label.setVisible(true);
            pat = false;
        }
    }

    public void update(JTextField text, JLabel label,boolean pat){
        label.setForeground(Color.red);
        try {
            try {
                float x = Float.parseFloat(text.getText().trim());
                if (x <= 0) {
                    label.setText("Параметр не должен быть меньше 1");
                    label.setVisible(true);
                    pat = false;
                } else if (x > 10000000) {
                    label.setText("Параметр не должен быть больше 10000000");
                    label.setVisible(true);
                    pat = false;
                } else {
                    label.setVisible(false);
                    pat = true;
                }
            } catch (NumberFormatException ev) {

                throw new FloatException();
            }
        }
        catch (FloatException e){
            if (text.getText().trim().equals("")) {
                label.setText("Поле не должно быть пустым");
            } else {
                label.setText(e.toString());
            }
            label.setVisible(true);
            pat = false;
        }

    }


    public void insert1(JTextField text, JLabel label,boolean pat){
        label.setForeground(Color.red);
        try {
            int x = Integer.parseInt(text.getText().trim());
            if (x <= 0) {
                label.setText("Размер компании не должен быть меньше 1");
                label.setVisible(true);
                label.setForeground(Color.RED);
                pat = false;
            } else if (x > 10000000) {
                label.setText("Размер компании не должен быть больше 10000000");
                label.setVisible(true);
                label.setForeground(Color.RED);
                pat = false;
            } else {
                label.setVisible(false);
                pat = true;
            }
        } catch (NumberFormatException ev) {
            label.setText("Поле должно содержать только цифры");
            label.setVisible(true);
            label.setForeground(Color.RED);
            pat = false;
        }

    }

    public void update1(JTextField text, JLabel label,boolean pat) {
        label.setForeground(Color.red);
        try {
            int x = Integer.parseInt(text.getText().trim());
            if (x <= 0) {
                label.setText("Размер компании не должен быть меньше 1");
                label.setVisible(true);
                label.setForeground(Color.RED);
                pat = false;
            } else if (x > 10000000) {
                label.setText("Размер компании не должен быть больше 10000000");
                label.setVisible(true);
                label.setForeground(Color.RED);
                pat = false;
            } else {
                label.setVisible(false);
                pat = true;
            }
        } catch (NumberFormatException ev) {
            if (text.getText().trim().equals("")) {
                label.setText("Поле не должно быть пустым");
            } else {
                label.setText("Поле должно содержать только цифры");
            }
            label.setVisible(true);
            label.setForeground(Color.RED);
            pat = false;
        }
    }
}
