package ÉúÃüÓÎÏ·;

import java.awt.EventQueue;
import java.util.Scanner;
public class Main {
	public static void main(String[]agrs) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Map frame = new Map();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}
}
