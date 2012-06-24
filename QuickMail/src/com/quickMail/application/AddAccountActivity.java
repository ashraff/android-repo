package com.quickMail.application;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.quickMail.application.db.ORMDBAdapter;
import com.quickMail.application.entities.AccountInfo;
import com.quickMail.application.entities.ServerInfo;
import com.quickMail.application.utils.QuickMailConstants;

public class AddAccountActivity extends Activity {

	private static final String TAG = AddAccountActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_account);

		final CheckBox showPassword = (CheckBox) findViewById(R.id.chk_showpassword);

		final EditText password = (EditText) this
				.findViewById(R.id.txt_password);

		showPassword.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (showPassword.isChecked())
					password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				else
					password.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
		});

	}

	public void onNextClick(View view) {

		ORMDBAdapter dbAdapter = new ORMDBAdapter(this);

		try {

			Resources res = this.getResources();

			EditText emailAddress = (EditText) this
					.findViewById(R.id.txt_email);

			EditText password = (EditText) this.findViewById(R.id.txt_password);

			this.findViewById(R.id.chk_defaultmail);

			CheckBox defaultMail = (CheckBox) findViewById(R.id.chk_defaultmail);

			if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
					emailAddress.getText()).matches()) {
				emailAddress.setError(res
						.getString(R.string.msg_invalidemailladdress));
				return;
			}

			if (password.getText().equals("")
					&& password.getText().length() <= 4) {
				password.setError(res.getString(R.string.msg_invalidpassword));
				return;
			}

			String[] emailSplit = emailAddress.getText().toString()
					.toUpperCase().split("@");

			Log.d(TAG, "Email Name " + emailSplit[0] + ": Domain Name "
					+ emailSplit[1]);

			ServerInfo serverInfo = new ServerInfo();
			serverInfo.setDomain(emailSplit[1]);

			RuntimeExceptionDao<ServerInfo, Integer> dao = dbAdapter
					.getServerInfoDao();
			serverInfo = dao.queryForSameId(serverInfo);

			Log.d(TAG, "ServerInfo retrieved is " + serverInfo.toString());

			AccountInfo accountInfo = new AccountInfo();
			accountInfo.setAccountName(emailSplit[0]);
			if (defaultMail.isChecked())
				accountInfo.setDefaultInd(1);
			else
				accountInfo.setDefaultInd(0);
			accountInfo.setEmailId(emailAddress.getText().toString());
			accountInfo.setEnabledInd(1);
			accountInfo.setIncomingServer(serverInfo.getIncomingServer());
			accountInfo.setIncomingServerPort(serverInfo
					.getIncomingServerPort());
			accountInfo
					.setMailBoxGrp(QuickMailConstants.DEFAULT_MAILBOX_GROUP_NAME);
			accountInfo.setOutgoingServer(serverInfo.getOutgoingServer());
			accountInfo.setOutGoingServerPort(serverInfo
					.getOutGoingServerPort());
			accountInfo.setPassword(password.getText().toString());
			accountInfo.setProvider(serverInfo.getProvider());
			accountInfo.setUseSSL(0);

			RuntimeExceptionDao<AccountInfo, Integer> acctInfoDao = dbAdapter
					.getAccountInfoDao();

			AccountInfo existingAccount = acctInfoDao
					.queryForSameId(accountInfo);
			if (existingAccount != null
					&& existingAccount.getEmailId().equals(
							accountInfo.getEmailId())) {
				emailAddress.setError(res
						.getString(R.string.msg_existingaccount));
				return;
			}
			else 
			{
				acctInfoDao.create(accountInfo);
			}
		} finally {
			dbAdapter.close();
		}

	}

}
