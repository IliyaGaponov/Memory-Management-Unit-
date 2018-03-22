package com.hit.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.MRUAlgoCacheImpl;
import com.hit.algorithm.RandomAlgoCacheImpl;
import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.processes.Process;
import com.hit.processes.ProcessCycles;
import com.hit.processes.RunConfiguration;
import com.hit.util.MMULogger;

public class MMUModel extends Observable implements Model {

	private int numProcesses;
	private int ramCapacity; 
	private String[] command;
	private List<String> loggersList;
	IAlgoCache<Long, Long> algo = null;

	@Override
	public void start() 
	{
		ramCapacity = Integer.parseInt(command[1]);
		MMULogger.getInstance().write("RC:" + ramCapacity + "\n", Level.INFO);

		if(command[0].equalsIgnoreCase("LRU"))
		{
			algo = new LRUAlgoCacheImpl<Long, Long>(ramCapacity);
		}
		else if(command[0].equalsIgnoreCase("MRU"))
		{
			algo = new MRUAlgoCacheImpl<Long, Long>(ramCapacity);
		}
		else if(command[0].equalsIgnoreCase("Random"))
		{
			algo = new RandomAlgoCacheImpl<Long, Long>(ramCapacity);
		}

		MemoryManagementUnit mmu = new MemoryManagementUnit(ramCapacity, algo);
		RunConfiguration runConfig = null;
		runConfig = readConfigurationFile();
		List<ProcessCycles> processCycles = runConfig.getProcessesCycles();
		List<Process> processes = createProcesses(processCycles, mmu);
		MMULogger.getInstance().write("PN:" + processes.size() + "\n", Level.INFO);
		numProcesses = processes.size();
		runProcesses(processes);		
		mmu.shutDown();
		
		try 
		{
			readDataFromLog();
		} 
		catch (IOException e)
		{
			MMULogger.getInstance().write(e.getMessage(), Level.SEVERE);
		}
		
		setChanged();
		notifyObservers();
	}	
	
	public void readDataFromLog() throws IOException
	{
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		loggersList = new ArrayList<String>();
		
		try
		{
			fileReader = new FileReader(MMULogger.DEFAULT_FILE_NAME);
			bufferedReader = new BufferedReader(fileReader);
			
			String currentLine;
			while((currentLine = bufferedReader.readLine()) != null)
			{
				currentLine = currentLine.trim();
				if(!currentLine.isEmpty())
				{
					loggersList.add(currentLine);
				}
			}
		}
		catch (Exception e) {
			MMULogger.getInstance().write(e.getMessage(), Level.SEVERE);
		}
		finally
		{
			if(bufferedReader != null)
			{
				fileReader.close();
				bufferedReader.close();
			}
		}
	}

	public static List<Process> createProcesses(List<ProcessCycles> appliocationsScenarios, MemoryManagementUnit mmu)
	{
		List<Process> processesList = new ArrayList<Process>(appliocationsScenarios.size());
		int idProcess = 1;
		for(ProcessCycles pc : appliocationsScenarios)
		{
			processesList.add(new Process(idProcess, mmu, pc));
			idProcess++;
		}			
		
		return processesList;
	}

	public static void runProcesses(List<Process> applications)
	{
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>(); 
		for(Process process : applications)
		{
			futures.add(executor.submit(process));
		}
		
		try {
			executor.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			MMULogger.getInstance().write(e.getMessage(), Level.SEVERE);
		}

		executor.shutdown();	
	}

	public static RunConfiguration readConfigurationFile()
	{
		Gson gson = new Gson();
		JsonReader jreader = null;
		try {
			jreader = new JsonReader(new FileReader("src/main/resources/com/hit/config/Configuration.json"));
		}
		catch (FileNotFoundException e)
		{
			MMULogger.getInstance().write(e.getMessage(), Level.SEVERE);
		}

		RunConfiguration runConfig = gson.fromJson(jreader , RunConfiguration.class);
		return runConfig;
	}

	public void setConfiguration(List<String> configuration)
	{
		command = configuration.toArray(new String[configuration.size()]);
	}

	public List<String> getData() {
		return loggersList;
	}

	public void setData(List<String> data) {
		this.loggersList = data;
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
}
