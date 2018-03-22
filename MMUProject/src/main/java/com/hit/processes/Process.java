package com.hit.processes;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;
import com.hit.util.MMULogger;

public class Process implements Callable<Boolean>{

	private MemoryManagementUnit mmu;
	private int ProcessId;
	private ProcessCycles processCycles;

	public Process(int id, MemoryManagementUnit mmu, ProcessCycles processCycles)
	{
		ProcessId = id;
		this.mmu = mmu;
		this.processCycles = processCycles;
	}

	@Override
	public Boolean call() throws Exception
	{
		synchronized (mmu) {
			List<ProcessCycle> cycles = processCycles.getProcessCycles();
			List<Long> pagesId = null;
			
			for(ProcessCycle cycle : cycles)
			{				
				pagesId = new ArrayList<Long>();
				for(Integer id : cycle.getPages())
				{
					pagesId.add(id.longValue());
				}

				List<byte[]> datas = cycle.getData();
				Page<byte[]>[] pages = mmu.getPages(pagesId.toArray(new Long[pagesId.size()]));
				
				for(int i = 0; i < pages.length; i++)
				{
					pages[i].setContent(datas.get(i));
					MMULogger.getInstance().write(
							MessageFormat.format("GP:p{0} {1} {2}\n", 
									ProcessId, pages[i].getPageId(), Arrays.toString(pages[i].getContent())),
							Level.INFO);					
				}			
				
				try
				{
					Thread.sleep(cycle.getSleepMs());
				}
				catch (InterruptedException e) {
					MMULogger.getInstance().write("The tread sleep was interrupted: ", Level.SEVERE);
				}
			}		
		}
		
		return true;
	}

	public int getId()
	{
		return ProcessId;
	}

	public void setId(int id)
	{
		ProcessId = id;
	}
}
