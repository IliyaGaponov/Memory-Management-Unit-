package com.hit.processes;

import java.util.ArrayList;
import java.util.List;

public class RunConfiguration 
{
	private List<ProcessCycles> processesCycles;
	
	public RunConfiguration(List<ProcessCycles> processesCycles) 
	{
		this.processesCycles = new ArrayList<ProcessCycles>(processesCycles);
	}
	
	public List<ProcessCycles> getProcessesCycles()
	{
		return processesCycles;		
	}
	
	public void setProcessesCycles(List<ProcessCycles> processesCycles)
	{
		for(ProcessCycles pc : processesCycles)
		{
			this.processesCycles.add(pc);
		}
	}
	
	@Override
	public String toString()
	{
		return processesCycles.toArray().toString();		
	}
}
