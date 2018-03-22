package com.hit.controller;

import java.util.Arrays;
import java.util.Observable;

import com.hit.model.MMUModel;
import com.hit.model.Model;
import com.hit.view.CLI;
import com.hit.view.MMUView;
import com.hit.view.View;

public class MMUController extends Object implements Controller{
	
	private Model model;
	private View view;

	public MMUController(Model model, View view) 
	{
		this.model = model;
		this.view = view;
	}

	public void update(Observable o, Object arg)
	{
		if(o instanceof MMUView)
		{			
			((MMUView)view).setData(((MMUModel)model).getData());
		}
		else if(o instanceof CLI)
		{
			String[] commands = (String[]) arg;
			((MMUModel)model).setConfiguration(Arrays.asList(commands));
			((MMUModel)model).start();
		}
		else if(o instanceof MMUModel)
		{		
			((MMUView)view).setNumProcesses(((MMUModel) o).getNumProcesses());
			((MMUView)view).setRamCapacity(((MMUModel) o).getRamCapacity());
			((MMUView)view).start();
			
		}
	}
}
