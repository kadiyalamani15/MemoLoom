package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SetCard {
	
	private String name;
	private String user;
	private LocalDateTime timestamp;
	
	

    private LocalDateTime parseTimestamp(String timestampStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(timestampStr, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing timestamp: " + e.getMessage());
            return null; // or handle more gracefully
        }
    }
    
    
    public SetCard(String name, String user, String timestampStr) {
    	this.name = name;
    	this.user = user;
    	this.timestamp = parseTimestamp(timestampStr);
    }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public LocalDateTime getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
    
    
    
}
