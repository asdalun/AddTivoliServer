package com.zyk;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class SybaseManager extends DBManager {
	/**
	 * 缓存日志
	 */
	private static Logger logger = Logger.getLogger(SybaseManager.class);
	
	public SybaseManager() {
		super("Sybase", logger);
		super.setDriveName("com.sybase.jdbc2.jdbc.SybDriver");
		super.setConnectString("jdbc:sybase:Tds:10.101.10.142:4100/alerts?charset=cp936");
		super.setUser("root");
		super.setPassword("");
	}

	public boolean insertTivoliData(ResultSet rs) {
		try {
			String wl = "insert into alerts.status(Severity, Node, Summary, Identifier, FirstOccurrence, LastOccurrence, BUSINESSNAME, BUSINESSID, "
					+ "DEVICETYPE, ITMSitType, Summary_ori, AlertKey, IP) " +
					" values(5, '" + rs.getString("NODE") + "', '" + rs.getString("SUMMARY") + "', '"+ rs.getString("IDENTIFIER") + "', getdate(), getdate(), '" +
					rs.getString("BUSINESSNAME") + "','" + rs.getString("BUSINESSID") + "', '" +
					rs.getString("DEVICETYPE") + "', '" + rs.getString("ITMSITTYPE") + "', '" + rs.getString("SUMMARY_ORI") + 
					"', '" + rs.getString("ALERTKEY") + "', '" + rs.getString("IP") + "')";
			
			if (this.updateData(wl)) {
				logger.info("插入数据：" + wl);
				return true;
			}
			else {
				logger.error("插入数据错误：" + wl);
				return false;
			}
		}
		catch (Exception ex) {
			logger.error("插入数据错误：" + ex.toString());
			return false;
		}
	}
}
