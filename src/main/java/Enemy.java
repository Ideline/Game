import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Random;

public class Enemy implements Runnable{

    public Enemy(){}

    public Enemy(Terminal terminal, String[] map, int mapRowLength, int mapPaddingX, int mapPaddingY, int level, String threadName) {
        this.terminal = terminal;
        this.map = map;
        this.mapRowLength = mapRowLength;
        this.mapPaddingX = mapPaddingX;
        this.mapPaddingY = mapPaddingY;
        this.level = level;
        this.isRunning = true;
        this.threadName = threadName;
    }

    private Terminal terminal;
    private int x, y;
    private String[] map;
    private int mapPaddingX, mapPaddingY, mapRowLength;
    private int level;
    private boolean coordinateContainsCrum = false;
    protected boolean isRunning;
    protected Thread t;
    protected String threadName;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getThreadName() { return threadName; }

    public void initEnemy() {
        for(int i = 0; i < map.length; i++){
            if(map[i].equals("E")){
                map[i] = "e"; //Upptagen nu
                x = (i > 0 ? (i % mapRowLength) : 0) + mapPaddingX;
                y = (i > mapRowLength-1 ? (i / mapRowLength) : 0) + mapPaddingY;
                setCharacter();
                break;
            }
        }
    }

    private void setCharacter() {
        try {
            terminal.setCursorPosition(x, y);
            switch(this.getClass().getName()){
                case "EasyEnemy":
                    terminal.setForegroundColor(TextColor.ANSI.WHITE);
                    break;
                case "MediumEnemy":
                    terminal.setForegroundColor(TextColor.ANSI.GREEN);
                    break;
                case "HardEnemy":
                    terminal.setForegroundColor(TextColor.ANSI.RED);
                    break;
            }

            terminal.putCharacter('Ω');
            terminal.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void movement(){}

    protected int randomizer(){
        Random rand = new Random();
        int direction = rand.nextInt(4) + 1;
        return direction;
    }

    private String getCoordinateObject(int x, int y)
    {
        int index = (x - mapPaddingX) + (y - mapPaddingY) * mapRowLength;
        return map[index];
    }

    protected boolean move(int gcd, int direction){
        return move(gcd, direction,false);
    }
    protected boolean move(int gcd, int direction, boolean privileged){
        try{
            switch(direction)
            {
                case 1: { // upp
                    if(isMovePossible(x, y-1, privileged)){
                        Thread.sleep(gcd);
                        resetEnemy();
                        y--;
                        setCharacter();
                        if(isCrossing()){
                            return false;
                        }
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                case 2: { // ner
                    if(isMovePossible(x, y+1, privileged)) {
                        Thread.sleep(gcd);
                        resetEnemy();
                        y++;
                        setCharacter();
                        if(isCrossing()){
                            return false;
                        }
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                case 3: { // vänster
                    if(isMovePossible(x-2, y, privileged)) {
                        Thread.sleep(gcd);
                        resetEnemy();
                        x = x - 2;
                        setCharacter();
                        if(isCrossing()) {
                            return false;
                        }
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                case 4: { // höger
                    if(isMovePossible(x+2, y, privileged)) {
                        Thread.sleep(gcd);
                        resetEnemy();
                        x = x + 2;
                        setCharacter();
                        if(isCrossing()){
                            return false;
                        }
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                default:
                    return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void resetEnemy() {
        try {
            boolean isCrum = getCoordinateObject(x, y).equals("*") || getCoordinateObject(x, y).equals(".");
            terminal.setCursorPosition(x, y);
            terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
            terminal.putCharacter(isCrum ? '•' : ' ');
            terminal.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isMovePossible(int x, int y) {
        return isMovePossible(x, y, false);
    }
    private boolean isMovePossible(int x, int y, boolean privileged)
    {
        int index = (x - mapPaddingX) + (y - mapPaddingY) * mapRowLength;
        String s = map[index];
        if(s.equals("*") || s.equals("+") || s.equals("P") || s.equals("E") || s.equals("e") || s.equals(".") || s.equals("!") || s.equals(",") || s.equals("^") || (s.equals("-") && privileged))
            return true;
        return false;
    }

    private boolean isCrossing(){
        int index = (x - mapPaddingX) + (y - mapPaddingY) * mapRowLength;
        String s = map[index];
        if(s.equals(".") || s.equals("!")){
            return true;
        }
        return false;
    }

    public boolean isExit(){
        int index = (x - mapPaddingX) + (y - mapPaddingY) * mapRowLength;
        if(map[index].equals(",")){
            return true;
        }
        return false;
    }

    public int exitNest(int gcd) {
        return exitNest(gcd, false);
    }
    public int exitNest(int gcd, boolean hardMode){
        int move2 = 0;
        while(!isExit()){
            move(gcd,3, true);
        }
        for(int i = 0; i < 3; i++){
            move(gcd,1, true);
        }
        if(!hardMode) {
            Random r = new Random();
            move2 = r.nextInt(2) + 3;
            move(gcd, move2);
        } else {
            move2 = huntPlayer().getDirection();
        }
        return move2;
    }

    public Coordinates findPlayer(){
        Coordinates xy = new Coordinates(Game.p.getX(), Game.p.getY());
        return xy;
    }

    public DirectionResult huntPlayer(){
        DirectionResult dr = new DirectionResult();
        Coordinates c = findPlayer();
        String directionS = "";
        int direction = 0;
        int nr = 0;
        if(c.getX() < x){
            // gå väst
            directionS = directionS + "w";
        }
        else if(c.getX() == x){
            // gå nord eller syd
            directionS = directionS + "ns";
        }
        else if(c.getX() > x){
            // gå öst
            directionS = directionS + "e";
        }
        if(c.getY() < y){
            // gå norr
            directionS = directionS + "n";
        }
        else if(c.getY() == y){
            // gå öst eller väst
            directionS = directionS + "ew";
        }
        else if(c.getY() > y){
            // gå söder
            directionS = directionS + "s";
        }

        switch (directionS){
            case "wn":
                if(isMovePossible(x, y - 1) && isMovePossible(x - 1, y )){
                    nr = randomizer();
                    if(nr == 1 || nr == 2){
                        direction = 1;
                    }
                    else
                    direction = 3;
                }
                else if(isMovePossible(x, y - 1)){
                    direction = 1;
                }
                else if(isMovePossible(x - 1, y )){
                    direction = 3;
                }
                else{
                    direction = randomizer();
                }
                break;
            case "wew":
                direction = isMovePossible(x-1, y) ? 3 : randomizer();
                break;
            case "ws":
                if(isMovePossible(x, y + 1) && isMovePossible(x - 1, y )){
                    nr = randomizer();
                    if(nr == 1 || nr == 2){
                        direction = 2;
                    }
                    else
                        direction = 3;
                }
                else if(isMovePossible(x, y + 1)){
                    direction = 2;
                }
                else if(isMovePossible(x - 1, y )){
                    direction = 3;
                }
                else{
                    direction = randomizer();
                }
                break;
            case "nsn":
                direction = isMovePossible(x, y-1) ? 1 : randomizer();
                break;
            case "nsew":
                // gameOver
                break;
            case "nss":
                direction = isMovePossible(x, y+1) ? 2 : randomizer();
                break;
            case "en":
                if(isMovePossible(x, y - 1) && isMovePossible(x + 1, y )){
                    nr = randomizer();
                    if(nr == 1 || nr == 2){
                        direction = 1;
                    }
                    else
                        direction = 4;
                }
                else if(isMovePossible(x, y - 1)){
                    direction = 1;
                }
                else if(isMovePossible(x + 1, y )){
                    direction = 4;
                }
                else{
                    direction = randomizer();
                }
            case "eew":
                direction = isMovePossible(x+1, y) ? 4 : randomizer();
                break;
            case "es":
                if(isMovePossible(x + 1, y) && isMovePossible(x, y + 1)){
                    nr = randomizer();
                    if(nr == 1 || nr == 2){
                        direction = 4;
                    }
                    else
                        direction = 2;
                }
                else if(isMovePossible(x + 1, y)){
                    direction = 4;
                }
                else if(isMovePossible(x, y + 1)){
                    direction = 2;
                }
                else{
                    direction = randomizer();
                }
                break;
        }

        dr.setDidMove(move(400, direction));
        dr.setDirection(direction);

        return dr;
    }

    public void run() {
//        try {
//            while(isRunning) {
//                movement();
//            }
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
    }

    public void start() {
//        if(t == null) {
//            t = new Thread(this, threadName);
//            t.start();
//        }
//
//        initEnemy();
    }
}
