package views;

import javax.swing.*;
import Controller.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EnterDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonEnter;
    private JButton buttonRegistration;
    private JTextField textLogin;
    private JPasswordField passwordField1;
    private JLabel lb1;
    private JLabel lb2;

    public JTextField getTextLogin(){
        return textLogin;
    }
    public JPasswordField getPasswordField1(){
        return passwordField1;
    }

    public EnterDialog() {
        setContentPane(contentPane);
        //setUndecorated(true);
        //getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
        setModal(true);
        getRootPane().setDefaultButton(buttonEnter);
        Controller.getInstance().initialize(this);
        buttonEnter.addActionListener(Controller.getInstance());
        buttonRegistration.setActionCommand("showRegistrationFrame");
        buttonRegistration.addActionListener(Controller.getInstance());
        setDefaultCloseOperation(EnterDialog.DISPOSE_ON_CLOSE);
        textLogin.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textLogin.getText().length() >= 15) // limit to 3 characters
                {
                    textLogin.setText("");
                    JOptionPane.showMessageDialog(EnterDialog.this,
                            "Вы ввели больше 15 символов",
                            "Ошибка авторизации",
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
        passwordField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (passwordField1.getText().length() >= 15) // limit to 3 characters
                {
                    passwordField1.setText("");
                    JOptionPane.showMessageDialog(EnterDialog.this,
                            "Вы ввели больше 15 символов",
                            "Ошибка авторизации",
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
    }

    

}
