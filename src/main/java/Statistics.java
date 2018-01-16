public class Statistics {

    public Statistics(){
        this.mapTime = 0;
        this.totalTime = 0;
        this.mapScore = 0;
        this.totalScore = 0;
        this.coinScore = 0;
        this.bonusPoints = 0;
    }

    private long mapTime = 0;
    private long totalTime = 0;
    private int mapScore = 0;
    private int totalScore = 0;
    private int coinScore = 0;
    private int bonusPoints = 0;

    public long getMapTime() {
        mapTime = System.currentTimeMillis() - Game.startTime;
        return mapTime;
    }

    public long getTotalTime() {
        totalTime = totalTime + mapTime;
        return totalTime;
    }

    public int getCoinScore(){
        coinScore = Game.map.getNrCoinsTaken() * 10;
        return coinScore;
    }

    public void resetAllScores(){
        coinScore = 0;
        Game.map.setNrCoinsTaken(0);
        mapScore = 0;
        totalScore = 0;
        bonusPoints = 0;
    }

    public void resetScores(){
        coinScore = 0;
        Game.map.setNrCoinsTaken(0);
        mapScore = 0;
        bonusPoints = 0;
    }

    public int getMapScore(){
        return mapScore;
    }

    public void setMapScore() {
        mapScore = getCoinScore() + bonusPoints;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore() {
        totalScore = totalScore + mapScore;;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public static String formateTime(long time){

        String min, sec, ten;
        long minutes = ((time / 1000)  / 60) % 60;
        long seconds = (time / 1000) % 60;
        long tenths = (time / 10) % 60;
        if(minutes < 10) min = "0" + minutes;
        else min = "" + minutes;
        if(seconds < 10) sec = "0" + seconds;
        else sec = "" + seconds;
        if(tenths < 10) ten = "0" + tenths;
        else ten = "" + tenths;


        return min + "." + sec + "." + ten;
    }
}
