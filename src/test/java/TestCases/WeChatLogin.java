package TestCases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jmoney.jiumiaodai.base.TestUnit;
import com.jmoney.jiumiaodai.service.RunUnitService;
import com.jmoney.jiumiaodai.service.AndroidXmlParseService;

public class WeChatLogin {
	
	private static RunUnitService runService;
		
	@BeforeTest
	private void stup() throws Exception{
		TestUnit testunit = AndroidXmlParseService.parse("src/test/java/TestCaseXml/WeChatLogin.xml");
		runService = new RunUnitService(testunit);
		System.out.println("-----------------------------------【微信登录流程的测试场景点】-----------------------------------");
	}
	
	@Test
	public void case1() throws Exception{
		runService.runCase("case1");
		runService.TestReportRemarks("验证在Android系统中，首次启动微信APP，点击登录按钮后，可以正常进入登录界面");
	}

	@Test
	public void case2() throws Exception{
		runService.runCase("case2");
		runService.TestReportRemarks("验证在登录界面，输入正确的手机号和密码，点击登录按钮后，可以正常登录成功，并跳转至首页主界面");
	}
	
	@AfterTest
	public void TearDown(){
		runService.closeApp();
	}
}
