package com.memoloom;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FlashCardList {
	
	//Flashcard list is user specific this class creates flashcard list for a give user. User is passed as a parameter to the constructor

	private String user;
	private String filePath = "/home/vishal/OODProject/data.csv";
	
//	Flashcard variable is in-memory data storage for crud operation. It has question as a key to check duplicacy 
	private Map<String, FlashCard> flashCards = new HashMap<>();

	public FlashCardList(String user) {
		this.user = user;
		loadFlashCards();
	}

	private void loadFlashCards() {
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				if (values.length > 4 && values[4].equals(this.user)) {
					FlashCard card = new FlashCard(values[0], values[1], values[2], values[4]);
					this.flashCards.put(card.getQuestion(), card);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkFlash(String question) {
		return flashCards.containsKey(question);
	}

	public boolean createFlashCards(String question, String answer, String example) {
		// Function to create a new flashcard and insert into data storage

		if (this.flashCards.containsKey(question)) {
			return false;
		}

		FlashCard card = new FlashCard(question, answer, example, this.user);

		try (FileWriter fw = new FileWriter(this.filePath, true); PrintWriter pw = new PrintWriter(fw)) {

			// Construct the new line to be appended
			StringBuilder sb = new StringBuilder();

			sb.append(card.getQuestion()).append(",");
			sb.append(card.getAnswer()).append(",");
			sb.append(card.getExample()).append(",");
			sb.append(false).append(",");
			sb.append(this.user);

			// Append the new data as a new line in the CSV file
			pw.println(sb.toString());
			this.flashCards.put(question, card);

			System.out.println("New line appended to the CSV file successfully.");

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

}