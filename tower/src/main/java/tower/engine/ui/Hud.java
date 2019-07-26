package tower.engine.ui;

import de.gurkenlabs.litiengine.Game;
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

public class Hud extends GuiComponent {
  public static Color hudRedColor = new Color(188, 12, 12);
  public static Color shadowColor = new Color(48, 48, 48, 200);
  public static Color textColor = new Color(255, 255, 255);

  public Hud(double x, double y) {
    super(x, y);
  }

  public void render(Graphics2D g) {
    renderHealthBar(g);
    renderInventory(g);
    renderCount(g);
  }

  private void renderHealthBar(Graphics2D g) {
    double screenWidth = Game.screens().current().getWidth();
    double screenHeight = Game.screens().current().getHeight();
    double healthBarMaxWidth = screenWidth * 0.3;
    double healthBarHeight = screenHeight * 0.05;
    double healthBarX = (screenWidth - healthBarMaxWidth) / 2.0D;
    double healthBarY = screenHeight * 0.85;
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

  private void renderInventory(Graphics2D g) {
    double screenWidth = Game.screens().current().getWidth();
    double screenHeight = Game.screens().current().getHeight();
    double inventoryCellWidth = screenWidth * 0.03;
    double inventoryMargin = screenWidth * 0.015;
    double inventoryHeight = inventoryCellWidth;
    double inventoryX = (screenWidth / 2.0) - (inventoryCellWidth * 1.5) - (inventoryMargin);
    double inventoryY = screenHeight * 0.05;

    String[] icons = new String[]{"prop-painkiller", "prop-beer", "prop-carrot"};
    for (int i = 0; i < icons.length; i++) {
      Rectangle2D shadowRect = new Rectangle2D.Double(inventoryX + (inventoryCellWidth + inventoryMargin) * i,
                                                      inventoryY, inventoryCellWidth, inventoryHeight);
      g.setColor(shadowColor);
      g.fill(shadowRect);
      BufferedImage image = Resources.spritesheets().get(icons[i]).getImage();
      Point2D location = new Point2D.Double(inventoryX + (inventoryCellWidth + inventoryMargin) * i + inventoryCellWidth * 0.5 - image.getWidth() / 2,
                                            inventoryY + inventoryHeight * 0.5 - image.getHeight() / 2);
      ImageRenderer.render(g, image, location);
    }
  }

  private void renderCount(Graphics2D g) {
    double screenWidth = Game.screens().current().getWidth();
    double screenHeight = Game.screens().current().getHeight();
    g.setFont(Utils.fontLarge());
    FontMetrics fm = g.getFontMetrics();
    g.setColor(textColor);

    double countY = screenHeight * 0.85;
    String countText = GM.soldierCount();
    double countX = screenWidth * 0.05 - (double) fm.stringWidth(countText) / 2.0D;
    TextRenderer.renderWithOutline(g, countText, countX, countY, Color.BLACK);

    String enemyCountText = GM.enemyCount();
    double enemyCountX = screenWidth * 0.95 - (double) fm.stringWidth(enemyCountText) / 2.0D;
    TextRenderer.renderWithOutline(g, enemyCountText, enemyCountX, countY, Color.BLACK);
  }
}