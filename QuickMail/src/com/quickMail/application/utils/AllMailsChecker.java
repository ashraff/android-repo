package com.quickMail.application.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.quickMail.application.mails.MailRetriever;

public class AllMailsChecker implements Runnable {
	
	MailRetriever retriever = new MailRetriever("asrafw@gmail.com",
			"Pirates11", "imap.gmail.com", "imaps");
	private Handler mHandler;
	
	public AllMailsChecker(Handler handler)
	{
	this.mHandler = handler;
	}

	public void run() {
		Log.d(AllMailsChecker.class.getName(),"AllMailsChecker started");
		while (true) {
			try {
				Message msg = new Message();
				String textTochange = " Mails["
						+ retriever.getMessagesCount() + "]";
				msg.obj = textTochange;
				msg.what = 1;
				mHandler.sendMessage(msg);

				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}
		}

	}

}
