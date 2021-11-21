package com.example.cloudcards;

import java.util.Locale;

public class Card {
    /**
     * TMP Array of Cards until the API works.
     * I just wanted the ability to test out the gallary layout.
     */
    public static final Card[] cards = {
            new Card(81, "Chromeshell Crab", "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=470627&type=card",  "4U", "Morph {4}{U} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)\n",
                3, 3),
            new Card(27, "Lunarch Veteran // Luminous Phantom", "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=534783&type=card", "W", "Whenever another creature enters the battlefield under your control, you gain 1 life.\n",
                    1, 1)
    };

    /**
     * Another Test Function for Now. All it does is return the Temp Card Array.
     * @return
     */
    public static Card[] getAllCards() {return cards;}

    public Card(int id, String card_name, String card_img, String card_mana, String card_text,
                int power, int toughness) {
        this.card_number = id;
        this.card_name = card_name;
        this.card_img = card_img;
        this.card_mana = card_mana;
        this.card_text = card_text;
        this.power = power;
        this.toughness = toughness;
    }

    public static Card getCardByName(String cardName) {
        Card card = null;
        for(int i = 0; i < cards.length; i++) {
            Card c = cards[i];
            if(cardName.toLowerCase().trim().equals(c.getCard_name().toLowerCase().trim())) {
                card = c;
                break;
            }
        }
        return card;
    }
    
    public static Card getCardByName(String cardName, Card[] cardList) {
        Card card = null;
        for(int i = 0; i < cards.length; i++) {
            Card c = cardList[i];
            if(cardName.toLowerCase().trim().equals(c.getCard_name().toLowerCase().trim())) {
                card = c;
                break;
            }
        }
        return card;
    }
    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_mana() {
        return card_mana;
    }

    public void setCard_mana(String card_mana) {
        this.card_mana = card_mana;
    }

    public String getCard_text() {
        return card_text;
    }

    public void setCard_text(String card_text) {
        this.card_text = card_text;
    }

    public String getCard_img() {
        return card_img;
    }

    public void setCard_img(String card_img) {
        this.card_img = card_img;
    }

    public int getCard_number() {
        return card_number;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getToughness() {
        return toughness;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public void setCard_number(int card_number) {
        this.card_number = card_number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    private String card_name;
    private String card_mana;
    private String card_text;
    private String card_img;
    private String type;
    private int power;
    private int toughness;
    private int card_number;

    public Card() {

    }
}