package com.zyk;

import java.io.File;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigManager {
	/**
	 * 缓存日志
	 */
	private static Logger logger = Logger.getLogger(ConfigManager.class);
	/**
	 * xml配置文件名称
	 */
	private String xmlFile = "aa.xml";
	/**
	 * 启动备份线程间隔秒数
	 */
	private long interval;
	
	ConfigManager() {
		interval = 5000;
		this.loadConfig();
	}
	/**
	 * 加载配置文件信息  aa.xml
	 */
	private void loadConfig() {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(xmlFile));
			Element dbinfo = document.getRootElement();
			Element intervalItem = dbinfo.element("Interval");
			interval = Long.parseLong(intervalItem.getText());
		}
		catch (Exception ex) {
			logger.error("加载配置文件错误：" + ex.toString());
		}
	}
	
	public long getInterval() {
		return this.interval;
	}
	
}

