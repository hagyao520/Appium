package com.jmoney.jiumiaodai.handler;

import java.io.IOException;

import com.jmoney.jiumiaodai.base.TestStep;
import com.jmoney.jiumiaodai.util.ConfigUtil;
import com.jmoney.jiumiaodai.util.Constants;

/**
 * <br>录屏操作<br/>
 *
 * @author  505719
 * @email   刘智
 * @date    2017年8月14日11:39:50
 * @version 1.0
 * @since   1.0
 */
public class RecordActionHandler {
	
	/**
	 * <br>开始录制视频</br>
	 * 
	 * @param RecordCaseName
	 */
	public void startRecord(TestStep step) throws Exception {
	    Runtime rt = Runtime.getRuntime();
	    String RecordCaseName = step.getCaseid();
	    try {
	  		 rt.exec("cmd.exe /C adb shell screenrecord /sdcard/"+ RecordCaseName +".mp4");
	  		 System.out.println(step.getDesc());
	  	} 
	    catch (IOException e) {
	    	 e.printStackTrace();
	  	}
	}
	
	/**
	 * <br>结束录制视频</br>
	 * 
	 */
	public void endRecord(TestStep step) throws Exception {
	    Runtime rt = Runtime.getRuntime();
	    try {
	  		 rt.exec("cmd.exe /C adb kill-server");
	  		 rt.exec("cmd.exe /C adb start-server");
	  		 Thread.sleep(8000);
	  		 System.out.println(step.getDesc());
	  	} 
	    catch (IOException e) {
	    	 e.printStackTrace();
	    }
	}
	
	/**
	 * <br>上传录制的视频</br>
	 * 
	 * @param RecordCaseName
	 */
	public void pullRecord(TestStep step) throws Exception {
	    Runtime rt = Runtime.getRuntime();
	    String RecordCaseName = step.getCaseid();
	    try {
	  		 rt.exec("cmd.exe /C adb pull /sdcard/"+ RecordCaseName +".mp4");
	  		 Thread.sleep(3000);
	  		 System.out.println(step.getDesc());
	  	} 
	    catch (IOException e) {
	    	 e.printStackTrace();
	  		 System.out.println("file not found"); 
	    }
	}
		
	/**
	 * <br>移动录制的视频</br>
	 * 
	 * @param RecordCaseName
	 */
	public void moveRecord(TestStep step) throws Exception {
	    Runtime rt = Runtime.getRuntime();
	    String RecordCaseName = step.getCaseid();
	    try {
	  		 rt.exec("cmd.exe /C move "+ RecordCaseName +".mp4 " + ConfigUtil.getProperty("video.path", Constants.CONFIG_COMMON));
	  		 System.out.println(step.getDesc());
	  	} 
	    catch (IOException e) {
	    	 e.printStackTrace();
	  		 System.out.println("file not found"); 
	    }
	}
}
