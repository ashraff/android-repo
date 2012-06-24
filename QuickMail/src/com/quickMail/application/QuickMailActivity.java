package com.quickMail.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quickMail.application.adapters.MailListAdapter;
import com.quickMail.application.handlers.GenericTextMessageHandler;
import com.quickMail.application.utils.AllMailsChecker;

public class QuickMailActivity extends Activity {

	static final String[] MAILS = new String[] { "asrafw@gmail.com" };

	ListView homeListView;

	MailListAdapter mailListAdapter;	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.homeview);
		
		Log.d(QuickMailActivity.class.getName(),"Home Activity Started");
		
		final Handler mHandler = new GenericTextMessageHandler((Button)findViewById(R.id.allMailsButton),this);
		
		
		AllMailsChecker allMailChecker = new AllMailsChecker(mHandler);
		(new Thread(allMailChecker)).start();
		

		homeListView = (ListView) findViewById(R.id.list);
		mailListAdapter = new MailListAdapter(this, MAILS);
		homeListView.setAdapter(mailListAdapter);

		OnItemClickListener listener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String text = ((TextView) view.findViewById(R.id.mailBoxName))
						.getText().toString();
				Message msg = new Message();

				msg.obj = text;
				msg.what = 2;
				mHandler.sendMessage(msg);
			}

		};

		homeListView.setOnItemClickListener(listener);

	}

	public void Log(String message) {
		Toast.makeText(this, "Message is " + message, Toast.LENGTH_LONG).show();
	}

	public void openAllMails(View view) {
		Intent allMailViewer = new Intent(this, AllMailViewActivity.class);
		startActivity(allMailViewer);

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.homepage_options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.composemail:
			Toast.makeText(this, "You pressed the icon!", Toast.LENGTH_LONG)
					.show();
			break;
		case R.id.addaccount:
			Intent addAccountIntent = new Intent(this, AddAccountActivity.class);
			startActivity(addAccountIntent);
			break;
		case R.id.deleteaccount:
			Toast.makeText(this, "You pressed the icon and text!",
					Toast.LENGTH_LONG).show();
			break;
		}
		return true;
	}
	
	
	@Override
	public void onStop()
	{
		super.onStop();
		Log.d(QuickMailActivity.class.getName(),"Home Activity Stopped");
	}
	
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.d(QuickMailActivity.class.getName(),"Home Activity Destroyed");
	}

}