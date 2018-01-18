package com.hundsun.zgcs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox jcb;
	/*
	 * 数据字典存放的excel
	 */
	private File dictionary;
	// 需求Excel文件
	private File file1;
	// 修改单Excel文件
	private File file2;
	private ArrayList<String> cloList = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					// 设置居中
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 创建一个Frame
	 */
	public Frame() {

		File dictionary = new File("./dictionary.xlsx");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		setTitle("Excel统计工具");
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		JLabel label = new JLabel("统计模式:");
		label.setBounds(10, 50, 60, 20);
		contentPane.add(label);

		// 下拉框
		jcb = new JComboBox();
		jcb.addItem("缺陷类需求单统计");
		jcb.addItem("临时补丁遗漏统计");

		jcb.setBounds(80, 50, 490, 20);
		contentPane.add(jcb);
		jcb.setVisible(true);

		JButton button1 = new JButton("选择文件");
		button1.setBounds(480, 170, 90, 20);
		contentPane.add(button1);

		JLabel label_1 = new JLabel("需求Excel:");
		label_1.setBounds(10, 170, 60, 20);
		contentPane.add(label_1);

		JTextPane textPane1 = new JTextPane();
		textPane1.setBounds(80, 170, 390, 20);
		contentPane.add(textPane1);
		// 添加按钮点击事件
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChoose = new JFileChooser();
				// 过滤展示Excel格式的文件
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel文件", "xls", "xlsx", "csv");
				fileChoose.setFileFilter(filter);
				int returnVal = fileChoose.showOpenDialog(Frame.this);
				if (returnVal == fileChoose.APPROVE_OPTION) {
					file1 = fileChoose.getSelectedFile();

					System.out.println(file1.getName());
					textPane1.setText(file1.getAbsolutePath());

				}
			}

		});

		JButton button2 = new JButton("选择文件");
		button2.setBounds(480, 110, 90, 20);
		contentPane.add(button2);

		JLabel label_2 = new JLabel("修改Excel:");
		label_2.setBounds(10, 110, 60, 20);
		contentPane.add(label_2);

		JTextPane textPane2 = new JTextPane();
		textPane2.setBounds(80, 110, 390, 20);
		contentPane.add(textPane2);

		JButton startBut = new JButton("开始统计");
		startBut.setBounds(240, 320, 110, 30);
		contentPane.add(startBut);
		// 需要显示的列
		cloList.add("姓名");
		cloList.add("数量");

		// 添加按钮点击事件
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChoose = new JFileChooser();
				// 过滤展示Excel格式的文件
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel文件", "xls", "xlsx", "csv");
				fileChoose.setFileFilter(filter);
				int returnVal = fileChoose.showOpenDialog(Frame.this);
				if (returnVal == fileChoose.APPROVE_OPTION) {
					file2 = fileChoose.getSelectedFile();

					System.out.println(file2.getName());
					textPane2.setText(file2.getAbsolutePath());

				}
			}

		});

		// 下拉框添加事件
		jcb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 获取选择的统计模式
				String str = (String) jcb.getSelectedItem();
				switch (str) {
				case "临时补丁遗漏统计":
					button1.setVisible(false);
					label_1.setVisible(false);
					textPane1.setVisible(false);
					startBut.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							ArrayList<String> list = ExcelUtil.getColumByName(file2, "测试执行人");
							System.out.println("开始统计");
							ArrayList<TjBean> beanList = TjUtil.getCount(list, ExcelUtil.getColumnList(dictionary));
							long time = System.currentTimeMillis();
							System.out.println("开始打印Excel");

							ExcelUtil.printExcel(beanList, str + time, "", cloList, str);
							File test = new File(str + time + ".xlsx");
							System.out.println(test);
							System.out.println("Excel结束打印");
						}
					});

					System.out.println("临时补丁遗漏统计");

					break;

				case "缺陷类需求单统计":
					button1.setVisible(true);
					label_1.setVisible(true);
					textPane1.setVisible(true);
					System.out.println("缺陷类需求单统计");
					startBut.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							ArrayList<String> list1 = ExcelUtil.getColumByName(file1, "需求编号");
							ArrayList<String> list2 = ExcelUtil.getColumByName(file2, "需求编号");
							ArrayList<String> targetList = ExcelUtil.getColumByName(file2, "测试执行人");
							ArrayList<String> list = TjUtil.getTemFile(list1, list2, targetList);
							System.out.println("开始统计");
							ArrayList<TjBean> beanList = TjUtil.getCount(list, ExcelUtil.getColumnList(dictionary));
							long time = System.currentTimeMillis();
							System.out.println("开始打印Excel");

							ExcelUtil.printExcel(beanList, str + time, "", cloList, str);
							File test = new File(str + time + ".xlsx");
							System.out.println(test);
							System.out.println("Excel结束打印");
						}
					});

					break;
				}

			}
		});

	}
}
