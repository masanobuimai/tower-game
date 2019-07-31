package tower.engine.ui;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import tower.engine.Utils;

import java.awt.*;

public class TitleScreen extends GameScreen implements IUpdateable {

  public TitleScreen() {
    super("title");
  }

  @Override
  public void update() {
  }

  @Override
  public void render(Graphics2D g) {
    super.render(g);
    g.setColor(Color.WHITE);
    g.setFont(Utils.fontLarge());
    FontMetrics fm = g.getFontMetrics();
    String title = "space key -> game start!!";
    TextRenderer.renderWithOutline(g, title, (Utils.screenWidth() / 2) - (fm.stringWidth(title) / 2),
                                   Utils.screenHeight() * 0.3, Color.BLACK);
  }

  @Override
  public void prepare() {
    super.prepare();
    Game.loop().attach(this);
    Game.world().loadEnvironment("map");
  }

  @Override
  public void suspend() {
    super.suspend();
    Game.loop().detach(this);
  }
}
