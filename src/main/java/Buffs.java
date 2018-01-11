public class Buffs implements Runnable{

    private boolean runSpeed = false;
    private boolean reverse = false;
    private Thread t;
    private String threadName = "buffThread";
    private volatile boolean running = true;

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

    public void speedBuff() throws Exception{
        if(runSpeed) {
            int gcd = 5000;
            Game.player.setGlobalDelay(0);
            Thread.sleep(gcd);
            Game.player.setGlobalDelay(200);
            runSpeed = false;
            running = false;
        }
    }

    private void reversedControlls()throws Exception{
        if(reverse){
            Thread.sleep(20000);
            Player.reverse = false;
            reverse = false;
            running = false;
        }
    }
}
