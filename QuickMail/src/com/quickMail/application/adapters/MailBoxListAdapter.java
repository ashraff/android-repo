package com.quickMail.application.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quickMail.application.R;
import com.quickMail.application.entities.MailMessage;

public class MailBoxListAdapter extends BaseAdapter {

	private Activity activity;
	private List<MailMessage> data;
	private static LayoutInflater inflater = null;

	public MailBoxListAdapter(Activity quickMailActivity,
			List<MailMessage> mailList) {
		activity = quickMailActivity;
		data = mailList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		  return data.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		 return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		 View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.mailmessagelist_item, null);
	 
	        TextView name = (TextView)vi.findViewById(R.id.mailMessage);
	       
	        name.setText(data.get(position).getSubject());
	        return vi;
	}

}
