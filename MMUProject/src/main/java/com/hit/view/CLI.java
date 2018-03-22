package com.hit.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Scanner;

import com.hit.memoryunits.HardDisk;

public class CLI extends Observable implements Runnable, View{

	public static final String START = "start";
	public static final String STOP = "stop";
	public static final String LRU = "LRU";
	public static final String MRU = "MRU";
	public static final String Random = "Random";

	private InputStream input;
	private PrintStream output;
	private String[] configuration;

	public CLI(InputStream in, OutputStream out)
	{
		input = in;
		output = (PrintStream) out;
	}

	@Override
	public void run() 
	{
		String command;
		Scanner scan = new Scanner(input);
		while(true)
		{
			write("Enter Start/Stop command");		
			command = scan.nextLine();
			if(command.equalsIgnoreCase(START)) 
			{
				while(true)
				{
					write("Please enter required algorithm and RAM capacity from 1 to 999");
					configuration = scan.nextLine().split(" ");
					if(configuration.length == 2 && configuration[0].equalsIgnoreCase(LRU) || configuration[0].equalsIgnoreCase(MRU) || 
							configuration[0].equalsIgnoreCase(Random) && Integer.parseInt(configuration[1]) > 0
							&& Integer.parseInt(configuration[1]) < HardDisk.getInstance().getSize())
					{
						
						setChanged();
						notifyObservers(configuration);						
						break;
					}
					else
					{
						write("Not a valid command");
					}
				}
			}
			else if(command.equalsIgnoreCase(STOP))
			{
				write("Thank you");
				break;
			}
			else
			{
				write("Not a valid command");
			}
		}

		try 
		{
			input.close();
			scan.close();
			output.close();
		} catch (IOException e) {
			System.out.println("Scanner/Input/Output error" + e.getMessage());
		}
	}

	public void write(String string)
	{
		output.println(string);
	}
	
	public String[] getConfiguration()
	{
		return configuration;
	}

	@Override
	public void start() {}
}
