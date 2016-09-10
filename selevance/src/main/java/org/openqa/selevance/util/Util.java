package org.openqa.selevance.util;
/**
 * @author Tanmay Sarkar
 */
public class Util {
	public static void sleep(int milisec){
		try {
			Thread.sleep(milisec);
		} catch (InterruptedException e) {
		}
	}
	public static String executionTime(long finish,long start){
		long sec = (finish - start)/1000;
		long min =0;
		if(sec>60){
			min = sec/60;
			sec = sec %60;
		}return "Total Execution Time : " + min + " Min "+ sec+"Sec";
	}
}
