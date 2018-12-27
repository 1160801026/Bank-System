package bank;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Login {

  private JFrame frame = new JFrame();
  private JPanel contentPane;
  private JTextField textField;
  private JTextField textField_1;
  private Role role = Role.NULL;
  private String uid = "N/A";
  private Client client;

  /**
   * Create the frame.
   */
  public Login(Client client) {
    this.client=client;
    frame.setTitle("登录");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    frame.setContentPane(contentPane);
    contentPane.setLayout(null);

    textField = new JTextField();
    textField.setBounds(199, 63, 120, 24);
    contentPane.add(textField);
    textField.setColumns(10);

    textField_1 = new JTextField();
    textField_1.setBounds(199, 122, 120, 24);
    contentPane.add(textField_1);
    textField_1.setColumns(10);

    JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");
    label.setBounds(113, 66, 72, 18);
    contentPane.add(label);

    JLabel label_1 = new JLabel("\u5BC6  \u7801\uFF1A");
    label_1.setBounds(113, 125, 72, 18);
    contentPane.add(label_1);

    JButton btnNewButton = new JButton("\u767B\u5F55");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          if (!textField.getText().equals("") && !textField_1.getText().equals("")) {
            String result = Database.login(textField.getText(), textField_1.getText());
            if (result.equals("Client")) {
              role = Role.Client;
              uid = textField.getText();
              
              client.setUID(Role.Administrator, uid);
              client.setRole(Role.Administrator, role);
              frame.setVisible(false);
            } else if (result.equals("Clark")) {
              role = Role.Clark;
              uid = textField.getText();
              client.setUID(Role.Administrator, uid);
              client.setRole(Role.Administrator, role);
              frame.setVisible(false);
            } else if (result.equals("Manager")) {
              role = Role.Manager;
              uid = textField.getText();
              client.setUID(Role.Administrator, uid);
              client.setRole(Role.Administrator, role);
              frame.setVisible(false);
            } else if (result.equals("Administrator")) {
              role = Role.Administrator;
              uid = textField.getText();
              client.setUID(Role.Administrator, uid);
              client.setRole(Role.Administrator, role);
              frame.setVisible(false);
            } else if (result.equals("NULL")) {
              role = Role.NULL;
              frame.setVisible(false);
            } else {
              JOptionPane.showMessageDialog(frame, result);
            }
          } else {
            JOptionPane.showMessageDialog(frame, "用户名和密码均不能为空！");
          }
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    btnNewButton.setBounds(89, 189, 113, 27);
    contentPane.add(btnNewButton);

    JButton button = new JButton("\u53D6\u6D88");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        frame.setVisible(false);
      }
    });
    button.setBounds(251, 189, 113, 27);
    contentPane.add(button);
  }
}
