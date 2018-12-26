package bank;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;

public class Client extends JFrame {

  private JPanel contentPane;
  private Role currentRole=Role.NULL;
  private String currentUID="N/A";
  private JTable table;
  private JLabel username_label;
  private final Action action = new LoginAction();
  private final Action action2 = new LogoutAction();
  private final Action action3 = new SearchAction();
  private final Action action4 = new ModifyAction();
  private final Action action5 = new DeleteAction();
  private final Action action6 = new AddAction();
  private final Action action7 = new AboutAction();

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Database.initDatabase(Role.Client,"D:\\database.txt");
          Client frame = new Client();
          frame.setVisible(true);
          frame.setTitle("Bank System Client Program");
          frame.setResizable(false);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public Client() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 600, 500);
    
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    
    JMenu account_menu = new JMenu("\u8D26\u6237");
    menuBar.add(account_menu);
    
    JMenuItem menuItem_login = new JMenuItem("\u767B\u5F55");
    menuItem_login.setAction(action);
    account_menu.add(menuItem_login);
    
    JMenuItem menuItem_logout = new JMenuItem("\u6CE8\u9500");
    menuItem_logout.setAction(action2);
    account_menu.add(menuItem_logout);
    
    JMenu data_menu = new JMenu("\u64CD\u4F5C");
    menuBar.add(data_menu);
    
    JMenuItem menuItem_search = new JMenuItem("\u67E5\u8BE2");
    menuItem_search.setAction(action3);
    data_menu.add(menuItem_search);
    
    JMenuItem menuItem_modify = new JMenuItem("\u4FEE\u6539");
    menuItem_modify.setAction(action4);
    data_menu.add(menuItem_modify);
    
    JMenuItem menuItem_delete = new JMenuItem("\u5220\u9664");
    menuItem_delete.setAction(action5);
    data_menu.add(menuItem_delete);
    
    JMenuItem menuItem_add = new JMenuItem("新建");
    menuItem_add.setAction(action6);
    data_menu.add(menuItem_add);
    
    JMenu menu_2 = new JMenu("\u5173\u4E8E");
    menuBar.add(menu_2);
    
    JMenuItem menuItem_5 = new JMenuItem("\u5173\u4E8E\u672C\u7A0B\u5E8F");
    menuItem_5.setAction(action7);
    menu_2.add(menuItem_5);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    table = new JTable(new TableModel());
    table.setBounds(14, 13, 395, 401);
    table.setDragEnabled(false);
    JScrollPane scroll = new JScrollPane(table);  
    scroll.setBounds(14, 13, 395, 401);
    contentPane.add(scroll);
    
    JButton button = new JButton("\u67E5\u8BE2");
    button.setBounds(443, 32, 113, 27);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              Search frame = new Search(table,currentUID);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    });
    contentPane.add(button);
    
    JButton button_1 = new JButton("\u4FEE\u6539");
    button_1.setBounds(443, 87, 113, 27);
    button_1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              Modify frame = new Modify(table,currentUID);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    });
    contentPane.add(button_1);
    
    JButton button_2 = new JButton("\u5220\u9664");
    button_2.setBounds(443, 142, 113, 27);
    button_2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              Delete frame = new Delete(table,currentUID);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    });
    contentPane.add(button_2);
    
    JButton button_3 = new JButton("新建");
    button_3.setBounds(443, 197, 113, 27);
    button_3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              Add frame = new Add(table,currentUID);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    });
    contentPane.add(button_3);
    
    JButton button_4 = new JButton("保存并退出");
    button_4.setBounds(443, 252, 113, 27);
    button_4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        try {
          Database.saveFile("D:\\database.txt");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        Database.logger.log(Level.INFO,"程序退出");
        System.exit(0);
      }
    });
    contentPane.add(button_4);
   
    
    JLabel label = new JLabel("\u5F53\u524D\u7528\u6237\uFF1A");
    label.setBounds(423, 358, 133, 18);
    contentPane.add(label);
    
    username_label = new JLabel("\u672A\u767B\u5F55");
    username_label.setFont(new Font("微软雅黑", Font.BOLD, 17));
    username_label.setBounds(423, 383, 145, 31);
    contentPane.add(username_label);
  }
  
  void setUID(Role role,String uid) {
    if(role.compareTo(Role.Administrator)>=0) {
      this.currentUID=uid;
      this.username_label.setText(currentUID);
    }
  }
  
  void setRole(Role role,Role new_role) {
    if(role.compareTo(Role.Administrator)>=0)
      this.currentRole=new_role;
  }
  
  private class LoginAction extends AbstractAction {
    public LoginAction() {
      putValue(NAME, "登录");
      putValue(SHORT_DESCRIPTION, "登陆到系统以确认身份");
    }
    public void actionPerformed(ActionEvent e) {
      EventQueue.invokeLater(new Runnable() {
        public void run() {
          try {
            Login login = new Login(Client.this);
          } catch (Exception e1) {
            e1.printStackTrace();
          }
        }
      });
    }
  }
  
  private class LogoutAction extends AbstractAction {
    public LogoutAction() {
      putValue(NAME, "注销");
      putValue(SHORT_DESCRIPTION, "注销账号");
    }
    public void actionPerformed(ActionEvent e) {
      if(currentRole.compareTo(Role.NULL)>0) {
        Database.logger.log(Level.INFO, "当前用户 "+currentUID+" 注销");
        setRole(Role.Administrator, Role.NULL);
        setUID(Role.Administrator,"N/A");
        table.setModel(new TableModel());
        JOptionPane.showMessageDialog(Client.this, "Successfully logged out.\nNow your uid is N/A");
      }else {
        JOptionPane.showMessageDialog(Client.this, "No need to log out");
      }
    }
  }
  
  private class SearchAction extends AbstractAction {
    public SearchAction() {
      putValue(NAME, "查询");
      putValue(SHORT_DESCRIPTION, "查询信息");
    }
    public void actionPerformed(ActionEvent e) {
      EventQueue.invokeLater(new Runnable() {
        public void run() {
          try {
            Search frame = new Search(table,currentUID);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
  }
  
  private class ModifyAction extends AbstractAction {
    public ModifyAction() {
      putValue(NAME, "修改");
      putValue(SHORT_DESCRIPTION, "修改信息");
    }
    public void actionPerformed(ActionEvent e) {
      EventQueue.invokeLater(new Runnable() {
        public void run() {
          try {
            Modify frame = new Modify(table,currentUID);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
  }
  
  private class DeleteAction extends AbstractAction {
    public DeleteAction() {
      putValue(NAME, "删除");
      putValue(SHORT_DESCRIPTION, "删除信息");
    }
    public void actionPerformed(ActionEvent e) {
      EventQueue.invokeLater(new Runnable() {
        public void run() {
          try {
            Delete frame = new Delete(table,currentUID);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
  }
  
  private class AddAction extends AbstractAction {
    public AddAction() {
      putValue(NAME, "添加");
      putValue(SHORT_DESCRIPTION, "添加信息");
    }
    public void actionPerformed(ActionEvent e) {
      EventQueue.invokeLater(new Runnable() {
        public void run() {
          try {
            Add frame = new Add(table,currentUID);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
  }
  
  private class AboutAction extends AbstractAction {
    public AboutAction() {
      putValue(NAME, "关于本程序");
      putValue(SHORT_DESCRIPTION, "本程序的相关信息");
    }
    public void actionPerformed(ActionEvent e) {
      JOptionPane.showMessageDialog(Client.this, "由Dotman编写的银行完整性模拟系统\nDotman(c)2018");
    }
  }
}
