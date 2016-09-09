package org.openqa.selevance;

/**
 * @author <a href='mailto:me@tanmaysarkar.com'>Tanmay Sarkar</a> *
 */
public class GlobalExtn extends Agents{
	protected final static String PROPFILEPATH ="src\\test\\resources\\";
	protected final static String ADDONPATH ="src\\test\\resources\\addons\\";
	protected final static String PROPFILENAME = "config.properties";
	public static final int MAX_TIME_OUT = 600;
	public static final int MAX_TRY =15;
	final String IEDRIVER = "IEDriverServer.exe";
	final String ChromeDRIVER = "chromedriver.exe";
	final String ffagent = "general.useragent.override";
}