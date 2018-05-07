package com.jmoney.jiumiaodai.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;

import com.jmoney.jiumiaodai.base.StepAction;
import com.jmoney.jiumiaodai.base.TestCase;
import com.jmoney.jiumiaodai.base.TestStep;
import com.jmoney.jiumiaodai.base.TestUnit;

/**
 * <br>TODO(描述该类的作用)</br>
 *
 * @date    2017年7月27日 下午5:50:09
 * @version 1.0
 * @since   1.0
 */
public class RunUnitService {

	private TestUnit testunit;
	
	public RunUnitService(TestUnit testunit){
		this.testunit = testunit;
	}
	
    /**
     * <br>根据索引从TestUnit中获取测试用例</br>
     *
     * @param index
     * @return
     */
    public TestCase getCase(int index){
        int n = 0;
        if(testunit.getCaseMap() == null)
            return null;
        
        int size = testunit.getCaseMap().size();
        if(index < 0 || (index > 0 && index >= size))
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        
        //遍历map内部Entry集合
        for(Map.Entry<String,TestCase> e : testunit.getCaseMap().entrySet()){
            if(n++ == index)
                return e.getValue();
        }
        
        return null;
    }

    /**
     * <br>根据用例名，即case元素中的id元素值，从TestUnit中获取测试用例</br>
     *
     * @param id
     * @return
     */
    public TestCase getCase(String id){
        return testunit.getCaseMap() == null ? null : testunit.getCaseMap().get(id);
    }
    
    /**
     * <br>执行测试用例</br>
     *
     * @param id
     * @throws Exception
     */
    public void runCase(String id) throws Exception{
    	TestCase testCase = getCase(id);
    	List<TestStep> steps = testCase.getSteps();
    	
    	for(TestStep step : steps){
    		if(step.isCancel())
    			continue;
//    		System.out.println("开始执行: "+step.toString());
    		StepAction action = step.getAction();
    		Class<?> clazz = action.handler();
    		
    		//如果StepAction实例有handler字段，则调用handler中的方法，否则直接调用run方法
    		if(clazz != null){
    			String key = step.getAction().key();
    			Method m = clazz.getDeclaredMethod(getMethodName(key), new Class<?>[]{TestStep.class});
    			m.invoke(clazz.newInstance(), step);
    		}else{
    			action.run(step);
    		}
    	}
    }
    
    /**
     * <br>根据step元素的值解析出对应的方法名</br>
     * 作用是将"-"后面的第一个字母转为大写，并且去掉“-”
     *
     * @param actionKey
     * @return
     */
    private String getMethodName(String actionKey){
    	if(actionKey == null || "".equals(actionKey))
    		throw new RuntimeException("empty action key");
    	
    	char[] arr = actionKey.toCharArray();
    	char prevChar = '\0';
    	StringBuilder sb = new StringBuilder();
    	char splitChar = '-';
    	
    	for(char c : arr){
    		if(c == splitChar){
    			prevChar = c;
    			continue;
    		}
    		if(prevChar == splitChar) {
				sb.append(Character.toUpperCase(c));
			} else {
    			sb.append(c);
			}
    		prevChar = c;
    	}
    	
    	return sb.toString();
    }
    
    /**
	 * <br>执行完毕，退出App程序</br>
	 */
	public void closeApp() {
		AndroidXmlParseService.QuitApp();
	}
    
	/**
	 * <br>执行完毕，关闭浏览器</br>
	 */
	public void closeBrowser() {
		WebXmlParseService.QuitBrowser();
	}
	
    /**
     * <br>标记备注，一般展示在测试报告中</br>
     *
     * @param RemarksName
     */
    public void TestReportRemarks(String RemarksName){
 	   Reporter.log(RemarksName);
    }
    
    /**
     * <br>标记备注，一般展示在测试报告中</br>
     *
     * @param command
     */
    public void AndroidAdb(String command) throws IOException{
    	Runtime.getRuntime().exec(command);
    }
}    
