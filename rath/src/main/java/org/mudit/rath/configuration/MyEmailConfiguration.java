package org.mudit.rath.configuration;

public final class MyEmailConfiguration {
	private static String sender;
	

	private static String host;

	private MyEmailConfiguration() {
		// TODO Auto-generated constructor stub
		sender =  "magrawal2015@my.fit.edu";
		host = "localhost";
	}
	public static String getSender() {
		if (sender==null){
			sender = "magrawal2015@my.fit.edu";
		}
		return sender;
	}

	public static void setSender(String sender) {
		MyEmailConfiguration.sender = sender;
	}
	
	public static String getHost() {
		if (host==null){
			host = "localhost";
		}
		return host;
	}
	
	public static void setHost(String host) {
		MyEmailConfiguration.host = host;
	}
	
}
