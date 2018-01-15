import java.util.ArrayList;

public class TimescoreEntry implements Comparable{

    public TimescoreEntry(String playerName, String timescore){
        this.playerName = playerName;
        this.timescore = timescore;
    }

    private String playerName;
    private String timescore;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTimescore() {
        return timescore;
    }

    public void setTimescore(String timescore) {
        this.timescore = timescore;
    }


    @Override
    public int compareTo(Object o) {
        int compareTimescore = Integer.parseInt (((TimescoreEntry)o).getTimescore().replace(".",""));
        int compareTimescore2 = Integer.parseInt(this.getTimescore().replace(".","")) ;

        return compareTimescore-compareTimescore2;
    }

    @Override
    public String toString() {
        return playerName + ": " + timescore + "\r\n";
    }




}
