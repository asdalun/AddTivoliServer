package com.zyk;

import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;


public class AddTivoliServer {
	/**
	 * 缓存日志
	 * https://www.cnblogs.com/logsharing/p/8042319.html
	 */
	private static Logger logger = Logger.getLogger(AddTivoliServer.class);
	/**
	 * 运行时间器
	 */
	private Timer runTimer;
	/**
	 * 当前运行状态  0：可以进行查询；1：查询添加操作中
	 */
	private short runState;
	/**
	 * Oracle数据库管理类
	 */
	private OracleManager om;
	/**
	 * Sybase数据库管理类
	 */
	private SybaseManager sm;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddTivoliServer as = new AddTivoliServer();
		as.start();
	}
	/**
	 * 运行入口
	 */
	public void start() {
		om = null;
		sm = null;
		ConfigManager cm = new ConfigManager();
		logger.info("进入主程序: " + cm.getInterval());
		this.runState = 0;
		runTimer = new Timer();
		runTimer.schedule(addTask, 0, cm.getInterval());
	}
	/**
	 * 设置运行状态
	 * @param s
	 */
	public void setRunState(short s) {
		this.runState = s;
	}
	/**
	 * 每次间隔执行一次任务
	 */
	TimerTask addTask = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (runState != 0) {
				return;
			}
			try {
				runState = 1;
				if (om == null) 
					om = new OracleManager();
				ResultSet rs = om.getTivoliData();
				if (rs == null) {
					runState = 0;
					return;
				}
				if (sm == null)
					sm = new SybaseManager();
				while (rs.next()) {
					if(!sm.insertTivoliData(rs)) {  //如果插入数据失败，直接返回，等待下次插入
						runState = 0;
						return;
					}
					else {
						//如果修改失败，直接返回，下次重新插入和修改
						if (!om.setTivoliData(rs.getString("I_ID"))) {
							runState = 0;
							return;
						}
					}
				}
				runState = 0;
			}
			catch (Exception ex) {
				logger.error("执行错误：" + ex.toString());
				runState = 0;
			}
		}
	};
}
