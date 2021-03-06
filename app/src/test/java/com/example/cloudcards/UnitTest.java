package com.example.cloudcards;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {

    //Card
    Card testCard = new Card(81, "Chromeshell Crab", "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=470627&type=card",  "4U", "Morph {4}{U} (You may cast this card face down as a 2/2 creature for {3}. Turn it face up any time for its morph cost.)\n",
            3, 3);
    @Test
    public void checkCardID() {
        assertEquals(81, testCard.getCard_id());

    }

    @Test
    public void checkCardName(){
        assertEquals("Chromeshell Crab", testCard.getCard_name());
    }

    @Test
    public void checkCardImage(){
        assertEquals("http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=470627&type=card", testCard.getCard_img());
    }

    @Test
    public void checkCardMana(){
        assertEquals("4U", testCard.getCard_mana());
    }
    @Test
    public void checkCardPower(){
        assertEquals(3, testCard.getPower());
    }
    @Test
    public void checkCardToughness(){
        assertEquals(3, testCard.getToughness());
    }

//    @Test
//    public void invalidInput(){
//
//    }

    //CardDetail

}