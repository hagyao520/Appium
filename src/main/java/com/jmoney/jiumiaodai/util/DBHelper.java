package com.jmoney.jiumiaodai.util;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jmoney.jiumiaodai.util.ConfigUtil;
import com.jmoney.jiumiaodai.util.Constants;
import com.jmoney.jiumiaodai.util.DBHelper;

/**
 * 数据库操作类
 */
@SuppressWarnings("unused")
public class DBHelper {
    
    private static final Logger LOG = LoggerFactory.getLogger(DBHelper.class);
    
    private static final String DRIVER = ConfigUtil.getProperty("Oracle.jdbc.driver", Constants.CONFIG_JDBC);
   
    private static final String URL = ConfigUtil.getProperty("Oracle.jdbc.url", Constants.CONFIG_JDBC);
    
    private static final String USERNAME = ConfigUtil.getProperty("Oracle.jdbc.username", Constants.CONFIG_JDBC);
    
    private static final String PASSWORD = ConfigUtil.getProperty("Oracle.jdbc.password", Constants.CONFIG_JDBC);
    
    private static final String DRIVER1 = ConfigUtil.getProperty("MySql.jdbc.driver", Constants.CONFIG_JDBC);
    
    private static final String URL1 = ConfigUtil.getProperty("MySql.jdbc.url", Constants.CONFIG_JDBC);
    
    private static final String USERNAME1 = ConfigUtil.getProperty("MySql.jdbc.username", Constants.CONFIG_JDBC);
    
    private static final String PASSWORD1 = ConfigUtil.getProperty("MySql.jdbc.password", Constants.CONFIG_JDBC);
   
	private static final String SSH_User = ConfigUtil.getProperty("MySql.ssh.user", Constants.CONFIG_JDBC);
    
    private static final String SSH_Host = ConfigUtil.getProperty("MySql.ssh.host", Constants.CONFIG_JDBC);
    
    private static final int SSH_Port = 22;
    
    private static final String SSH_Password = ConfigUtil.getProperty("MySql.ssh.password", Constants.CONFIG_JDBC);
   
    private static final int SSH_Lport = 3306;
    
    private static final String SSH_Rhost = ConfigUtil.getProperty("MySql.ssh.rhost", Constants.CONFIG_JDBC);
    
    private static final int SSH_Rport = 3306;
    
    private static final String SSH_DRIVER = ConfigUtil.getProperty("MySql.ssh.driver", Constants.CONFIG_JDBC);
    
    private static final String SSH_URL = ConfigUtil.getProperty("MySql.ssh.url", Constants.CONFIG_JDBC);
    
    private static final String SSH_USERNAME = ConfigUtil.getProperty("MySql.ssh.username", Constants.CONFIG_JDBC);
    
    private static final String SSH_PASSWORD = ConfigUtil.getProperty("MySql.ssh.password1", Constants.CONFIG_JDBC);
   
    private static Connection connection;
 
    /**
     * 数据库插入-Oracle
     * @param sql
     * @return
     */
    public static int insert(String sql){
        return executeUpdate(sql, OpType.INSERT);
    }
    
    public static int insert1(String sql){
        return executeUpdate1(sql, OpType.INSERT);
    }
    
    public static int insert2(String sql){
        return executeUpdate2(sql, OpType.INSERT);
    }
    
    /**
     * 数据库删除-Oracle
     * @param sql
     * @return
     */
    public static int delete(String sql){
        return executeUpdate(sql, OpType.DELETE);
    }
    
    public static int delete1(String sql){
        return executeUpdate1(sql, OpType.DELETE);
    }
    
    public static int delete2(String sql){
        return executeUpdate2(sql, OpType.DELETE);
    }
    
    /**
     * 数据库修改-Oracle
     * @param sql
     * @return
     */
    public static int update(String sql){
        return executeUpdate(sql, OpType.UPDATE);
    }
    
    public static int update1(String sql){
        return executeUpdate1(sql, OpType.UPDATE);
    }
 
    public static int update2(String sql){
        return executeUpdate2(sql, OpType.UPDATE);
    }
    
    /**
     * 数据库查询-Oracle
     * @param sql
     * @return
     */
    public static List<Map<String, Object>> query(String sql){
        checkConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        LOG.info("Sql: " + sql);
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                int count = metaData.getColumnCount();
                Map<String, Object> rowMap = new HashMap<String, Object>();
                for(int i=1; i<=count; i++){    	
                    rowMap.put(metaData.getColumnLabel(i), rs.getObject(i));
//                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
//                        System.out.print("\t");
                    }
                }
//                System.out.println("");
                results.add(rowMap);
            }
            LOG.info("Count: " + results.size());
        } catch (SQLException e) {
            LOG.error("查询失败", e.fillInStackTrace());
        } finally {
            close(ps, rs);
        }
        return results;
    }

    public static List<Map<String, Object>> query1(String sql){
        checkConnection1();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        LOG.info("Sql: " + sql);
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                int count = metaData.getColumnCount();
                Map<String, Object> rowMap = new HashMap<String, Object>();
                for(int i=1; i<=count; i++){    	
                    rowMap.put(metaData.getColumnLabel(i), rs.getObject(i));
//                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
//                        System.out.print("\t");
                    }
                }
//                System.out.println("");
                results.add(rowMap);
            }
            LOG.info("Count: " + results.size());
        } catch (SQLException e) {
            LOG.error("查询失败", e.fillInStackTrace());
        } finally {
            close(ps, rs);
        }
        return results;
    }

    public static List<Map<String, Object>> query2(String sql){
        checkConnection2();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        LOG.info("Sql: " + sql);
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            while(rs.next()){
                int count = metaData.getColumnCount();
                Map<String, Object> rowMap = new HashMap<String, Object>();
                for(int i=1; i<=count; i++){    	
                    rowMap.put(metaData.getColumnLabel(i), rs.getObject(i));
//                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
//                        System.out.print("\t");
                    }
                }
//                System.out.println("");
                results.add(rowMap);
            }
            LOG.info("Count: " + results.size());
        } catch (SQLException e) {
            LOG.error("查询失败", e.fillInStackTrace());
        } finally {
            close(ps, rs);
        }
        return results;
    }
    
	/**
     * 指定SQL语句,执行查询操作,并打印结果
     * @param sql
     * @return
     */
    public static void Query(String SQL) {  
	  	checkConnection();
	  	PreparedStatement ps = null;
        ResultSet rs = null;
	        try {
	              ps = connection.prepareStatement(SQL);
	              rs = ps.executeQuery();
	              int col = rs.getMetaData().getColumnCount(); 
	              System.out.println("============================");
	              while (rs.next()) {
	                   for (int i = 1; i <= col; i++) {
	                        System.out.print(rs.getString(i) + "\t");
	                      if ((i == 2) && (rs.getString(i).length() < 8)) {
	                             System.out.print("\t");
	                      }
	                   }
	                System.out.println("");
	              }
	                System.out.println("============================");
	                close();
	  	    }
	        catch (SQLException e) {
	        	System.out.println("查询失败");
	            e.printStackTrace();
	        }
	}
    
    /**
     * 执行存储过程,带参数
     * @param procedure
     * @param params
     * @return
     */
    public static int procedure(String prc_name, Object... params) {
        checkConnection();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall(prc_name);
            if(params != null && params.length > 0){
                for(int i=0; i<params.length; i++){
                	//TODO set类型对应数据库,包含输入和输出
                    cs.setString(i+1, String.valueOf(params[i]));
                }
            }
            LOG.info("开始执行存储过程: "+ prc_name); 
            cs.execute();
            LOG.info("存储过程执行成功 "); 
        } catch (SQLException e) {
            LOG.error("存储过程["+prc_name+"]执行失败", e.fillInStackTrace());
        } finally {
            try {
                if(cs != null){ cs.close(); cs = null;}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return 0;   
    }
    
    public static int procedure1(String prc_name, Object... params) {
        checkConnection1();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall(prc_name);
            if(params != null && params.length > 0){
                for(int i=0; i<params.length; i++){
                	//TODO set类型对应数据库,包含输入和输出
                    cs.setString(i+1, String.valueOf(params[i]));
                }
            }
            LOG.info("开始执行存储过程: "+ prc_name); 
            cs.execute();
            LOG.info("存储过程执行成功 "); 
        } catch (SQLException e) {
            LOG.error("存储过程["+prc_name+"]执行失败", e.fillInStackTrace());
        } finally {
            try {
                if(cs != null){ cs.close(); cs = null;}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return 0;   
    }
    
    public static int procedure2(String prc_name, Object... params) {
        checkConnection2();
        CallableStatement cs = null;
        try {
            cs = connection.prepareCall(prc_name);
            if(params != null && params.length > 0){
                for(int i=0; i<params.length; i++){
                	//TODO set类型对应数据库,包含输入和输出
                    cs.setString(i+1, String.valueOf(params[i]));
                }
            }
            LOG.info("开始执行存储过程: "+ prc_name); 
            cs.execute();
            LOG.info("存储过程执行成功 "); 
        } catch (SQLException e) {
            LOG.error("存储过程["+prc_name+"]执行失败", e.fillInStackTrace());
        } finally {
            try {
                if(cs != null){ cs.close(); cs = null;}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return 0;   
    }
    
//    public static int procedure2(String prc_name, String params) {
//        checkConnection();
//        CallableStatement cs = null;
//        try {
//            cs = connection.prepareCall(prc_name);
//            //给存储过程的第一个参数设置值
//            cs.setBigDecimal(1, new BigDecimal(params));
//            //注册存储过程的第二个参数
//            cs.registerOutParameter(2,java.sql.Types.VARCHAR);
//            cs.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//		return 0;
//    }
//
//    public static void procedure2(String Process,String Value,String Result) {  
//  		checkConnection();
//  		CallableStatement cs;
//  		PreparedStatement ps;
//        try {
//        	cs = conn.prepareCall(Process);
//        	cs.setString(1, Value);
//        	System.out.println("开始执行存储过程: "+ Process); 
//        	cs.execute();
//        	System.out.println("存储过程执行成功, 生成数据如下: "); 
//        	ps = conn.prepareStatement(Result);
//        	rs = ps.executeQuery();
//        	int col = rs.getMetaData().getColumnCount();
//            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//            while (rs.next()) {
//                 for (int i = 1; i <= col; i++) {
//                      System.out.print(rs.getString(i) + "\t");
//                    if ((i == 2) && (rs.getString(i).length() < 8)) {
//                           System.out.print("\t");
//                    }
//                 }
//              System.out.println("");
//            }
//              System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//            CloseDatabase();
//            System.out.println("数据库连接已关闭！");
//        } 
//        catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("存储过程执行失败！");
//        }
//    }
    
    /**
     * 执行数据库操作
     * @param sql
     * @param type
     * @return
     */
    private static int executeUpdate(String sql, OpType type){
        checkConnection();
        PreparedStatement ps = null;
        LOG.info("Sql: " + sql);
        try {
            ps = connection.prepareStatement(sql);
            int result = ps.executeUpdate();
            LOG.info("Result: " + result);
            return result;
        } catch (SQLException e) {
            LOG.error(type.desc()+"失败", e.fillInStackTrace());
        } finally {
//            close(ps);
            close();
        }
        return -1;
    }
    
    private static int executeUpdate1(String sql, OpType type){
        checkConnection1();
        PreparedStatement ps = null;
        LOG.info("Sql: " + sql);
        try {
            ps = connection.prepareStatement(sql);
            int result = ps.executeUpdate();
            LOG.info("Result: " + result);
            return result;
        } catch (SQLException e) {
            LOG.error(type.desc()+"失败", e.fillInStackTrace());
        } finally {
//            close(ps);
            close();
        }
        return -1;
    }
    
    private static int executeUpdate2(String sql, OpType type){
        checkConnection2();
        PreparedStatement ps = null;
        LOG.info("Sql: " + sql);
        try {
            ps = connection.prepareStatement(sql);
            int result = ps.executeUpdate();
            LOG.info("Result: " + result);
            return result;
        } catch (SQLException e) {
            LOG.error(type.desc()+"失败", e.fillInStackTrace());
        } finally {
//            close(ps);
            close();
        }
        return -1;
    }
    
    private static void checkConnection(){
        try {
            if(connection == null || connection.isClosed())
                connect();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.fillInStackTrace());
        }
    }
    
    private static void checkConnection1(){
        try {
            if(connection == null || connection.isClosed())
                connect1();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.fillInStackTrace());
        }
    }
    
    private static void checkConnection2(){
        try {
            if(connection == null || connection.isClosed())
            	connectssh();
                connect2();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e.fillInStackTrace());
        }
    }
    
    /**
     * 连接数据库
     * @throws Exception
     */
    public static void connect() throws Exception{
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            LOG.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            if(e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        } 
    }
    
    public static void connect1() throws Exception{
        try {
            Class.forName(DRIVER1);
            connection = DriverManager.getConnection(URL1, USERNAME1, PASSWORD1);
            LOG.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            if(e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        } 
    }
    
    public static void connectssh() throws Exception{
    	try {  
            JSch jsch = new JSch();  
            Session session = jsch.getSession(SSH_User, SSH_Host, SSH_Port);  
            session.setPassword(SSH_Password);  
            System.out.println(SSH_User+SSH_Host+SSH_Port+SSH_Password);  
            session.setConfig("StrictHostKeyChecking", "no");  
            session.connect();  
            System.out.println("SSH服务器连接成功，版本信息为："+session.getServerVersion());//这里打印SSH服务器版本信息  
            int assinged_port = session.setPortForwardingL(SSH_Lport, SSH_Rhost,SSH_Rport);  
            System.out.println("端口映射成功：localhost:" + assinged_port + " -> " + SSH_Rhost + ":" + SSH_Rport);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
    }
    
    public static void connect2() throws Exception{
        try {
            Class.forName(SSH_DRIVER);
            connection = DriverManager.getConnection(SSH_URL,SSH_USERNAME,SSH_PASSWORD);
            LOG.info("数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();  
            String message = "数据库连接失败";
            if(e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        } 
    }
    
    /**
     * 关闭资源
     * @param ps
     */
    public static void close(PreparedStatement ps){
        if(ps == null)
            return ;
        close(ps, null);
    }
    
    /**
     * 关闭资源
     * @param ps
     */
    public static void close(PreparedStatement ps, ResultSet rs){
        try {
            if(rs != null){
                rs.close();
                rs = null;
            }
            if(ps != null){
                ps.close();
                ps = null;
            }
        } catch (SQLException e) {
            LOG.error("数据库异常", e.fillInStackTrace());
        }
    }
    
    /**
     * 关闭数据库
     */
    public static void close(){
        try {
            if(connection != null && !connection.isClosed())
                connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库关闭失败");
        }
    }
    
    /**
     * 数据库操作枚举
     */
    enum OpType {
        
        INSERT("插入"), 
        UPDATE("更新"), 
        DELETE("删除"), 
        QUERY("查询"), 
    	PROCEDURE("执行存储过程");
    	
        String desc;
        
        OpType(String desc){
            this.desc = desc;
        }
        
        String desc(){
            return desc;
        }
    } 
}
