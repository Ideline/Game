import java.util.ArrayList;

public class Enemies {
    public Enemies() {

    }

    private ArrayList<Enemy> enemies = null;

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void init(int level) {
        enemies = new ArrayList<Enemy>();
        switch (level) {
            case 1:
                enemies.add(new EasyEnemy());
                enemies.add(new EasyEnemy());
                enemies.add(new MediumEnemy());
                enemies.add(new HardEnemy());
                break;
            case 2:
                enemies.add(new EasyEnemy());
                enemies.add(new EasyEnemy());
                enemies.add(new MediumEnemy());
                enemies.add(new HardEnemy());
                break;
            case 3:
                enemies.add(new EasyEnemy());
                enemies.add(new EasyEnemy());
                enemies.add(new MediumEnemy());
                enemies.add(new HardEnemy());
                break;
        }
    }

    public void create() throws Exception {
        int gcd = 0;
        for(Enemy e : enemies) {
            if (e instanceof EasyEnemy) {
                ((EasyEnemy) e).start(gcd);
            } else if (e instanceof MediumEnemy) {
                ((MediumEnemy) e).start(gcd);
            } else {
                ((HardEnemy) e).start(gcd);
            }
            gcd += 10000;
        }
    }

}
