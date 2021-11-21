package com.example.cloudcards;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Card implements Parcelable {
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

    public Card(int id, String card_name, String card_img, String card_mana, String card_text,
                int power, int toughness, String type, String identity){
        this.card_number = id;
        this.card_name = card_name;
        this.card_img = card_img;
        this.card_mana = card_mana;
        this.card_text = card_text;
        this.power = power;
        this.toughness = toughness;
        this.type = type;
        this.card_colour_identity = identity;

    }

    public Card(JSONObject result){
        try {
            this.card_number= result.getInt("multiverseid");
            this.card_img = result.getString("imageUrl");
            this.card_name = result.getString("name");
            this.card_mana= result.getString("manaCost");
            this.card_text = result.getString("text");
            this.power = result.getInt("power");
            this.toughness = result.getInt("toughness");
            this.type = result.getString("type");
            this.card_colour_identity = result.getString("colorIdentity");

        } catch (JSONException a) {
            a.printStackTrace();
        }
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

    public int getCard_id(){
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

    public String getCard_colour_identity() { return this.card_colour_identity;}

    private String card_name;
    private String card_mana;
    private String card_text;
    private String card_img;
    private String type;
    private int power;
    private int toughness;
    private int card_number;
    private String card_colour_identity;

    public Card() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(card_name);
        parcel.writeString(card_mana);
        parcel.writeString(card_text);
        parcel.writeString(card_img);
        parcel.writeString(type);
        parcel.writeInt(power);
        parcel.writeInt(toughness);
        parcel.writeInt(card_number);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Card (Parcel in) {
        card_name = in.readString();
        card_mana = in.readString();
        card_text = in.readString();
        card_img = in.readString();
        type = in.readString();
        power = in.readInt();
        toughness = in.readInt();
        card_number = in.readInt();
    }
}
