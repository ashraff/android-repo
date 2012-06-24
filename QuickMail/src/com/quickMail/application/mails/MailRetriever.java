package com.quickMail.application.mails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import android.util.Log;

import com.quickMail.application.entities.MailMessage;

public class MailRetriever {

	private static final String TAG = MailRetriever.class.getName();
	private String emailuser;
	private String emailpassword;
	private String emailserver;
	private String emailprovider;

	public MailRetriever(String emailuser, String emailpassword,
			String emailserver, String emailprovider) {
		this.emailuser = emailuser;
		this.emailpassword = emailpassword;
		this.emailserver = emailserver;
		this.emailprovider = emailprovider;
	}

	public int getMessagesCount() {
		Session session;
		Store store = null;
		Folder folder = null;
		Folder inboxfolder = null;

		Properties props = System.getProperties();
		props.setProperty("mail.pop3s.rsetbeforequit", "true");
		props.setProperty("mail.pop3.rsetbeforequit", "true");
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.imaps.auth.ntlm.disable", "true");

		session = Session.getInstance(props, null);
		int messagesCount = -1;

		try {
			store = session.getStore(emailprovider);
			store.connect(emailserver, emailuser, emailpassword);
			folder = store.getDefaultFolder();
			if (folder == null)
				throw new Exception("No default folder");
			inboxfolder = folder.getFolder("INBOX");
			if (inboxfolder == null)
				throw new Exception("No INBOX");
			inboxfolder.open(Folder.READ_ONLY);

			messagesCount = inboxfolder.getMessageCount();

			/*
			 * Message[] msgs=inboxfolder.getMessages();
			 * 
			 * FetchProfile fp=new FetchProfile(); fp.add("Subject");
			 * inboxfolder.fetch(msgs,fp);
			 * 
			 * for(int j=msgs.length-1;j>=0;j--) {
			 * if(msgs[j].getSubject().startsWith("MailPage:")) {
			 * setLatestMessage(msgs[j]); break; } }
			 */

			inboxfolder.close(false);
			store.close();

		} catch (NoSuchProviderException ex) {
			ex.printStackTrace();
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (store != null)
					store.close();
			} catch (MessagingException ex) {
				ex.printStackTrace();
			}
		}

		return messagesCount;
	}

	private boolean textIsHtml = false,saveAttachments = false;
	List<String> attachments = new ArrayList<String>();

	public List<MailMessage> getMessages() {
		Session session;
		Store store = null;
		Folder folder = null;
		Folder inboxfolder = null;

		Properties props = System.getProperties();
		props.setProperty("mail.pop3s.rsetbeforequit", "true");
		props.setProperty("mail.pop3.rsetbeforequit", "true");
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.imaps.auth.ntlm.disable", "true");

		session = Session.getInstance(props, null);
		int messagesCount = -1;
		List<MailMessage> mailMessageList = new ArrayList<MailMessage>();
		try {
			store = session.getStore(emailprovider);
			store.connect(emailserver, emailuser, emailpassword);
			folder = store.getDefaultFolder();
			if (folder == null)
				throw new Exception("No default folder");
			inboxfolder = folder.getFolder("INBOX");
			if (inboxfolder == null)
				throw new Exception("No INBOX");
			inboxfolder.open(Folder.READ_ONLY);

			messagesCount = inboxfolder.getMessageCount();

			if (messagesCount > 0) {
				Message[] msgs = inboxfolder.getMessages();

				FetchProfile fp = new FetchProfile();
				fp.add("Subject");
				inboxfolder.fetch(msgs, fp);

				for (int j = msgs.length - 1; j >= 0; j--) {
					MailMessage message = new MailMessage();
					
					message.setSubject(msgs[j].getSubject());
					textIsHtml = false;
					saveAttachments = true;
					attachments = new ArrayList<String>();
					String messageContent = getText(msgs[j]);
					getAttachments(msgs[j]);
					message.setMessageType(textIsHtml ? "text/html"
							: "text/plain");
					
					message.setAttachments(attachments);
					message.setMessage(messageContent);

					mailMessageList.add(message);
				}
			}

			inboxfolder.close(false);
			store.close();

		} catch (NoSuchProviderException ex) {
			Log.e(TAG, ex.getMessage(), ex);
			ex.printStackTrace();
		} catch (MessagingException ex) {
			Log.e(TAG, ex.getMessage(), ex);
			ex.printStackTrace();
		} catch (Exception ex) {
			Log.e(TAG, ex.getMessage(), ex);
			ex.printStackTrace();
		} finally {
			try {
				if (store != null)
					store.close();
			} catch (MessagingException ex) {
				Log.e(TAG, ex.getMessage(), ex);
				ex.printStackTrace();
			}
		}

		return mailMessageList;
	}
	
	
	private void getAttachments(Part p) throws MessagingException, IOException {
		if (saveAttachments) {
			if (p.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) p.getContent();
				for (int i = 0; i < mp.getCount(); i++) {

					String disposition = mp.getBodyPart(i).getDisposition();

					if (disposition != null
							&& (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition
									.equalsIgnoreCase(Part.INLINE))) {
						attachments.add(mp.getBodyPart(i).getFileName());
					}
				}
			}
		}
	
	}

	private String getText(Part p) throws MessagingException, IOException {
		
		if (p.isMimeType("text/html")) {
			String s = (String) p.getContent();
			textIsHtml = true;
			return s;
		} else if (p.isMimeType("text/plain")) {
			String s = (String) p.getContent();
			textIsHtml = false;
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		

		return null;
	}
}
