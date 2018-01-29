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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	public static Logger log = LogManager.getLogger(Frame.class);

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
					frame.setAlwaysOnTop(isDefaultLookAndFeelDecorated());
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

		File dictionary = new File("dictionary.xlsx");
		log.info("数据字典的路径" + dictionary.getAbsolutePath());
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

					System.out.println(file1.getName());
					textPane1.setText(file1.getAbsolutePath());
					log.debug("需求列表的路径：" + file1.getAbsolutePath());

				}
			}

		});

		JButton button2 = new JButton("选择文件");
		button2.setBounds(480, 110, 90, 20);
		contentPane.add(button2);

		JLabel label_2 = new JLabel("修改单:");
		label_2.setBounds(10, 110, 60, 20);
		contentPane.add(label_2);

		JTextPane textPane2 = new JTextPane();
		textPane2.setBounds(80, 110, 390, 20);
		contentPane.add(textPane2);

		JButton startBut = new JButton("开始统计");
		startBut.setBounds(240, 340, 110, 30);
		contentPane.add(startBut);

		JLabel logLabel = new JLabel();
		logLabel.setBounds(20, 210, 550, 120);
		contentPane.add(logLabel);
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
					log.debug("修改单列表的路径：" + file2.getAbsolutePath());
				}
			}

		});

		// 补丁统计监听
		ActionListener bdTjLister = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				log.info("临时补丁遗漏统计开始");
				log.debug("获取列名为测试执行人的列");
				ArrayList<String> keyList = ExcelUtil.getColumByName(file2, "补丁编号");
				ArrayList<String> valueList = ExcelUtil.getColumByName(file2, "测试执行人");
				ArrayList<String> verList = ExcelUtil.getColumByName(file2, "修改的版本");
				// 根据缺陷编号对原始数据去重
				ArrayList<String> list = ExcelUtil.removal(keyList, valueList, verList, "需求").get(1);
				log.debug("调用统计数量的方法");
				label.setText("开始统计");
				ArrayList<TjBean> beanList = TjUtil.getCount(list, ExcelUtil.getColumnList(dictionary));
				long time = System.currentTimeMillis();
				log.info("开始打印Excel文件");
				label.setText("开始导出Excel");
				log.info("导出文件的名称:" + jcb.getSelectedItem() + time);
				ExcelUtil.printExcel(beanList, (String) jcb.getSelectedItem() + time, "", cloList,
						(String) jcb.getSelectedItem());
				label.setText("导出Excel结束");
				label.setText("统计结束");
				log.info("Excel打印结束");

			}
		};

		// 需求统计监听
		ActionListener xqTjLister = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<String> xqList = ExcelUtil.getColumByName(file1, "需求编号");
				ArrayList<String> keyList = ExcelUtil.getColumByName(file2, "需求编号");
				ArrayList<String> valueList = ExcelUtil.getColumByName(file2, "测试执行人");
				ArrayList<String> verList = ExcelUtil.getColumByName(file2, "修改的版本");
				// 对修改单根据需求编号去重
				ArrayList<String> xgList = ExcelUtil.removal(keyList, valueList, verList, "需求").get(0);
				ArrayList<String> targetList = ExcelUtil.removal(keyList, valueList, verList, "需求").get(1);
				log.debug("获取缺陷类需求的修改单的测试执行人列");
				ArrayList<String> list = TjUtil.getTemFile(xgList, xqList, targetList);
				log.debug("缺陷类需求的修改单数量：" + list.size());
				log.debug("调用统计数量的方法");
				label.setText("开始统计");
				ArrayList<TjBean> beanList = TjUtil.getCount(list, ExcelUtil.getColumnList(dictionary));
				long time = System.currentTimeMillis();

				log.info("开始导出统计结果的excel");
				label.setText("开始导出excel");
				log.info("导出文件的名称:" + jcb.getSelectedItem() + time);
				ExcelUtil.printExcel(beanList, (String) jcb.getSelectedItem() + time, "", cloList,
						(String) jcb.getSelectedItem());
				log.info("Excel打印结束");
				label.setText("导出Excel结束");
				label.setText("统计结束");
			}

		};

		startBut.addActionListener(xqTjLister);

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
					log.info("统计模式为:" + jcb.getSelectedItem());
					log.debug("删除开始统计按钮统计缺陷类需求的监听事件");
					startBut.removeActionListener(xqTjLister);
					log.debug(startBut.getActionListeners().length);
					if (startBut.getActionListeners().length == 0) {
						startBut.addActionListener(bdTjLister);
					}
					break;

				case "缺陷类需求单统计":
					button1.setVisible(true);
					label_1.setVisible(true);
					textPane1.setVisible(true);
					log.info("统计模式为:" + jcb.getSelectedItem());
					log.debug("删除开始统计按钮统计补丁数量的监听事件");
					startBut.removeActionListener(bdTjLister);
					log.debug(startBut.getActionListeners().length);
					if (startBut.getActionListeners().length == 0) {
						startBut.addActionListener(xqTjLister);
					}
					break;
				}

			}
		});

	}
}