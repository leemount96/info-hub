package main;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


/**
 * Generates an email sent daily with a summary of stock moves and important news stories
 */
public class EmailGenerator {
	private final String USER = "infohub100@gmail.com";
	private final String HOST = "smtp.gmail.com";
	private final String PSWD = "chiphimit";
	private final String PORT = "465";
	private final Properties properties;
	private final List<String> emailAddresses;
	
	/**
	 * Create a new EmailGenerator object
	 */
	public EmailGenerator(){
		this.properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);
		properties.setProperty("mail.smtp.user", USER);
		properties.setProperty("mail.smtp.port", PORT);
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.socketFactory.port", PORT);
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		
		this.emailAddresses = new ArrayList<>();
	}
	
	/**
	 * Send an email to all users in emailAddresses
	 * @param content of email to send
	 */
	public void sendMail(String content){
		Session session = Session.getDefaultInstance(properties,
				new Authenticator(){
					protected PasswordAuthentication getPasswordAuthentication(){
						return new PasswordAuthentication(USER, PSWD);
					}
		});
		
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USER));
			for (String address : emailAddresses){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
			}
			message.setSubject("InfoHub Daily Mail " + getFormattedDate());
			message.setText(content);
			Transport transport = session.getTransport("smtps");
			transport.connect(HOST, Integer.parseInt(PORT), USER, PSWD);
			Transport.send(message, message.getAllRecipients());
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Add an email address to emailAddresses
	 * @param email address to add
	 */
	public void addEmail(String email){
		if(!this.emailAddresses.contains(email)){
			this.emailAddresses.add(email);
		}
	}
	
	/**
	 * Remove email address from emailAddresses
	 * @param email to remove
	 */
	public void removeEmail(String email){
		if(this.emailAddresses.contains(email)){
			this.emailAddresses.remove(email);
		}
	}
	
	/*
	 * private method to get formatted date string
	 */
	private String getFormattedDate(){
		String result = "";
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		result += month;
		result += "/";
		result += c.get(Calendar.DAY_OF_MONTH);
		result += "/";
		result += c.get(Calendar.YEAR);
		return result;
	}
}
