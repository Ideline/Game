import com.googlecode.lanterna.terminal.Terminal;

public class EasyEnemy extends Enemy {

    public EasyEnemy(){}

    public EasyEnemy(int level, String threadName) {
        super(Game.getTerminal(), Map.getMap(), Map.getCoinMap(), Map.getMapRowLength(), Map.getMapRowHeight(), Map.getMapPaddingX(), Map.getMapPaddingY(), level, threadName);
    }

    private boolean inNest = true;
    private boolean didMove = true;
    private int directionNr = 0;
    private int gcd = 0;

    public void start(int gcd) {
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
