package com.hit.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class RamTableRenderer implements TableCellRenderer
{
	private static final TableCellRenderer renderer = new DefaultTableCellRenderer();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) 
	{
		Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(value != null)
		{
			if(value.toString().contains("$"))
			{
				component.setForeground(Color.WHITE);
			}
			else
			{
				component.setForeground(Color.BLACK);
				component.setFont(new Font(component.getFont().getName(), 
						component.getFont().getStyle(), 16));
			}
		}
		
		return component;
	}
}
