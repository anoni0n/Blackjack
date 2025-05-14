import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class Card {

    public static final Image HIDDEN_CARD_BACK = new ImageIcon(Objects.requireNonNull(Card.class.getResource("./cards/BACK.png"))).getImage();
    private String value;
    private String suit;
    private Image image;


    public Card(String value, String suit){
        this.value = value;
        this.suit = suit;
        this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(this.getImagePath()))).getImage();
    }

    public Image getImage(){
        return this.image;
    }

    public String getImagePath(){
        return "./cards/"+toString()+".png";
    }

    public int getValue() {
        if ("KQJ".contains(value)){
            return 10;
        }
        else if ("A".contains(value)){
            return 11;
        }
        return Integer.parseInt(value);
    }

    public boolean isAce(){
        return getValue() == 11;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    @Override
    public String toString(){
        return this.value+"-"+this.suit;
    }
}
