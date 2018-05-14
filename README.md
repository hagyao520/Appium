# 欢迎查阅Appium（Android自动化测试框架体系）
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Appium
![](https://testerhome.com/uploads/photo/2018/27f5bcda-c947-48e5-8f9b-8e62c42c5a11.png!large)

    Appium是一个移动端的自动化框架，可用于测试原生应用，移动网页应用和混合型应用，且是跨平台的，可用于IOS和Android以及Firefox OS的操作系统
        •  原生的应用是指用Android或IOS的SDK编写的应用，例如微信，QQ等APP
        •  移动网页应用是指网页应用，例如IOS中Safari，Chrome等浏览器的应用。
        •  混合应用是指一种包裹WebView的应用，原生应用于网页内容交互性的应用，例如微信即有分期
        •  其中最重要的是Appium是跨平台的，何为跨平台，意思就是可以针对不同的平台用一套API来编写测试用例

 ---
### 框架介绍
    Java + Appium + Maven + TestNG + JDBC + Xml+ Git + +Ant + Jenkins 
        •  使用Java作为项目编程语言
        •  使用Appium作为App项目底层服务驱动框架
        •  使用Maven作为项目类型，方便管理架包
        •  使用TestNG作为项目运行框架，方便执行测试用例，生成测试报告
        •  使用JDBC作为数据库管理工具，方便连接数据库，执行SQL
        •  使用Xml作为用例管理工具，方便编写测试用例，维护测试脚本
        •  使用Git作为仓库管理工具，方便管理项目代码
        •  使用Ant作为Java的build打包工具，方便项目代码打包
        •  使用Jenkins作为自动化持续集成平台，方便自动编译，自动打包，自动运行测试脚本，邮件发送测试报告

 ---
### 主要功能
    1.  实现了基于Appium，WebDriver等常用操作方法的二次封装，包括（滑动，点击，输入，元素定位）等，使用起来更简便
    2.  实现了基于Windows，Android，IOS操作系统的cmd，adb，terminal常用DOS命令的快速调用
    3.  实现了基于Windows，Android，IOS操作系统等键盘按键功能的调用，可模拟实际的键盘操作
    4.  实现了基于Appium的断言功能，检查点失败自动截图保存，可在测试报告中查看，一个检查点失败不影响后续用例执行
    5.  实现了基于Xml文件内容的基本解析，包括（Unit，Case，Step）等，基本内容符合测试用例编写步骤，编写测试用例脚本更简单
    6.  实现的基于Oracle，MySql等常用数据库SQL操作，包含（Insert into，Delete，Update，Query）和执行"存储过程"操作等
    7.  实现了基于Oracle，MySql等常用数据库数据检查功能，获取数据库字段值，保存到本地缓存，然后进行比对效验，需使用正则表达式
    8.  实现了快速获取界面信息数据到本地缓存功能，获取当前界面上的数据，保存到本地缓存，可用作测试用例参数使用，需使用正则表达式
    9.  实现了常用API接口请求操作，包含（POST，GET）等，可直接在测试脚本中调用，只需传递对应参数即可，满足多种测试需求
    10. 实现了基于ExtentReports，TestNG生成的测试报告二次美化功能，界面更美观，内容清晰

 ---
### 环境配置
   1. [JDK1.7以上](http://www.Oracle.com/technetwork/Java/javase/downloads/index.html)
   2. [Eclipse](http://www.eclipse.org/downloads)/[IDEA](https://www.jetbrains.com/idea/)
   3. [Android SDK](http://www.androiddevtools.cn) 
   4. [Selenium](https://www.seleniumhq.org/download) 
   5. [Appium](https://pan.baidu.com/s/1FasYQHUQ1nsMyCpZF4fJtA) 
   6. [Maven](http://maven.apache.org/download.cgi) 
   7. [Git](https://git-scm.com/) 
   7. [Ant](https://ant.apache.org) 
   8. [Jenkins](https://jenkins.io) 
   9. [一台安卓手机或者安卓模拟器，推荐使用真机](https://www.yeshen.com) 

 - 部分网站需要翻墙，具体安装参考：https://blog.csdn.net/love4399/article/details/77164500
 
 ---
### 注意事项
 - 工程项目编码需要设置成UTF-8，否则会出现中文乱码情况

 ---
### 一、创建测试对象类，例如【WeChatLogin.java】
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
            TestUnit testunit = AndroidXmlParseService.ParseTest("src/test/java/TestCaseXml/WeChatLogin.xml");
            runService = new RunUnitService(testunit);
            System.out.println("--------------------------【微信登录流程的测试场景点】--------------------------");
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

 ---
### 二、创建测试脚本用例，例如【WeChatLogin.xml】
    <?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
    <unit id="WeChatLogin" desc="微信登录流程的测试场景点">

    <case 
        id="case1" 
        name="验证在Android系统中，首次启动微信APP，点击登录按钮后，可以正常进入登录界面">
        <step action="wait-forced" value="6000" desc="强制等待5秒"/>
        <step action="android-click" locator="resource-id=com.tencent.mm:id/ca4" desc="点击登录按钮"/>    
        <step action="android-check" locator="resource-id=com.tencent.mm:id/a_m" expect="登录" message="进入登录界面失败(实际结果和预期结果不一致)" caseid="Case1" desc="检查在Android系统中，首次启动微信APP，点击登录按钮后，可以正常进入登录界面"/>
    </case>

    <case 
         id="case2" 
        name="验证在登录界面，输入正确的手机号和密码，点击登录按钮后，可以正常登录成功，并跳转至首页主界面">
        <step action="android-input" locator="text=你的手机号码" value="XXX" desc="输入正确的手机号"/>
        <step action="android-input" locator="resource-id=com.tencent.mm:id/g_" value="XXX" desc="输入正确的密码"/>
        <step action="android-click" locator="text=登录" desc="点击登录按钮"/>
        <step action="android-check" locator="text=通讯录" expect="通讯录"  message="登录失败(实际结果和预期结果不一致)" caseid="Case3" desc="检查在登录界面，输入正确的手机号和密码，点击登录按钮后，可以正常登录成功，并跳转至首页主界面"/>    
    </case>
</unit>

    以上只是单个案例，账户和密码请用自己的真实数据，是不是很简单，和写测试用例基本一致
       <unit>到</unit>之间的内容为测试脚本集合，相当与测试用例集合，搭配测试类使用（WeChatLogin.java）
       <case>到</case>之间的内容为单个测试脚本，相当与单个测试用例，id对应测试用例中的序号，name对应测试用例中的标题，注意这里的id需要和测试类（WeChatLogin.java）中的一致
       <step>到</step>之间的内容为测试脚本步骤，相当与测试用例操作步骤，action=要执行的操作，locator=元素的坐标属性及值，value=需要传递的参数，desc=该步骤的备注，会打印到控制台，expect=预期结果，message=测试执行失败的提示信息，会展示到测试报告中，caseid=测试用例失败截图的名称，一般和Caseid一致，表示是该用例的截图

  - 具体脚本编方法请参考: https://pan.baidu.com/s/1fdMMGrr9XY6lJdXCv-1AYw

 ---
### 三、Appium服务配置
    public static void appiumConfigure() throws Exception {
          //指定APK安装路径:
          File apk = new File(ConfigUtil.getProperty("apk.path", Constants.CONFIG_COMMON), "weixin_1300.apk");

          //设置自动化相关参数:
          DesiredCapabilities capabilities = new DesiredCapabilities();

          //设置Appium测试引擎:
          capabilities.setCapability("device", "uiautomator");

          //指定测试设备型号或物理ID，系统及系统版本:
          //当前连接的手机,默认识别一台
          capabilities.setCapability("deviceName", "Android Emulator");

          //小米5S(黑色-USB有线连接)
          //capabilities.setCapability("deviceName", "29739ff4");
          //capabilities.setCapability("udid", "29739ff4");
        
          //小米5S(金色-USB有线连接)
          //capabilities.setCapability("deviceName", "b62f7ec6");
          //capabilities.setCapability("udid", "b62f7ec6");
        
          //小米5S(金色-WIFI无线连接)
          //capabilities.setCapability("deviceName", ConfigUtil.getProperty("MI_5S_golden.WIFI", Constants.CONFIG_COMMON));
          //capabilities.setCapability("udid", ConfigUtil.getProperty("MI_5S_golden.WIFI", Constants.CONFIG_COMMON));
        
          capabilities.setCapability("platformName", "Android");
          capabilities.setCapability("platformVersion", "4.2.2");

          //初始化APP缓存，false(初始化)/true(不初始化)
          capabilities.setCapability("noReset", true);

          //重新安装APP，true(重新安装)/false(不重新安装)
          capabilities.setCapability("fullReset", false);

          //启动时是否覆盖session，true(覆盖)/false(不覆盖)
          capabilities.setCapability("sessionOverride", false);

          //开启中文输入，安装Unicode输入法，true(安装)/false(不安装)
          capabilities.setCapability("unicodeKeyboard", true);

          //还原系统默认输入法，true(还原)/false(不还原)
          capabilities.setCapability("resetKeyboard", true);

          //设置Appium超时时间:
          capabilities.setCapability("newCommandTimeout", 60000);

          //APK重新签名，false(重签)/true(不重签)
          capabilities.setCapability("noSign", true);

          //已安装后启动APP
          capabilities.setCapability("app", apk.getAbsolutePath());

          //进入Webview
          capabilities.setCapability("autoWebview", true);

          //初始化AndroidDriver
          driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    
          //设置全局隐性等待时间
          driver.manage().timeouts().implicitlyWait(80000, TimeUnit.MILLISECONDS);
    }

 - 测试执行时需要指定DeviceName，PlatformName，PlatformVersion等信息，DeviceName通过命令adb devices获取

 ---
### 四、执行用例
 - 编写完对应测试用例类【WeChatLogin.java】，和测试脚本【WeChatLogin.xml】后，在IDE集成开发环境下选择WeChatLogin.java右键使用TestNG运行即可
![](https://testerhome.com/uploads/photo/2018/d50bb63a-c419-42d6-9606-cc587c219a3b.png!large)

 ---
### 五、测试报告
 - 测试报告分为两种，一种是TestNG自带的TestngReport测试报告，另外一种则是调用ExtentReports生成的报告，第二种更加美观

### [TestngReport](https://static.oschina.net/uploads/space/2018/0508/141415_q7hQ_3854545.png)
    <?xml version="1.0" encoding="UTF-8"?>
    <suite name="Appium测试报告" parallel="false" configfailurepolicy ="continue">
        <test name="微信登录流程" junit="false" verbose="3" parallel="false" thread-count="5" annotations="javadoc" time-out="60000" enabled="true" skipfailedinvocationcounts="true" preserve-order="true" allow-return-values="true">
            <classes>
                <class name="TestCases.WeChatLogin"/>
                    <methods>
                        <include name="case1" />
                        <include name="case2" />
                        <exclude name="" />
                    </methods>
            </classes>
        </test>  
     ------------------------------------------------------------------------------------------------------
    <!-- 调用的监听 -->    
        <listeners>
            <listener class-name="org.uncommons.reportng.HTMLReporter" />
            <listener class-name="org.uncommons.reportng.JUnitXMLReporter" />
        </listeners>      
    </suite>
![](https://testerhome.com/uploads/photo/2018/7ed6f23c-6ede-4439-8dac-1a09a0885221.png!large)

 ---
### [ExtentReports](https://static.oschina.net/uploads/space/2018/0508/141802_q76M_3854545.png)
    <?xml version="1.0" encoding="UTF-8"?>
    <suite name="Suite" verbose="1" preserve-order="true" parallel="false">
        <suite-files>
            <suite-file path="TestngReport.xml"></suite-file>
        </suite-files>
        <listeners>
            <listener class-name="com.jmoney.jiumiaodai.service.ExtentReportGenerateService"></listener>
        </listeners> 
        <!-- C:\Windows\System32\drivers\etc
        151.139.237.11    cdn.rawgit.com -->
    </suite>
 ![](https://testerhome.com/uploads/photo/2018/d0a65967-b63b-4a0a-acc5-dcd7e1988fce.png!large)
 - 第二种测种试报告，需要翻墙才能正常显示，否则页面显示乱码，因为是国外的东西
 - 或者在C:\Windows\System32\drivers\etc    host文件末尾添加151.139.237.11   cdn.rawgit.com
 
 ---
### 六、Jnekins持续集成：
![](https://testerhome.com/uploads/photo/2018/6c209373-80f5-47f3-a9d7-e7dbfe3ea523.png!large)
![](https://testerhome.com/uploads/photo/2018/e24ab598-67da-471d-a924-cc5360c92ec7.png!large)
 - 搭建Jenkins环境，具体请参考: https://blog.csdn.net/wuxuehong0306/article/details/50016547
 - 配置Jenkins自动化持续集成项目，即可实现远程服务器自动（构建，编译，打包）运行脚本，发送邮件测试报告等
 
 ---
### 七、感谢
#### 如果您觉得这个框架对您有用，您可以捐赠下我，让我有理由继续下去，非常感谢。
![](https://testerhome.com/uploads/photo/2018/26d494a6-7b4f-4b69-8db1-0a3b45f886b7.png!large)

**非常感谢您花费时间阅读，祝您在这里记录、阅读、分享愉快！**
**欢迎留言评论，有问题也可以联系我或者加群交流...**

作者：[@刘智King](http://shang.qq.com/email/stop/email_stop.html?qq=1306086303&sig=a1c657365db7e82805ea4b2351081fc3ebcde159f8ae49b1&tttt=1)         
QQ：1306086303     
Email：hagyao520@163.com

> QQ官方交流群 <a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=346d11a1a76d05086cd48bc8249126f514248479b50f96288189ab5ae0ca7ba5"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="126325132" title="126325132"></a>