package com.quickMail.application.threads;

import com.quickMail.application.model.QuickMailModel;

public class MailCountFetcherThread extends Thread {
	
	private final QuickMailModel quickMailModel;

	public MailCountFetcherThread(QuickMailModel quickMailModel) {
		this.quickMailModel = quickMailModel;
	}

	public void run() {
		try {
			quickMailModel.getClass();
			Thread.sleep(10);
		} catch (InterruptedException e) {
		} finally {

		}
	}
}
