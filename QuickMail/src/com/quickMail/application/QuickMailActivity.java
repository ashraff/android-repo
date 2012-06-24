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
import com.quickMail.application.handlers.QuickMailModelListener;
import com.quickMail.application.model.QuickMailModel;
import com.quickMail.application.threads.MailCountFetcherThread;
import com.quickMail.application.utils.QuickMailConstants;

public class QuickMailActivity extends Activity implements
		QuickMailModelListener {

	private static final String QUICK_MAIL_MODEL_KEY = "com.quickMail.application.model.QuickMailModel";

	static final String[] MAILS = new String[] { "asrafw@gmail.com" };

	// background threads use this Handler to post messages to
	// the main application thread
	private final Handler mHandler = new Handler();

	// this data model knows when a thread is fetching data
	private QuickMailModel mQuickMailModel;

	// post this to the Handler when the background thread completes
	private final Runnable mUpdateDisplayRunnable = new Runnable() {
		public void run() {
			updateDisplay();
		}
	};

	ListView homeListView;

	MailListAdapter mailListAdapter;

	public void Log(String message) {
		Toast.makeText(this, "Message is " + message, Toast.LENGTH_LONG).show();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.homeview);

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(QUICK_MAIL_MODEL_KEY)) {
				mQuickMailModel = (QuickMailModel) savedInstanceState
						.getSerializable(QUICK_MAIL_MODEL_KEY);
			}
		}
		if (mQuickMailModel == null) {
			// the first time in, create a new model
			mQuickMailModel = new QuickMailModel();
		}

		MailCountFetcherThread allMailChecker = new MailCountFetcherThread(mQuickMailModel);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.homepage_options_menu, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(QuickMailActivity.class.getName(), "Home Activity Destroyed");
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
	public void onStop() {
		super.onStop();
		Log.d(QuickMailActivity.class.getName(), "Home Activity Stopped");
	}

	public void openAllMails(View view) {
		Intent allMailViewer = new Intent(this, AllMailViewActivity.class);
		startActivity(allMailViewer);

	}

	public void quickMailModelChanged(QuickMailModel hm) {
		mHandler.post(mUpdateDisplayRunnable);

	}

	private void updateDisplay() {
		Button allMailsButton = (Button) findViewById(R.id.allMailsButton);
		allMailsButton.setText("Mails[" + mQuickMailModel.getData().get(
				QuickMailConstants.DEFAULT_MAILBOX_GROUP_NAME)
				+ "]");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// detach from the model
		mQuickMailModel.setQuickMailModelListener(null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// attach to the model
		mQuickMailModel.setQuickMailModelListener(this);

		// synchronize the display, in case the thread completed
		// while this activity was not visible. For example, if
		// a phone call occurred while the thread was running.
		updateDisplay();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(QUICK_MAIL_MODEL_KEY, mQuickMailModel);
	}

}