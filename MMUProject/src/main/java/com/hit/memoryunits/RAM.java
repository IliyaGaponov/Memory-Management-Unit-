package com.hit.memoryunits;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class RAM 
{
	private int capacity;
	private Map<Long,Page<byte[]>> ramPages;

	RAM(int initialCapacity)
	{
		ramPages = new LinkedHashMap<Long,Page<byte[]>>(capacity, 0.75f, true);
		setInitialCapacity(initialCapacity);
	}

	public void addPage(Page<byte[]> addPage) 
	{
		if(addPage != null && ramPages.size() < capacity)
		{
			ramPages.put(addPage.getPageId(), addPage);
		}
	}

	public void addPages(Page<byte[]>[] addPages) 
	{
		for(Page<byte[]> page : addPages)
		{
			if(ramPages.size() < capacity)
			{
				ramPages.put(page.getPageId(), page);
			}
		}
	}

	public Page<byte[]> getPage(Long pageId) 
	{
		Page<byte[]> page = null;
		if(ramPages.containsKey(pageId))
		{
			page = ramPages.get(pageId);
		}	
		
		return page;
	}

	public Map<Long,Page<byte[]>> getPages() 
	{
		return ramPages;		
	}

	@SuppressWarnings("unchecked")
	public Page<byte[]>[] getPages(Long[] pageIds) 
	{
		ArrayList<Page<byte[]>> pageArr = new ArrayList<Page<byte[]>>();
		for(Long id : pageIds)
		{
			if(ramPages.containsKey(id))
			{
				pageArr.add(ramPages.get(id));
			}
		}

		return pageArr.toArray(new Page[pageArr.size()]);			
	}

	public void removePage(Page<byte[]> removePage) 
	{
		if(removePage != null)
		{
			ramPages.remove(removePage.getPageId());
		}
	}

	public void removePages(Page<byte[]>[] removePages) 
	{
		for(Page<byte[]> p : removePages)
		{
			if(ramPages.containsKey(p.getPageId()))
			{
				ramPages.remove(p.getPageId());
			}
		}
	}

	public int 	getInitialCapacity() 
	{
		return capacity;		
	}

	public void setInitialCapacity(int initialCapacity) 
	{
		capacity = initialCapacity;
	}

	public void setPages(Map<Long,Page<byte[]>> pages) 
	{
		ramPages = pages;
	}

	public boolean isFull()
	{		
		return getPages().size() >= getInitialCapacity();		
	}
}
