package com.hit.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MMULogger {
	public final static String DEFAULT_FILE_NAME = "log.txt";
	private static MMULogger loger;
	private FileHandler handler;
	
	private MMULogger()
	{
		try {
			handler = new FileHandler(DEFAULT_FILE_NAME);
			handler.setFormatter(new OnlyMessageFormatter());
		} catch (SecurityException | IOException e) {
			System.err.println("Can't create file handler");
		}		
	}
	
	public static MMULogger getInstance()
	{
		if(loger == null)
		{
			loger = new MMULogger();
		}
		
		return loger;
	}
	
	public synchronized void write(String command, Level level)
	{
		handler.publish(new LogRecord(level, command));
	}
	
	public class OnlyMessageFormatter extends Formatter
	{
		public OnlyMessageFormatter() { super();}
		
		@Override
		public String format(final LogRecord record) 
		{
			return record.getMessage();
		}		
	}
}
