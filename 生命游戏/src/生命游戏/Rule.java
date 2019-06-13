package 生命游戏;
import javax.swing.*;
public class Rule {
	static int []Row= {-1,-1,-1,0,0,1,1,1};
	static int []Col= {-1,0,1,-1,1,-1,0,1};
	  public static boolean judge(int row,int col,boolean [][]survival,int n) {//判断规则
		//判断位置为block[row][col]下一个状态，n为地图的大小
		int row1,col1;//周围细胞的位置存储
		int count=0;//存储周围细胞的存活个数
		boolean state;
		for(int i=0;i<8;i++) {
			row1=row+Row[i];
			col1=col+Col[i];
			if(row1>=0&&row1<n&&col1>=0&&col1<n) {  //判读细胞有没有越界
				if(survival[row1][col1]) count++;
			}
		}
		if(count==2)
			state=survival[row][col]; //两个为生，目标细胞保持不变
		else if(count==3)
			state=true;
		else
			state=false;
		return state;
	}
}
