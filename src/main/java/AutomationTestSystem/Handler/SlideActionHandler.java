package AutomationTestSystem.Handler;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;

import AutomationTestSystem.Base.TestStep;
import AutomationTestSystem.Util.AppiumUtil;
import io.appium.java_client.android.AndroidDriver;

/**
 * <br>滑动逻辑类</br>
 * <h1>一共有四种滑动方式：</h1>
 * 1.默认滑动，上下左右</br>
 * 2.基于控件滑动，上下左右</br>
 * 3.给定起始点滑动，上下左右</br>
 * 4.自定义起始点和结束点滑动，方法slide()</br>
 *
 * @author  刘智
 * @date    2018年8月27日 15:36:41
 * @version 1.0
 * @since   1.0
 */
public class SlideActionHandler {
	
    /**
     * <br>向左滑动的处理逻辑</br>
     *
     * @param step
     * @throws Exception
     */
    public void slideLeft(TestStep step) throws Exception{
    	//滑动起始点与滑动幅度
    	System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
    	double[] cor = {0.75 ,0.5 ,0.25 ,0.5};
    	if(StringUtils.isBlank(step.getDetails().get("amp")))
    		commonSlide(step,cor);
    	else commonSlide(step,5);
    	Thread.sleep(500);
    }
    
    /**
     * <br>向右滑动的处理逻辑</br>
     *
     * @param step
     * @throws Exception
     */
    public void slideRight(TestStep step) throws Exception{
    	//向右滑动的起点与终点坐标
    	System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
    	double[] cor = {0.25 ,0.5 ,0.75 ,0.5};
    	if(StringUtils.isBlank(step.getDetails().get("amp")))
    		commonSlide(step,cor);
    	else commonSlide(step,3);
    	Thread.sleep(500);
    }
    
    /**
     * <br>向上滑动的操作</br>
     *
     * @author    刘智
     * @date      2017年7月26日 下午4:51:20
     * @param step
     * @throws Exception 
     */
    public void slideUp(TestStep step) throws Exception{
    	System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
    	//向上滑动的起点与终点坐标
    	double[] cor = {0.5 ,0.75 ,0.5 ,0.2947};
    	if(StringUtils.isBlank(step.getDetails().get("amp")))
    		commonSlide(step,cor);
    	else commonSlide(step,7);
        Thread.sleep(500);
    }    
    
    /**
     * <br>向下滑动的操作</br>
     *
     * @author    刘智
     * @date      2017年7月26日 下午4:51:20
     * @param step
     * @throws Exception 
     */
    public void slideDown(TestStep step) throws Exception{
    	//向下滑动的起点与终点坐标
    	System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
    	double[] cor = {0.5 ,0.25 ,0.5 ,0.75};
    	if(StringUtils.isBlank(step.getDetails().get("amp")))
    		commonSlide(step,cor);
    	else commonSlide(step,1);
    	Thread.sleep(500);
    } 
    
    /**
     * <br>用户自定义滑动</br>
     *
     * @author    刘智
     * @date      2017年8月3日 上午11:06:02
     * @param step
     * @throws Exception 
     */
    public void slide(TestStep step) throws Exception{
    	System.out.println("『正常测试』开始执行: " + "<" +step.getDesc() + ">");
    	commonSlide(step); 
    	Thread.sleep(500);
    }
    
    /**
     * <br>界面上的滑动处理逻辑:上、下、左、右</br>
     * 用户需要在配置文件中配置起始点与滑动幅度的确定逻辑：</br>
     * 1.用户配置了起始点，滑动幅度相对于屏幕</br>
     * 2.用户指定了控件，起始点与滑动幅度相对于控件</br>
     * 
     * <h1>优先使用1的配置，如果两个都没有配置，则报错</h1>
     *
     * @author    刘智
     * @date      2017年7月31日 上午11:21:11
     * @param driver
     * @param cor 滑动的坐标数组，大小为4，由滑动的起始坐标点x坐标比例，起始坐标点y坐标比例，
     * 						结束坐标点x坐标比例，结束坐标点y坐标比例
     * @param 滑动的方向 1-下 3-右 5-左 7-上
     * @throws Exception 
     */
    private void commonSlide(TestStep step,int direct) throws Exception {
    	AndroidDriver<WebElement> driver = step.getAndroidDriver();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        
        int duration = Integer.valueOf(step.getDetails().get("duration")); 
		int num = Integer.valueOf(step.getDetails().get("num"));
        
		int startx = -1,starty =-1 ,endx = -1,endy = -1,ampx = 0,ampy = 0;
		
		if(StringUtils.isBlank(step.getDetails().get("amp"))) 
			throw new Exception("必须指定滑动幅度！");
		
		if(!StringUtils.isBlank(step.getDetails().get("sxr"))){
			startx = (int)(width*Double.valueOf(step.getDetails().get("sxr")));
		}
		if(!StringUtils.isBlank(step.getDetails().get("syr"))){
			starty = (int)(width*Double.valueOf(step.getDetails().get("sxr")));
		}
		if(startx < 0 ^ starty < 0)
			throw new Exception("滑动的起始坐标配置不全或错误！");;
		
		//没有配置起点，则由控件确定起点坐标
		if(startx < 0 && starty < 0){
			WebElement e = AppiumUtil.getElement(step);
			
			startx = e.getLocation().x+2+(direct%3)*(e.getSize().width-4)/2;
			starty = e.getLocation().y+2+(direct/3)*(e.getSize().height-4)/2;
			
			width = e.getSize().width-4;
			height = e.getSize().height-4;
		}
		
		ampx = (int)(width*Double.valueOf(step.getDetails().get("amp")));
		ampy = (int)(height*Double.valueOf(step.getDetails().get("amp")));
		
		endx = startx+(1-direct%3)*ampx;
		endy = starty+(1-direct/3)*ampy;
		
//		System.out.println(step.toString()+" "+startx+" "+starty+" "+endx+" "+endy);
		
        for (int i = 0; i < num; i++) {  
        	driver.swipe(startx, starty, endx, endy, duration);
        }
    }
    
    /**
     * <br>默认滑动</br>
     * 代码中指定的滑动逻辑
     *
     * @author    刘智
     * @date      2017年7月31日 下午5:53:51
     * @param step
     * @param cor 起始和结束点
     * @throws Exception
     */
    private void commonSlide(TestStep step,double[] cor) throws Exception{
    	AndroidDriver<WebElement> driver = step.getAndroidDriver();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        
        int during = Integer.valueOf(step.getDetails().get("duration")); 
		int num = Integer.valueOf(step.getDetails().get("num"));
                
        for (int i = 0; i < num; i++) {  
        	step.getAndroidDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
        	driver.swipe((int)(width*cor[0]), (int)(height*cor[1]), 
        			(int)(width*cor[2]), (int)(height*cor[3]), during);
        }
    }
    
    /**
     * <br>自定义滑动</br>
     * 用户可指定滑动时间、滑动次数、滑动起始点、滑动结束点</br>
     * 如果测试用例配置文件中没有配置起始点和结束点，则报错
     *
     * @author    刘智
     * @date      2017年7月31日 下午5:53:51
     * @param step
     * @param cor 起始和结束点
     * @throws Exception
     */
    private void commonSlide(TestStep step) throws Exception{
    	AndroidDriver<WebElement> driver = step.getAndroidDriver();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        
        int during = Integer.valueOf(step.getDetails().get("duration")); 
		int num = Integer.valueOf(step.getDetails().get("num"));
        double[] cor = new double[4];
		
        String[] corname = {"sxr","syr","exr","eyr"};
        int count = 0;
        for(int i = 0;i < 4;i++){
        	String cc = step.getDetails().get(corname[i]);
	        if(!StringUtils.isBlank(cc)){
	        	cor[i] = Double.valueOf(cc);
	        }else{
	        	count++;
	        }
        }
  
        if(count == 4 ) throw new Exception("滑动的坐标配置不全或错误！");
                
        for (int i = 0; i < num; i++) {  
        	step.getAndroidDriver().manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
        	driver.swipe((int)(width*cor[0]), (int)(height*cor[3]), 
        			(int)(width*cor[2]), (int)(height*cor[1]), during);
        }
    }
}
