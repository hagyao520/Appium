package AutomationTestSystem.Handler;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import AutomationTestSystem.Base.TestStep;

public class WindowsSystemHandler {
	
	/**
	 * <br>模拟点击Windows系统键盘普通按键,例如：Home键</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void windowsKeybg(TestStep step) throws Exception{ 
		Actions action = new Actions(step.getWebDriver()); 
	    action.sendKeys(Keys.valueOf(step.getKey())).perform();
	    step.getWebDriver().manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * <br>模拟点击Windows系统键盘组合按键,例如：Ctrl+W</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void windowsKeybc(TestStep step) throws Exception{ 
		Actions action = new Actions(step.getWebDriver()); 
		action.keyDown(Keys.valueOf(step.getKey())).sendKeys(step.getValue()).perform();
	    step.getWebDriver().manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * <br>模拟点击Windows系统键盘特殊组合按键,例如：Ctrl+Tab</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void windowsSkeybc(TestStep step) throws Exception{ 
		Actions action = new Actions(step.getWebDriver()); 
		action.keyDown(Keys.valueOf(step.getKey())).sendKeys(Keys.valueOf(step.getKeys())).keyUp(Keys.valueOf(step.getKey())).sendKeys(Keys.valueOf(step.getKeys())).perform();
	    step.getWebDriver().manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * <br>模拟点击Windows系统键盘多重特殊组合按键,例如：Ctrl+Shift+K</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void windowsSkeybcm(TestStep step) throws Exception{ 
		Actions action = new Actions(step.getWebDriver()); 
		action.keyDown(Keys.valueOf(step.getKey())).sendKeys(Keys.valueOf(step.getKeys())).sendKeys(step.getValue()).keyUp(Keys.valueOf(step.getKey())).sendKeys(Keys.valueOf(step.getKeys())).sendKeys(step.getValue()).perform();
	    step.getWebDriver().manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * <br>模拟操作切换浏览器到当前最新窗口</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void windowsStcnw(TestStep step) throws Exception{ 
		String WindowHandle  = step.getWebDriver().getWindowHandle();
		step.getWebDriver().switchTo().window(WindowHandle);
    	step.getWebDriver().manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * <br>模拟执行Windows系统的cmd命令</br>
	 *
	 * @param step
	 * @throws Exception 
	 */
	public void windowsCmd(TestStep step) throws Exception{ 
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		Runtime runtime=Runtime.getRuntime();
        try{
            runtime.exec(step.getValue());  
        }
        catch(Exception e){	
        	System.out.println("Error exec!"); 
        }
        Thread.sleep(500);
	}
}
