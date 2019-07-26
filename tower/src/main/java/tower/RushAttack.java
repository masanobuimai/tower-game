package tower;

public interface RushAttack {
  boolean attack(Enemy enemy);

  interface Enemy {
    int getHp();

    double getHpRatio();

    double getDestRatio();
  }
}
