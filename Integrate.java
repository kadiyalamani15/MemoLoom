package com.memoloom;

import java.util.ArrayList;
public class Integrate {


    public static void main(String[] args) {
        FlashCardList cardList = new FlashCardList();
        cardList.loadFlashCards(); 
        cardList.createFlashCard("What is Java?", "A programming language.", "Example: public static void main(String[] args) { }");

       
        boolean isUpdated = cardList.updateFlashCard("What is Java?", "What is Java used for?", "It's used for building applications.", "Java is widely used for building enterprise applications.");
        System.out.println("Update was " + (isUpdated ? "successful" : "unsuccessful"));


        boolean isDeleted = cardList.deleteFlashCard("What is Java used for?");
        System.out.println("Deletion was " + (isDeleted ? "successful" : "unsuccessful"));

   
        for (FlashCard card : cardList.getAllFlashCards()) {
            System.out.println(card.getQuestion() + " - " + card.getAnswer());
        }
    }


}
