package bank;

import java.util.List;
import javax.swing.table.AbstractTableModel;

class TableModel extends AbstractTableModel {
  /*
   * ����͸ղ�һ��������������ÿ�����ݵ�ֵ
   */
  String[] columnNames = {"Number","Name","ID","Role","Phone","Balance","Other"};
  Object[][] data= {{" "," "," "," "," "," "," "},{" "," "," "," "," "," "," "}};
  
  /**
   * �չ��췽��
   */
  public TableModel() {
    
  }

  /**
   * ���췽������ʼ����ά����data��Ӧ�����ݣ�ʹ��List��Ϊ������Ϊ���Ժ���Ӱ�����Ϣ��ѯ�ȹ��ܷ���һ������ʾ��������
   */
  public TableModel(List<Record> records) {
    int i;
    int len=records.size();
    data=new Object[len][7];
    for(i=0;i<len;i++) {
      Record tmp=records.get(i);
      data[i][0]=tmp.getCard_number().toString();
      data[i][1]=tmp.getName().toString();
      data[i][2]=tmp.getID().toString();
      data[i][3]=tmp.getRole().toString();
      if(tmp.getPhone_number()!=null) {
        data[i][4]=tmp.getPhone_number().toString();
      }else {
        data[i][4]="";
      }
      if(tmp.getBalance()!=-1) {
        data[i][5]=new Double(tmp.getBalance()).toString();
      }else {
        data[i][5]="";
      }
      if(tmp.getOther_info()!=null) {
        data[i][6]=tmp.getOther_info().toString();
      }else {
        data[i][6]="";
      }
    }
  }

  // ����Ϊ�̳���AbstractTableModle�ķ����������Զ���
  /**
   * �õ�����
   */
  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  /**
   * ��д�������õ��������
   */
  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  /**
   * �õ��������
   */
  @Override
  public int getRowCount() {
    return data.length;
  }

  /**
   * �õ���������Ӧ����
   */
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return data[rowIndex][columnIndex];
  }

  /**
   * �õ�ָ���е���������
   */
  @Override
  public Class<?> getColumnClass(int columnIndex) {
    return data[0][columnIndex].getClass();
  }

  /**
   * ָ���������ݵ�Ԫ�Ƿ�ɱ༭.��������"����","ѧ��"���ɱ༭
   */
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
//    if (columnIndex < 2)
//      return false;
//    else
//      return true;
    return false;
  }

  /**
   * ������ݵ�ԪΪ�ɱ༭���򽫱༭���ֵ�滻ԭ����ֵ
   */
  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    data[rowIndex][columnIndex] = aValue;
    /* ֪ͨ���������ݵ�Ԫ�����Ѿ��ı� */
    fireTableCellUpdated(rowIndex, columnIndex);
  }

}
