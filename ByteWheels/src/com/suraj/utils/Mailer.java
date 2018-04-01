package com.suraj.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.Logger;

/**
 * The Class responsible for all the Mailing activity
 * 
 * @author suraj.udayashankar
 *
 */
public class Mailer {
	static Logger log = Logger.getLogger(Mailer.class);

	/**
	 * This  method sends an email based on the email configurations given in the property file
	 * @param from
	 * @param password2
	 * @param to
	 * @param sub
	 * @param msg
	 */
	public static void send(String from, String password2, String to, String sub, String msg) {
		final String username = from;
		final String password = password2;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(sub);
			message.setText(msg);

			Transport.send(message);

			log.info("Mail sent to "+to+" with message : " + msg );

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}