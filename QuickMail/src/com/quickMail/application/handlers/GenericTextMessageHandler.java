package com.quickMail.application.handlers;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class GenericTextMessageHandler extends Handler {

	private TextView txtView;
	private Activity currentActivity;

	public GenericTextMessageHandler(TextView txtView, Activity myActivity) {
		this.txtView = txtView;
		this.currentActivity = myActivity;
	}

	public GenericTextMessageHandler(Activity myActivity) {
		this.currentActivity = myActivity;
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:

			String text = (String) msg.obj;
			txtView.setText(text);

			break;
		case 2:

			showMessage((String) msg.obj);
			break;

		}
	}

	public void showMessage(String message) {
		Toast.makeText(currentActivity, "Message is " + message,
				Toast.LENGTH_LONG).show();
	}

}
