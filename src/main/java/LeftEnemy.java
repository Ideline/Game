import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class LeftEnemy extends Enemy {

    public LeftEnemy(int directionNr){
        super(Game.map.getMap(), Game.map.getCoinMap(), Game.map.getMapRowLength(), Game.map.getMapRowHeight(), Game.map.getMapPaddingX(), Game.map.getMapPaddingY(), "left" + Game.totalBots+1);
        Game.totalBots++;
        this.directionNr = directionNr;
    }

    private boolean didMove = true;
    private int directionNr;
    private int gcd = 0;

    public int getDirectionNr() {
        return directionNr;
    }

    public void start(int gcd) throws Exception {
        this.gcd = gcd;

        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }

        initEnemy();
    }

    public void run() {
        try {
            Thread.sleep(gcd);
            while(isRunning) {
                movement();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void movement(){
        if (didMove) {
            didMove = moveLeft(400, directionNr);
        }
        else {
            if (directionNr==3)
                directionNr=2;
            else if (directionNr==2)
                directionNr=4;
            else if (directionNr==4)
                directionNr=1;
            else if (directionNr==1)
                directionNr=3;
            didMove = move(400, directionNr);
        }
    }
}