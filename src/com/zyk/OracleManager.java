package com.zyk;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class OracleManager extends DBManager{
	/**
	 * 缓存日志
	 */
	private static Logger logger = Logger.getLogger(OracleManager.class);
	/**
	 * 构造函数，初始化数据库连接
	 */
	public OracleManager() {
		super("Oracle", logger);
		super.setDriveName("oracle.jdbc.OracleDriver");
		super.setConnectString("jdbc:oracle:thin:@10.103.5.36:1521:orclzyk");
		super.setUser("zyk");
		super.setPassword("dbszyk");
	}
	/**
	 * 通过查询语句得到Tivoli数据
	 * @return
	 */
	public ResultSet getTivoliData() {
		String wl = "select I_ID, IDENTIFIER, NODE, SEVERITY, SUMMARY, FIRSTOCCURRENCE, LASTOCCURRENCE, BUSINESSNAME, " +
    			"BUSINESSID, DEVICETYPE, SUMMARY_ORI, IP, C_ZT, C_CODE, ALERTKEY, ITMSITTYPE from TIVOLIPOSTLIST " +
				"where C_ZT = '0'";
		return this.getData(wl);
	}
	/**
	 * 修改tivoli的数据，将已经添加过的信息id 的C_ZT 字段设为 1
	 * @param _id
	 * @return
	 */
	public boolean setTivoliData(String _id) {
		String wl = "update TIVOLIPOSTLIST set C_ZT = '1' where I_ID = " + _id;
		logger.info("修改数据：" + wl);
		return this.updateData(wl);
	}
}
