package com.quickMail.application.model;

import java.io.Serializable;
import java.util.Hashtable;

import com.quickMail.application.handlers.QuickMailModelListener;

public class QuickMailModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2166152709439150431L;

	private Hashtable<String, Integer> data = new Hashtable<String, Integer>();


	private QuickMailModelListener quickMailModelListener;

	public synchronized Hashtable<String, Integer> getData() {
		return data;
	}

	public synchronized void setHomeModelListener(QuickMailModelListener l) {
		quickMailModelListener = l;
		
	}

	public void setData(Hashtable<String, Integer> data) {
		// copy to a local variable inside the synchronized block
		// to avoid synchronization while calling homeModelChanged()
		QuickMailModelListener qml = null;
		synchronized (this) {
			if (this.data == data) {				
				return; // no change
			}
			this.data = data;
			qml = this.quickMailModelListener;
		}

		// notify the listener here, not synchronized
		if (qml != null) {
			qml.quickMailModelChanged(this);
		}
	}

}
