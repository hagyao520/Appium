package TestCases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import AutomationTestSystem.Base.TestUnit;
import AutomationTestSystem.Service.RunUnitService;
import AutomationTestSystem.Service.AndroidXmlParseService;

public class WeChatLogin {
	
	private static RunUnitService runService;
		
	@BeforeTest
	private void stup() throws Exception{
		TestUnit testunit = AndroidXmlParseService.parse("WeChat.apk","com.tencent.mm","Android","8.0","55CDU16726008808","WeChatLogin.xml");
		runService = new RunUnitService(testunit);
		System.out.println("-----------------------------------【微信登录流程的测试场景点】-----------------------------------");
	}
	
	@Test
	public void case1() throws Exception{
		runService.runCase("case1");
	}

	@Test
	public void case2() throws Exception{
		runService.runCase("case2");
	}
	
	@AfterTest
	public void TearDown(){
		runService.closeApp();
	}
}
