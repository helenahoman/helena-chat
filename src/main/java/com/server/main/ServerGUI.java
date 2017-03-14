package com.server.main;

import com.server.presenter.ServerPresenter;
import com.server.view.ServerView;

/**
 * 
 * @author Helena
 *
 */
public class ServerGUI {
	
	public static void main(String[] args) {
		
		new ServerPresenter(new ServerView());
		
	}
}
