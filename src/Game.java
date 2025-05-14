import java.util.ArrayList;

public class Game {

    private static ArrayList<Game> gameLog = new ArrayList<>();
    private static String WLT = "";
    private final ArrayList<String> dealerHand;
    private final ArrayList<String> playerHand;
    private Boolean win;
    private double currentWinRate;

    public Game(){
        gameLog.add(this);
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();
    }

    public void logCard(boolean dealer, Card card){
        if (dealer){
            this.dealerHand.add(card.toString());
        }
        else {
            this.playerHand.add(card.toString());
        }
    }


    public void logResult(Boolean win){
        this.win = win;
        int winCount = 0;
        int lossCount = 0;
        int tieCount = 0;
        for (Game game: gameLog){
            if (win == null){
                tieCount++;
            }
            else if (win){
                winCount++;
            }
            else {
                lossCount++;
            }
            currentWinRate = (double) winCount/(winCount+lossCount+tieCount);
        }
        WLT = winCount+" - "+lossCount+" - "+tieCount;
    }

    private String winnerAsString(){
        return (win) ? "Player" : "Dealer";
    }

    public boolean getWin(){
        return win;
    }

    public ArrayList<Game> getGameLog(){
        return gameLog;
    }

    @Override
    public String toString(){
        return "Game "+(gameLog.indexOf(this)+1)+":\n" +
                "\nDealer Hand: "+dealerHand+
                "\nPlayer Hand: "+playerHand+
                "\nWinner: %s"+winnerAsString()+
                "\nCurrent Win Rate: "+currentWinRate+"\n\n";

    }

}
