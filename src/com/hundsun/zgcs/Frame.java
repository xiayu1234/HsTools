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
	private JPanel contentPane;
	private JComboBox jcb;
	/*
	 * 数据字典存放的excel
	 */
	private File dictionary;
	private JFileChooser fileChoose;
	private File[] fileList;
	private File tagFile;
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

		String str2 = "D:\\\\test\\\\testDic.xlsx";
		String loc2 = str2.replace("\\\\", "/");
		File dictionary = new File(loc2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		setTitle("Excel统计工具");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		JButton button = new JButton("选择文件");
		button.setBounds(480, 50, 90, 20);
		contentPane.add(button);

		JLabel label_1 = new JLabel("目标文件:");
		label_1.setBounds(10, 50, 60, 20);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("统计模式:");
		label_2.setBounds(10, 170, 60, 20);
		contentPane.add(label_2);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(80, 50, 390, 20);
		contentPane.add(textPane);

		// 添加按钮点击事件
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChoose = new JFileChooser();
				fileChoose.setMultiSelectionEnabled(true);
				// 过滤展示Excel格式的文件
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel文件", "xls", "xlsx", "csv");
				fileChoose.setFileFilter(filter);
				int returnVal = fileChoose.showOpenDialog(Frame.this);
				if (returnVal == fileChoose.APPROVE_OPTION) {
					File[] fileList = fileChoose.getSelectedFiles();

					for (File file : fileList) {
						System.out.println(file.getName());
						textPane.setText(file.getAbsolutePath());
					}
				}
			}

		});

		// 下拉框
		jcb = new JComboBox();
		jcb.addItem("临时补丁遗漏统计");
		jcb.addItem("缺陷类需求单统计");
		jcb.setBounds(80, 170, 390, 20);
		contentPane.add(jcb);
		jcb.setVisible(true);

		JLabel stateLabel = new JLabel("");
		stateLabel.setBounds(480, 170, 90, 20);
		contentPane.add(stateLabel);

		JButton startBut = new JButton("开始统计");
		startBut.setBounds(240, 320, 120, 30);
		contentPane.add(startBut);

		// 点击统计按钮的监听事件
		startBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String str = (String) jcb.getSelectedItem();
				cloList.add("姓名");
				cloList.add("数量");
				// 根据不通的统计模式调用不用的方法
				switch (str) {
				case "临时补丁遗漏统计":
					// for (int i = 0; i < fileList.length; i++) {
					ArrayList<String> list = ExcelUtil.getColumByName(tagFile, "测试执行人");
					System.out.println("开始统计");
					ArrayList<TjBean> beanList = TjUtil.getCount(list, ExcelUtil.getColumnList(dictionary));
					long time = System.currentTimeMillis();
					System.out.println("打印开始");
					ExcelUtil.printExcel(beanList, "", loc2.toLowerCase(), cloList, str);
					// }
					System.out.println("打印结束" + loc2.toLowerCase());
					break;

				case "缺陷类需求单统计":

					break;
				}

			}
		});

	}
}
