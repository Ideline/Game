import com.googlecode.lanterna.terminal.Terminal;

public class MediumEnemy extends Enemy {

    public MediumEnemy(){}

    public MediumEnemy(int level, String threadName) {
        super(Game.getTerminal(), Map.getMap(), Map.getMapRowLength(), Map.getMapRowHeight(), Map.getMapPaddingX(), Map.getMapPaddingY(), level, threadName);
    }

    private static boolean inNest = true;
    private static boolean didMove = true;
    private static int directionNr = 0;

    public void start() {
        if(t == null) {
            t = new Thread(this, threadName);
            t.start();
        }

        initEnemy();
    }

    public void run() {
        try {
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
