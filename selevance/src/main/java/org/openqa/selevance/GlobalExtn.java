package org.openqa.selevance;

/**
 * @author Tanmay Sarkar
 */
public class GlobalExtn extends Agents{
	protected final static String PROPFILEPATH ="src\\test\\resources\\";
	protected final static String ADDONPATH ="src\\test\\resources\\addons\\";
	protected final static String PROPFILENAME = "config.properties";
	public static final int MAX_TIME_OUT = 600;
	public static final int MAX_TRY =15;
	public static final int MAX_ALERT_WAIT =5;
	public static final int MAX_RETRY =3;
	public static final boolean SLOW_CONNECTION = false;
	final String IEDRIVER = "IEDriverServer.exe";
	final String ChromeDRIVER = "chromedriver.exe";
	final String PHANTOMDRIVER ="phantomjs.exe";
	final String ffagent = "general.useragent.override";
	public static final int TIMEOUT =30;
}