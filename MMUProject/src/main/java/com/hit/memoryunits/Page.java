package com.hit.memoryunits;
import java.io.Serializable;
import java.util.Arrays;

public class Page<T> implements Serializable
{
	private static final long serialVersionUID = -616017516154926601L;
	
	private Long pageId;
	private T pageContent;

	Page(Long id, T content) 
	{
		setPageId(id);
		setContent(content);
	}
	
	public Long getPageId()
	{
		return pageId;		
	}
	
	public void setPageId(Long pageId)
	{
		this.pageId = pageId;
	}
	
	public T getContent()
	{
		return pageContent;		
	}
	
	public void setContent(T content)
	{
		pageContent = content;
	}
	
	@Override
	public int hashCode()
	{
		return pageId.intValue();		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean pageEquals = false;
		if(this.hashCode() == obj.hashCode())
		{
			pageEquals = true;
		}
		return pageEquals;		
	}
	
	@Override
	public String toString()
	{
		String msg = "Page id: " + pageId + "\t" + "Page content: " + Arrays.toString((byte[])pageContent);
		return msg;		
	}
}
