package com.hit.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MMUView extends Observable implements View
{
	private JFrame frame;
	private ActivationPanel activationPanel;
	private ProcessesPanel processesPanel;
	private CounterPanel counterPanel;
	private RamTablePanel ramTablePanel;
	private int numProcesses;
	private int ramCapacity;
	private List<String> data;
	private String singleCommand = null;
	private List<String> pagesToHd;
	private List<String> processesToShow;
	private Integer lineIndex = 1;
	private Boolean firstClickPlay = true; 

	public MMUView()
	{
		pagesToHd = new LinkedList<String>();
		processesToShow = new LinkedList<String>();
	}

	public void setData(List<String> data)
	{
		this.data = data;		
	}

	@Override
	public void start() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {				
				createAndShowGui();			
			}
		});
	}

	public void createAndShowGui()
	{
		notifyController();
		frame = new JFrame("MMU Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.YELLOW);
		activationPanel = new ActivationPanel(this);
		ramTablePanel = new RamTablePanel(this);
		counterPanel = new CounterPanel();
		processesPanel = new ProcessesPanel(this);
		frame.getContentPane().setLayout(new GridLayout(2,2));
		frame.getContentPane().add(ramTablePanel);
		frame.getContentPane().add(counterPanel);
		frame.getContentPane().add(activationPanel);
		frame.getContentPane().add(processesPanel);

		counterPanel.setRamCapacityField(String.valueOf(ramCapacity));

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}

	public void playClicked()
	{
		if(firstClickPlay)
		{
			tagLinesOfSelectedProcesses();
			firstClickPlay = false;
		}

		if(lineIndex < data.size())
		{
			singleCommand = data.get(lineIndex);
			while(singleCommand.contains("$"))
			{
				if(lineIndex == data.size())
				{
					break;
				}

				fillGui();
			}

			activationPanel.setCommandField(singleCommand);
			fillGui();
		}
		else
		{
			activationPanel.getPlayButton().setEnabled(false);
			activationPanel.getPlayAllButton().setEnabled(false);
		}
	}

	public void playAllClicked()
	{
		activationPanel.getPlayButton().setEnabled(false);
		activationPanel.getPlayAllButton().setEnabled(false);
		while(lineIndex < data.size())
		{
			playClicked();
		}
	}

	public void resetClicked()
	{
		lineIndex = 2;
		ramTablePanel.clearTable();
		counterPanel.clearCounter();
		activationPanel.getCommandField().setText(null);
		pagesToHd.clear();
		singleCommand = null;
		firstClickPlay = true;
		processesPanel.getProcessesList().setEnabled(true);
		activationPanel.getPlayButton().setEnabled(true);
		activationPanel.getPlayAllButton().setEnabled(true);
		for(int i = 0; i < data.size(); i++)
		{
			if(data.get(i).contains("$"))
			{				
				data.set(i, data.get(i).substring(0, data.get(i).length() - 1));
			}
		}
	}

	public void fillGui()
	{
		if(singleCommand.contains("PF"))
		{
			counterPanel.updateCountPageFault();
		}
		else if (singleCommand.contains("PR"))
		{
			pagesToHd.add(singleCommand);				
			counterPanel.updateCountPageReplacement();
		}
		else if(singleCommand.contains("GP"))
		{
			ramTablePanel.updateTable(singleCommand);
		}

		lineIndex++;
		if(lineIndex < data.size())
		{
			singleCommand = data.get(lineIndex);
		}
	}

	public void tagLinesOfSelectedProcesses()
	{
		processesToShow = processesPanel.getProcessesList().getSelectedValuesList();
		processesPanel.getProcessesList().setEnabled(false);
		lineIndex = data.size() - 1;
		singleCommand = data.get(lineIndex);
		while(lineIndex > 1)
		{
			Boolean processNotSelected = true;
			if(singleCommand.contains("GP"))
			{
				String workingProcess = singleCommand.substring(4, 5);
				for(String processNum : processesToShow) ///Checking if process is selected
				{
					if(processNum.contains(workingProcess))
					{
						processNotSelected = false;
						break;
					}
				}
			}
			else if(singleCommand.contains("PF") || singleCommand.contains("PR"))
			{
				String AboveSingleCommand = data.get(lineIndex + 1);
				if(AboveSingleCommand.contains("$"))
				{
					processNotSelected = true;
				}
				else
				{
					processNotSelected = false;
				}
			}

			if(processNotSelected)
			{
				data.set(lineIndex, data.get(lineIndex) + "$");
			}

			lineIndex--;
			singleCommand = data.get(lineIndex);
		}

		lineIndex++;
	}

	public void notifyController()
	{
		setChanged();		
		notifyObservers();
	}

	public int getNumProcesses() {
		return numProcesses;
	}

	public void setNumProcesses(int numProcesses) {
		this.numProcesses = numProcesses;
	}


	public int getRamCapacity() {
		return ramCapacity;
	}


	public void setRamCapacity(int ramCapacity) {
		this.ramCapacity = ramCapacity;
	}

	public List<String> getPagesToHd()
	{
		return pagesToHd;
	}

	public void removePagesToHd(String page)
	{
		pagesToHd.remove(page);
	}
}
