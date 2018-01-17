import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.screen.Screen;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Menu {

    public static boolean gameOver = false;

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
            Game.gameState = GameState.CHOOSEMAP;
            Game.map.setCoinMode(false);
            stop.set(true);
        });

        Button b3 = new Button("Quit", () -> {
            Game.gameState = GameState.EXIT;
            stop.set(true);
        });

        Label l2 = new Label("Game Menu");
        Label l1 = new Label("███████╗███████╗ █████╗ ██╗  ██╗     ███╗   ███╗ █████╗ ███╗   ██╗\n" +
                "██╔════╝██╔════╝██╔══██╗██║ ██╔╝     ████╗ ████║██╔══██╗████╗  ██║\n" +
                "█████╗  █████╗  ███████║█████╔╝█████╗██╔████╔██║███████║██╔██╗ ██║\n" +
                "██╔══╝  ██╔══╝  ██╔══██║██╔═██╗╚════╝██║╚██╔╝██║██╔══██║██║╚██╗██║\n" +
                "██║     ██║     ██║  ██║██║  ██╗     ██║ ╚═╝ ██║██║  ██║██║ ╚████║\n" +
                "╚═╝     ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝     ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝");
//        Label l1 = new Label(" ___________  ___________  ___________   ____  ____                ________ ________   ___________   ____________\n" +
//                "|\\    ______\\|\\    ______\\|\\    ___   \\ |\\   \\|\\   \\              |\\    ___ \\ ___   \\ |\\    ___   \\ |\\    ____   \\\n" +
//                "\\ \\   \\___ _|\\ \\   \\___ _|\\ \\   \\_|\\   \\\\ \\   \\/   /|_  __________\\ \\   \\\\ \\___\\ \\   \\\\ \\   \\_|\\   \\\\ \\   \\__|\\   \\\n" +
//                " \\ \\    ____\\ \\ \\    ____\\ \\ \\    ___   \\\\ \\    ___   \\|\\__________\\ \\   \\\\|___|\\ \\   \\\\ \\    ___   \\\\ \\   \\ \\ \\   \\\n" +
//                "  \\ \\   \\___|  \\ \\   \\___|  \\ \\   \\\\ \\   \\\\ \\   \\\\ \\   \\|__________|\\ \\   \\      \\ \\   \\\\ \\   \\\\ \\   \\\\ \\   \\ \\ \\   \\\n" +
//                "   \\ \\___\\      \\ \\___\\      \\ \\___\\\\ \\___\\\\ \\___\\\\ \\___\\            \\ \\___\\      \\ \\___\\\\ \\___\\\\ \\___\\\\ \\___\\ \\ \\___\\\n" +
//                "    \\|____|      \\|_ __|      \\|____|\\|____|\\|___| \\|___|             \\|____|      \\|____|\\|____|\\|____|\\|____| \\|____|");
//        Label l1 = new Label("████ ████  ▄███▄  ██ ▄██     ██▄ ▄██  ▄███▄  ██▄ ██\n" +
//                "██▄  ██▄  ██▀ ▀██ ████▀      ██▀█▀██ ██▀ ▀██ ███▄██\n" +
//                "██▀  ██▀  ███████ ████▄  ▀▀▀ ██   ██ ███████ ██▀███\n" +
//                "██   ██   ██   ██ ██ ▀██     ██   ██ ██   ██ ██  ██");

        //Vart vill vi skriva menyn på skärmen?
        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35 / 2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 10;

        l2.setForegroundColor(TextColor.ANSI.WHITE);
        l2.setSize(new TerminalSize(76, 1));
        l2.addStyle(SGR.BOLD);
        l2.setPosition(new TerminalPosition(columnPosition + 13, rowPosition += 2));
//        for(int i = 0; i < l1.getText().length(); i++){
//            if(l1.getText().charAt(i) == '█' || l1.getText().charAt(i) == '▄' || l1.getText().charAt(i) == '▀'){
//                l1.getText().charAt(i).
//            }
//            else{
//                l1.addStyle(SGR.BLINK);
//            }
//        }
        l1.addStyle(SGR.BLINK);

//        l1.setForegroundColor(TextColor.ANSI.WHITE);
        l1.setSize(new TerminalSize(130, 10));
        l1.addStyle(SGR.BOLD);
        l1.setPosition(new TerminalPosition(37, 7));

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b2.setSize(new TerminalSize(35, 1));
        b2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b3.setSize(new TerminalSize(35, 1));
        b3.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        for (Button button : Arrays.asList(b1, b2, b3)) {
            contentArea.addComponent(button);
        }
        contentArea.addComponent(l1);
        contentArea.addComponent(l2);

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().setComponent(contentArea);
        while (!stop.get()) {
            if (!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }
    }

    public static void chooseMap() throws Exception {

        Screen screen = Game.getScreen();
        screen.clear();
        screen.setCursorPosition(null);

        //AtomicBoolean är en boolean som är trådsäker
        final AtomicBoolean stop = new AtomicBoolean(false);
        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new AbsoluteLayout());

        Label l1 = new Label("Choose Map");

        Button b1 = new Button("Map 1", () -> {
            Game.gameState = GameState.MENU_DIFFICULTY;
            Game.map.setMapRotate(1);
            stop.set(true);
        });
        Button b2 = new Button("Map 2", () -> {
            Game.gameState = GameState.MENU_DIFFICULTY;
            Game.map.setMapRotate(2);
            stop.set(true);
        });
        Button b3 = new Button("Map 3", () -> {
            Game.gameState = GameState.MENU_DIFFICULTY;
            Game.map.setMapRotate(3);
            stop.set(true);
        });
        Button b4 = new Button("Map 4", () -> {
            Game.gameState = GameState.MENU_DIFFICULTY;
            Game.map.setMapRotate(4);
            stop.set(true);
        });
        Button b5 = new Button("Map 5", () -> {
            Game.gameState = GameState.MENU_DIFFICULTY;
            Game.map.setMapRotate(5);
            stop.set(true);
        });
        Button b6 = new Button("Map 6", () -> {
            Game.gameState = GameState.MENU_DIFFICULTY;
            Game.map.setMapRotate(6);
            stop.set(true);
        });

        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35 / 2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 10;

        l1.setForegroundColor(TextColor.ANSI.WHITE);
        l1.setSize(new TerminalSize(76, 1));
        l1.addStyle(SGR.BOLD);
        l1.setPosition(new TerminalPosition(columnPosition + 13, rowPosition += 2));

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b2.setSize(new TerminalSize(35, 1));
        b2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b3.setSize(new TerminalSize(35, 1));
        b3.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b4.setSize(new TerminalSize(35, 1));
        b4.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b5.setSize(new TerminalSize(35, 1));
        b5.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b6.setSize(new TerminalSize(35, 1));
        b6.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        for (Button button : Arrays.asList(b1, b2, b3, b4, b5, b6)) {
            contentArea.addComponent(button);
        }
        contentArea.addComponent(l1);

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().setComponent(contentArea);
        while (!stop.get()) {
            if (!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }
    }

    public static void levelDifficulty() throws Exception {
        Screen screen = Game.getScreen();
        //Game.map.setMapRotate(6);
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
        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35 / 2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 10;

        l1.setForegroundColor(TextColor.ANSI.WHITE);
        l1.setSize(new TerminalSize(76, 1));
        l1.addStyle(SGR.BOLD);
        l1.setPosition(new TerminalPosition(columnPosition + 9, rowPosition += 2));

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b2.setSize(new TerminalSize(35, 1));
        b2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b3.setSize(new TerminalSize(35, 1));
        b3.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b4.setSize(new TerminalSize(35, 1));
        b4.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        for (Button button : Arrays.asList(b1, b2, b3, b4)) {
            contentArea.addComponent(button);
        }
        contentArea.addComponent(l1);

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().setComponent(contentArea);
        while (!stop.get()) {
            if (!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }
    }

    public static void finishedLevel() throws Exception {
        Screen screen = Game.getScreen();
        screen.clear();
        screen.setCursorPosition(null);

        Game.stats.setTotalScore();

        //AtomicBoolean är en boolean som är trådsäker
        final AtomicBoolean stop = new AtomicBoolean(false);
        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new AbsoluteLayout());

        boolean newMapHighScore = Game.highscore.isNewMapHighScore(); // NYTT

        Label l1 = new Label("You won!!!");
        Label l2 = new Label("Your time was: " + Statistics.formateTime(Game.stats.getMapTime())); // NYTT
        Label l3 = new Label("Your score on this map: " + Game.stats.getMapScore()); // NYTT


        //https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/buttons.md
        Button b1 = new Button("Continue to next map", () -> {
            Game.gameState = GameState.GAME;
            Game.stats.resetScores();
            stop.set(true);
        });

        Button b2 = new Button("View highscore", () -> {
            Game.gameState = GameState.HIGHSCORE;
            stop.set(true);
        });

        Button b4 = new Button("Back to main menu", () -> {
            Game.gameState = GameState.MENU;
            Game.stats.resetAllScores();
            stop.set(true);
        });


        //Vart vill vi skriva menyn på skärmen?
        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35 / 2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 20;

        l1.setForegroundColor(TextColor.ANSI.WHITE);
        l1.setSize(new TerminalSize(76, 1));
        l1.addStyle(SGR.BOLD);
        l1.setPosition(new TerminalPosition(columnPosition, rowPosition));

        l2.setForegroundColor(TextColor.ANSI.WHITE);
        l2.setSize(new TerminalSize(76, 1));
        l2.addStyle(SGR.BOLD);
        l2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        l3.setForegroundColor(TextColor.ANSI.WHITE);
        l3.setSize(new TerminalSize(76, 1));
        l3.addStyle(SGR.BOLD);
        l3.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b2.setSize(new TerminalSize(35, 1));
        b2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        for (Button button : Arrays.asList(b1, b2)) {
            contentArea.addComponent(button);
        }
        for (Label label : Arrays.asList(l1, l2, l3)) { // NYTT
            contentArea.addComponent(label);
        }
        if (newMapHighScore) { // NYTT
            Label l5 = new Label("NEW MAP HIGHSCORE!!!"); // NYTT

            l5.setForegroundColor(TextColor.ANSI.WHITE);
            l5.setSize(new TerminalSize(76, 1));
            l5.addStyle(SGR.BOLD);
            l5.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));


            for (Label label : Arrays.asList(l5)) { // NYTT
                contentArea.addComponent(label);
            }
        }

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().setComponent(contentArea);
        while (!stop.get()) {
            if (!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }

        int mapRotate = Game.map.getMapRotate();

        if (Game.getLevel() == 3 && Game.map.getMapRotate() == 5) {
            // vi ska tas till en slutmeny
        } else {
            if (mapRotate < 5) {
                mapRotate++;
                Game.map.setMapRotate(mapRotate);
            } else {
                Game.map.setMapRotate(1);
                Game.setLevel(2);
            }
        }
    }

    public static void gameOver() throws Exception {
        Screen screen = Game.getScreen();
        screen.clear();
        screen.setCursorPosition(null);

        gameOver = true;

        boolean newMapHighScore = Game.highscore.isNewMapHighScore(); // NYTT
        boolean newTotalHighScore = Game.highscore.isNewTotalHighScore(); // NYTT
        boolean newMapTimeScore = Game.timescore.isNewMapTimeScore();
        boolean newHighOrTime;

        if (!Game.map.isCoinMode()) {
            newHighOrTime = newMapTimeScore;
        } else {
            newHighOrTime = newMapHighScore;
        }

        //AtomicBoolean är en boolean som är trådsäker
        final AtomicBoolean stop = new AtomicBoolean(false);
        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new AbsoluteLayout());

        //Vart vill vi skriva menyn på skärmen?
        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35 / 2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 10;

        Label l1 = new Label("██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗ \n" +
                "██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗\n" +
                "██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝\n" +
                "██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗\n" +
                "╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║\n" +
                " ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝"); // NYTT
        Label l2 = new Label("Your time on this map was: " + Statistics.formateTime(Game.stats.getMapTime())); // NYTT
        Label l3 = new Label("Your score on this map: " + Game.stats.getMapScore()); // NYTT
        Label l4 = new Label("Your total score is: " + Game.stats.getTotalScore()); // NYTT


        //https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/buttons.md

        Button b1 = new Button("View highscore", () -> {
            Game.gameState = GameState.HIGHSCORE;
            stop.set(true);
        });

        Button b2 = new Button("View timescore", () -> {
            Game.gameState = GameState.TIMESCORE;
            stop.set(true);
        });

        Button b3 = new Button("Back to main menu", () -> {
            Game.gameState = GameState.MENU;
            Game.stats.resetAllScores();
            stop.set(true);
        });

        //l1.setForegroundColor(TextColor.ANSI.WHITE);
        l1.setSize(new TerminalSize(100, 8));
        l1.addStyle(SGR.BOLD);
        l1.addStyle(SGR.BLINK);
        l1.setPosition(new TerminalPosition(37, 8));

        l2.setForegroundColor(TextColor.ANSI.WHITE);
        l2.setSize(new TerminalSize(76, 1));
        l2.addStyle(SGR.BOLD);
        l2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        l3.setForegroundColor(TextColor.ANSI.WHITE);
        l3.setSize(new TerminalSize(76, 1));
        l3.addStyle(SGR.BOLD);
        l3.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        l4.setForegroundColor(TextColor.ANSI.WHITE);
        l4.setSize(new TerminalSize(76, 1));
        l4.addStyle(SGR.BOLD);
        l4.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        if (newHighOrTime) {
            Label l5 = new Label("NEW MAP HIGHSCORE!!!"); // NYTT

            l5.setForegroundColor(TextColor.ANSI.WHITE);
            l5.setSize(new TerminalSize(76, 1));
            l5.addStyle(SGR.BOLD);
            l5.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

            for (Label label : Arrays.asList(l5)) { // NYTT
                contentArea.addComponent(label);
            }
        }

        if (newTotalHighScore && Game.map.isCoinMode()) { // NYTT
            Label l7 = new Label("NEW TOTAL HIGHSCORE!!!");

            l7.setForegroundColor(TextColor.ANSI.WHITE);
            l7.setSize(new TerminalSize(76, 1));
            l7.addStyle(SGR.BOLD);
            l7.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

            for (Label label : Arrays.asList(l7)) { // NYTT
                contentArea.addComponent(label);
            }
        }

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b3.setSize(new TerminalSize(35, 1));
        b3.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));
        b2.setSize(new TerminalSize(35, 1));
        b2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        if (Game.map.isCoinMode()) {
            for (Button button : Arrays.asList(b1, b3)) {
                contentArea.addComponent(button);
            }
        }
        contentArea.addComponent(l1);
        if (!Game.map.isCoinMode()) { // NYTT
            contentArea.addComponent(l2);
        } else

        {
            for (Label label : Arrays.asList(l3, l4)) { // NYTT
                contentArea.addComponent(label);
            }
        }

        if (!Game.map.isCoinMode())

        {
            contentArea.addComponent(b2);
        }

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().

                setComponent(contentArea);
        while (!stop.get())

        {
            if (!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }

        Game.setLevel(1);
        Game.map.setMapRotate(1);
    }

    public static void highscoreMenu() throws Exception { // NYTT
        Screen screen = Game.getScreen();
        screen.clear();
        screen.setCursorPosition(null);
        String mapHighscore = "";
        String totalHighscore = "";

        //AtomicBoolean är en boolean som är trådsäker
        final AtomicBoolean stop = new AtomicBoolean(false);
        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new AbsoluteLayout());

        //Vart vill vi skriva menyn på skärmen?
        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35 / 2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 20;
        mapHighscore = Game.highscore.toString(Game.highscore.getMapHighscore());
        totalHighscore = Game.highscore.toString(Game.highscore.getTotalHighscore());

        Label l1 = new Label("HIGHSCORE ON THIS MAP!!!");
        Label l2 = new Label(mapHighscore);
        Label l3 = new Label("TOTAL HIGHSCORE!!!");
        Label l4 = new Label(totalHighscore);


        //https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/buttons.md

        Button b1 = new Button("Back to main menu", () -> {
            Game.gameState = GameState.MENU;
            Game.stats.resetAllScores();
            stop.set(true);
        });

        Button b2 = new Button("Continue to next map", () -> {
            Game.gameState = GameState.GAME;
            Game.stats.resetScores();
            stop.set(true);
        });

        l1.setForegroundColor(TextColor.ANSI.WHITE);
        l1.setSize(new TerminalSize(76, 1));
        l1.addStyle(SGR.BOLD);
        l1.setPosition(new TerminalPosition(columnPosition, rowPosition));

        l2.setForegroundColor(TextColor.ANSI.WHITE);
        l2.setSize(new TerminalSize(76, Game.highscore.getMapHighscore().size()));
        l2.addStyle(SGR.BOLD);
        l2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        l3.setForegroundColor(TextColor.ANSI.WHITE);
        l3.setSize(new TerminalSize(76, 1));
        l3.addStyle(SGR.BOLD);
        l3.setPosition(new TerminalPosition(columnPosition, rowPosition += 2 * Game.highscore.getMapHighscore().size()));

        l4.setForegroundColor(TextColor.ANSI.WHITE);
        l4.setSize(new TerminalSize(76, Game.highscore.getTotalHighscore().size()));
        l4.addStyle(SGR.BOLD);
        l4.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition += 2 * Game.highscore.getTotalHighscore().size()));
        b2.setSize(new TerminalSize(35, 1));
        b2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2 * Game.highscore.getTotalHighscore().size()));

        if (gameOver) contentArea.addComponent(b1);
        else contentArea.addComponent(b2);

        for (Label label : Arrays.asList(l1, l2, l3, l4)) {
            contentArea.addComponent(label);
        }

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().setComponent(contentArea);
        while (!stop.get()) {
            if (!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }

    }

    public static void timescoreMenu() throws Exception { // NYTT
        Screen screen = Game.getScreen();
        screen.clear();
        screen.setCursorPosition(null);
        String mapTimescore = "";


        //AtomicBoolean är en boolean som är trådsäker
        final AtomicBoolean stop = new AtomicBoolean(false);
        MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

        Panel contentArea = new Panel();
        contentArea.setLayoutManager(new AbsoluteLayout());

        //Vart vill vi skriva menyn på skärmen?
        int columnPosition = screen.getTerminalSize().getColumns() / 2 - 35 / 2;
        int rowPosition = screen.getTerminalSize().getRows() / 2 - 20;
        mapTimescore = Game.timescore.timescoretoString(Game.timescore.getMapTimescore());


        Label l1 = new Label("TiMESCORE ON THIS MAP!!!");
        Label l2 = new Label(mapTimescore);

        //https://github.com/mabe02/lanterna/blob/master/docs/examples/gui/buttons.md
        Button b1 = new Button("Back to main menu", () -> {
            Game.gameState = GameState.MENU;
            stop.set(true);
        });



        l1.setForegroundColor(TextColor.ANSI.WHITE);
        l1.setSize(new TerminalSize(76, 1));
        l1.addStyle(SGR.BOLD);
        l1.setPosition(new TerminalPosition(columnPosition, rowPosition));

        l2.setForegroundColor(TextColor.ANSI.WHITE);
        l2.setSize(new TerminalSize(76, Game.highscore.getMapHighscore().size()));
        l2.addStyle(SGR.BOLD);
        l2.setPosition(new TerminalPosition(columnPosition, rowPosition += 2));

        b1.setSize(new TerminalSize(35, 1));
        b1.setPosition(new TerminalPosition(columnPosition, rowPosition += 2 * Game.highscore.getTotalHighscore().size()));

        for (Button button : Arrays.asList(b1)) {
            contentArea.addComponent(button);
        }

        for (Label label : Arrays.asList(l1, l2)) {
            contentArea.addComponent(label);
        }

        //https://github.com/mabe02/lanterna/blob/master/src/test/java/com/googlecode/lanterna/gui2/FullScreenTextGUITest.java
        textGUI.getBackgroundPane().setComponent(contentArea);
        while (!stop.get()) {
            if (!textGUI.getGUIThread().processEventsAndUpdate()) {
                Thread.sleep(1);
            }
        }
    }

    public static void enterPlayerName() { // NYTT
        String playerName = TextInputDialog.showDialog(Game.textGUI, "ENTER YOUR NAME", "", "");
        Game.playerName = playerName;
        Game.gameState = GameState.MENU;
    }
}