package com.quickMail.application.db;

import java.sql.SQLException;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.quickMail.application.R;
import com.quickMail.application.entities.AccountInfo;
import com.quickMail.application.entities.ServerInfo;

public class ORMDBAdapter extends OrmLiteSqliteOpenHelper {



	private static final String DATABASE_NAME = "QuickMail.db";
	
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 1;

	private static final String TAG = ORMDBAdapter.class.getName();

	//the DAO object we use to access the tables	
	private RuntimeExceptionDao<ServerInfo, Integer> serverInfoRuntimeDao = null;
	private RuntimeExceptionDao<AccountInfo, Integer> accountInfoRuntimeDao = null;
	

	public ORMDBAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our ServerInfo class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ServerInfo, Integer> getServerInfoDao() {
		if (serverInfoRuntimeDao == null) {
			serverInfoRuntimeDao = getRuntimeExceptionDao(ServerInfo.class);
		}
		return serverInfoRuntimeDao;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "onCreate");
			TableUtils.createTable(connectionSource, ServerInfo.class);
			TableUtils.createTable(connectionSource, AccountInfo.class);
		} catch (SQLException e) {
			Log.e(TAG, "Can't create database", e);
			throw new RuntimeException(e);
		}

		// here we try inserting data in the on-create as a test
		RuntimeExceptionDao<ServerInfo, Integer> dao = getServerInfoDao();
		long millis = System.currentTimeMillis();
		// create some entries in the onCreate
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setDomain("GMAIL.COM");
		serverInfo.setIncomingServer("imap.gmail.com");
		serverInfo.setOutgoingServer("imap.gmail.com");
		serverInfo.setIncomingServerPort(-1);
		serverInfo.setOutGoingServerPort(-1);
		serverInfo.setProvider("IMAPS");
		serverInfo.setIncomingSSLServer("");
		serverInfo.setOutgoingSSLServer("");
		serverInfo.setIncomingSSLServerPort(-1);
		serverInfo.setOutGoingSSLServerPort(-1);
		dao.create(serverInfo);
		
		Log.i(TAG,
				"created new entries in onCreate: " + millis);

	}
	
	
	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(TAG, "onUpgrade");
			TableUtils.dropTable(connectionSource, ServerInfo.class, true);
			TableUtils.dropTable(connectionSource, AccountInfo.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(TAG, "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public RuntimeExceptionDao<AccountInfo, Integer> getAccountInfoDao() {
		if (accountInfoRuntimeDao == null) {
			accountInfoRuntimeDao = getRuntimeExceptionDao(AccountInfo.class);
		}
		return accountInfoRuntimeDao;
	}

}

