package ������Ϸ;
import javax.swing.*;
public class Rule {
	static int []Row= {-1,-1,-1,0,0,1,1,1};
	static int []Col= {-1,0,1,-1,1,-1,0,1};
	  public static boolean judge(int row,int col,boolean [][]survival,int n) {//�жϹ���
		//�ж�λ��Ϊblock[row][col]��һ��״̬��nΪ��ͼ�Ĵ�С
		int row1,col1;//��Χϸ����λ�ô洢
		int count=0;//�洢��Χϸ���Ĵ�����
		boolean state;
		for(int i=0;i<8;i++) {
			row1=row+Row[i];
			col1=col+Col[i];
			if(row1>=0&&row1<n&&col1>=0&&col1<n) {  //�ж�ϸ����û��Խ��
				if(survival[row1][col1]) count++;
			}
		}
		if(count==2)
			state=survival[row][col]; //����Ϊ����Ŀ��ϸ�����ֲ���
		else if(count==3)
			state=true;
		else
			state=false;
		return state;
	}
}
