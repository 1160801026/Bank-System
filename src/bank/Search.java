package bank;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import exception.PermissionDeniedException;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Search {

  private JFrame frame = new JFrame();
  private JPanel contentPane;
  private JTextField textField;

  /**
   * Create the frame.
   */
  public Search(JTable table,String ID) {
    frame.setTitle("ËÑË÷ - " + ID);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(100, 100, 450, 211);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    frame.setContentPane(contentPane);
    contentPane.setLayout(null);

    textField = new JTextField();
    textField.setBounds(247, 48, 86, 24);
    contentPane.add(textField);
    textField.setColumns(10);

    JLabel label = new JLabel("\u8F93\u5165\u60F3\u8981\u67E5\u8BE2\u7684\u5361\u53F7\uFF1A");
    label.setBounds(74, 51, 159, 18);
    contentPane.add(label);

    JButton button = new JButton("\u67E5\u8BE2");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!textField.getText().equals("")) {
          try {
            Record record= Database.search(ID, textField.getText());
            if(record!=null) {
              List<Record> records=new ArrayList<>();
              records.add(record);
              table.setModel(new TableModel(records));
              frame.setVisible(false);
            }else {
              JOptionPane.showMessageDialog(frame, "No result");
            }
          } catch (PermissionDeniedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        }else {
          JOptionPane.showMessageDialog(frame, "¿¨ºÅ²»ÄÜÎª¿Õ£¡");
        }
      }
    });
    button.setBounds(68, 106, 113, 27);
    contentPane.add(button);

    JButton button_1 = new JButton("\u53D6\u6D88");
    button_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        frame.setVisible(false);
      }
    });
    button_1.setBounds(255, 106, 113, 27);
    contentPane.add(button_1);
  }
}
