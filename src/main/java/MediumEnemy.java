import com.googlecode.lanterna.terminal.Terminal;

public class MediumEnemy extends Enemy {

    public MediumEnemy(){}

    public MediumEnemy(Terminal terminal, String[] map, int mapRowLength, int mapPaddingX, int mapPaddingY, int level, String threadName) {
        super(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, threadName);
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
                System.out.println("är i mediumklassen!!!");
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
