package 生命游戏;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.Scanner;

public class Map extends JFrame {
	private JPanel Panel;
	private JPanel Panel2;
	private JButton but[];       //开始、继续、暂停按钮
	private JPanel [][]block;
	private  boolean [][]survival;//标志每个细胞的存活状态，true为活细胞，false为死细胞
	private  Mythread mythread;//利用线程进行控制
	private  boolean [][]lastSurvival;
	private  int n=10;               //地图的大小
	private int speed;
	private String shape;
	private int count=0;          //记录更新的次数
	private Color color=Color.black;
	private Control con;
	public Map() {//建立地图,同时初始地图
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("生命游戏");
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
		String[]str= {"开始","继续","暂停","设置"};
		but=new JButton[4];
		for(int i=0;i<4;i++) {
			but[i]=new JButton(str[i]);
			Panel2.add(but[i]);
		}
		
		but[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InitMap();      //初始化细胞的状态
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
	public void InitMap() {   //初始化，地图中的细胞的存活状态为随机生成的
		survival=new boolean[n][n];
		lastSurvival=new boolean[n][n];
		shape=con.getshape();
		switch(shape) {
		case "心形":
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
		case "矩形":
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
		case "随机":
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++) {
					if(Math.random()<0.8)
						survival[i][j]=false;   //该细胞为死细胞
					else
						survival[i][j]=true;	//该细胞为活细胞
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
	class Mythread extends Thread{     //将Mythread作为Map中的一个内部类
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
                JOptionPane.showMessageDialog(null, "生命演化结束：\n"
                        + "        所用步数为"+count);
                stop();
            }
			}
		}
	}
}
