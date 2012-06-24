package com.quickMail.application.threads;

import java.util.Hashtable;

import android.util.Log;

import com.quickMail.application.mails.MailRetriever;
import com.quickMail.application.model.QuickMailModel;
import com.quickMail.application.utils.QuickMailConstants;

public class MailCountFetcherThread implements Runnable {
	
	MailRetriever retriever = new MailRetriever("asrafw@gmail.com",
			"Pirates11", "imap.gmail.com", "imaps");
	
	private QuickMailModel mQuickMailModel;
	
	public MailCountFetcherThread(QuickMailModel mQuickMailModel)
	{
		this.mQuickMailModel = mQuickMailModel;
	}

	public void run() {
		Log.d(MailCountFetcherThread.class.getName(),"AllMailsChecker started");
		while (true) {
			try {
				
				Hashtable<String, Integer> data = new Hashtable<String, Integer>(); 
				data.put(QuickMailConstants.DEFAULT_MAILBOX_GROUP_NAME, retriever.getMessagesCount());
				mQuickMailModel.setData(data);				
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}
		}

	}

}
