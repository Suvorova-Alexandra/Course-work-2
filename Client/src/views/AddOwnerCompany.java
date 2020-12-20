package views;

import Controller.Controller;
import model.FloatException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddOwnerCompany extends JFrame {
    private JPanel contentPane;
    private JButton cancel;
    private JButton add;
    private JButton clear;
    SpinnerNumberModel model = new SpinnerNumberModel(300, 1, 100000000, 1);
    private JTextField nameCompany;
    private JTextField size;
    private JComboBox industry;
    private JTextField tf1;
    private String[] madel = {"Выручка", "Чистая прибыль", "Прибыль до уплаты налогов", "Денежный поток", "Денежный поток до налогообложения", "Дивиденды"};
    private String[] industries = {"Не выбрано","Промышленность", "Сельское хозяйство", "Лесное хозяйство", "Строительство", "Прочие виды деятельности сферы материального производства","Обслуживание сельского хозяйства","Транспорт","Связь","Торговля и общественное питание",
    "Материально-техническое снабжение и сбыт","Информационно-вычислительное обслуживание","Операции с недвижимым имуществом","Общая коммерческая деятельность по обеспечению функционирования рынка","Геология и разведка недр, геодезическая и гидрометеорологическая службы","Жилищное хозяйство","Коммунальное хозяйство","Непроизводственные виды бытового обслуживания населения","Здравоохранение, физическая культура и социальное обеспечение",
    "Народное образование","Культура и искусство","Наука и научное обслуживание","Финансы, кредит, страхование, пенсионное обеспечение","Управление", "Общественные объединения"};
    private JComboBox countryBox;
    private JTextField cost;
    private JLabel lbCost;
    private JButton addAnalog;
    private JComboBox comboBoxParametrs1;
    private JButton addParameter;
    private JTextField tf2;
    private JTextField tf3;
    private JTextField tf4;
    private JTextField tf5;
    private JTextField tf6;
    private JComboBox comboBoxParametrs2;
    private JComboBox comboBoxParametrs3;
    private JComboBox comboBoxParametrs4;
    private JComboBox comboBoxParametrs5;
    private JComboBox comboBoxParametrs6;
    private JLabel lb1;
    private JLabel lb2;
    private JLabel lb3;
    private JLabel lb4;
    private JLabel lb5;
    private JLabel lb6;
    private JButton hideParameter;
    private JLabel sizeError;
    private JLabel tf1Error;
    private JLabel tf2Error;
    private JLabel tf3Error;
    private JLabel tf4Error;
    private JLabel tf5Error;
    private JLabel tf6Error;
    private JLabel costError;
    private boolean pr1=false;
    private boolean pr2=false;
    private boolean pr3=false;
    private boolean pr4=false;
    private boolean pr5=false;
    private boolean pr6=false;

    private boolean pr7=false;

    private boolean pr8=false;
    private int count = 0;

    public AddOwnerCompany() {
        setContentPane(contentPane);
        //setUndecorated(true);
       // getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
        lbCost.setVisible(false);
        cost.setVisible(false);
        add.setEnabled(false);
        addAnalog.setEnabled(false);
        costError.setVisible(false);
        tf1Error.setVisible(false);
        tf2Error.setVisible(false);
        tf3Error.setVisible(false);
        tf4Error.setVisible(false);
        tf5Error.setVisible(false);
        tf6Error.setVisible(false);
        sizeError.setVisible(false);

        DefaultComboBoxModel model1 = new DefaultComboBoxModel(madel);
        DefaultComboBoxModel model2 = new DefaultComboBoxModel(madel);
        DefaultComboBoxModel model3 = new DefaultComboBoxModel(madel);
        DefaultComboBoxModel model4 = new DefaultComboBoxModel(madel);
        DefaultComboBoxModel model5 = new DefaultComboBoxModel(madel);
        DefaultComboBoxModel model6 = new DefaultComboBoxModel(madel);
        DefaultComboBoxModel industries1=new DefaultComboBoxModel(industries);
        addAnalog.setVisible(false);
        lb2.setVisible(false);
        lb3.setVisible(false);
        lb4.setVisible(false);
        lb5.setVisible(false);
        lb6.setVisible(false);
        tf2.setVisible(false);
        tf3.setVisible(false);
        tf4.setVisible(false);
        tf5.setVisible(false);
        tf6.setVisible(false);
        comboBoxParametrs2.setVisible(false);
        comboBoxParametrs3.setVisible(false);
        comboBoxParametrs4.setVisible(false);
        comboBoxParametrs5.setVisible(false);
        comboBoxParametrs6.setVisible(false);
        comboBoxParametrs1.setModel(model1);
        industry.setModel(industries1);
        Controller.getInstance().initialize(this);
        add.setActionCommand("addOwnerCompany");
        add.addActionListener(Controller.getInstance());
        addAnalog.setActionCommand("addAnalogCompany");
        addAnalog.addActionListener(Controller.getInstance());
        hideParameter.setEnabled(false);
        addParameter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (count) {
                    case 0: {
                        lb2.setVisible(true);
                        hideParameter.setEnabled(true);
                        comboBoxParametrs2.setVisible(true);
                        comboBoxParametrs2.setModel(model2);
                        tf2.setVisible(true);
                    }
                    break;
                    case 1: {
                        lb3.setVisible(true);
                        comboBoxParametrs3.setVisible(true);
                        comboBoxParametrs3.setModel(model3);
                        tf3.setVisible(true);
                    }
                    break;
                    case 2: {
                        lb4.setVisible(true);
                        comboBoxParametrs4.setVisible(true);
                        comboBoxParametrs4.setModel(model4);
                        tf4.setVisible(true);
                    }
                    break;
                    case 3: {
                        lb5.setVisible(true);
                        comboBoxParametrs5.setVisible(true);
                        comboBoxParametrs5.setModel(model5);
                        tf5.setVisible(true);
                    }
                    break;
                    case 4: {
                        lb6.setVisible(true);
                        comboBoxParametrs6.setVisible(true);
                        comboBoxParametrs6.setModel(model6);
                        tf6.setVisible(true);
                        addParameter.setEnabled(false);

                    }
                    break;


                }
                count++;

            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameCompany.setText("");
                size.setText("");
                industry.setSelectedItem("Не выбрано");
                countryBox.setSelectedItem("Не выбрано");
                tf1.setText("");
                tf2.setText("");
                tf3.setText("");
                tf4.setText("");
                tf5.setText("");
                tf6.setText("");
                cost.setText("");
                lb2.setVisible(false);
                comboBoxParametrs2.setVisible(false);
                tf2.setVisible(false);
                hideParameter.setEnabled(false);
                lb3.setVisible(false);
                comboBoxParametrs3.setVisible(false);
                tf3.setVisible(false);
                lb4.setVisible(false);
                comboBoxParametrs4.setVisible(false);
                tf4.setVisible(false);
                lb5.setVisible(false);
                comboBoxParametrs5.setVisible(false);
                tf5.setVisible(false);
                lb6.setVisible(false);
                comboBoxParametrs6.setVisible(false);
                tf6.setVisible(false);
                addParameter.setEnabled(true);
                count = 0;
            }
        });
        hideParameter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (count) {
                    case 0: {

                    }
                    case 1: {
                        lb2.setVisible(false);
                        comboBoxParametrs2.setVisible(false);
                        tf2.setVisible(false);
                        hideParameter.setEnabled(false);
                    }
                    break;
                    case 2: {
                        lb3.setVisible(false);
                        comboBoxParametrs3.setVisible(false);
                        tf3.setVisible(false);
                    }
                    break;
                    case 3: {
                        lb4.setVisible(false);
                        comboBoxParametrs4.setVisible(false);
                        tf4.setVisible(false);
                    }
                    break;
                    case 4: {
                        lb5.setVisible(false);
                        comboBoxParametrs5.setVisible(false);
                        tf5.setVisible(false);
                    }
                    break;
                    case 5: {
                        lb6.setVisible(false);
                        comboBoxParametrs6.setVisible(false);
                        tf6.setVisible(false);
                        addParameter.setEnabled(true);
                    }
                    break;
                }
                count--;
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        DocumentListener listener1 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               insert(tf1,tf1Error,pr1);
               addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
               update(tf1,tf1Error,pr1);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener2 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(tf2,tf2Error,pr2);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(tf2,tf2Error,pr2);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener3 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(tf3,tf3Error,pr3);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(tf3,tf3Error,pr3);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener4 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(tf4,tf4Error,pr4);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(tf4,tf4Error,pr4);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener5 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(tf5,tf5Error,pr5);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(tf5,tf5Error,pr5);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener6 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(tf6,tf6Error,pr6);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(tf6,tf6Error,pr6);
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
                insert1(size,sizeError,pr8);
                addButtonEnable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update1(size,sizeError,pr8);
                addButtonEnable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        tf1.getDocument().addDocumentListener(listener1);
        tf2.getDocument().addDocumentListener(listener2);
        tf3.getDocument().addDocumentListener(listener3);
        tf4.getDocument().addDocumentListener(listener4);
        tf5.getDocument().addDocumentListener(listener5);
        tf6.getDocument().addDocumentListener(listener6);
        size.getDocument().addDocumentListener(listener8);
        cost.getDocument().addDocumentListener(listener7);
    }

    public JComboBox getCountryBox() {
        return countryBox;
    }

    public JTextField getNameCompany() {
        return nameCompany;
    }

    public JTextField getSizeCompany() {
        return size;

    }

    public JComboBox getIndustry() {
        return industry;
    }



    public void visibleCost() {
        lbCost.setVisible(true);
        cost.setVisible(true);

        addAnalog.setVisible(true);
        add.setVisible(false);

    }

    public JTextField getCost() {
        return cost;
    }

    public String getTf1() {
        if (tf1.isVisible()) {
            return tf1.getText();
        } else return "no";
    }

    public String getTf2() {

        if (tf2.isVisible()) {
            return tf2.getText();
        } else return "no";
    }

    public String getTf3() {

        if (tf3.isVisible()) {
            return tf3.getText();
        } else return "no";
    }

    public String getTf4() {

        if (tf4.isVisible()) {
            return tf4.getText();
        } else return "no";
    }

    public String getTf5() {

        if (tf5.isVisible()) {
            return tf5.getText();
        } else return "no";
    }

    public String getTf6() {

        if (tf6.isVisible()) {
            return tf6.getText();
        } else return "no";
    }

    public String getComboBoxParametrs1() {
        if (comboBoxParametrs1.isVisible())
            return comboBoxParametrs1.getSelectedItem().toString();
        else return "no";
    }

    public String getComboBoxParametrs2() {
        if (comboBoxParametrs2.isVisible())
            return comboBoxParametrs2.getSelectedItem().toString();
        else return "no";
    }

    public String getComboBoxParametrs3() {
        if (comboBoxParametrs3.isVisible())
            return comboBoxParametrs3.getSelectedItem().toString();
        else return "no";
    }

    public String getComboBoxParametrs4() {
        if (comboBoxParametrs4.isVisible())
            return comboBoxParametrs4.getSelectedItem().toString();
        else return "no";
    }

    public String getComboBoxParametrs5() {
        if (comboBoxParametrs5.isVisible())
            return comboBoxParametrs5.getSelectedItem().toString();
        else return "no";
    }

    public String getComboBoxParametrs6() {
        if (comboBoxParametrs6.isVisible())
            return comboBoxParametrs6.getSelectedItem().toString();
        else return "no";
    }

    public JLabel getLb1() {
        return lb1;
    }

    public JLabel getLb2() {
        return lb2;
    }

    public JLabel getLb3() {
        return lb3;
    }

    public JLabel getLb4() {
        return lb4;
    }

    public JLabel getLb5() {
        return lb5;
    }

    public JLabel getLb6() {
        return lb6;
    }



    private void addButtonEnable() {
        if (!tf1Error.isVisible() && !tf2Error.isVisible() && !tf3Error.isVisible() && !tf4Error.isVisible() && !tf5Error.isVisible() && !tf6Error.isVisible()
               && !costError.isVisible() && !sizeError.isVisible()) {
            add.setEnabled(true);
            addAnalog.setEnabled(true);
        } else {add.setEnabled(false);
            addAnalog.setEnabled(false);}
    }

    public void insert(JTextField text, JLabel label,boolean pat){
        label.setForeground(Color.red);
        try {
            try {
                float x = Float.parseFloat(text.getText().trim());
                if (x <= 0) {
                    label.setText("Параметр не должен быть меньше 1");
                    label.setVisible(true);
                    label.setForeground(Color.RED);
                    pat = false;
                } else if (x > 10000000) {
                    label.setText("Параметр не должен быть больше 10000000");
                    label.setVisible(true);
                    label.setForeground(Color.RED);
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
            label.setForeground(Color.RED);
            pat = false;
        }
    }

    public void update(JTextField text, JLabel label,boolean pat){
        label.setForeground(Color.red);
        try {
            float x = Float.parseFloat(text.getText().trim());
            if (x <= 0) {
                label.setText("Параметр не должен быть меньше 1");
                label.setVisible(true);
                label.setForeground(Color.RED);
                pat = false;
            } else if (x > 10000000) {
                label.setText("Параметр не должен быть больше 10000000");
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

    public void update1(JTextField text, JLabel label,boolean pat){
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

