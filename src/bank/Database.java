package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import exception.PermissionDeniedException;

public class Database {
  private static Map<String, Record> records = new HashMap<>(); // card_number,Record
  private static Map<String, Role> ID2role = new HashMap<>(); // ID_number,Role
  private static Map<String, String> ID2name = new HashMap<>(); // ID_number,name

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
  }

  public static void saveFile(String filesrc) throws IOException {
    FileWriter fw = new FileWriter(new File(filesrc));
    BufferedWriter writer = new BufferedWriter(fw);

    for (String record : records.keySet()) {
      writer.write(records.get(record).toString() + "\r\n");
    }

    writer.close();
  }

  public static Record search(String ID, String card_number) throws PermissionDeniedException {
    Role role = getRolebyID(Role.Administrator, ID);
    Record result = records.get(card_number);
    if (role.compareTo(Role.Clark) >= 0) {
      return result;
    } else {
      if (result != null) {
        if (result.getID().equals(ID)) {
          return result;
        } else {
          throw new PermissionDeniedException(role, Role.Administrator);
        }
      } else {
        return result;
      }
    }
  }

  /***
   * ATTENTION! Even if this returns true it doesnt mean something must have been changed.
   * 
   * @param ID
   * @param card_number
   * @param new_record
   * @return
   * @throws PermissionDeniedException
   */
  public static boolean modify(String ID, String card_number, Record new_record)
      throws PermissionDeniedException {
    Role role = getRolebyID(Role.Administrator, ID);
    boolean success = false;
    Record target = search(ID, card_number);
    if (target != null) {
      if (role.compareTo(Role.Clark) >= 0 && role.compareTo(Role.Administrator) < 0) {
        System.out.println("根据角色属性仅允许修改手机号码和其他信息\n");
        if (new_record.getPhone_number() != null) {
          target.setPhone_number(new_record.getPhone_number());
        }
        if (new_record.getOther_info() != null) {
          target.setOther_info(new_record.getOther_info());
        }
        success = true;
      } else if (role.compareTo(Role.Administrator) >= 0) {
        System.out.println("根据角色信息可以修改所有信息\n");
        records.replace(card_number, new_record);
        success = true;
      }
    }
    return success;
  }
  
  public static boolean delete(String ID, String card_number) throws PermissionDeniedException {
    Role role = getRolebyID(Role.Administrator, ID);
    boolean success=false;
    if(role.compareTo(Role.Administrator)>=0) {
      records.remove(card_number);
      success=true;
    }else {
      throw new PermissionDeniedException(role, Role.Administrator);
    }
    return success;
  }
  
  public static boolean add(String ID,Record record) throws PermissionDeniedException{
    boolean success=false;
    Role role=getRolebyID(Role.Administrator, ID);
    if(role.compareTo(Role.Administrator)==0) {
      if(!records.containsKey(record.getCard_number())) {
        records.put(record.getCard_number(), record);
        success=true;
      }
    }else {
      throw new PermissionDeniedException(role, Role.Administrator);
    }
    return success;
  }
  
  public static String login(String uid,String passwd) throws IOException {
    Role role=Role.NULL;
    FileInputStream inputstream = new FileInputStream(new File("D:\\database_users.txt"));
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));

    String str = "";
    boolean find=false;
    while ((str = reader.readLine()) != null) {
      if(str.split(",")[0].equals(uid)) {
        find=true;
        if(str.split(",")[1].equals(passwd)) {
          return str.split(",")[2];
        }else {
          return "Wrong password";
        }
      }
    }
    if(find==false) {
      return "No such account";
    }
    return "Unknown error";
  }
}
