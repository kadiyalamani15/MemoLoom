package com.memoloom;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		FlashCardList fclist = new FlashCardList("User");
		
//		fclist.createFlashCards("Sample1", "Answer1", "Example");
		
		ArrayList<FlashCard> cardList = new ArrayList<>();
		
		cardList = fclist.getFlashCardList();
		
		if (!cardList.isEmpty()) {
            System.out.println(cardList.get(0).getQuestion()); // Corrected way to access elements in ArrayList
        } else {
            System.out.println("No flash cards available.");
        }

	}

}
