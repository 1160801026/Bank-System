package bank;

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
import java.awt.event.ActionEvent;

public class Delete {

  private JPanel contentPane;
  private JFrame frame=new JFrame();
  private JTextField textField;

  /**
   * Create the frame.
   */
  public Delete(JTable table,String ID) {
    frame.setTitle("É¾³ý- " + ID);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    frame.setContentPane(contentPane);
    contentPane.setLayout(null);
    
    textField = new JTextField();
    textField.setBounds(275, 75, 113, 24);
    contentPane.add(textField);
    textField.setColumns(10);
    
    JLabel label = new JLabel("\u8F93\u5165\u8981\u5220\u9664\u7684\u8BB0\u5F55\u5BF9\u5E94\u7684\u5361\u53F7\uFF1A");
    label.setBounds(45, 78, 230, 18);
    contentPane.add(label);
    
    JButton button = new JButton("\u786E\u8BA4\u5220\u9664");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        if (!textField.getText().equals("")) {
          try {
            boolean success=Database.delete(ID, textField.getText());
            if(success) {
              JOptionPane.showMessageDialog(frame, "É¾³ý³É¹¦");
              frame.setVisible(false);
            }else {
              JOptionPane.showMessageDialog(frame, "É¾³ýÊ§°Ü£¬ÇëÈ·ÈÏ¿¨ºÅ´æÔÚ");
            }
          } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }else {
          JOptionPane.showMessageDialog(frame, "¿¨ºÅ²»ÄÜÎª¿Õ£¡");
        }
      }
    });
    button.setBounds(80, 171, 113, 27);
    contentPane.add(button);
    
    JButton button_1 = new JButton("\u53D6\u6D88");
    button_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
      }
    });
    button_1.setBounds(249, 171, 113, 27);
    contentPane.add(button_1);
    
    
  }

}
