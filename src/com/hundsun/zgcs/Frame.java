package com.hundsun.zgcs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.javafx.collections.MappingChange.Map;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
	/**
	 * 需求Excel
	 */
	private File file1;
	/**
	 * 修改单Excel文件
	 */
	private File file2;
	/**
	 * 补丁单Excel文件
	 */
	private File file3;

	JLabel logLabel;
	private ArrayList<String> cloList = new ArrayList<>();

	public static Logger log = LogManager.getLogger(Frame.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
					UIManager.setLookAndFeel(windows);
					Frame frame = new Frame();

					frame.setLocationRelativeTo(null);

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

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage("Statistics.ico");
		System.out.println(image);
		this.setIconImage(image);

		this.setAlwaysOnTop(isDefaultLookAndFeelDecorated());
		this.setResizable(false);
		this.setVisible(true);

		File dictionary = new File("dictionary.xlsx");
		log.debug("数据字典的路径" + dictionary.getAbsolutePath());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		setTitle("Statistics.exe");
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		// 下拉框
		/*
		 * jcb = new JComboBox(); jcb.addItem("缺陷类需求单统计"); jcb.addItem("临时补丁遗漏统计");
		 * jcb.setBounds(80, 50, 490, 20); contentPane.add(jcb); jcb.setVisible(true);
		 */

		JButton button1 = new JButton("选择文件");
		button1.setBounds(480, 170, 90, 20);
		contentPane.add(button1);

		JLabel label_1 = new JLabel("需求单:");
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
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel文件", "xls", "xlsx");
				fileChoose.setFileFilter(filter);
				int returnVal = fileChoose.showOpenDialog(Frame.this);
				if (returnVal == fileChoose.APPROVE_OPTION) {
					file1 = fileChoose.getSelectedFile();

					// System.out.println(file1.getName());
					textPane1.setText(file1.getAbsolutePath());
					log.debug("需求列表的路径：" + file1.getAbsolutePath());

				}
			}

		});

		JLabel label_2 = new JLabel("修改单:");
		label_2.setBounds(10, 110, 60, 20);
		contentPane.add(label_2);

		JButton button2 = new JButton("选择文件");
		button2.setBounds(480, 110, 90, 20);
		contentPane.add(button2);

		JTextPane textPane2 = new JTextPane();
		textPane2.setBounds(80, 110, 390, 20);
		contentPane.add(textPane2);

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

					// System.out.println(file2.getName());
					textPane2.setText(file2.getAbsolutePath());
					log.debug("修改单列表的路径：" + file2.getAbsolutePath());
				}
			}

		});

		JButton startBut = new JButton("开始统计");
		startBut.setBounds(240, 340, 110, 30);
		contentPane.add(startBut);

		logLabel = new JLabel("", JLabel.CENTER);

		logLabel.setFont(new java.awt.Font("Dialog", 1, 15));
		logLabel.setForeground(Color.red);

		logLabel.setBounds(20, 210, 550, 120);
		contentPane.add(logLabel);

		JLabel label_3 = new JLabel("补丁单:");
		label_3.setBounds(10, 50, 60, 20);
		contentPane.add(label_3);

		JTextPane textPane3 = new JTextPane();
		textPane3.setBounds(80, 50, 390, 20);
		contentPane.add(textPane3);

		JButton button3 = new JButton("选择文件");
		button3.setBounds(480, 50, 90, 20);
		contentPane.add(button3);
		button3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChoose = new JFileChooser();
				// 过滤展示Excel格式的文件
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel文件", "xls", "xlsx");
				fileChoose.setFileFilter(filter);
				int returnVal = fileChoose.showOpenDialog(Frame.this);
				if (returnVal == fileChoose.APPROVE_OPTION) {
					file3 = fileChoose.getSelectedFile();

					// System.out.println(file1.getName());
					textPane3.setText(file3.getAbsolutePath());
					log.debug("需求列表的路径：" + file3.getAbsolutePath());

				}
			}
		});
		// 需要显示的列
		cloList.add(0, "姓名");
		cloList.add(1, "缺陷补丁");
		cloList.add(2, "补丁总数");
		cloList.add(3, "缺陷需求");
		cloList.add(4, "需求总数");
		cloList.add(5, "百分比");

		startBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				logLabel.setText("");

				ArrayList<String> verList = ExcelUtil.getColumByName(file2, "修改的版本");// 从修改单excel里取出来的版本信息
				ArrayList<String> zxrList = ExcelUtil.getColumByName(file2, "测试执行人");// 从修改单excel里取出来的测试执行人
				ArrayList<String> list = ExcelUtil.getColumByName(file2, "需求编号");// 从修改单excel里取出来的需求编号

				ArrayList<ArrayList<String>> dicList = ExcelUtil.getColumnList(dictionary);

				ArrayList<ArrayList<String>> xqLists = ExcelUtil.dataSeparate(list, zxrList, verList);// 将多个需求分离修改单列表
			
				ArrayList<ArrayList<String>> qcxqLists = ExcelUtil.removal(xqLists.get(0), xqLists.get(1),
						xqLists.get(2), "需求");// 根据版本去重后的修改单集合
				
				for (int i = 0; i < qcxqLists.get(0).size(); i++) {
					
					System.out.println("需求编号" +  qcxqLists.get(0).get(i) + "的测试执行人：" +qcxqLists.get(1).get(i) );
					
				}
				
				ArrayList<String> xqList = qcxqLists.get(1);// 去重后的修改单测试执行人
				System.out.println("xqList" + xqList.size());
				// 缺陷类需求
				ArrayList<String> qxxqList = TjUtil.getTemFile(qcxqLists.get(0),
						ExcelUtil.getColumByName(file1, "需求编号"), qcxqLists.get(1));
				System.out.println("缺陷需求的个数" + qxxqList.size());

				// 所有补丁
				ArrayList<String> bdList = ExcelUtil.getColumByName(file3, "测试执行人");
				System.out.println("bdList" + bdList.size());

				// 缺陷补丁
				ArrayList<String> qxbdList = ExcelUtil.filterBd(file3);

				System.out.println("qxbdList:" + qxbdList.size());

				HashMap<String, Integer> qxbdMap = TjUtil.getCount(qxbdList, dicList);
				HashMap<String, Integer> bdMap = TjUtil.getCount(bdList, dicList);
				HashMap<String, Integer> qxxqMap = TjUtil.getCount(qxxqList, dicList);
				HashMap<String, Integer> xqMap = TjUtil.getCount(xqList, dicList);

				System.out.println("qxbdMap" + qxbdMap.size());
				System.out.println("bdMap" + bdMap.size());
				System.out.println("qxxqMap" + qxxqMap.size());
				System.out.println("xqMap" + xqMap.size());

				ArrayList<TjBean> beanList = new ArrayList<>();
				System.out.println(dicList.size());
				for (int k = 0; k < dicList.size(); k++) {
					for (int l = 0; l < dicList.get(k).size(); l++) {
						System.out.println(dicList.get(k).get(l));
						if (dicList.get(k).get(l).length() >= 2) {
							TjBean tjBean = new TjBean();

							tjBean.setName(dicList.get(k).get(l));
							int bdNum = 0;
							int bdSum = 0;
							int xqNum = 0;
							int xqSum = 0;

							if (qxbdMap.get(dicList.get(k).get(l)) != null) {
								bdNum = qxbdMap.get(dicList.get(k).get(l));
							}

							if (bdMap.get(dicList.get(k).get(l)) != null) {
								bdSum = bdMap.get(dicList.get(k).get(l));
							}

							if (qxxqMap.get(dicList.get(k).get(l)) != null) {
								xqNum = qxxqMap.get(dicList.get(k).get(l));
							}
							if (xqMap.get(dicList.get(k).get(l)) != null) {
								xqSum = xqMap.get(dicList.get(k).get(l));
							}

							tjBean.setBdNum(bdNum);
							tjBean.setBdSum(bdSum);
							tjBean.setXqNum(xqNum);
							tjBean.setXqSum(xqSum);
							if ((bdSum + xqSum) == 0 || (bdNum + xqNum) == 0) {
								tjBean.setPercent("0%");
							} else {
								double result = ((double) (bdNum + xqNum) / (bdSum + xqSum));
								System.out.println(result);
								DecimalFormat df = new DecimalFormat("0.00%");
								String percent = df.format(result);
								tjBean.setPercent(percent);
							}

							beanList.add(tjBean);
						}

					}

				}
				
				TjBean tjBean = new TjBean();
				tjBean.setName("总计");
				tjBean.setBdNum(qxbdList.size());
				tjBean.setBdSum(bdList.size() - 1);//去除表头
				tjBean.setXqNum(qxxqList.size());
				tjBean.setXqSum(xqList.size());
				if ((qxbdList.size() + (bdList.size() - 1)) == 0 || (qxxqList.size() + xqList.size()) == 0) {
					tjBean.setPercent("0%");
				} else {
					double result = ((double)(qxbdList.size() + qxxqList.size()) / ((bdList.size() - 1 + xqList.size())));
					System.out.println(result);
					DecimalFormat df = new DecimalFormat("0.00%");
					String percent = df.format(result);
					tjBean.setPercent(percent);
					beanList.add(tjBean);
				}
				
				long time = System.currentTimeMillis();
				
				

				ExcelUtil.printExcel(beanList, "统计结果", time + "", cloList);
				logLabel.setText("统计结束,请查看结果");
			}
		});

	}
}