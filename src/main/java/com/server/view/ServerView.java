package com.server.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.server.presenter.ServerPresenter;

/**
 * 
 * @author Helena
 *
 */
public class ServerView extends JFrame implements ServerPresenter.Display {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1411143547036656702L;

	/** Button for start.*/
	private JButton start = new JButton("Start");
	
	/** Button for stop.*/
	private JButton stop = new JButton("Stop");

	/** Label that tells user to enter port.*/
	private JLabel portLabel = new JLabel("Port: ", SwingConstants.RIGHT);

	/** Field for entering port.*/
	private JTextField portField = new JTextField(4);

	/** Label that tells the status of server.*/
	private JLabel statusLabel = new JLabel();
	
	/** Control panel for all items, with background.*/
	private JPanel controlPanel;
	
	public ServerView() throws HeadlessException {
		
		super("Chat server");
		
		controlPanel = new JPanel(new GridLayout(2, 1));
		JPanel portPanel = new JPanel(new GridLayout(1, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		portPanel.add(portLabel);
		portPanel.add(portField);
		controlPanel.add(portPanel);
		
		start.setEnabled(true);
		stop.setEnabled(false);
		
		buttonPanel.add(start);
		buttonPanel.add(stop);
		controlPanel.add(buttonPanel);

		add(controlPanel, BorderLayout.NORTH);
		add(statusLabel, BorderLayout.SOUTH);
		
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	@Override
	public JButton getStart() {
		return start;
	}

	@Override
	public JButton getStop() {
		return stop;
	}

	@Override
	public JTextField getPortField() {
		return portField;
	}

	@Override
	public JLabel getStatusLabel() {
		return statusLabel;
	}

}
