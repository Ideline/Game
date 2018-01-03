import com.googlecode.lanterna.terminal.Terminal;

public class MediumEnemy extends Enemy {

    public MediumEnemy(){}

    public MediumEnemy(int level, String threadName) {
        super(Map.getMap(), Map.getCoinMap(), Map.getMapRowLength(), Map.getMapRowHeight(), Map.getMapPaddingX(), Map.getMapPaddingY(), level, threadName);
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
            directionNr = exitNest(200);
            inNest = false;
        }

        if (didMove) {
            didMove = move(200, directionNr);
        }
        else {
            directionNr = randomizer();
            didMove = move(200, directionNr);
        }
    }
}
