package AutomationTestSystem.Handler;

import java.util.concurrent.TimeUnit;

import AutomationTestSystem.Base.TestStep;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDeviceActionShortcuts;

public class AndroidSystemHandler {
	
	/**
	 * <br>模拟点击Android系统键盘按键操作,例如：KEYCODE_HOME 3</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void androidKeycode(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
	       ((AndroidDeviceActionShortcuts) step.getAndroidDriver()).
	        	pressKeyCode(Integer.valueOf(step.getDetails().get("key")));
	            Thread.sleep(600);
	}
	
	/**
	 * <br>模拟点击Android系统返回按键操作,可返回多次</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void androidReturn(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		int Num = Integer.valueOf(step.getDetails().get("num"));
		for (int i = 0; i < Num; i++) {  
			step.getAndroidDriver().navigate().back(); 
        }
	}
	
	/**
	 * <br>模拟打开Androud系统通知栏操作</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void androidOpennb(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getAndroidDriver().openNotifications();
	}
	
	/**
	 * <br>模拟执行Androud系统页面滚动操作</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void androidScroll(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getAndroidDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		step.getAndroidDriver().scrollToExact(step.getValue()).click();
	}
	
	/**
	 * <br>模拟执行Androud系统页面滑动操作</br>
	 * 
	 * @param step
	 * @throws Exception 
	 */
	public void androidSwipe(TestStep step) throws Exception {
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		TouchAction tAction = new TouchAction(step.getAndroidDriver());
		tAction.press(400,500).waitAction(800).moveTo(50,500).release().perform();
	}
	
	/**
	 * <br>Android-ADB指令页面滑动操作</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void adbSwipe(TestStep step) throws Exception{ 
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		step.getAndroidDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		Runtime.getRuntime().exec("adb shell input swipe "+ step.getValue());
	}
}
