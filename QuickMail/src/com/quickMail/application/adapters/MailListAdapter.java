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
import com.quickMail.application.entities.AccountInfo;

public class MailListAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	private Activity activity;
	private List<AccountInfo> data;

	public MailListAdapter(Activity a, List<AccountInfo> accountInfoList) {
		activity = a;
		data = accountInfoList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {		
	
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.mailboxlist_item, null);

		TextView name = (TextView) vi.findViewById(R.id.mailBoxName);

		name.setText(data.get(position).getEmailId());
		return vi;
	}
	
	
}
