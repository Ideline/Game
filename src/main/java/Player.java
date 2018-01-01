import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.Terminal;

public class Player {
    public Player(Terminal terminal, String[] map, int mapRowLength, int mapPaddingX, int mapPaddingY) {
        this.terminal = terminal;
        this.map = map;
        this.mapRowLength = mapRowLength;
        this.mapPaddingX = mapPaddingX;
        this.mapPaddingY = mapPaddingY;
    }

    private Terminal terminal;
    private int x, y;
    private String[] map;
    private int mapPaddingX, mapPaddingY, mapRowLength;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void initPlayer() {
        for(int i = 0; i < map.length; i++){
            if(map[i].equals("P")){
                x = (i > 0 ? (i % mapRowLength) : 0) + mapPaddingX;
                y = (i > mapRowLength-1 ? (i / mapRowLength) : 0) + mapPaddingY;

            }
        }
        setCharacter();
    }

    public void MoveUp() {
        if(isMovePossible(x, y-1)){
            resetPlayer();
            y--;
            setCharacter();
        }
    }

    public void MoveDown() {
        if(isMovePossible(x, y+1)) {
            resetPlayer();
            y++;
            setCharacter();
        }
    }

    public void MoveLeft() {
        if(isMovePossible(x-1, y)) {
            resetPlayer();
            x = x - 2;
            setCharacter();
        }
    }

    public void MoveRight() {
        if(isMovePossible(x+1, y)) {
            resetPlayer();
            x = x + 2;
            setCharacter();
        }
    }

    private void resetPlayer() {
        try {
            terminal.setCursorPosition(x, y);
            terminal.putCharacter(' ');
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setCharacter() {
        try {
            terminal.setCursorPosition(x, y);
            terminal.setForegroundColor(TextColor.ANSI.YELLOW);
            terminal.putCharacter('☻');
            terminal.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isMovePossible(int x, int y)
    {
        int index = (x - mapPaddingX) + (y - mapPaddingY) * mapRowLength;
        if(map[index].equals("*") || map[index].equals("+") || map[index].equals("."))
            return true;
        return false;
    }
}
