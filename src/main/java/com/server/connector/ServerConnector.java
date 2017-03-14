package com.server.connector;

import java.util.ArrayList;

import com.chat.communication.CommunicationChannel;
import com.chat.communication.IncomingCommunication.CommunicationListener;
import com.chat.communication.IncomingNetworkCommunication;
import com.chat.communication.MessageListener;
import com.chat.log.ConsoleLogger;
import com.chat.server.Server;
import com.chat.server.ServerConnectionThread;
import com.chat.server.ServerImpl;

/**
 * 
 * @author Helena
 *
 */
public class ServerConnector {
	
	private ConsoleLogger logger = new ConsoleLogger("server");
	private Server server;
	
	/**
	 * Start the server.
	 * @param port
	 */
	public void start(int port) {
		
		IncomingNetworkCommunication incomingNetworkCommunication = new IncomingNetworkCommunication(port, logger);
		MessageListener messageListener = new MessageListener() {
			
			@Override
			public void onMessageReceived(String message) {
				logger.log("Reading line from client: " + message);
			}
		};
		
		ArrayList<ServerConnectionThread> serverConnectionThreads = new ArrayList<ServerConnectionThread>();
		
		server = new ServerImpl(logger, incomingNetworkCommunication, serverConnectionThreads);

		logger.log("Waiting for clients on port " + port + "...");
		
		new ServerRunning(incomingNetworkCommunication, serverConnectionThreads, messageListener).start();
	}
	
	/**
	 * Stop the server.
	 */
	public void stop() {
		
		if (server == null) {
			logger.log("Exception stopping server that is null.");
		}
		
		server.stop();
		
	}
	
	/**
	 * Start in thread.
	 * @author Helena
	 *
	 */
	private class ServerRunning extends Thread {
		
		private IncomingNetworkCommunication incomingNetworkCommunication;
		private ArrayList<ServerConnectionThread> serverConnectionThreads;
		private MessageListener messageListener;
		
		public ServerRunning(IncomingNetworkCommunication incomingNetworkCommunication,
							 ArrayList<ServerConnectionThread> serverConnectionThreads, 
							 MessageListener messageListener) {
			
			this.incomingNetworkCommunication = incomingNetworkCommunication;
			this.serverConnectionThreads = serverConnectionThreads;
			this.messageListener = messageListener;
		}

		public void run() {
			
			while (server.isRunning()) {

				incomingNetworkCommunication.listen(new CommunicationListener() {
					
					@Override
					public void onCommunicationChannelOpened(CommunicationChannel clientCommunicationCahnnel) {

						// start new thread after accepting connection with client
						ServerConnectionThread serverConnectionThread = new ServerConnectionThread(logger,
																								   clientCommunicationCahnnel,
																								   serverConnectionThreads,
																								   messageListener);
						server.start(serverConnectionThread);

						
					}
				});
			}
		}
	}

}
