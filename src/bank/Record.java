package bank;

public class Record {
  private String card_number;
  private String name;
  private String ID;
  private Role role;
  private String phone_number;
  private double balance = -1;
  private String other_info;

  public Record(String number, String name, String ID, Role role) {
    this.card_number = number;
    this.name = name;
    this.ID = ID;
    this.setRole(role);
  }

  public String getCard_number() {
    return card_number;
  }

  public void setCard_number(String card_number) {
    this.card_number = card_number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double d) {
    this.balance = d;
  }

  public String getOther_info() {
    return other_info;
  }

  public void setOther_info(String other_info) {
    this.other_info = other_info;
  }

  public String getID() {
    return ID;
  }

  public void setID(String iD) {
    ID = iD;
  }

  public String getPhone_number() {
    return phone_number;
  }

  public void setPhone_number(String phone_number) {
    this.phone_number = phone_number;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  /**
   * 写文件时用
   */
  public String toString() {
    String result = card_number + "," + name + "," + ID + "," + role.toString() + "," + phone_number
        + "," + balance + "," + other_info;
    return result;
  }
}
