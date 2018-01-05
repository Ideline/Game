import com.googlecode.lanterna.terminal.Terminal;

public class EasyEnemy extends Enemy {

    public EasyEnemy() {
        super(Game.map.getMap(), Game.map.getCoinMap(), Game.map.getMapRowLength(), Game.map.getMapRowHeight(), Game.map.getMapPaddingX(), Game.map.getMapPaddingY(), "easy" + Game.totalBots+1);
        Game.totalBots++;
    }

    private boolean inNest = true;
    private boolean didMove = true;
    private int directionNr = 0;
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

    public void movement(){

        if(inNest) {
            directionNr = exitNest(400);
            inNest = false;
        }

        if (didMove) {
            didMove = move(400, directionNr);
        }
        else {
            directionNr = randomizer();
            didMove = move(400, directionNr);
        }
    }
}
