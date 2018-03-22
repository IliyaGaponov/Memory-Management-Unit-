package com.hit.processes;

import java.util.ArrayList;
import java.util.List;

public class ProcessCycles 
{
	private List<ProcessCycle> processCycles;
	
	public ProcessCycles(List<ProcessCycle> processCycles) 
	{
		this.processCycles = new ArrayList<ProcessCycle>(processCycles);
	}
	
	public List<ProcessCycle> getProcessCycles()
	{
		return processCycles;		
	}
	
	public void setProcessCycles(List<ProcessCycle> processCycles)
	{
		for(ProcessCycle pc : processCycles)
		{
			this.processCycles.add(pc);
		}
	}
	
	@Override
	public String toString()
	{
		return processCycles.toArray().toString();
	}
}
