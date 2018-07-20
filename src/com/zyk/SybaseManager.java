package com.zyk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class SybaseManager {
	/**
	 * 数据库名称（类型）比如Oracle, SQLServer等
	 */
	private String DBName = "";
	/**
	 * 缓存日志
	 */
	private static Logger logger = Logger.getLogger(SybaseManager.class);
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
	
	public SybaseManager() {
		this.DBName = "Sybase";
		this.DriverName = "com.sybase.jdbc2.jdbc.SybDriver";
		this.ConnectString = "jdbc:sybase:Tds:10.101.10.142:4100/alerts?charset=cp936";
		this.User = "root";
		this.Password = "";
	}
	/**
	 * 连接数据库；返回连接器
	 */
	public Connection conncetToDB() {
		try {
			Class.forName(this.DriverName);
			Connection conn = DriverManager.getConnection(this.ConnectString,
	                        this.User, this.Password);
	        return conn;
		}
		catch (Exception ex) {
			logger.error("连接" + this.DBName + "失败：" + ex.toString());
			return null;
		}
	}
	/**
	 * 插入数据到Sybase
	 * @param rs  查询出的数据集
	 * @return    成功 true 失败 false
	 */
	public boolean insertTivoliData(ResultSet rs) {
		try {
			String wl = "insert into alerts.status(Severity, Node, Summary, Identifier, FirstOccurrence, LastOccurrence, BUSINESSNAME, BUSINESSID, "
					+ "DEVICETYPE, ITMSitType, Summary_ori, AlertKey, IP) " +
					" values(5, '" + rs.getString("NODE") + "', '" + rs.getString("SUMMARY") + "', '"+ rs.getString("IDENTIFIER") + "', getdate(), getdate(), '" +
					rs.getString("BUSINESSNAME") + "','" + rs.getString("BUSINESSID") + "', '" +
					rs.getString("DEVICETYPE") + "', '" + rs.getString("ITMSITTYPE") + "', '" + rs.getString("SUMMARY_ORI") + 
					"', '" + rs.getString("ALERTKEY") + "', '" + rs.getString("IP") + "')";
			
			Connection conn = conncetToDB();
			if (conn == null) {
				logger.error("连接" + this.DBName + "失败!");
				return false;
			}
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(wl);
			stmt.close();
			conn.close();
			logger.info("添加数据：" + wl);
			return true;
		}
		catch (Exception ex) {
			logger.error("插入数据错误：" + ex.toString());
			return false;
		}
	}
}
