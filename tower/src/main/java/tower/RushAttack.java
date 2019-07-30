package tower;

public interface RushAttack {
  int MAX_RUSH_ATTACK_COUNT = 5;

  boolean attack(Enemy enemy);

  interface Enemy {
    int getHp();

    double getHpRatio();

    double getDestRatio();
  }
}
