package AutomationTestSystem.Handler;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

import AutomationTestSystem.Base.TestStep;
import AutomationTestSystem.Util.AppiumUtil;
import AutomationTestSystem.Util.SeleniumUtil;

public class ClearActionHandler {
	
	/**
	 * <br>Web端清除操作</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void webClear(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getWebDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		WebElement e = SeleniumUtil.getElement(step);
		e.clear();	
	}
	
	/**
	 * <br>Android端清除操作</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void androidClear(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getAndroidDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		WebElement e = AppiumUtil.getElement(step);
		e.clear();	
	}
}
