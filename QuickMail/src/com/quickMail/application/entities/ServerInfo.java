package com.quickMail.application.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="TBL_MAIL_SERVER_INFO")
public class ServerInfo {
	
	
	@DatabaseField(columnName="DOMAIN",canBeNull=false,unique=true,id=true)	
	private String domain;
	
	@DatabaseField(columnName="INCOMING_SERVER",canBeNull=false)	
	private String incomingServer;
	
	@DatabaseField(columnName="OUTGOING_SERVER",canBeNull=false)
	private String outgoingServer;
	
	@DatabaseField(columnName="PROVIDER",canBeNull=false)	
	private String provider;
	
	@DatabaseField(columnName="INCOMING_SERVER_PORT")
	private int incomingServerPort;
	
	@DatabaseField(columnName="OUTGOING_SERVER_PORT")
	private int outGoingServerPort;		
	
	@DatabaseField(columnName="INCOMING_SSL_SERVER")	
	private String incomingSSLServer;
	
	@DatabaseField(columnName="OUTGOING_SSL_SERVER")
	private String outgoingSSLServer;		
	
	@DatabaseField(columnName="INCOMING_SSL_SERVER_PORT")
	private int incomingSSLServerPort;
	
	@DatabaseField(columnName="OUTGOING_SSL_SERVER_PORT")
	private int outGoingSSLServerPort;	
		
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIncomingSSLServer() {
		return incomingSSLServer;
	}

	public void setIncomingSSLServer(String incomingSSLServer) {
		this.incomingSSLServer = incomingSSLServer;
	}

	public String getOutgoingSSLServer() {
		return outgoingSSLServer;
	}

	public void setOutgoingSSLServer(String outgoingSSLServer) {
		this.outgoingSSLServer = outgoingSSLServer;
	}

	public int getIncomingSSLServerPort() {
		return incomingSSLServerPort;
	}

	public void setIncomingSSLServerPort(int incomingSSLServerPort) {
		this.incomingSSLServerPort = incomingSSLServerPort;
	}

	public int getOutGoingSSLServerPort() {
		return outGoingSSLServerPort;
	}

	public void setOutGoingSSLServerPort(int outGoingSSLServerPort) {
		this.outGoingSSLServerPort = outGoingSSLServerPort;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Domain = ").append(domain).append("\n");
		builder.append("incomingServer = ").append(incomingServer).append("\n");
		builder.append("outgoingServer = ").append(outgoingServer).append("\n");
		builder.append("provider = ").append(provider).append("\n");
		builder.append("incomingServerPort = ").append(incomingServerPort).append("\n");
		builder.append("outGoingServerPort = ").append(outGoingServerPort).append("\n");
		
		return builder.toString();
	}


}
