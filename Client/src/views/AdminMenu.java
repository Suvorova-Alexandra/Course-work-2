package views;
import Controller.Controller;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.*;

public class AdminMenu extends JFrame {
    private JPanel contentPane;
    private JButton exit;
    private JTable tableUsers;
    private JButton delete;
    private JTable tableCompanies;
    private JComboBox comboBoxSort;
    private JComboBox comboBoxFilterRole;
    private JButton deleteCompany;
    private JButton forbid;
    private JButton allow;
    private String[] roles={"Не выбрано","Владелец","Аналитик"};
    private String[] sorting={"По дате добавления","По алфавиту","По размеру компании(по убыванию)"};
    private EnterDialog dialog;

    public AdminMenu() {
            setContentPane(contentPane);
        //setUndecorated(true);
        deleteCompany.setVisible(false);
        getRootPane().setBackground(new Color(70,80,70));
        dialog = new EnterDialog();
        forbid.setEnabled(false);
        allow.setEnabled(false);
        Controller.getInstance().initialize(this);
        tableUsers.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(tableUsers.getSelectedRow());
                    delete.setEnabled(true);
                    forbid.setEnabled(true);
                    allow.setEnabled(true);
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
                System.out.println(tableCompanies.getSelectedRow());
                deleteCompany.setEnabled(true);
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
        DefaultComboBoxModel model=new DefaultComboBoxModel(roles);
        comboBoxFilterRole.setModel(model);
        DefaultComboBoxModel model2=new DefaultComboBoxModel(sorting);
        comboBoxSort.setModel(model2);
        comboBoxFilterRole.setActionCommand("FilterRolesAdmin");
        comboBoxFilterRole.addActionListener(Controller.getInstance());
        comboBoxSort.setActionCommand("SortCompaniesAdmin");
        comboBoxSort.addActionListener(Controller.getInstance());
        delete.setActionCommand("deleteUser");
        delete.addActionListener(Controller.getInstance());
        delete.setEnabled(false);
        deleteCompany.setEnabled(false);
        deleteCompany.setActionCommand("deleteOwnerCompanyAdmin");
        deleteCompany.addActionListener(Controller.getInstance());
        forbid.setActionCommand("forbidAccess");
        forbid.addActionListener(Controller.getInstance());
        allow.setActionCommand("allowAccess");
        allow.addActionListener(Controller.getInstance());
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
        setDefaultCloseOperation(AdminMenu.EXIT_ON_CLOSE);
    }
    public JTable getTableUsers() {
        return tableUsers;
    }
    public String[] getSelected() {
        int[] selectedRows = tableUsers.getSelectedRows();
        String[] title=new String[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            int selIndex = selectedRows[i];
            title[i]= (String) tableUsers.getValueAt(selIndex, 4);
        }
        return title;
    }

    public String[] getSelectedCompanies() {
        int[] selectedRows = tableCompanies.getSelectedRows();
        String[] title=new String[selectedRows.length];
        for (int i = 0; i < selectedRows.length; i++) {
            int selIndex = selectedRows[i];
            title[i]= (String) tableCompanies.getValueAt(selIndex, 4);
        }
        return title;
    }


   public JButton getDeleteButton(){
        return delete;
   }
    public JButton getDeleteCompanyButton(){
        return deleteCompany;
    }
   public JTable getTableCompanies(){
        return tableCompanies;
   }

    public String getSelectedRole() {
        String title;
        title = comboBoxFilterRole.getSelectedItem().toString();
        return title;
    }

    public String getSelectedSort() {
        String title;
        title = comboBoxSort.getSelectedItem().toString();
        return title;
    }

    public JComboBox getComboBoxFilterRole() {
        return comboBoxFilterRole;
    }
}
