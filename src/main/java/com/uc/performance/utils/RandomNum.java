package com.uc.performance.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class RandomNum {
	
	public static double getDoubleRandomNum(double min,double max){
		Random random=new Random();
		DecimalFormat df = new DecimalFormat("#.00"); 
		double randomnum;
		randomnum = min + (double)(random.nextDouble()*(max-min)+1);
		randomnum = Double.valueOf(df.format(randomnum));
		return randomnum;
	}
	
	public static int getIntRandomNum(int min,int max){
		Random random=new Random();
		int randomnum;
		return randomnum = min + (int)(random.nextDouble()*(max-min)+1);
	}
}
