package com.quickMail.application.threads;

import android.util.Log;

import com.quickMail.application.mails.MailRetriever;
import com.quickMail.application.model.QuickMailModel;

public class MailCountFetcherThread implements Runnable {
	
	MailRetriever retriever = new MailRetriever("asrafw@gmail.com",
			"xxxxxxxx", "imap.gmail.com", "imaps");
	
	private QuickMailModel mQuickMailModel;
	
	public MailCountFetcherThread(QuickMailModel mQuickMailModel)
	{
		this.mQuickMailModel = mQuickMailModel;
	}

	public void run() {
		Log.d(MailCountFetcherThread.class.getName(),"AllMailsChecker started");
		while (true) {
			try {				
				mQuickMailModel.setData(retriever.getAllMessagesCount(mQuickMailModel.getAccountInfo()));				
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				Log.e(MailCountFetcherThread.class.getName(), "Thread Interupted");
			}
		}
		
	}

}
