package com.memoloom;

public class FlashCard {
    private String question;
    private String answer;
    private String example;
    private String creator;
    private Boolean visited;
    
    
    

    public FlashCard(String question, String answer, String example, String creator) {
		super();
		this.question = question;
		this.answer = answer;
		this.example = example;
		this.visited = false;
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public Boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
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
