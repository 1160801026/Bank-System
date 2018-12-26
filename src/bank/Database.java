package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import exception.PermissionDeniedException;

public class Database {
  private static Map<String, Record> records = new HashMap<>(); // card_number,Record
  private static Map<String, Role> ID2role = new HashMap<>(); // ID_number,Role
  private static Map<String, String> ID2name = new HashMap<>(); // ID_number,name
  static Logger logger=Logger.getLogger("Bank System Log");

  public Database(Role role, String filesrc) throws IOException {
    initDatabase(role, filesrc);
  }

  public static String getNamebyID(Role role, String id) throws PermissionDeniedException {
    String name = "N/A";
    if (role.compareTo(Role.Clark) >= 0) {
      name = ID2name.get(id);
      return name;
    } else {
      throw new PermissionDeniedException(role, Role.Clark);
    }
  }

  public static Role getRolebyID(Role role, String id) throws PermissionDeniedException {
    Role result = Role.NULL;
    if (role.compareTo(Role.Clark) >= 0) {
      result = ID2role.get(id);
      return result;
    } else {
      throw new PermissionDeniedException(role, Role.Clark);
    }
  }

  public static Record getRecordbyID(Role role, String id) throws PermissionDeniedException {
    Record record = null;
    if (role.compareTo(Role.Clark) >= 0) {
      record = records.get(id);
      return record;
    } else {
      throw new PermissionDeniedException(role, Role.Clark);
    }
  }

  public static void initDatabase(Role role, String filesrc) throws IOException {   
    FileHandler fileHandler = new FileHandler("D:\\BankLog.txt");
    fileHandler.setFormatter(new SimpleFormatter());
    logger.addHandler(fileHandler);
    logger.log(Level.INFO,"数据库开始初始化……");
    
    FileInputStream inputstream = new FileInputStream(new File(filesrc));
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));

    String str = "";
    while ((str = reader.readLine()) != null) {
      String[] tmp = str.split(",");
      Role r = Role.NULL;
      String roleString = tmp[3];
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
      Record record = new Record(tmp[0], tmp[1], tmp[2], r);
      if (tmp.length > 4) {
        record.setPhone_number(tmp[4]);
        if (tmp.length > 5) {
          record.setBalance(Double.parseDouble(tmp[5]));
          if (tmp.length > 6) {
            record.setOther_info(tmp[6]);
          }
        }
      }
      records.put(tmp[0], record);
    }
    reader.close();
    logger.log(Level.INFO, "初始化主数据表完成");
    
    FileInputStream inputstream2 = new FileInputStream(new File("D:\\database_users.txt"));
    BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputstream2));

    String str2 = "";
    while((str2=reader2.readLine())!=null) {
      String[] tmp = str2.split(",");
      Role r;
      String roleString = tmp[2];
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
      ID2role.put(tmp[0], r);
    }
    reader2.close();
    logger.log(Level.INFO, "初始化ID2role表完成");
  }

  public static void saveFile(String filesrc) throws IOException {
    FileWriter fw = new FileWriter(new File(filesrc));
    BufferedWriter writer = new BufferedWriter(fw);

    for (String record : records.keySet()) {
      writer.write(records.get(record).toString() + "\r\n");
    }
    logger.log(Level.INFO, "写入信息完成");
    writer.close();
  }

  public static Record search(String ID, String card_number) throws PermissionDeniedException {
    logger.log(Level.INFO,"用户 "+ID+" 尝试搜索关键字 "+card_number+" 对应的记录");
    Role role = getRolebyID(Role.Administrator, ID);
    Record result = records.get(card_number);
    if (role.compareTo(Role.Clark) >= 0) {
      if(result!=null) {
        logger.log(Level.INFO, "特权用户 "+ID+" ，已返回其希望获得的信息");
      }else {
        logger.log(Level.INFO, "特权用户 "+ID+" ，尝试搜索的"+card_number+"不存在");
      }
      return result;
    } else {
      if (result != null) {
        if (result.getID().equals(ID)) {
          logger.log(Level.INFO, "卡号对应用户 "+ID+" 本人，该用户查询到了自己的相关信息");
          return result;
        } else {
          logger.log(Level.WARNING, "用户 "+ID+" 权限不够，进行了一次无效查询尝试");
          throw new PermissionDeniedException(role, Role.Administrator);
        }
      } else {
        logger.log(Level.INFO, "用户 "+ID+" 键入的信息无对应结果");
        return result;
      }
    }
  }

  /***
   * ATTENTION! Even if this returns true it doesn’t mean something must have been changed.
   * 
   * @param ID
   * @param card_number
   * @param new_record
   * @return
   * @throws PermissionDeniedException
   */
  public static boolean modify(String ID, String card_number, Record new_record)
      throws PermissionDeniedException {
    logger.log(Level.INFO,"用户 "+ID+" 尝试修改关键字 "+card_number+" 对应的记录");
    Role role = getRolebyID(Role.Administrator, ID);
    boolean success = false;
    Record target = search(ID, card_number);
    if (target != null) {
      if (role.compareTo(Role.Clark) >= 0 && role.compareTo(Role.Administrator) < 0) {
        System.out.println("根据角色属性仅允许修改手机号码和其他信息\n");
        if (new_record.getPhone_number() != null) {
          logger.log(Level.INFO,"用户 "+ID+" 修改了关键字 "+card_number+" 对应的手机号码");
          target.setPhone_number(new_record.getPhone_number());
        }
        if(new_record.getBalance()!=-1) {
          logger.log(Level.INFO,"用户 "+ID+" 修改了关键字 "+card_number+" 对应的余额");
          target.setBalance(new_record.getBalance());
        }
        if (new_record.getOther_info() != null) {
          logger.log(Level.INFO,"用户 "+ID+" 修改了关键字 "+card_number+" 对应的备注信息");
          target.setOther_info(new_record.getOther_info());
        }
        success = true;
      } else if (role.compareTo(Role.Administrator) >= 0) {
        System.out.println("根据角色信息可以修改所有信息\n");
        logger.log(Level.INFO,"特权用户 "+ID+" 修改了关键字 "+card_number+" 对应的所有信息");
        records.replace(card_number, new_record);
        success = true;
      } else {
        logger.log(Level.WARNING, "用户 "+ID+" 权限不够，进行了一次无效修改尝试");
        throw new PermissionDeniedException(role, Role.Administrator);
      }
    }
    return success;
  }
  
  public static boolean delete(String ID, String card_number) throws PermissionDeniedException {
    Role role = getRolebyID(Role.Administrator, ID);
    logger.log(Level.INFO,"用户 "+ID+" 尝试删除关键字 "+card_number+" 对应的记录");
    boolean success=false;
    if(role.compareTo(Role.Administrator)>=0) {
      if(records.containsKey(card_number)) {
        records.remove(card_number);
        logger.log(Level.INFO,"用户 "+ID+" 成功删除关键字 "+card_number+" 对应的记录");
        success=true;
      }else {
        logger.log(Level.INFO,"用户 "+ID+" 删除关键字 "+card_number+" 对应的记录失败，因为不存在该条信息");
      }
    }else {
      logger.log(Level.WARNING,"用户 "+ID+" 权限不够，进行了一次无效删除尝试");
      throw new PermissionDeniedException(role, Role.Administrator);
    }
    return success;
  }
  
  public static boolean add(String ID,Record record) throws PermissionDeniedException{
    boolean success=false;
    logger.log(Level.INFO,"用户 "+ID+" 尝试添加一条新记录，对应关键字 "+record.getCard_number());
    Role role=getRolebyID(Role.Administrator, ID);
    if(role.compareTo(Role.Manager)>=0) {
      if(!records.containsKey(record.getCard_number())) {
        logger.log(Level.INFO,"用户 "+ID+" 成功添加一条新记录，对应关键字 "+record.getCard_number());
        records.put(record.getCard_number(), record);
        success=true;
      }
    }else {
      logger.log(Level.WARNING,"用户 "+ID+" 权限不够，进行了一次无效新建尝试");
      throw new PermissionDeniedException(role, Role.Administrator);
    }
    return success;
  }
  
  public static String login(String uid,String passwd) throws IOException {
    FileInputStream inputstream = new FileInputStream(new File("D:\\database_users.txt"));
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
    logger.log(Level.INFO, "用户输入用户名 "+uid+" 尝试登陆");
    
    String str = "";
    boolean find=false;
    while ((str = reader.readLine()) != null) {
      if(str.split(",")[0].equals(uid)) {
        find=true;
        if(str.split(",")[1].equals(passwd)) {
          reader.close();
          logger.log(Level.INFO,"用户 "+uid+" 登陆成功");
          return str.split(",")[2];
        }else {
          reader.close();
          logger.log(Level.WARNING,"用户 "+uid+" 密码输入错误");
          return "Wrong password";
        }
      }
    }
    if(find==false) {
      reader.close();
      logger.log(Level.INFO,"输入了不存在的用户 "+uid);
      return "No such account";
    }
    reader.close();
    logger.log(Level.INFO,"登录时出现未知错误");
    return "Unknown error";
  }
}
