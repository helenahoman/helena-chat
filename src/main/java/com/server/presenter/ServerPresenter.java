package com.server.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.server.connector.ServerConnector;
import com.server.view.ServerView;

/**
 * 
 * @author Helena 
 *
 */
public class ServerPresenter {

	/** 
	 * Data that are displayed in view.
	 * 
	 * @author Helena
	 *
	 */
	public static interface Display {

		public JButton getStart();
		public JButton getStop();
		public JTextField getPortField();
		public JLabel getStatusLabel();
		
	}
	
	private ServerView view;
	private ServerConnector serverConnector = new ServerConnector();
	
	public ServerPresenter(ServerView view) {
		super();
		this.view = view;

		bind();
	}

	private void bind() {
		
		// Start button pressed.
		view.getStart().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					view.getStop().setEnabled(true);
					view.getStart().setEnabled(false);
					view.getStatusLabel().setText("Server started");

					serverConnector.start(Integer.parseInt(view.getPortField().getText()));

				} catch (NumberFormatException ex) {
					
					view.getStop().setEnabled(false);
					view.getStart().setEnabled(true);
					view.getStatusLabel().setText("Validation error: Port must be number!");
				}
				
			}
		});

		// Stop button pressed.
		view.getStop().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				view.getStop().setEnabled(false);
				view.getStart().setEnabled(true);
				view.getStatusLabel().setText("Server stopped");
				
				serverConnector.stop();
				
			}
		});
		
		// Entering new port.
		view.getPortField().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				view.getStatusLabel().setText("");
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
	}

}
