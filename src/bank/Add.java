package bank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import exception.PermissionDeniedException;

public class Add {

  private JPanel contentPane;
  private JFrame frame = new JFrame();
  private JTextField textField_1;
  private JTextField textField_2;
  private JTextField textField_3;
  private JTextField textField_4;
  private JTextField textField_5;
  private JTextField textField_6;
  private JTextField textField_7;

  /**
   * Create the frame.
   */
  public Add(JTable table, String ID) {
    frame.setTitle("添加 - " + ID);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(100, 100, 456, 432);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    frame.setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel label_1 = new JLabel("\u5361\u53F7\uFF1A");
    label_1.setBounds(142, 47, 72, 18);
    contentPane.add(label_1);

    JLabel label_2 = new JLabel("\u59D3\u540D\uFF1A");
    label_2.setBounds(142, 78, 72, 18);
    contentPane.add(label_2);

    JLabel lblId = new JLabel("ID\uFF1A");
    lblId.setBounds(142, 109, 72, 18);
    contentPane.add(lblId);

    JLabel label_3 = new JLabel("\u89D2\u8272\uFF1A");
    label_3.setBounds(142, 140, 72, 18);
    contentPane.add(label_3);

    JLabel label_4 = new JLabel("\u624B\u673A\uFF1A");
    label_4.setBounds(142, 171, 72, 18);
    contentPane.add(label_4);

    JLabel label_5 = new JLabel("\u4F59\u989D\uFF1A");
    label_5.setBounds(142, 202, 72, 18);
    contentPane.add(label_5);

    JLabel label_6 = new JLabel("\u5907\u6CE8\uFF1A");
    label_6.setBounds(142, 233, 72, 18);
    contentPane.add(label_6);

    textField_1 = new JTextField();
    textField_1.setBounds(242, 44, 86, 24);
    contentPane.add(textField_1);
    textField_1.setColumns(10);

    textField_2 = new JTextField();
    textField_2.setBounds(242, 75, 86, 24);
    contentPane.add(textField_2);
    textField_2.setColumns(10);

    textField_3 = new JTextField();
    textField_3.setBounds(242, 106, 86, 24);
    contentPane.add(textField_3);
    textField_3.setColumns(10);

    textField_4 = new JTextField();
    textField_4.setBounds(242, 137, 86, 24);
    contentPane.add(textField_4);
    textField_4.setColumns(10);

    textField_5 = new JTextField();
    textField_5.setBounds(242, 168, 86, 24);
    contentPane.add(textField_5);
    textField_5.setColumns(10);

    textField_6 = new JTextField();
    textField_6.setBounds(242, 199, 86, 24);
    contentPane.add(textField_6);
    textField_6.setColumns(10);

    textField_7 = new JTextField();
    textField_7.setBounds(242, 230, 86, 24);
    contentPane.add(textField_7);
    textField_7.setColumns(10);

    JButton button = new JButton("新建");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!textField_1.getText().equals("") && !textField_2.getText().equals("")
            && !textField_3.getText().equals("") && !textField_4.getText().equals("")) {
          try {
            Role r;
            String roleString = textField_4.getText();
            if (roleString.equals("Client")) {
              r = Role.Client;
            } else if (roleString.equals("Clark")) {
              r = Role.Clark;
            } else if (roleString.equals("Manager")) {
              r = Role.Manager;
            } else if (roleString.equals("Administrator")) {
              r = Role.Administrator;
            } else {
              r = Role.NULL;
            }
            Record record =
                new Record(textField_1.getText(), textField_2.getText(), textField_3.getText(), r);
            if (!textField_5.getText().equals("")) {
              record.setPhone_number(textField_5.getText());
            }
            if (!textField_6.getText().equals("")) {
              record.setBalance(Double.parseDouble(textField_6.getText()));
            }
            if (!textField_7.getText().equals("")) {
              record.setOther_info(textField_7.getText());
            }
            boolean success = Database.add(ID, record);
            if (success) {
              JOptionPane.showMessageDialog(frame, "添加成功");
              frame.setVisible(false);
            } else {
              JOptionPane.showMessageDialog(frame, "添加失败");
            }
          } catch (PermissionDeniedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        } else {
          JOptionPane.showMessageDialog(frame, "信息填写不完整");
        }
      }
    });
    button.setBounds(101, 310, 113, 27);
    contentPane.add(button);

    JButton button_1 = new JButton("\u53D6\u6D88");
    button_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
      }
    });
    button_1.setBounds(238, 310, 113, 27);
    contentPane.add(button_1);

  }
}


