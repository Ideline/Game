import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class HardEnemy extends Enemy {

    public HardEnemy(){}

    public HardEnemy(Terminal terminal, String[] map, int mapRowLength, int mapPaddingX, int mapPaddingY, int level, String threadName) {
        super(terminal, map, mapRowLength, mapPaddingX, mapPaddingY, level, threadName);
    }

    private static boolean inNest = true;
    private static boolean didMove = true;
    private static int direction = 0;

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
