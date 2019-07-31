package tower.engine.ui;

import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.resources.Resources;
import tower.engine.GM;
import tower.engine.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class Hud extends GuiComponent {
  public static Color hudRedColor = new Color(188, 12, 12);
  public static Color shadowColor = new Color(48, 48, 48, 200);
  public static Color textColor = new Color(255, 255, 255);

  public Hud(double x, double y) {
    super(x, y);
  }

  public void render(Graphics2D g) {
    renderHealthBar(g);
    renderAbility(g);
    renderCount(g);
  }

  private static final Ability[] ABILITIES = new Ability[]{
      new Ability("prop-painkiller",
                  () -> GM.abilityRecover().v1, () -> GM.abilityRecover().v2),
      new Ability("prop-beer",
                  () -> GM.abilityShake().v1, () -> GM.abilityShake().v2),
      new Ability("prop-carrot",
                  () -> GM.abilityRush().v1, () -> GM.abilityRush().v2),
      };

  private static class Ability {
    String name;
    Supplier<Boolean> available;
    Supplier<Integer> count;

    public Ability(String name, Supplier<Boolean> available, Supplier<Integer> count) {
      this.name = name;
      this.available = available;
      this.count = count;
    }
  }

  private void renderHealthBar(Graphics2D g) {
    double healthBarMaxWidth = Utils.screenWidth() * 0.3;
    double healthBarHeight = Utils.screenHeight() * 0.05;
    double healthBarX = (Utils.screenWidth() - healthBarMaxWidth) / 2.0D;
    double healthBarY = Utils.screenHeight() * 0.85;
    int hp = GM.tower().getHitPoints().getCurrentValue();
    int maxHp = GM.tower().getHitPoints().getMaxValue();
    double currentHealthRatio = hp * 1.0 / maxHp;
    String healthRatioText = String.valueOf(hp);
    Rectangle2D healthShadowRect = new Rectangle2D.Double(healthBarX - 2.0D, healthBarY - 2.0D,
                                                          healthBarMaxWidth + 4.0D, healthBarHeight + 4.0D);
    Rectangle2D healthRect = new Rectangle2D.Double(healthBarX, healthBarY,
                                                    currentHealthRatio * healthBarMaxWidth, healthBarHeight);
    g.setFont(Utils.fontLarge());
    FontMetrics fm = g.getFontMetrics();
    double healthTextX = healthBarX + healthBarMaxWidth / 2.0D - (double) fm.stringWidth(healthRatioText) / 2.0D;
    double healthTextY = healthBarY + (double) fm.getAscent() + (healthBarHeight - (double) (fm.getAscent() + fm.getDescent())) / 2.0D;

    g.setColor(shadowColor);
    g.fill(healthShadowRect);
    g.setColor(hudRedColor);
    g.fill(healthRect);
    g.setColor(textColor);
    TextRenderer.renderWithOutline(g, healthRatioText, healthTextX, healthTextY, Color.BLACK);
  }

  private void renderAbility(Graphics2D g) {
    double abilityCellWidth = Utils.screenWidth() * 0.03;
    double abilityMargin = Utils.screenWidth() * 0.015;
    double abilityHeight = abilityCellWidth;
    double abilityX = (Utils.screenWidth() / 2.0) - (abilityCellWidth * 1.5) - (abilityMargin);
    double abilityY = Utils.screenHeight() * 0.05;

    for (int i = 0; i < ABILITIES.length; i++) {
      Rectangle2D shadowRect = new Rectangle2D.Double(abilityX + (abilityCellWidth + abilityMargin) * i,
                                                      abilityY, abilityCellWidth, abilityHeight);
      g.setColor(shadowColor);
      g.fill(shadowRect);

      g.setFont(Utils.fontNormal());
      FontMetrics fm = g.getFontMetrics();
      g.setColor(textColor);
      TextRenderer.renderWithOutline(g, "f" + (i + 1), shadowRect.getX(),
                                     shadowRect.getY() + fm.getHeight(), Color.BLACK);

      if (ABILITIES[i].available.get() && ABILITIES[i].count.get() > 0) {
        BufferedImage image = Resources.spritesheets().get(ABILITIES[i].name).getImage();
        double x = abilityX + (abilityCellWidth + abilityMargin) * i + abilityCellWidth * 0.5 - image.getWidth() / 2;
        double y = abilityY + abilityHeight * 0.5 - image.getHeight() / 2;
        ImageRenderer.render(g, image, new Point2D.Double(x, y));

        g.setColor(textColor);
        TextRenderer.renderWithOutline(g, String.valueOf(ABILITIES[i].count.get()), x, y * 2, Color.BLACK);
      }
    }
  }

  private void renderCount(Graphics2D g) {
    g.setFont(Utils.fontLarge());
    FontMetrics fm = g.getFontMetrics();
    g.setColor(textColor);

    double countY = Utils.screenHeight() * 0.85;
    String countText = GM.soldierCount();
    double countX = Utils.screenWidth() * 0.05 - (double) fm.stringWidth(countText) / 2.0D;
    TextRenderer.renderWithOutline(g, countText, countX, countY, Color.BLACK);

    String enemyCountText = GM.enemyCount();
    double enemyCountX = Utils.screenWidth() * 0.95 - (double) fm.stringWidth(enemyCountText) / 2.0D;
    TextRenderer.renderWithOutline(g, enemyCountText, enemyCountX, countY, Color.BLACK);
  }
}
