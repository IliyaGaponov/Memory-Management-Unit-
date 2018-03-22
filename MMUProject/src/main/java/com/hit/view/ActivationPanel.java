package com.hit.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ActivationPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel buttonsPanel;
	private JPanel commandPanel;
	private JButton play;
	private	JButton playAll;
	private JButton reset;
	private JTextField commandField;
	private JLabel commandLabel;
	private MMUView mmuView;	
	
	public ActivationPanel(MMUView mmuView)
	{
		this.mmuView = mmuView;
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Color.YELLOW);
		setLayout(new GridLayout(2,1));

		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
		play = new JButton("PLAY");
		playAll = new JButton("PLAY ALL");		
		reset = new JButton("RESET");
		buttonsPanel.add(play);
		buttonsPanel.add(playAll);
		buttonsPanel.add(reset);
		buttonsPanel.setBackground(Color.YELLOW);
		add(buttonsPanel);
		play.addActionListener(this);
		playAll.addActionListener(this);
		reset.addActionListener(this);
		
		commandPanel = new JPanel();
		commandPanel.setLayout(new FlowLayout());
		commandPanel.setBackground(Color.YELLOW);
		commandLabel = new JLabel("Command: ");
		commandLabel.setFont(new Font(commandLabel.getFont().getName(), 
				commandLabel.getFont().getStyle(), 16));
		
		commandField = new JTextField();
		commandField.setEditable(false);
		commandField.setFont(new Font(commandField.getFont().getName(), 
				commandField.getFont().getStyle(), 16));
		commandField.setColumns(17);
		commandPanel.add(commandLabel);
		commandPanel.add(commandField);
		commandPanel.setPreferredSize(new Dimension(130, 20));
		commandPanel.setBorder(BorderFactory.createEmptyBorder(19, 20, 0, 0));
		commandPanel.setLayout(new FlowLayout(0, 0, FlowLayout.LEADING));
		add(commandPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == play)
		{
			mmuView.playClicked();
		}
		else if(e.getSource() == playAll)
		{
			mmuView.playAllClicked();
		}
		else if(e.getSource() == reset)
		{
			mmuView.resetClicked();
		}		
	}
	
	public void setCommandField(String command) {
		commandField.setText(command);
	}
	
	public JTextField getCommandField() {
		return commandField;
	}
	
	public JButton getPlayButton(){
		return play;
	}
	
	public JButton getPlayAllButton(){
		return playAll;
	}
}
