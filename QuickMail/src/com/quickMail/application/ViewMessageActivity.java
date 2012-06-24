package com.quickMail.application;

import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.quickMail.application.entities.MailMessage;

public class ViewMessageActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewmessage);
		MailMessage message = (MailMessage) getIntent().getSerializableExtra("MESSAGE");;
	
		TextView tv01 = (TextView) this.findViewById(R.id.dummy01);
		
		tv01.setText(message.getMessageType());
		
		TextView tv02 = (TextView) this.findViewById(R.id.dummy02);
		
		for(String attachment : message.getAttachments())
		{
			tv02.setText(tv02.getText() + " \n" + attachment);
		}
		
		WebView myWebView = (WebView) findViewById(R.id.web_messageview);
		//myWebView.loadUrl("http://www.google.com");
		
		Log.d("ViewMessageActivity",message.getMessageType());

		myWebView.loadData(URLEncoder.encode(message.getMessage()).replaceAll("\\+"," "), "text/html", "utf-8");

	}

}
