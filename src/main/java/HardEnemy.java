import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class HardEnemy extends Enemy {

    public HardEnemy() {
        super(Game.map.getMap(), Game.map.getCoinMap(), Game.map.getMapRowLength(), Game.map.getMapRowHeight(), Game.map.getMapPaddingX(), Game.map.getMapPaddingY(), "hard" + Game.totalBots+1);
        Game.totalBots++;
    }

    private boolean inNest = true;
    private boolean didMove = true;
    private int direction = 0;
    private int gcd = 0;

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
        if(inNest) {
            direction = exitNest(400, true);
            inNest = false;
        }
        if (didMove) {
            didMove = move(400, direction);
        }
        else {
            DirectionResult dr = huntPlayer();
            direction = dr.getDirection();
            didMove = dr.getDidMove();
        }
    }
}
