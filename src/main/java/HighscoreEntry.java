public class HighscoreEntry implements Comparable{

    public HighscoreEntry(String playerName, int highscore){
        this.playerName = playerName;
        this.highscore = highscore;
    }

    private String playerName;
    private int highscore;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    // https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
    @Override
    public int compareTo(Object o) {
        int compareHighscore = ((HighscoreEntry)o).getHighscore();

        return compareHighscore-this.highscore;
    }

    @Override
    public String toString() {
        return playerName + ": " + highscore + "\r\n";
    }
}
