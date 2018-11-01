package AutomationTestSystem.Handler;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import AutomationTestSystem.Base.TestStep;
import AutomationTestSystem.Util.AppiumUtil;
import AutomationTestSystem.Util.SeleniumUtil;

public class SetActionHandler {
	
	/**
	 * <br>Web端设置值到全局</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void webSet(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getWebDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		if(StringUtils.isBlank(step.getDetails().get("key")))
			throw new Exception("set操作必须设置保存结果的键值，供后续操作使用，例子为details='key:credit'！");
		String value = SeleniumUtil.getElement(step).getText();
		SeleniumUtil.localmap.put(step.getDetails().get("key").toUpperCase(), value);
//		System.out.println("『正常测试』开始执行: <成功记录到本地List列表，" +SeleniumUtil.localmap.toString() + ">");
	}
	
	/**
	 * <br>Android端设置值到全局</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void androidSet(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">" + AppiumUtil.localmap.toString());
		step.getAndroidDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		if(StringUtils.isBlank(step.getDetails().get("key")))
			throw new Exception("set操作必须设置保存结果的键值，供后续操作使用，例子为details='key:credit'！");
		String value = AppiumUtil.getElement(step).getText();
		AppiumUtil.localmap.put(step.getDetails().get("key").toUpperCase(), value);
//		System.out.println("『正常测试』开始执行: <成功记录到本地List列表，" +SeleniumUtil.localmap.toString() + ">");
	}
}
