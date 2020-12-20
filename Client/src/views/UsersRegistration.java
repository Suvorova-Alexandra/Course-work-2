package views;

import Controller.Controller;
import model.FloatException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;


public class UsersRegistration extends JFrame {
    private JPanel contentPane;
    private JTextField login;
    private JPasswordField password;
    private JFormattedTextField tel;
    private JButton registrate;
    private JLabel lb1;
    private JLabel lb2;
    private JButton cancel;
    private JTextField nameField;
    private JTextField surnameField;
    MaskFormatter phoneFormatter;
    private JLabel lb3;
    private JLabel lb4;
    private JLabel lb5;
    private JComboBox boxCompany;
    private JLabel labelCompany;
    private JButton addAnalyst;
    private JButton cancelAnalyst;
    private JComboBox countriesCB;
    private JLabel nameError;
    private JLabel surnameError;
    private String code = "+375";
    private boolean pat1 = false;
    private boolean pat2 = false;


    public UsersRegistration() {
        setContentPane(contentPane);
        //setUndecorated(true);
        Controller.getInstance().initialize(this);
        boxCompany.setVisible(false);
        labelCompany.setVisible(false);
        addAnalyst.setVisible(false);
        registrate.setActionCommand("registrationUsers");
        registrate.addActionListener(Controller.getInstance());
        cancel.setActionCommand("backToAutorization");
        cancel.addActionListener(Controller.getInstance());
        cancelAnalyst.setVisible(false);
        nameError.setVisible(false);
        surnameError.setVisible(false);
        addAnalyst.setEnabled(false);
        registrate.setEnabled(false);
        addAnalyst.setActionCommand("registrationAnalyst");
        addAnalyst.addActionListener(Controller.getInstance());
        cancelAnalyst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        countriesCB.setActionCommand("countriesPhone");
        countriesCB.addActionListener(Controller.getInstance());
        password.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (password.getText().length() >= 15) // limit to 3 characters
                {
                    password.setText("");
                    JOptionPane.showMessageDialog(UsersRegistration.this,
                            "Вы ввели больше 15 символов",
                            "Ошибка регистрации",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        login.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (login.getText().length() >= 15) // limit to 3 characters
                {
                    login.setText("");
                    JOptionPane.showMessageDialog(UsersRegistration.this,
                            "Вы ввели больше 15 символов",
                            "Ошибка регистрации",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (nameField.getText().length() >= 15) // limit to 3 characters
                {
                    nameField.setText("");
                    JOptionPane.showMessageDialog(UsersRegistration.this,
                            "Вы ввели больше 15 символов",
                            "Ошибка регистрации",
                            JOptionPane.ERROR_MESSAGE);
                }
                addButton();

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        surnameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (surnameField.getText().length() >= 15) // limit to 3 characters
                {
                    surnameField.setText("");
                    JOptionPane.showMessageDialog(UsersRegistration.this,
                            "Вы ввели больше 15 символов",
                            "Ошибка регистрации",
                            JOptionPane.ERROR_MESSAGE);
                }
                addButton();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        DocumentListener listener1 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(surnameField, surnameError, pat1);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(surnameField, surnameError, pat1);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        DocumentListener listener2 = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                insert(nameField, nameError, pat2);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(nameField, nameError, pat2);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };

        surnameField.getDocument().addDocumentListener(listener1);
        nameField.getDocument().addDocumentListener(listener2);
    }


    public JTextField getLogin() {
        return login;
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JFormattedTextField getPhoneField() {
        return tel;
    }

    public JTextField getSurnameField() {
        return surnameField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public String getBoxCompany() {
        return boxCompany.getSelectedItem().toString();
    }

    public JComboBox getBoxCompany2() {
        return boxCompany;
    }

    public void change() {
        DefaultFormatterFactory factory = new DefaultFormatterFactory(phoneFormatter);
        System.out.println("mask"+phoneFormatter.getMask());
        System.out.println("тел");
        tel.setFormatterFactory(factory);
        tel.setValue(null);
    }

    public void companyVisible() {
        boxCompany.setVisible(true);
        labelCompany.setVisible(true);
        addAnalyst.setVisible(true);
        registrate.setVisible(false);
        cancelAnalyst.setVisible(true);
        cancel.setVisible(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        try {
            phoneFormatter = new MaskFormatter(code + "-###-###-##-##");
            phoneFormatter.setPlaceholderCharacter('_');
            tel = new JFormattedTextField(phoneFormatter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JComboBox getCountriesCB() {
        return countriesCB;
    }

    public String getCode() {
        return code;
    }

    public MaskFormatter getPhoneFormatter() {
        return phoneFormatter;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void insert(JTextField text, JLabel label, boolean pat) {

        String text2 = text.getText();
        boolean match = text2.matches("[a-zA-Zа-яёА-ЯЁ]+");
        if (!match) {
            label.setText("Неправильный ввод! Должны быть только буквы.");
            label.setForeground(Color.RED);
            label.setVisible(true);
            pat = false;
        } else {
            label.setVisible(false);
            pat = true;
        }
    }

    public void update(JTextField text, JLabel label, boolean pat) {

        String text2 = text.getText();
        boolean match = text2.matches("[a-zA-Zа-яёА-ЯЁ]+");
        if (!match) {
            label.setText("Неправильный ввод! Должны быть только буквы.");
            label.setForeground(Color.RED);
            label.setVisible(true);
            pat = false;
        } else {
            label.setVisible(false);
            pat = true;
        }

    }

    public void addButton() {
        if (nameError.isVisible() && surnameError.isVisible() || nameError.isVisible() || surnameError.isVisible()) {
            addAnalyst.setEnabled(false);
            registrate.setEnabled(false);
        } else {
            addAnalyst.setEnabled(true);
            registrate.setEnabled(true);
        }
    }
}
