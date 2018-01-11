import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Specials implements Runnable{

    public Specials(String threadName){
        this.threadName = threadName;
    }

    private ArrayList<Character> specials = new ArrayList<Character>();
    private Coordinates[] spawnPoints;
    private Thread t;
    private String threadName;


    public ArrayList<Character> getSpecials() {
        return specials;
    }

    public void start() throws Exception {

        if(t == null) {
            t = new Thread(this, threadName);
            t.start();

        }
    }

    public void run() {
        try {
            while (Game.isGameRunning()){
                drawSpecial();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void makeSpecial(){
        specials.add('S');
        specials.add('R');
//        specials.add('C');
//        specials.add('D');
    }

    private void drawSpecial() throws Exception{
        Thread.sleep(10000);
        int nrSpecials = specials.size();
        Random r = new Random();
        int roll = r.nextInt(nrSpecials);
        int index = randomizeSpawnPoint();
        Map.printToScreen(spawnPoints[index].getX() + Game.map.getMapPaddingX(), spawnPoints[index].getY() + Game.map.getMapPaddingY(), specials.get(roll), TextColor.ANSI.GREEN);
    }

    public void createSpawnPoints(){
        int nrSpawnPoints = 0;

        for (int y = 0; y < Game.map.getMapRowHeight(); y++) {
            for (int x = 0; x < Game.map.getMapRowLength(); x++) {
                if(Game.map.getMap()[x][y] == '.') nrSpawnPoints++;
            }
        }

        spawnPoints = new Coordinates[nrSpawnPoints];
        int index = 0;

        for (int y = 1; y < Game.map.getMapRowHeight(); y++) {
            for (int x = 10; x < Game.map.getMapRowLength(); x++) {
                if(Game.map.getMap()[x][y] == '.'){
                    spawnPoints[index] = new Coordinates(x, y);
                    index++;
                }
            }
        }
    }

    private int randomizeSpawnPoint(){
        Random r = new Random();
        int roll = r.nextInt(spawnPoints.length);
        return roll;
    }
}
