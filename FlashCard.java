package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FlashCard {



	    // Constructor, getters, and setters
	  
	private String question;
	private String answer;
	private String example;
	private String creator;
	private Boolean visited;
	private String setName;
	private LocalDateTime timestamp;
	@Override
	

    
	public String toString() {
	    return "Question: " + question + ", Answer: " + answer;
	}

	// Constructor including all fields
	public FlashCard(String question, String answer, String creator, String setName) {
		this.question = question;
		this.answer = answer;
//        this.example = example;
		this.creator = creator;
		this.visited = false;
		this.setName = setName;
//        this.timestamp = parseTimestamp(timestampStr);
	}

    public String getQuestion1() {
        return question;
    }

	// Method to parse the timestamp from a string
//    private LocalDateTime parseTimestamp(String timestampStr) {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            return LocalDateTime.parse(timestampStr, formatter);
//        } catch (DateTimeParseException e) {
//            System.err.println("Error parsing timestamp: " + e.getMessage());
//            return null; // or handle more gracefully
//        }
//    }

	public FlashCard(String[] values) {
		// TODO Auto-generated constructor stub
	}

	// Getter and setter methods for new fields
	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

//	public LocalDateTime getTimestamp() {
//		return timestamp;
//	}
//
//	public void setTimestamp(LocalDateTime timestamp) {
//		this.timestamp = timestamp;
//	}

	// Existing getters and setters...
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

//    public String getExample() {
//        return example;
//    }

//    public void setExample(String example) {
//        this.example = example;
//    }

	public Boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
