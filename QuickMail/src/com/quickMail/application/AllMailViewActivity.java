package com.quickMail.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


import com.quickMail.application.adapters.MailBoxListAdapter;
import com.quickMail.application.entities.MailMessage;
import com.quickMail.application.mails.MailRetriever;

public class AllMailViewActivity extends Activity {

	private class MyMessageItemClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			MailMessage message = mailList.get(position);

			Intent messageViewer = new Intent(view.getContext(),
					ViewMessageActivity.class);
			messageViewer.putExtra("MESSAGE", message);

			startActivity(messageViewer);

		}

	}

	MailBoxListAdapter mailBoxListAdapter;

	List<MailMessage> mailList = new ArrayList<MailMessage>();
	ListView mailListView;

	MailRetriever retriever = new MailRetriever("asrafw@gmail.com",
			"xxxxxx", "imap.gmail.com", "imaps");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.mailview);		
		
		mailListView = (ListView) findViewById(R.id.maillist);

		mailList = retriever.getMessages();
		mailBoxListAdapter = new MailBoxListAdapter(this, mailList);
		mailListView.setAdapter(mailBoxListAdapter);

		MyMessageItemClickListener listener = new MyMessageItemClickListener();

		mailListView.setOnItemClickListener(listener);

	}


}
