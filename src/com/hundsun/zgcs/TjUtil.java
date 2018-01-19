package com.hundsun.zgcs;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.media.sound.SoftMixingSourceDataLine;

/**
 * 工具类
 * 
 * @author xiayu20540
 *
 */
public class TjUtil {

	public TjUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 统计数量
	 * 
	 * @param column
	 *            需要统计的列
	 * @param list
	 *            数据字典列表
	 * @return 统计结果的键值对
	 */
	public static ArrayList<TjBean> getCount(ArrayList<String> column, ArrayList<ArrayList<String>> list) {

		HashMap<Object, Integer> map = new HashMap<>();
		ArrayList<TjBean> resList = new ArrayList<>();
		Boolean flag = false;
		for (int i = 0; i < column.size(); i++) {
			for (int j = 0; j < list.size(); j++) {

				// 人员数据统计
				for (int p = 0; p < list.get(j).size(); p++) {

					// 判断人员是否在测试执行人里
					if (column.get(i).indexOf(list.get(j).get(p)) != -1) {
						// 判断键值是否存在
						if (map.containsKey(list.get(j).get(p))) {
							map.put(list.get(j).get(p), map.get(list.get(j).get(p)) + 1);
						} else {
							map.put(list.get(j).get(p), 1);
						}
						flag = true;
					}
				}

				if (flag) {
					// 小组数量统计
					if (map.containsKey(list.get(j).get(0))) {
						map.put(list.get(j).get(0), map.get(list.get(j).get(0)) + 1);
					} else {
						map.put(list.get(j).get(0), 1);
					}
					flag = false;
				}

			}

		}

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				if (map.get(list.get(i).get(j)) != null) {
					TjBean tjBean = new TjBean();
					tjBean.setNumber(map.get(list.get(i).get(j)));
					tjBean.setName(list.get(i).get(j));
					resList.add(tjBean);
				}
			}
		}

		return resList;
	}

	/**
	 * 获取包含在列表一中的列表二的目标值的列
	 * 
	 * @param list1
	 *            列表一(大)
	 * @param list2
	 *            列表二(小)
	 * @param targetList
	 * @return 结果列
	 */
	public static ArrayList<String> getTemFile(ArrayList<String> list1, ArrayList<String> list2,
			ArrayList<String> targetList) {

		ArrayList<String> list = new ArrayList<>();

		for (int i = 1; i < list1.size(); i++) {
			// list2包含元素list1.get(i)
			for (int j = 0; j < list2.size(); j++) {
				if (list1.get(i).indexOf(list2.get(j)) != -1) {
					list.add(targetList.get(i));
				}
			}

		}

		return list;
	}

}
