import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Menu {

    public static void createMenu() throws Exception {
        Screen screen = Game.getScreen();
        screen.clear();
        screen.setCursorPosition(null);

        //AtomicBoolean är en boolean som är trådsäker
        final AtomicBoolean stop = new AtomicBoolean(false);
        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new AbsoluteLayout());

        //https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/buttons.md
        Button b1 = new Button("FfakMan", () -> {
            Game.gameState = GameState.MENU_DIFFICULTY;
            Game.map.setCoinMode(true);
            stop.set(true);
        });

        Button b2 = new Button("Survivor", () -> {
            Game.gameState = GameState.MENU_DIFFICULTY;
            Game.map.setCoinMode(false);
            stop.set(true);
        });

        Button b3 = new Button("Quit", () -> {
            Game.gameState = GameState.EXIT;
            stop.set(true);
        });

        Label l1 = new Label("Game Menu");

        //Vart vill vi skriva menyn på skärmen?
        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35/2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 20;

        l1.setForegroundColor(TextColor.ANSI.YELLOW);
        l1.setSize(new TerminalSize(76, 1));
        l1.addStyle(SGR.BOLD);
        l1.setPosition(new TerminalPosition(columnPosition + 13, rowPosition+=2));

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition+=2));
        b2.setSize(new TerminalSize(35, 1));
        b2.setPosition(new TerminalPosition(columnPosition, rowPosition+=2));
        b3.setSize(new TerminalSize(35, 1));
        b3.setPosition(new TerminalPosition(columnPosition, rowPosition+=2));

        for(Button button : Arrays.asList(b1, b2, b3)) {
            contentArea.addComponent(button);
        }
        contentArea.addComponent(l1);

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().setComponent(contentArea);
        while(!stop.get()) {
            if(!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }
    }

    public static void levelDifficulty() throws Exception {
        Screen screen = Game.getScreen();
        screen.clear();
        screen.setCursorPosition(null);

        //AtomicBoolean är en boolean som är trådsäker
        final AtomicBoolean stop = new AtomicBoolean(false);
        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new AbsoluteLayout());

        Label l1 = new Label("Choose difficulty");

        //https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/buttons.md
        Button b1 = new Button("Easy", () -> {
            Game.setLevel(1);
            Game.gameState = GameState.GAME;
            stop.set(true);
        });

        Button b2 = new Button("Medium", () -> {
            Game.setLevel(2);
            Game.gameState = GameState.GAME;
            stop.set(true);
        });

        Button b3 = new Button("Hard", () -> {
            Game.setLevel(3);
            Game.gameState = GameState.GAME;
            stop.set(true);
        });

        Button b4 = new Button("Back", () -> {
            Game.gameState = GameState.MENU;
            stop.set(true);
        });

        //Vart vill vi skriva menyn på skärmen?
        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35/2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 20;

        l1.setForegroundColor(TextColor.ANSI.YELLOW);
        l1.setSize(new TerminalSize(76, 1));
        l1.addStyle(SGR.BOLD);
        l1.setPosition(new TerminalPosition(columnPosition + 9, rowPosition+=2));

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition+=2));
        b2.setSize(new TerminalSize(35, 1));
        b2.setPosition(new TerminalPosition(columnPosition, rowPosition+=2));
        b3.setSize(new TerminalSize(35, 1));
        b3.setPosition(new TerminalPosition(columnPosition, rowPosition+=2));
        b4.setSize(new TerminalSize(35, 1));
        b4.setPosition(new TerminalPosition(columnPosition, rowPosition+=2));

        for(Button button : Arrays.asList(b1, b2, b3, b4)) {
            contentArea.addComponent(button);
        }
        contentArea.addComponent(l1);

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().setComponent(contentArea);
        while(!stop.get()) {
            if(!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }
    }
}