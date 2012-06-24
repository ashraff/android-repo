package com.quickMail.application.entities;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;


@DatabaseTable(tableName="TBL_ACCOUNT_INFO")
public class AccountInfo {
	
	@DatabaseField(columnName="ACCOUNT_NAME")
	private String accountName;
	
	@DatabaseField(columnName="EMAIL_ID",canBeNull=false,unique=true,id=true)
	private String emailId;
	
	@DatabaseField(columnName="PASSWORD",canBeNull=false)
	private String password;
	
	@DatabaseField(columnName="ENABLED_IND")
	private int enabledInd;
	
	@DatabaseField(columnName="DEFAULT_IND")
	private int defaultInd;	
	
	@DatabaseField(columnName="MAIL_GRP")	
	private String mailBoxGrp;
	
	@DatabaseField(columnName="INCOMING_SERVER")	
	private String incomingServer;
	
	@DatabaseField(columnName="OUTGOING_SERVER")
	private String outgoingServer;
	
	@DatabaseField(columnName="PROVIDER",canBeNull=false)	
	private String provider;
	
	@DatabaseField(columnName="INCOMING_SERVER_PORT")
	private int incomingServerPort;
	
	@DatabaseField(columnName="OUTGOING_SERVER_PORT")
	private int outGoingServerPort;	
	
	@DatabaseField(columnName="USE_SSL")
	private int useSSL;		


	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnabledInd() {
		return enabledInd;
	}

	public void setEnabledInd(int enabledInd) {
		this.enabledInd = enabledInd;
	}

	public int getDefaultInd() {
		return defaultInd;
	}

	public void setDefaultInd(int defaultInd) {
		this.defaultInd = defaultInd;
	}

	public String getMailBoxGrp() {
		return mailBoxGrp;
	}

	public void setMailBoxGrp(String mailBoxGrp) {
		this.mailBoxGrp = mailBoxGrp;
	}

	public String getIncomingServer() {
		return incomingServer;
	}

	public void setIncomingServer(String incomingServer) {
		this.incomingServer = incomingServer;
	}

	public String getOutgoingServer() {
		return outgoingServer;
	}

	public void setOutgoingServer(String outgoingServer) {
		this.outgoingServer = outgoingServer;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public int getIncomingServerPort() {
		return incomingServerPort;
	}

	public void setIncomingServerPort(int incomingServerPort) {
		this.incomingServerPort = incomingServerPort;
	}

	public int getOutGoingServerPort() {
		return outGoingServerPort;
	}

	public void setOutGoingServerPort(int outGoingServerPort) {
		this.outGoingServerPort = outGoingServerPort;
	}

	public int getUseSSL() {
		return useSSL;
	}

	public void setUseSSL(int useSSL) {
		this.useSSL = useSSL;
	}
}
