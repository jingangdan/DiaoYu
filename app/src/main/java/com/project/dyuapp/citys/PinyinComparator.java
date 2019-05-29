package com.project.dyuapp.citys;

import com.project.dyuapp.entity.LivingPlaceBean;

import java.util.Comparator;

/**
 *
 * @author xiaanming
 *
 */
//public class PinyinComparator implements Comparator<LivingPlaceBean> {
//
//	public int compare(LivingPlaceBean o1, LivingPlaceBean o2) {
//		if (o1.getC().get(0).getSortLetters().equals("@")
//				|| o2.getC().get(0).getSortLetters().equals("#")) {
//			return -1;
//		} else if (o1.getC().get(0).getSortLetters().equals("#")
//				|| o2.getC().get(0).getSortLetters().equals("@")) {
//			return 1;
//		} else {
//			return o1.getC().get(0).getSortLetters().compareTo(o2.getC().get(0).getSortLetters());
//		}
//	}
//
//}
public class PinyinComparator implements Comparator<LivingPlaceBean.CBean> {

	public int compare(LivingPlaceBean.CBean o1, LivingPlaceBean.CBean o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
