import com.googlecode.lanterna.TextColor;

public class Buffs implements Runnable{

    private boolean runSpeed = false;
    private boolean reverse = false;
    private boolean cherryMode = false;
    private boolean wallWalkerMode = false;
    private Thread t;
    private String threadName = "buffThread";
    private volatile boolean running = true;

    public Buffs(){}

    public Buffs(long timeLeft, String buffName, TextColor tc){
        this.timLeft = timeLeft;
        this.buffName = buffName;
        this.tc = tc;
    }

    private long timLeft;
    private String buffName;
    private TextColor tc;

    public void start() throws Exception {

        if(t == null) {
            t = new Thread(this, threadName);
            t.start();

        }
    }

    public void run() {
        try {
            while (running){
                speedBuff();
                reversedControlls();
                cherryBuff();
                wallWalker();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void setRunSpeed(boolean buff) {
        this.runSpeed = buff;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public void setCherryMode(boolean cherryMode) {
        this.cherryMode = cherryMode;
    }

    public void setWallWalkerMode(boolean wallWalkerMode) {
        this.wallWalkerMode = wallWalkerMode;
    }

    public void speedBuff() throws Exception{
        if(runSpeed) {
            int gcd = 5000;
            Game.player.setGlobalDelay(0);
            Thread.sleep(gcd);
            //buffStartTime = System.currentTimeMillis();
            Map.printToScreen(Game.player.getX(), Game.player.getY(), '☻', TextColor.ANSI.YELLOW);
            Game.player.setGlobalDelay(200);
            Player.speedBuff = false;
            runSpeed = false;
            running = false;
            Player.buffName = "";
        }
    }

    private void reversedControlls()throws Exception{
        if(reverse){
            Thread.sleep(20000);
            Map.printToScreen(Game.player.getX(), Game.player.getY(), '☻', TextColor.ANSI.YELLOW);
            Player.reverse = false;
            reverse = false;
            running = false;
            Player.buffName = "";
        }
    }

    private void cherryBuff()throws Exception{
        if(cherryMode){
            Thread.sleep(10000);
            Map.printToScreen(Game.player.getX(), Game.player.getY(), '☻', TextColor.ANSI.YELLOW);
            Player.cherryMode = false;
            cherryMode = false;
            running = false;
            Player.buffName = "";
        }
    }

    private void wallWalker()throws Exception{
        if(wallWalkerMode){
            Thread.sleep(10000);
            Map.printToScreen(Game.player.getX(), Game.player.getY(), '☻', TextColor.ANSI.YELLOW);
            Player.wallWalker = false;
            wallWalkerMode = false;
            running = false;
            Player.buffName = "";
        }
    }
}
