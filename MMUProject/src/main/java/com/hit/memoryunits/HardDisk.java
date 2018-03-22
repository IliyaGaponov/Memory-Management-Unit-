package com.hit.memoryunits;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import com.hit.util.MMULogger;

public class HardDisk 
{
	// Constants =================
	private final int _SIZE = 1000; 
	private final String DEFAULT_FILE_NAME = "HardDiskFile";
	//============================

	private static HardDisk hd; 
	private Map<Long,Page<byte[]>> hardDiskMap;

	private HardDisk() 
	{
		try 
		{
			hardDiskMap = new HashMap<Long, Page<byte[]>>(_SIZE);
			File file = new File(DEFAULT_FILE_NAME);
			if(file.exists())
			{
				loadDataToMap();
			}
			else
			{
				for(Long i = 1L; i <= _SIZE; i++)
				{
					hardDiskMap.put(i, new Page<byte[]>(i, null));
				}

				loadDataToHd();
			}
		} 
		catch (IOException | ClassNotFoundException e) {
			MMULogger.getInstance().write("OutputStream/InputStream Error " + e.getMessage(), Level.SEVERE);
			e.printStackTrace();
		}
	}

	public static HardDisk getInstance()
	{
		if(hd == null)
		{
			hd = new HardDisk();
		}

		return hd;		
	}

	@SuppressWarnings("unchecked")
	public void loadDataToMap() throws IOException, ClassNotFoundException
	{
		FileInputStream fileInput = new FileInputStream(DEFAULT_FILE_NAME);
		ObjectInputStream hdInputFile = new ObjectInputStream(fileInput);
		hardDiskMap = (HashMap<Long, Page<byte[]>>) hdInputFile.readObject();
		fileInput.close();
		hdInputFile.close();
	}

	public void loadDataToHd() throws IOException
	{
		FileOutputStream hdFile = new FileOutputStream(DEFAULT_FILE_NAME);
		ObjectOutputStream writeData = new ObjectOutputStream(hdFile);
		writeData.writeObject(hardDiskMap);
		hdFile.close();
		writeData.close();
	}	

	public Page<byte[]> pageFromHd(Long pageId)
	{
		Page<byte[]> pageToRam = null;
		if(hardDiskMap.containsKey(pageId))
		{
			pageToRam = hardDiskMap.get(pageId);
			hardDiskMap.remove(pageId);
			try 
			{
				if(_SIZE >= hardDiskMap.size())
				{
					loadDataToHd();
				}
			} 
			catch (IOException e) 
			{
				MMULogger.getInstance().write("OutputStream Error " + e.getMessage(), Level.SEVERE);
			}
		}

		return pageToRam;
	}

	public Page<byte[]> pageFault(Long pageId)      
	{		
		Page<byte[]> pageToRam = null;
		pageToRam = pageFromHd(pageId);
		return pageToRam;		
	}

	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId)    
	{			
		Page<byte[]> pageToRam = null;
		if(moveToHdPage != null)
		{
			hardDiskMap.put(moveToHdPage.getPageId(), moveToHdPage);	
		}

		pageToRam = pageFromHd(moveToRamId);
		return pageToRam;	
	}

	public Map<Long,Page<byte[]>> getHardDiskMap()
	{
		return hardDiskMap;		
	}

	public void setHardDiskMap(Map<Long,Page<byte[]>> map)
	{
		try
		{
			hardDiskMap.putAll(map);
			loadDataToHd();
		}
		catch (Exception e) 
		{
			MMULogger.getInstance().write(e.getMessage(), Level.SEVERE);
		}
	}

	public int getSize()
	{
		return _SIZE;
	}
}
