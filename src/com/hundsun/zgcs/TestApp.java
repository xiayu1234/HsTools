package com.hundsun.zgcs;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestApp {

	public TestApp() {
		// TODO Auto-generated constructor stub
	}

	public static Logger log = LogManager.getLogger(TestApp.class);

	public static void main(String[] args) {

		JFrame jf = new JFrame();
		
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("Statistics.ico"); 
		System.out.println(image);
		jf.setIconImage(image);
		jf.setVisible(true);
	}

}
