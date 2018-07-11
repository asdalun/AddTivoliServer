package com.zyk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class DBManager {
	/**
	 * 数据库名称（类型）比如Oracle, SQLServer等
	 */
	private String DBName = "";
	/**
	 * 缓存日志
	 */
	private static Logger logger;
	/**
	 * 数据库连接
	 */
	Connection conn;
	/**
	 * 执行声明
	 */
	Statement stmt;
	/**
	 * 驱动名称
	 */
	String DriverName = "";
	/**
	 * 数据库连接串
	 */
	String ConnectString = "";
	/**
	 * 数据库用户名
	 */
	String User = "";
	/**
	 * 数据库密码
	 */
	String Password = "";
	/**
	 * 构造函数，初始化数据库连接
	 * @param dbname
	 */
	DBManager(String dbname, Logger _logger) {
		this.DBName = dbname;
		logger = _logger;
		this.conn = null;
		this.stmt = null;
	}
	/**
	 * 连接数据库
	 */
	public boolean conncetToDB() {
		try {
			if(this.conn != null) {
				if (!this.conn.isClosed()) {
			        if (stmt == null) {
			        	stmt = conn.createStatement();
			        }
			        else if (stmt.isClosed()) {
			        	stmt = null;
			        	stmt = conn.createStatement();
			        }
					return true;
				}
				this.conn.close();
				this.conn = null;
			}
			Class.forName(this.DriverName);
	        conn = DriverManager.getConnection(this.ConnectString,
	                        this.User, this.Password);
	        if (stmt == null) {
	        	stmt = conn.createStatement();
	        }
	        else if (stmt.isClosed()) {
	        	stmt = null;
	        	stmt = conn.createStatement();
	        }
	        return true;
		}
		catch (Exception ex) {
			logger.error("连接" + this.DBName + "失败：" + ex.toString());
			return false;
		}
	}
	/**
	 * 查询数据函数，带入查询语句，返回数据集
	 * @param wl
	 * @return
	 */
	public ResultSet getData(String wl) {
		try {
			if (!this.conncetToDB()) {
				return null;
			}
			//Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(wl);
			//stmt.close();
			return rs;
		}
		catch (Exception ex) {
			logger.error("查询" + this.DBName + "失败：" + ex.toString());
			return null;
		}
	}
	/**
	 * 执行一条 添加 或是 修改 的SQL语句  成功返回true；失败返回false
	 * @param wl
	 * @return
	 */
	public boolean updateData(String wl) {
		try {
			if (!this.conncetToDB()) {
				return false;
			}
			//Statement stmt = conn.createStatement();
			stmt.executeUpdate(wl);
			//stmt.close();
			return true;
		}
		catch (Exception ex) {
			logger.error("操作" + this.DBName + "失败：" + ex.toString());
			return false;
		}
	}
	/**
	 * 设置数据库驱动名称
	 * @param drivename
	 */
	public void setDriveName(String drivename) {
		this.DriverName = drivename;
	}
	/**
	 * 设置数据库连接串
	 * @param con_s
	 */
	public void setConnectString(String con_s) {
		this.ConnectString = con_s;
	}
	/**
	 * 设置数据库用户名
	 * @param user
	 */
	public void setUser(String user) {
		this.User = user;
	}
	/**
	 * 设置数据库密码
	 * @param pwd
	 */
	public void setPassword(String pwd) {
		this.Password = pwd;
	}
}
