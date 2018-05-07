package com.jmoney.jiumiaodai.handler;

import java.util.concurrent.TimeUnit;

import com.jmoney.jiumiaodai.base.TestStep;

/**
 * 等待动作处理类
 */
public class WaitActionHandler {
	
	/**
	 * 强制等待
	 * @param map
	 * @param step
	 */
	public void waitForced(TestStep step){
		try {
			System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
			String waitTime = step.getValue();
			Thread.sleep(Long.valueOf(waitTime));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Web端隐式等待
	 * @param map
	 * @param step
	 */
	public void webImplicit(TestStep step){
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		long waitTime = Long.valueOf(step.getValue());
		step.getWebDriver().manage().timeouts().implicitlyWait(waitTime, TimeUnit.MILLISECONDS);	
	}

	/**
	 * Android端隐式等待
	 * @param map
	 * @param step
	 */
	public void androidImplicit(TestStep step){
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		long waitTime = Long.valueOf(step.getValue());
		step.getAndroidDriver().manage().timeouts().implicitlyWait(waitTime, TimeUnit.MILLISECONDS);	
	}
	
}
