package 生命游戏;

import java.awt.Choice;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Control extends JFrame {
    private JPanel contentPane;
	private Choice speedBox;
	private Choice colorBox;
	private Choice shapeBox;
	private JTextField sizeText;
	public Control(Map map) {
		setTitle("\u63A7\u5236\u754C\u9762");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 285, 233);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel speedLabel = new JLabel("\u6F14\u5316\u901F\u5EA6:");
		speedLabel.setBounds(10, 27, 72, 21);
		contentPane.add(speedLabel);
		
		JLabel colorLabel = new JLabel("\u6D3B\u7EC6\u80DE\u989C\u8272\uFF1A");
		colorLabel.setBounds(10, 59, 73, 15);
		contentPane.add(colorLabel);
		
		JLabel sizeLabel = new JLabel("\u5730\u56FE\u5927\u5C0F\uFF1A");
		sizeLabel.setBounds(10, 92, 72, 15);
		contentPane.add(sizeLabel);
		
		JLabel shapeLabel = new JLabel("\u5F62\u72B6\uFF1A");
		shapeLabel.setBounds(10, 125, 54, 15);
		contentPane.add(shapeLabel);
		
		JButton okbut = new JButton("\u786E\u5B9A");
		okbut.setBounds(38, 150, 93, 23);
		contentPane.add(okbut);
		
		JButton cancelbut = new JButton("\u53D6\u6D88");
		cancelbut.setBounds(149, 150, 93, 23);
		contentPane.add(cancelbut);
		okbut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getsize()<12||getsize()>50)
					JOptionPane.showMessageDialog(null,"地图大小设置不合理"+"\n"+"地图大小在12~50之间");
				else{
					map.makePanel();
					setVisible(false);
				}
			}});
		cancelbut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}});
		colorBox=new Choice();
		colorBox.setSize(113, 21);
		colorBox.setLocation(109, 59);
	    String[]str1= {"red","blue","black"};
        for(int i=0;i<3;i++) {
        	colorBox.addItem(str1[i]);
        }
        contentPane.add(colorBox);
        
        speedBox=new Choice();//角色选择列表
        speedBox.setLocation(109, 27);
        speedBox.setSize(113, 21);
		int Speed[]= {1000,2000,3000};
        for(int i=0;i<3;i++) {
        	speedBox.addItem(Speed[i]+"");
        }
        contentPane.add(speedBox);
        
        shapeBox=new Choice();//角色选择列表
        shapeBox.setLocation(109, 113);
		shapeBox.setSize(113, 21);
	    String[]str3= {"心形","矩形","随机"};
        for(int i=0;i<3;i++) {
        	shapeBox.addItem(str3[i]);
        }
        contentPane.add(shapeBox);
        sizeText = new JTextField();
        sizeText.setBounds(109, 86, 113, 21);
        contentPane.add(sizeText);
        sizeText.setColumns(10);
        setVisible(true);
	}
	public Color getColor() {
		String str=colorBox.getSelectedItem();
		Color color;
		switch(str) {
		case "red":
			color=Color.red;
			break;
		case "blue":
			color=Color.blue;
			break;
		case "black":
			color=Color.black;
			break;
		default:
			color=null;
		}
		return color;
	}
	public int getSpeed() {
		String str=speedBox.getSelectedItem();
		int speed;
		speed=Integer.parseInt(str);
		return speed;
	}
	public int getsize() {
		String str=sizeText.getText();
		int Tsize=Integer.parseInt(str);
		return Tsize;
	}
	public String getshape() {
		return shapeBox.getSelectedItem();
	}
}
