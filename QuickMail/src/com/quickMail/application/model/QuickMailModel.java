package com.quickMail.application.model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import android.util.Log;

import com.quickMail.application.handlers.QuickMailModelListener;

public class QuickMailModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2166152709439150431L;

	private static final String TAG = QuickMailModel.class.getName();

	private Hashtable<String, Integer> data = new Hashtable<String, Integer>();

	private QuickMailModelListener quickMailModelListener;

	public synchronized Hashtable<String, Integer> getData() {
		return data;
	}

	public void setData(Hashtable<String, Integer> pData) {
		// copy to a local variable inside the synchronized block
		// to avoid synchronization while calling homeModelChanged()
		QuickMailModelListener qml = null;
		synchronized (this) {
			int size = 0;
			for (Map.Entry<String, Integer> htEntries : this.data.entrySet()) {
				if (pData.containsKey(htEntries.getKey())
						&& pData.get(htEntries.getKey()).equals(
								htEntries.getValue())) {
					size++;
				}
			}

			if (size!=0 && this.data.size() == size) {
				Log.e(TAG, "Both are equal");
				return; // no change
			}
			this.data = pData;
			qml = this.quickMailModelListener;
		}

		// notify the listener here, not synchronized
		if (qml != null) {
			qml.quickMailModelChanged(this);
		}
	}

	public synchronized void setQuickMailModelListener(QuickMailModelListener l) {
		quickMailModelListener = l;

	}

}
