package model.Client;
//import view.EnterDialog;

import views.EnterDialog;

public class StartDo {
    public static void main(String[] args) {
        EnterDialog dialog = new EnterDialog();
        dialog.setTitle("Авторизация");
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    // System.exit(0);
    }
}