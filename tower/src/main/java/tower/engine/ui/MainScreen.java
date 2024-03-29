package tower.engine.ui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import tower.engine.GM;
import tower.engine.Utils;
import tower.engine.entity.MobEntity;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MainScreen extends GameScreen implements IUpdateable {
  private Hud hud;

  public MainScreen() {
    super("main");
    hud = new Hud(0, 0);
    getComponents().add(hud);
    Game.graphics().onEntityRendered(e -> {
      if (e.getRenderedObject() instanceof MobEntity) {
        renderLifeBar((MobEntity) e.getRenderedObject(), e.getGraphics());
      }
    });
  }

  @Override
  public void update() {
    GM.update();
  }

  @Override
  public void render(Graphics2D g) {
    try {
      super.render(g);
      if (GM.getState() == GM.State.GAMEOVER) {
        // タワーだけになったらゲームオーバー
        g.setColor(Color.WHITE);
        g.setFont(Utils.fontNormal());
        TextRenderer.renderWithOutline(g, "esc key -> exit", 10, 15, Color.BLACK);
        g.setFont(Utils.fontLarge());
        TextRenderer.renderWithOutline(g, "game over:" + (GM.tower().isDead() ? "lose..." : "win!!"),
                                       Utils.screenWidth() * 0.3,
                                       Utils.screenHeight() * 0.3,
                                       Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        TextRenderer.renderWithOutline(g, "score:" + GM.tower().score(),
                                       Utils.screenWidth() * 0.3,
                                       Utils.screenHeight() * 0.3 + fm.getHeight() * 1.5,
                                       Color.BLACK);
      }
    } catch (Exception ignore) {}
  }

  public void renderLifeBar(MobEntity e, Graphics2D g) {
    if (!e.isDead()) {
      double hpRatio = e.getHitPoints().getCurrentValue().doubleValue() / e.getHitPoints().getMaxValue().doubleValue();
      double healthBarMaxWidth = e.getWidth();
      double healthBarWidth = healthBarMaxWidth * hpRatio;
      double healthBarHeight = 2.0;
      double x = Game.world().camera()
                     .getViewportDimensionCenter(e).getX() - healthBarWidth / 2.0;
      double y = Game.world().camera()
                     .getViewportDimensionCenter(e).getY() - (e.getHeight() * 3.0 / 4.0);
      Point2D healthBarOrigin = new Point2D.Double(x, y);
      Rectangle2D rect = new Rectangle2D.Double(healthBarOrigin.getX(), healthBarOrigin.getY(), healthBarWidth, healthBarHeight);
      g.setColor(Color.BLACK);
      g.fill(new Rectangle2D.Double(rect.getX() - 1, rect.getY() - 1, healthBarMaxWidth + 2, rect.getHeight() + 2));
      g.setColor(hpRatio < 0.5 ? Color.RED : Color.GREEN);
      g.fill(rect);
    }
  }

  @Override
  public void prepare() {
    super.prepare();
    Game.loop().attach(this);
  }

  @Override
  public void suspend() {
    super.suspend();
    Game.loop().detach(this);
  }
}
