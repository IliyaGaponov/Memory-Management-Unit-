package com.hit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RamTablePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTable ramTable;
	private MMUView mmuView;
	private int currColumnIndex = 0;
	private int sizeOfDataInPage = 5;
	private JScrollPane scroll;
	private RamTableRenderer ramTableRenderer;

	public RamTablePanel(MMUView mmuView) 
	{
		ramTableRenderer = new RamTableRenderer();
		this.mmuView = mmuView; 
		setBackground(Color.YELLOW);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout());

		DefaultTableModel model = new DefaultTableModel(6, mmuView.getRamCapacity());
		ramTable = new JTable(model);
		if(mmuView.getRamCapacity() > 5)
		{
			ramTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			setPreferredSize(new Dimension(400, 200));
		}
		else
		{
			setPreferredSize(new Dimension((int) ramTable.getSize().getWidth(), 140));
			ramTable.setAutoResizeMode(WIDTH);
		}
		
		ramTable.setRowHeight(0, 32);
		for(int i = 1; i <= sizeOfDataInPage; i++)
		{
			ramTable.setRowHeight(i, 23);
		}
		
		ramTable.setEnabled(false);
		scroll = new JScrollPane(ramTable);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));
		scroll.setBackground(Color.YELLOW);
		add(scroll);
		ramTable.setDefaultRenderer(Object.class, ramTableRenderer);
	}

	public void updateTable(String pageData)
	{
		Boolean processSelected = !pageData.contains("$");
		Boolean alreadyInTable = false;
		String pageId = pageData.substring(pageData.indexOf(" ") + 1, pageData.indexOf("[") - 1);
		String[] data = pageData.substring(pageData.indexOf("[") + 1, pageData.indexOf("]")).split(", ");
		for(int i = 0; i < mmuView.getRamCapacity(); i++)
		{		
			String pageInTable = (String) ramTable.getValueAt(0, i);
			if(pageInTable != null)
			{
				pageInTable = removeDollar(pageInTable);
				if(pageInTable.substring(5).equals(pageId))
				{
					alreadyInTable = true;
					addToTable(pageId, data, i, processSelected);
					break;
				}
			}
		}

		if(alreadyInTable == false)
		{
			if(mmuView.getRamCapacity() > currColumnIndex)
			{
				addToTable(pageId, data, currColumnIndex, processSelected);
				currColumnIndex++;
			}
			else
			{
				List<String> pagesToHd = mmuView.getPagesToHd();
				for(String page : pagesToHd)
				{
					page = removeDollar(page);
					if((page.substring(page.indexOf("MTR") + 4).equals(pageId)))
					{
						String idToHd = page.substring(page.indexOf("H") + 1 ,page.indexOf("MTR")).trim();
						for(int i = 0; i < mmuView.getRamCapacity(); i++)
						{
							String pageInTable = (String) ramTable.getValueAt(0, i);
							pageInTable = removeDollar(pageInTable);

							if(pageInTable.substring(5).equals(idToHd))
							{
								addToTable(pageId, data, i, processSelected);								
								break;
							}
						}

						mmuView.removePagesToHd(idToHd);
						break;
					}
				}
			}
		}
	}

	public void addToTable(String pageId, String[] data, int column, Boolean processSelected)
	{
		if(processSelected)
		{
			ramTable.setValueAt("Page " + pageId, 0, column);
			for(int j = 0; j < sizeOfDataInPage; j++)
			{
				ramTable.setValueAt(data[j], j + 1, column);
			}
		}
		else
		{
			ramTable.setValueAt("Page " + pageId + "$", 0, column);
			for(int j = 0; j < sizeOfDataInPage; j++)
			{
				ramTable.setValueAt(data[j] + "$", j + 1, column);
			}
		}
	}

	public void clearTable()
	{
		for(int i = 0; i < mmuView.getRamCapacity(); i++)
		{
			for(int j = 0; j < sizeOfDataInPage + 1; j++)
			{
				ramTable.setValueAt(null, j, i);
			}
		}

		currColumnIndex = 0;
	}
	
	public String removeDollar(String str)
	{		
		if(str.contains("$"))
		{
			str = str.substring(0, str.indexOf("$"));
		} 
		
		return str;
	}
}
