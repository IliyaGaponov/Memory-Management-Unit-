package com.hit.memoryunits;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;

import com.hit.algorithm.IAlgoCache;
import com.hit.util.MMULogger;

public class MemoryManagementUnit 
{
	private IAlgoCache<Long,Long> algo;
	private RAM mainMemory;

	public MemoryManagementUnit(int ramCapacity, IAlgoCache<Long,Long> algo)
	{
		mainMemory = new RAM(ramCapacity);
		setAlgo(algo);
	}

	@SuppressWarnings("unchecked")
	public Page<byte[]>[] getPages(Long[] pageIds) throws IOException
	{	
		ArrayList<Page<byte[]>> pagesArr = new ArrayList<Page<byte[]>>();
		ArrayList<Long> pgsNotInRam = new ArrayList<Long>();
		ArrayList<Long> pgsInRam = new ArrayList<Long>();
		Page<byte[]>[] pgsFromRam = null;
		for(Long id : pageIds)
		{
			if(algo.getElement(id) == null)
			{
				pgsNotInRam.add(id);
			}	
			else
			{
				pgsInRam.add(id);
			}
		}

		if(!pgsInRam.isEmpty())
		{
			Long[] idArray = pgsInRam.toArray(new Long[pgsInRam.size()]);	
			pgsFromRam = mainMemory.getPages(idArray);
			for(Page<byte[]> page : pgsFromRam)
			{
				pagesArr.add(page);
			}
		}

		if(!pgsNotInRam.isEmpty())
		{
			Long[] idArray = pgsNotInRam.toArray(new Long[pgsNotInRam.size()]);			
			for(Long id : idArray)
			{
				Page<byte[]> pageFromHd = null;
				if(!mainMemory.isFull())
				{
					pageFromHd = HardDisk.getInstance().pageFault(id);
					mainMemory.addPage(pageFromHd);
					if(pageFromHd != null)
					{
						algo.putElement(pageFromHd.getPageId(), pageFromHd.getPageId());
					}
					
					pagesArr.add(pageFromHd);
					MMULogger.getInstance().write(MessageFormat.format("PF:{0}\n", pageFromHd.getPageId()), Level.INFO);
				}
				else
				{
					Long removedPageId = algo.putElement(id, id); //delete some page from page table and add current page
					Page<byte[]> pageToHd = mainMemory.getPage(removedPageId);
					mainMemory.removePage(pageToHd);						
					pageFromHd = HardDisk.getInstance().pageReplacement(pageToHd, id);					
					mainMemory.addPage(pageFromHd);
					pagesArr.add(pageFromHd);
					MMULogger.getInstance().write(MessageFormat.format("PR:MTH {0} MTR {1}\n", pageToHd.getPageId(), id), Level.INFO);
				}
			}					
		}

		return pagesArr.toArray(new Page[pagesArr.size()]);	
	}

	public RAM getRam() 
	{
		return mainMemory;		
	}

	public void setAlgo(IAlgoCache<Long,Long> algo)
	{
		this.algo = algo;
	}
	public void setRam(RAM ram) 
	{
		mainMemory = ram;
	}

	public void shutDown()
	{
		HardDisk.getInstance().setHardDiskMap(mainMemory.getPages());
		mainMemory.getPages().clear();
	}
}
