package ������Ϸ;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.Scanner;

public class Map extends JFrame {
	private JPanel Panel;
	private JPanel Panel2;
	private JButton but[];       //��ʼ����������ͣ��ť
	private JPanel [][]block;
	private  boolean [][]survival;//��־ÿ��ϸ���Ĵ��״̬��trueΪ��ϸ����falseΪ��ϸ��
	private  Mythread mythread;//�����߳̽��п���
	private  boolean [][]lastSurvival;
	private  int n=10;               //��ͼ�Ĵ�С
	private int speed;
	private String shape;
	private int count=0;          //��¼���µĴ���
	private Color color=Color.black;
	private Control con;
	public Map() {//������ͼ,ͬʱ��ʼ��ͼ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("������Ϸ");
		con=new Control(this);
	}
	public void makePanel() {
		getContentPane().setLayout(new BorderLayout());
		n=con.getsize();
		speed=con.getSpeed();
		color=con.getColor();
		
		Panel=new JPanel();
		if(mythread!=null) 
					mythread.stop();
		Panel.setLayout(new GridLayout(n, n, 2, 2));
		getContentPane().add("Center",Panel);
		Panel2=new JPanel();
		String[]str= {"��ʼ","����","��ͣ","����"};
		but=new JButton[4];
		for(int i=0;i<4;i++) {
			but[i]=new JButton(str[i]);
			Panel2.add(but[i]);
		}
		
		but[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InitMap();      //��ʼ��ϸ����״̬
				makePanel();
				mythread.start();
			}
			
		});
		but[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mythread.resume();
			}
			
		});
		but[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mythread.suspend();
			}
			
		});
		but[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mythread.suspend();
				con.setVisible(true);
			}
			
		});
		getContentPane().add("South",Panel2);
		InitMap();
		block=new JPanel[n][n];
		mythread=new Mythread();
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++) {
				block[i][j]=new JPanel();
				if(survival[i][j]==false)
					block[i][j].setBackground(Color.WHITE);
				else
					block[i][j].setBackground(color);
				Panel.add(block[i][j]);
			}
		setVisible(true);
		
	}
	public void InitMap() {   //��ʼ������ͼ�е�ϸ���Ĵ��״̬Ϊ������ɵ�
		survival=new boolean[n][n];
		lastSurvival=new boolean[n][n];
		shape=con.getshape();
		switch(shape) {
		case "����":
			int num1=n/2;
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++)
					survival[i][j]=false;
			survival[num1-3][num1-3]=true;
			survival[num1-3][num1+3]=true;
			survival[num1-2][num1-4]=true;
			survival[num1-2][num1-3]=true;
			survival[num1-2][num1-2]=true;
			survival[num1-2][num1+4]=true;
			survival[num1-2][num1+3]=true;
			survival[num1-2][num1+2]=true;
			survival[num1-1][num1-5]=true;
			survival[num1-1][num1-4]=true;
			survival[num1-1][num1-3]=true;
			survival[num1-1][num1-2]=true;
			survival[num1-1][num1-1]=true;
			survival[num1-1][num1+5]=true;
			survival[num1-1][num1+4]=true;
			survival[num1-1][num1+3]=true;
			survival[num1-1][num1+2]=true;
			survival[num1-1][num1+1]=true;
			for(int j=10;j>=0;j--)
				for(int i=0-j/2;i<=j/2;i++)
					survival[num1+5-j/2][num1+i]=true;
			
			break;
		case "����":
			int num2=n/2-2;
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++)
					survival[i][j]=false;
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++)
					survival[i][j]=false;
			for(int i=num2;i<num2+4;i++)
				for(int j=0;j<n;j++)
					survival[i][j]=true;
			break;
		case "���":
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++) {
					if(Math.random()<0.8)
						survival[i][j]=false;   //��ϸ��Ϊ��ϸ��
					else
						survival[i][j]=true;	//��ϸ��Ϊ��ϸ��
				}	
			break;
		}
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				lastSurvival[i][j]=survival[i][j];
	}
	public synchronized void updateMap(int row,int col) {
		if(survival[row][col])
			block[row][col].setBackground(color);
		else
			block[row][col].setBackground(Color.WHITE);
	}
	class Mythread extends Thread{     //��Mythread��ΪMap�е�һ���ڲ���
		boolean end=true;
		int num=0;
		public void run() {
			while(end) {
		    int life=0;
		    for(int i=0;i<n;i++)
		    	for(int j=0;j<n;j++) {
		    		survival[i][j]=Rule.judge(i, j,lastSurvival,n);
		    		updateMap(i,j);
		    	}
		    count++;
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++)
					lastSurvival[i][j]=survival[i][j];
			try {
				sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i< n;i++)
                for (int j = 0; j<n;j++)
                    if (survival[i][j]==true)
                        life ++;
            if (life==0) {
                end = false;
                JOptionPane.showMessageDialog(null, "�����ݻ�������\n"
                        + "        ���ò���Ϊ"+count);
                stop();
            }
			}
		}
	}
}
