package exception;

import bank.Role;

public class PermissionDeniedException extends Exception{
  public PermissionDeniedException(Role r1,Role r2) {
    System.out.println("权限不够");
    System.out.println("操作者角色："+r1.toString());
    System.out.println("至少需要的角色："+r2.toString());
  }
}
