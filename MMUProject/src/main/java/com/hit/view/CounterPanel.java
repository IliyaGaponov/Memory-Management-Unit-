package com.hit.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CounterPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JLabel pageFaultLabel;
	private JLabel pageReplacementLabel;
	private JLabel ramCapacityLabel;
	private Integer counterPageFault = 0;
	private Integer counterPageReplacement = 0;
	private JTextField textFieldPFault;
	private JTextField textFieldPReplecement;
	private JTextField ramCapacityField;

	public CounterPanel() 
	{
		setLayout(new GridLayout(3,2));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		setBackground(Color.YELLOW);

		pageFaultLabel = new JLabel("Page fault amount                  ");
		pageFaultLabel.setFont(new Font(pageFaultLabel.getFont().getName(), 
				pageFaultLabel.getFont().getStyle(), 16));
		
		textFieldPFault = new JTextField(counterPageFault.toString());
		textFieldPFault.setEditable(false);
		textFieldPFault.setFont(new Font(textFieldPFault.getFont().getName(), 
				textFieldPFault.getFont().getStyle(), 16));	
		textFieldPFault.setColumns(2);

		pageReplacementLabel = new JLabel("Pagereplacement amount    ");
		pageReplacementLabel.setFont(new Font(pageReplacementLabel.getFont().getName(), 
				pageReplacementLabel.getFont().getStyle(), 16));
		
		textFieldPReplecement = new JTextField(counterPageReplacement.toString());
		textFieldPReplecement.setEditable(false);
		textFieldPReplecement.setFont(new Font(textFieldPReplecement.getFont().getName(), 
				textFieldPReplecement.getFont().getStyle(), 16));
		textFieldPReplecement.setColumns(2);
		
		ramCapacityLabel = new JLabel("RAM capacity:                         ");
		ramCapacityLabel.setFont(new Font(ramCapacityLabel.getFont().getName(), 
				ramCapacityLabel.getFont().getStyle(), 16));
		
		ramCapacityField = new JTextField();
		ramCapacityField.setEditable(false);
		ramCapacityField.setFont(new Font(ramCapacityField.getFont().getName(), 
				ramCapacityField.getFont().getStyle(), 16));
		ramCapacityField.setColumns(2);
		
		JPanel wrapper1 = new JPanel();
		wrapper1.setPreferredSize(new Dimension(150, 20));
		wrapper1.setLayout(new FlowLayout(0, 0, FlowLayout.LEADING));
		wrapper1.add(pageFaultLabel);
		wrapper1.add(textFieldPFault);
		wrapper1.setBackground(Color.YELLOW);
		JPanel wrapper2 = new JPanel();
		wrapper2.setPreferredSize(new Dimension(150, 20));
		wrapper2.setLayout(new FlowLayout(0, 0, FlowLayout.LEADING));
		wrapper2.add( pageReplacementLabel );
		wrapper2.add( textFieldPReplecement );
		wrapper2.setBackground(Color.YELLOW);
		JPanel wrapper3 = new JPanel();
		wrapper3.setPreferredSize(new Dimension(150, 20));
		wrapper3.setLayout(new FlowLayout(0, 0, FlowLayout.LEADING));
		wrapper3.add(ramCapacityLabel);
		wrapper3.add(ramCapacityField);
		wrapper3.setBackground(Color.YELLOW);
		add(wrapper3);
		add(wrapper1);
		add(wrapper2);
	}

	public void updateCountPageFault()
	{
		textFieldPFault.setText((++counterPageFault).toString());
	}

	public void updateCountPageReplacement()
	{
		textFieldPReplecement.setText((++counterPageReplacement).toString());
	}
	
	public void clearCounter()
	{
		counterPageFault = 0;
		counterPageReplacement = 0;
		textFieldPFault.setText(counterPageFault.toString());
		textFieldPReplecement.setText(counterPageReplacement.toString());
	}

	public Integer getCounterPageFault() {
		return counterPageFault;
	}

	public void setCounterPageFault(Integer counterPageFault) {
		this.counterPageFault = counterPageFault;
	}

	public Integer getCounterPageReplacement() {
		return counterPageReplacement;
	}

	public void setCounterPageReplacement(Integer counterPageReplacement) {
		this.counterPageReplacement = counterPageReplacement;
	}

	public JTextField getRamCapacityField() {
		return ramCapacityField;
	}

	public void setRamCapacityField(String command) {
		ramCapacityField.setText(command);
	}
}
