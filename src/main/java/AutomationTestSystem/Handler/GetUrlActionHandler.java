package AutomationTestSystem.Handler;

import java.util.concurrent.TimeUnit;

import AutomationTestSystem.Base.TestStep;

public class GetUrlActionHandler {
	
	/**
	 * <br>Web端打开网站链接操作</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void webGeturl(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getWebDriver().get(step.getValue());	
		step.getWebDriver().manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * <br>Android端打开网站链接操作</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void androidGeturl(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getAndroidDriver().get(step.getValue());	
		step.getAndroidDriver().manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
	}
}
