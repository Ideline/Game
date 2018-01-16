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
                if (Game.map.getMapRotate() == 5) {
                    for (int i = 0; i < 3; i++) {
                        enemies.add(new LeftEnemy(1));
                        enemies.add(new LeftEnemy(2));
                        enemies.add(new LeftEnemy(3));
                        enemies.add(new LeftEnemy(4));
                    }
                }
                enemies.add(new EasyEnemy());
                enemies.add(new MediumEnemy());
                enemies.add(new HardEnemy());
                enemies.add(new EasyEnemy());
                break;
            case 2:
                if (Game.map.getMapRotate() == 5) {
                    for (int i = 0; i < 3; i++) {
                        enemies.add(new LeftEnemy(1));
                        enemies.add(new LeftEnemy(2));
                        enemies.add(new LeftEnemy(3));
                        enemies.add(new LeftEnemy(4));
                    }
                }
                enemies.add(new EasyEnemy());
                enemies.add(new MediumEnemy());
                enemies.add(new HardEnemy());
                enemies.add(new EasyEnemy());
                enemies.add(new HardEnemy());
                break;
            case 3:
                if (Game.map.getMapRotate() == 5) {
                    for (int i = 0; i < 3; i++) {
                        enemies.add(new LeftEnemy(1));
                        enemies.add(new LeftEnemy(2));
                        enemies.add(new LeftEnemy(3));
                        enemies.add(new LeftEnemy(4));
                    }
                }
                enemies.add(new EasyEnemy());
                enemies.add(new MediumEnemy());
                enemies.add(new HardEnemy());
                enemies.add(new EasyEnemy());
                enemies.add(new MediumEnemy());
                enemies.add(new HardEnemy());
                break;
        }
    }

    public void create() throws Exception {
        int gcd = 0;
        for (Enemy e : enemies) {
            if (e instanceof EasyEnemy) {
                ((EasyEnemy) e).start(gcd);
                gcd += 10000;
            } else if (e instanceof MediumEnemy) {
                ((MediumEnemy) e).start(gcd);
                gcd += 10000;
            } else if (e instanceof HardEnemy) {
                ((HardEnemy) e).start(gcd);
                gcd += 10000;
            }
        }
    }

    public void createLeftEnemies() throws Exception {
        int gcd = 0;
        for (Enemy e : enemies) {

            if (e instanceof LeftEnemy) {
                ((LeftEnemy) e).start(gcd);
            }
        }
    }

}
