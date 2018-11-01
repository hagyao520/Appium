package AutomationTestSystem.Handler;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

import AutomationTestSystem.Base.TestStep;
import AutomationTestSystem.Util.AppiumUtil;
import AutomationTestSystem.Util.SeleniumUtil;

public class InputActionHandler {
	
	/**
	 * <br>Web端输入操作</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void webInput(TestStep step) throws Exception{ 
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getWebDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		WebElement e = SeleniumUtil.getElement(step);
		e.clear();
		e.sendKeys(SeleniumUtil.parseStringHasEls(step.getValue()));	
	}
	
	/**
	 * <br>Android端输入操作</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void androidInput(TestStep step) throws Exception{ 
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getAndroidDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		WebElement e = AppiumUtil.getElement(step);
		e.clear();
		e.sendKeys(AppiumUtil.parseStringHasEls(step.getValue()));	
	}
	
	/**
	 * <br>Android-ADB指令输入操作</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void adbInput(TestStep step) throws Exception{ 
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getAndroidDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		AppiumUtil.getElement(step).click(); 
		Runtime.getRuntime().exec("adb shell input text "+step.getValue());
	}
}
