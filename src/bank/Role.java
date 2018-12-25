package bank;

public enum Role {
  NULL, Client, Clark, Manager, Administrator;

  public String toString() {
    if (this.equals(Client)) {
      return "Client";
    } else if (this.equals(Clark)) {
      return "Clark";
    } else if (this.equals(Manager)) {
      return "Manager";
    } else if (this.equals(Administrator)) {
      return "Administrator";
    } else {
      return "NULL";
    }
  }
}

