package com.hit.processes;

import java.util.ArrayList;
import java.util.List;

public class ProcessCycle
{
	private List<Integer> pages;
	private int sleepMs;
	private List<byte[]> data;
	
	public ProcessCycle(List<Long> pages, int sleepMs, List<byte[]> data)
	{
		this.sleepMs = sleepMs;
		this.data = new ArrayList<byte[]>(data);
		this.pages = new ArrayList<Integer>();
		for(Long pgs : pages)
		{
			this.pages.add(pgs.intValue());
		}
	}
	
	public int getSleepMs()
	{
		return sleepMs;		
	}
	
	public void setSleepMs(int sleepMs)
	{		
		this.sleepMs = sleepMs;
	}
	
	public List<byte[]> getData()
	{
		return data;		
	}
	
	public void setData(List<byte[]> data)
	{
		this.data = data;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}
	
	@Override
	public String toString()
	{
		String msg = pages.toArray().toString() + sleepMs + data.toArray().toString();		
		return msg;		
	}
}
