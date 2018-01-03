//import com.googlecode.lanterna.TextColor;
//import com.googlecode.lanterna.terminal.Terminal;
//
//public class SkeletonMap implements Runnable {
//
//    public SkeletonMap(Terminal terminal, int mapRowLength){
//        this.terminal = terminal;
//        this.mapRowLength = mapRowLength;
//    }
//
//    private Thread t;
//    private Terminal terminal;
//    private int mapRowLength;
//
//    public void run() {
//        int mapPaddingY = 40;
//        int mapPaddingX = 28;
//
//        while(true) {
//            try {
//                for (int i = 0; i < Game.map.length; i++) {
//                    int y = (i > mapRowLength - 1 ? (i / mapRowLength) : 0) + mapPaddingY;
//                    int x = (i > 0 ? (i % mapRowLength) : 0) + mapPaddingX;
//                    terminal.setCursorPosition(x, y);
//                    terminal.setForegroundColor(TextColor.ANSI.WHITE);
//                    terminal.putCharacter(Game.map[i].charAt(0));
//                }
//                terminal.flush();
//            }
//            catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public void start() {
//        if(t == null) {
//            t = new Thread(this, "skeletonMap");
//            t.start();
//        }
//    }
//}
