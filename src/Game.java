import java.util.ArrayList;

public class Game {

    private static final ArrayList<Game> gameLog = new ArrayList<>();
    public static String WLT = "";
    private final ArrayList<String> dealerHand;
    private final ArrayList<String> playerHand;
    private Boolean win;
    private double currentWinRate;

    public Game(){
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
        gameLog.add(this);
        int winCount = 0;
        int lossCount = 0;
        int tieCount = 0;
        for (Game game: gameLog){
            if (game.isWin()){
                winCount++;
            }
            else if (!game.isWin()){
                lossCount++;
            }
            else {
                tieCount++;
            }
        }
        currentWinRate = 100* (double) winCount/(winCount+lossCount+tieCount);
        WLT = winCount+"-"+lossCount+"-"+tieCount;
    }

    private String winnerAsString(){
        if (win == null){
            return "Tie";
        }
        else if (win){
            return "Player";
        }
        else {
            return "Dealer";
        }
    }

    public Boolean isWin(){
        return this.win;
    }

    public ArrayList<Game> getGameLog(){
        return gameLog;
    }

    @Override
    public String toString(){
        return "Game "+(gameLog.indexOf(this)+1)+":\n" +
                "\nDealer Hand: "+dealerHand+
                "\nPlayer Hand: "+playerHand+
                "\nWinner: "+winnerAsString()+
                "\nCurrent Win Rate: "+String.format("%.2f%%",currentWinRate)+"\n\n";

    }

}
