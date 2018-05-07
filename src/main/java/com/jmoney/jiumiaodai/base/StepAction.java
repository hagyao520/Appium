package com.jmoney.jiumiaodai.base;

import java.util.HashMap;
import java.util.Map;

import com.jmoney.jiumiaodai.handler.AndroidSystemHandler;
import com.jmoney.jiumiaodai.handler.CheckActionHandler;
import com.jmoney.jiumiaodai.handler.ClearActionHandler;
import com.jmoney.jiumiaodai.handler.ClickActionHandler;
import com.jmoney.jiumiaodai.handler.DBActionHandler;
import com.jmoney.jiumiaodai.handler.GetUrlActionHandler;
import com.jmoney.jiumiaodai.handler.HttpRequestHandler;
import com.jmoney.jiumiaodai.handler.InputActionHandler;
import com.jmoney.jiumiaodai.handler.RecordActionHandler;
import com.jmoney.jiumiaodai.handler.SetActionHandler;
import com.jmoney.jiumiaodai.handler.SlideActionHandler;
import com.jmoney.jiumiaodai.handler.WaitActionHandler;
import com.jmoney.jiumiaodai.handler.WindowsSystemHandler;

public enum StepAction {
	
    WEB_SET("web-set", "Web端设置值到全局", SetActionHandler.class),
    ANDROID_SET("android-set", "Android端设置值到全局", SetActionHandler.class),
    
    WEB_GETURL("web-geturl", "Web端打开网站链接操作", GetUrlActionHandler.class),
    ANDROID_GETURL("android-geturl", "Android端打开网站链接操作", GetUrlActionHandler.class),
    
    WINDOWS_KEYBG("windows-keybg", "模拟点击Windows系统键盘普通按键,例如：Home键", WindowsSystemHandler.class),
    WINDOWS_KEYBC("windows-keybc", "模拟点击Windows系统键盘组合按键,例如：Ctrl+W", WindowsSystemHandler.class),
    WINDOWS_SKEYBC("windows-skeybc", "模拟点击Windows系统键盘特殊组合按键,例如：Ctrl+Tab", WindowsSystemHandler.class),
    WINDOWS_SKEYBCM("windows-skeybcm", "模拟点击Windows系统键盘多重特殊组合按键,例如：Ctrl+Shift+K", WindowsSystemHandler.class),
    WINDOWS_STCNW("windows-stcnw", "模拟操作切换浏览器到当前最新窗口", WindowsSystemHandler.class),
    WINDOWS_CMD("windows-cmd", "模拟执行Windows系统的cmd命令", WindowsSystemHandler.class),
    
    ANDROID_KEYCODE("android-keycode", "模拟Android系统键盘按键操作,例如：KEYCODE_HOME 3", AndroidSystemHandler.class),
    ANDROID_RETURN("android-return", "模拟Android系统返回按键操作,可返回多次", AndroidSystemHandler.class),
    ANDROID_OPENNB("android-opennb", "模拟打开Androud系统通知栏操作", AndroidSystemHandler.class),
    ANDROID_SCROLL("android-scroll", "模拟执行Androud系统页面滚动操作", AndroidSystemHandler.class),
    ANDROID_SWIPE("android-swipe", "模拟执行Androud系统页面滑动操作", AndroidSystemHandler.class),
    ADB_SWIPE("adb-swipe", "模拟执行ADB指令页面滑动操作", AndroidSystemHandler.class),
    
    WEB_CLEAR("web-clear", "Web端清除操作", ClearActionHandler.class),
    ANDROID_CLEAR("android-clear", "Android端清除操作", ClearActionHandler.class),
    
    WEB_INPUT("web-input", "Web端输入操作", InputActionHandler.class),
    ANDROID_INPUT("android-input", "Android端Appium输入操作", InputActionHandler.class),
    ADB_INPUT("adb-input", "Android端ADB输入操作", InputActionHandler.class),
    
    WEB_CHECK("web-check", "检查Web界面元素值", CheckActionHandler.class),
    WEB_CHECKLIST("web-checklist", "检查Web本地缓存的list中的元素", CheckActionHandler.class),
    ANDROID_CHECK("android-check", "检查Android界面元素值", CheckActionHandler.class),
    ANDROID_CHECKLIST("android-checklist", "检查Android本地缓存的list中的元素", CheckActionHandler.class),
    
    WAIT_FORCED("wait-forced", "强制等待", WaitActionHandler.class),
    WEBWAIT_IMPLICIT("web-implicit", "Web端隐式等待", WaitActionHandler.class),
    ANDROIDWAIT_IMPLICIT("android-implicit", "Android端隐式等待", WaitActionHandler.class),
    
    WEB_CLICK("web-click", "Web端点击操作", ClickActionHandler.class),
    ANDROID_CLICK("android-click", "Android端点击操作", ClickActionHandler.class),
    SELECT_CLICK("select-click", "Web端选项框点击操作", ClickActionHandler.class),
    CLICK_OK("click-ok", "点击浏览器弹出框的确定键", ClickActionHandler.class),
    CLICK_CANCEL("click-cancel", "点击浏览器弹出框的取消键", ClickActionHandler.class),
    CLICK_TEXT("click-text", "执行浏览器文本弹出框的文本输入", ClickActionHandler.class),
      
    START_RECORD("start-record", "开始录制视频",RecordActionHandler.class),
    END_RECORD("end-record", "结束录制视频",RecordActionHandler.class),
    PNLL_RECORD("pull-record", "上传录制的视频",RecordActionHandler.class),
    MOVE_RECORD("move-record", "移动录制的视频",RecordActionHandler.class),
    
    SLIDE("slide", "自定义滑动", SlideActionHandler.class),
    SLIDE_UP("slide-up", "向上滑动", SlideActionHandler.class),
    SLIDE_DOWN("slide-down", "向下滑动", SlideActionHandler.class),
    SLIDE_LEFT("slide-left", "向左滑动", SlideActionHandler.class),
    SLIDE_RIGHT("slide-right", "向右滑动", SlideActionHandler.class),
    
    GET_COOKIE("get-cookie", "获取cookie值", HttpRequestHandler.class),
    SEND_POST("send-post", "发送Post接口请求", HttpRequestHandler.class),
    
	DB_QUERYA("db-querya", "Oracle数据库查询", DBActionHandler.class),
	DB_QUERYW("db-queryw", "Oracle数据库查询", DBActionHandler.class),
    DB_UPDATE("db-update", "Oracle数据库更新", DBActionHandler.class),
    DB_DELETE("db-delete", "Oracle数据库删除", DBActionHandler.class),
    DB_INSERT("db-insert", "Oracle数据库插入", DBActionHandler.class),
	DB_PROCEDURE("db-procedure", "Oracle数据库存储过程", DBActionHandler.class),
	
	DB_QUERYMA("db-queryma", "MySql数据库查询", DBActionHandler.class),
	DB_QUERYMW("db-querymw", "MySql数据库查询", DBActionHandler.class),
    DB_UPDATEM("db-updatem", "MySql数据库更新", DBActionHandler.class),
    DB_DELETEM("db-deletem", "MySql数据库删除", DBActionHandler.class),
    DB_INSERTM("db-insertm", "MySql数据库插入", DBActionHandler.class),
	DB_PROCEDUREM("db-procedurem", "MySql数据库存储过程", DBActionHandler.class),
	
	DB_QUERYMSSHA("db-querymssha", "MySqlSSH数据库查询", DBActionHandler.class),
	DB_QUERYMSSHW("db-querymsshw", "MySqlSSH数据库查询", DBActionHandler.class),
    DB_UPDATEMSSH("db-updatemssh", "MySqlSSH数据库更新", DBActionHandler.class),
    DB_DELETEMSSH("db-deletemssh", "MySqlSSH数据库删除", DBActionHandler.class),
    DB_INSERTMSSH("db-insertmssh", "MySqlSSH数据库插入", DBActionHandler.class),
	DB_PROCEDUREMSSH("db-proceduremssh", "MySqlSSH数据库存储过程", DBActionHandler.class);
	
    private String key;

    private String desc;

    private Class<?> handler;

    private static Map<String,StepAction> map;

    static{
        map = new HashMap<String,StepAction>();
        for(StepAction action : StepAction.values()){
            map.put(action.key(), action);
        }
    }

    private StepAction(String key, String desc, Class<?> handler) {
        this(key, desc);
        this.handler = handler;
    }

    private StepAction(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public static StepAction action(String name){
        return map.get(name);
    }

    public String key(){
        return this.key;
    }

    public String desc(){
        return this.desc;
    }

    public Class<?> handler(){
        return this.handler;
    }
    
    public void run(TestStep step) throws Exception{
    	
    }
}
