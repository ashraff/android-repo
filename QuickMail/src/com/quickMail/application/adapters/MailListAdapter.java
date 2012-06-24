package com.quickMail.application.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quickMail.application.R;


public class MailListAdapter extends BaseAdapter {
	
	private Activity activity;
    private  String[] data;
    private static LayoutInflater inflater=null;
 
    public MailListAdapter(Activity a,  String[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.length;
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.mailboxlist_item, null);
 
        TextView name = (TextView)vi.findViewById(R.id.mailBoxName);
       
        name.setText(data[position]);
        return vi;
    }
}
