import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Blackjack {

    private ArrayList<Card> deck;
    private Card hiddenCard;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;
    private int playerSum;
    private int playerAceCount;

    Game game;

    private int dealerSum = 0;
    private int dealerAceCount = 0;
    int cardWidth = 110;
    int cardHeight = 154;
    int boardWidth = 600;
    int boardHeight = boardWidth;
    private final JFrame frame = new JFrame("Blackjack Game");
    private final JButton hitButton = new JButton("Hit");
    private final JButton stayButton = new JButton("Stay");
    JButton newGameButton = new JButton("New Game");
    JButton gameHistoryButton = new JButton("Game History");

    private final JPanel gamePanel = new JPanel()
    {
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            try {
                //draw hidden card
                Image hiddenCardImg = Card.HIDDEN_CARD_BACK;
                //reveals the hidden card the end of the game
                if (!stayButton.isEnabled()){
                    hiddenCardImg = hiddenCard.getImage();
                }
                g.drawImage(hiddenCardImg, 20, 20, cardWidth, cardHeight, null);

                //draw the dealer's hand
                for (int i = 0; i < dealerHand.size(); i++){
                    Card card = dealerHand.get(i);
                    Image cardImg = card.getImage();
                    g.drawImage(cardImg,cardWidth + 25 + (cardWidth+5)*i,20,cardWidth,cardHeight,null);
                }

                for (int i = 0; i < playerHand.size(); i++){
                    Card card = playerHand.get(i);
                    Image cardImg = card.getImage();
                    g.drawImage(cardImg,  20 +(cardWidth+5)*i,300,cardWidth,cardHeight,null);
                }

                if (!stayButton.isEnabled()){
                    String message;

                    if (playerSum > 21 || (dealerSum > playerSum && dealerSum <= 21)){
                        message = "You Lose!";
                        game.logResult(false);
                    }
                    else if (dealerSum > 21 || playerSum > dealerSum){
                        message = "You Win!";
                        game.logResult(true);
                    }
                    else {
                        message = "You Tied!";
                        game.logResult(null);
                    }

                    g.setFont(new Font("Ariel",Font.PLAIN,30));
                    g.setColor(Color.white);
                    g.drawString(message,220,250);
                }


            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    };


    public Blackjack(){

        frame.setSize(boardWidth,boardHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel.setBackground(new Color(53,101,77));
        frame.add(gamePanel);
        hitButton.setFocusable(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        newGameButton.setFocusable(false);
        buttonPanel.add(newGameButton);
        gameHistoryButton.setFocusable(false);
        buttonPanel.add(gameHistoryButton);
        frame.add(buttonPanel,BorderLayout.SOUTH);

        game = new Game();
        startGame();


        gameHistoryButton.addActionListener(e ->
        {
            String log = "W-L-T: "+Game.WLT+"\n\n";
            for (Game game : game.getGameLog()){
                log += game.toString();
            }
            JTextArea textArea = new JTextArea(log);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            scrollPane.setPreferredSize(new Dimension( 250, 250 ));
            JOptionPane.showMessageDialog(null, scrollPane);
        });

        newGameButton.addActionListener(e -> {
            if (stayButton.isEnabled()){
                game.logResult(false);
            }
            this.frame.dispose();
            Blackjack blackjack = new Blackjack();
        });

        hitButton.addActionListener(e -> {
            draw(false);
            gamePanel.repaint();
            if (playerSum > 21){
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
            }
        });
        stayButton.addActionListener(e -> {
            hitButton.setEnabled(false);
            stayButton.setEnabled(false);

            while (dealerSum < 17){
                draw(true);
            }
            gamePanel.repaint();
        });
        gamePanel.repaint();
        System.out.println(dealerHand);
    }

    public void startGame(){
        buildRandomizedDeck();
        dealStartingHands();
    }

    public void dealStartingHands(){
        dealerHand = new ArrayList<>();
        dealerSum = 0;
        dealerAceCount = 0;
        hiddenCard = deck.removeLast();
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1:0;
        game.logCard(true, hiddenCard);
        draw(true);

        //player stuff
        playerHand = new ArrayList<>();
        playerSum = 0;
        playerAceCount = 0;
        for(int i = 0; i < 2; i++){
            draw(false);
        }
    }

    /**
     * Makes an ArrayList with cards in a randomized order
     */
    public void buildRandomizedDeck(){
        deck = new ArrayList<>();
        String[] values = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        String[] suits = {"C","D","H","S"};

        for (String suit : suits){
            for (String value : values){
                deck.add(new Card(value,suit));
            }
        }
        Collections.shuffle(this.deck);
    }

    public void draw(boolean dealer){
        Card card = deck.removeLast();
        if (dealer){
            dealerAceCount += (card.isAce()) ? 1:0;
            dealerSum += card.getValue();
            dealerHand.add(card);
        }
        else {
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }
        game.logCard(dealer,card);
        reduceAceCount();
    }


    public void reduceAceCount(){
        while (dealerSum > 21 && dealerAceCount > 0){
            dealerSum -= 10;
            dealerAceCount--;
        }
        while (playerSum > 21 && playerAceCount > 0){
            playerSum -= 10;
            playerAceCount--;
        }
    }
}

