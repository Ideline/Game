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
    private TextColor color = TextColor.ANSI.DEFAULT;

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

    public void setColor(TextColor color) {
        this.color = color;
    }

    private void setColor(int roll){
        char c = specials.get(roll);
        switch (c){
            case 'S':
                color = TextColor.ANSI.BLUE;
                break;
            case 'R':
                color = TextColor.ANSI.MAGENTA;
                break;
            case 'C':
                color = TextColor.ANSI.RED;
                break;
            case 'W':
                color = TextColor.ANSI.CYAN;
                break;
            case '$':
                color = TextColor.ANSI.GREEN;
                break;
        }
    }

    public void makeSpecial(){
        specials.add('S');
        specials.add('R');
        specials.add('C');
        specials.add('W');
        specials.add('$');
    }



    private void drawSpecial() throws Exception{
        Thread.sleep(10000);
        int nrSpecials = specials.size();
        Random r = new Random();
        int roll = r.nextInt(nrSpecials);
        setColor(roll);
        int index = randomizeSpawnPoint();
        try {
            Map.printToScreen(spawnPoints[index].getX() + Game.map.getMapPaddingX(), spawnPoints[index].getY() + Game.map.getMapPaddingY(), specials.get(roll), color);
        }
        catch (Exception e){
            e.printStackTrace();
        }
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

        for (int y = 0; y < Game.map.getMapRowHeight(); y++) {
            for (int x = 0; x < Game.map.getMapRowLength(); x++) {
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
