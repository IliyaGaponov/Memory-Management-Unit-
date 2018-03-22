package com.hit.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ProcessesPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private MMUView mmuView;
	private JLabel processLabel;
	private JList<String> processesList;

	public ProcessesPanel(MMUView mmuView) 
	{
		String[] processesStr = new String[mmuView.getNumProcesses()];
		this.mmuView = mmuView;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		for(int i = 0; i < mmuView.getNumProcesses(); i++)
		{
			processesStr[i] = "Process " + (1+i);
		}

		setLayout(new BorderLayout());
		setBackground(Color.YELLOW);
		processLabel = new JLabel("Processes");
		processLabel.setFont(new Font(processLabel.getFont().getName(), processLabel.getFont().getStyle(), 16));
		processesList = new JList<String>(processesStr);
		processesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		processesList.setLayoutOrientation(JList.VERTICAL);
		processesList.setFont(new Font(processesList.getFont().getName(), processesList.getFont().getStyle(), 16));
		processesList.setSelectionInterval(0, processesStr.length - 1);
		processesList.setBackground(Color.YELLOW);
		processesList.setVisibleRowCount(7);
		
		add(processLabel, BorderLayout.NORTH);
		add(processesList, BorderLayout.WEST);
		add(new JScrollPane(processesList));
	}
	
	public JList<String> getProcessesList()
	{
		return processesList;
	}
}
