package exception;

import bank.Role;

public class PermissionDeniedException extends Exception{
  public PermissionDeniedException(Role r1,Role r2) {
    System.out.println("Ȩ�޲���");
    System.out.println("�����߽�ɫ��"+r1.toString());
    System.out.println("������Ҫ�Ľ�ɫ��"+r2.toString());
  }
}
