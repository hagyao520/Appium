package com.jmoney.jiumiaodai.handler;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.jmoney.jiumiaodai.base.TestStep;
import com.jmoney.jiumiaodai.service.AndroidXmlParseService;
import com.jmoney.jiumiaodai.service.WebXmlParseService;
import com.jmoney.jiumiaodai.util.AppiumUtil;
import com.jmoney.jiumiaodai.util.Constants;
import com.jmoney.jiumiaodai.util.SeleniumUtil;

/**
 * <br>检查操作<br/>
 *
 * @author  102051
 * @email   mengxiw@dafycredit.com
 * @date    2017年7月26日 上午10:27:26
 * @version 1.0
 * @since   1.0
 */
public class CheckActionHandler {
	
	/**
	 * <br>检查界面上的元素是否与预期值相等</br>
	 *
	 * @author    102051
	 * @date      2017年8月2日 下午6:03:33
	 * @param step
	 * @throws Exception 
	 */
	public void webCheck(TestStep step) throws Exception{
		String Actual = SeleniumUtil.getElement(step).getText();
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());	
		String FailHint = step.getMessage();
		String CaseID = step.getCaseid();
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		checkEqualsWeb(Actual,Expected,FailHint,CaseID);
	}
	
	public void androidCheck(TestStep step) throws Exception{
		String Actual = AppiumUtil.getElement(step).getText();
		String Expected = AppiumUtil.parseStringHasEls(step.getExpect());
		String FailHint = step.getMessage();
		String CaseID = step.getCaseid();
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		checkEqualsAndroid(Actual,Expected,FailHint,CaseID);
	}
	
	public void webChecklist(TestStep step) throws NumberFormatException, Exception{
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		String conditon = step.getDetails().get("condition");
		if(Constants.SIZE_NOT_ZERO.equals(conditon))
			weblistSizeNotZero(step);
		else if(Constants.FIELD.equals(conditon)) 
			webfieldEquals(step);
	}
	
	public void androidChecklist(TestStep step) throws NumberFormatException, Exception{
		System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
		String conditon = step.getDetails().get("condition");
		if(Constants.SIZE_NOT_ZERO.equals(conditon))
			androidlistSizeNotZero(step);
		else if(Constants.FIELD.equals(conditon)) 
			androidfieldEquals(step);
	}
	
	@SuppressWarnings("unchecked")
	public void weblistSizeNotZero(TestStep step) throws Exception{
		List<Map<String,Object>>  list = (List<Map<String,Object>>)
		SeleniumUtil.parseEL(step.getDetails().get("subject"));
		
		if(list.size() == 0)
			throw new Exception(step.getMessage());
	}
	
	@SuppressWarnings("unchecked")
	public void androidlistSizeNotZero(TestStep step) throws Exception{
		List<Map<String,Object>>  list = (List<Map<String,Object>>)
		AppiumUtil.parseEL(step.getDetails().get("subject"));
		
		if(list.size() == 0)
			throw new Exception(step.getMessage());
	}
	/**
	 * <br>检查列表中的字段值</br>
	 * 此时localmap中的值是一个List<Map<>>，所以需要提供检查的索引
	 *
	 * @author    102051
	 * @date      2017年8月3日 上午9:35:19
	 * @param step
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public void webfieldEquals(TestStep step) throws NumberFormatException, Exception{
//		System.out.println("CHECK_FILED---"+step.getDetails().get("subject"));	
		String Actual = (String)SeleniumUtil.parseStringHasEls(step.getDetails().get("subject"));
		String Expected = SeleniumUtil.parseStringHasEls(step.getExpect());
		String FailHint = step.getMessage();
		try {
			Assert.assertEquals(Actual,Expected,FailHint);
			Thread.sleep(500);
        }
 	    catch (Error | InterruptedException e)  {
 	    	Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
 	    }
	}	 

	public void androidfieldEquals(TestStep step) throws NumberFormatException, Exception{
//		System.out.println("CHECK_FILED---"+step.getDetails().get("subject"));	
		String Actual = (String)AppiumUtil.parseStringHasEls(step.getDetails().get("subject"));
		String Expected = AppiumUtil.parseStringHasEls(step.getExpect());
		String FailHint = step.getMessage();
		try {
			Assert.assertEquals(Actual,Expected,FailHint);
			Thread.sleep(500);
        }
 	    catch (Error | InterruptedException e)  {
 	    	Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
 	    }
	}
	
	/**
	 * <br>检查预期与实际是否相等，不等则提示错误信息，并截图</br>
	 *
	 * @author    102051
	 * @date      2017年8月2日 下午6:01:04
	 * @param Actual 
	 * @param Expected
	 * @param FailHint
	 * @param FailedScreenshotCaseName
	 */
	public void checkEqualsWeb(String Actual,String Expected,String FailHint,String CaseID){
		try {
			Assert.assertEquals(Actual,Expected,FailHint);
			Thread.sleep(500);
        }
 	    catch (Error | InterruptedException e)  {
 	    	WebXmlParseService.screenShot(CaseID);
 	    	Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
 	    }
	}

	public void checkEqualsAndroid(String Actual,String Expected,String FailHint,String CaseID){
		try {
			Assert.assertEquals(Actual,Expected,FailHint);
			Thread.sleep(500);
        }
 	    catch (Error | InterruptedException e)  {
 	    	AndroidXmlParseService.screenShot(CaseID);
 	    	Assert.fail(FailHint +"  "+"Actual 【"+ Actual +"】"+"  "+"but found 【"+ Expected +"】");
 	    }
	}
}
