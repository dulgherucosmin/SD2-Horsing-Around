// Horsing Around
// Group 9

package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;

public class Menu extends State implements StateMethods {

    private String[] labels = {"PLAY", "OPTIONS", "QUIT"};
    private Gamestate[] states = {Gamestate.PLAYING, Gamestate.OPTIONS, Gamestate.QUIT};
    private boolean[] hovered = new boolean[3];

    private Font titleFont;
    private Font menuFont;
    private BufferedImage bgImage;

    private static final int TITLE_Y   = (int)(Game.GAME_HEIGHT * 0.42f);
    private static final int MENU_START_Y = (int)(Game.GAME_HEIGHT * 0.60f);
    private static final int MENU_GAP  = (int)(Game.GAME_HEIGHT * 0.08f);

    private static final Color COL_NORMAL  = new Color(180, 200, 160);
    private static final Color COL_HOVERED = new Color(220, 235, 180);

    public Menu(Game game) {
        super(game);
        titleFont = LoadSave.loadFont("PressStart2P-Regular.ttf");
        menuFont  = LoadSave.loadFont("PressStart2P-Regular.ttf");
        bgImage   = LoadSave.GetSpriteAtlas("bg.png"); 
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        drawBackground(g);
        drawVignette(g2d);
        drawTitle(g);
        drawMenuItems(g);
    }

    private void drawBackground(Graphics g) {
        if (bgImage == null) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            return;
        }

        int imgW = bgImage.getWidth();
        int imgH = bgImage.getHeight();

        float scale = (float) Game.GAME_HEIGHT / imgH;
        int scaledW = (int)(imgW * scale);
        int scaledH = Game.GAME_HEIGHT;

        if (scaledW < Game.GAME_WIDTH) {
            scale = (float) Game.GAME_WIDTH / imgW;
            scaledW = Game.GAME_WIDTH;
            scaledH = (int)(imgH * scale);
        }

        int xOffset = (Game.GAME_WIDTH - scaledW) / 2;
        int yOffset = (Game.GAME_HEIGHT - scaledH) / 2;

        g.drawImage(bgImage, xOffset, yOffset, scaledW, scaledH, null);
    }

    private void drawVignette(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 80));
        g2d.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
    }

    private void drawTitle(Graphics g) {
        g.setFont(titleFont.deriveFont(Font.BOLD, 20f));
        FontMetrics fm = g.getFontMetrics();

        String line1 = "HORSING";
        String line2 = "AROUND";

        int lineH = fm.getHeight();

        g.setColor(new Color(0, 0, 0, 150));
        g.drawString(line1, (Game.GAME_WIDTH - fm.stringWidth(line1)) / 2 + 1, TITLE_Y - lineH + 1);
        g.drawString(line2, (Game.GAME_WIDTH - fm.stringWidth(line2)) / 2 + 1, TITLE_Y + 1);

        g.setColor(new Color(210, 220, 185));
        g.drawString(line1, (Game.GAME_WIDTH - fm.stringWidth(line1)) / 2, TITLE_Y - lineH);
        g.drawString(line2, (Game.GAME_WIDTH - fm.stringWidth(line2)) / 2, TITLE_Y);
    }

    private void drawMenuItems(Graphics g) {
        g.setFont(menuFont.deriveFont(8f));
        FontMetrics fm = g.getFontMetrics();

        for (int i = 0; i < labels.length; i++) {
            int y = MENU_START_Y + i * MENU_GAP;
            String text = labels[i];
            int textW = fm.stringWidth(text);
            int x = (Game.GAME_WIDTH - textW) / 2;

            if (hovered[i]) {
                g.setColor(COL_HOVERED);
                String arrow = "> " + text + " <";
                int arrowW = fm.stringWidth(arrow);
                g.drawString(arrow, (Game.GAME_WIDTH - arrowW) / 2, y);
            } else {
                g.setColor(COL_NORMAL);
                g.drawString(text, x, y);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < labels.length; i++) {
            if (getItemBounds(i).contains(e.getX(), e.getY())) {
                setGamestate(states[i]);
                return;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (int i = 0; i < labels.length; i++)
            hovered[i] = getItemBounds(i).contains(e.getX(), e.getY());
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private java.awt.Rectangle getItemBounds(int i) {
        float scaleX = (float) game.getGamePanel().getWidth() / Game.GAME_WIDTH;
        float scaleY = (float) game.getGamePanel().getHeight() / Game.GAME_HEIGHT;

        game.getGamePanel();
        Font f = menuFont.deriveFont(8f);

        int y = MENU_START_Y + i * MENU_GAP;
        int w = 120;
        int h = 14;
        int x = Game.GAME_WIDTH / 2 - w / 2;

        return new java.awt.Rectangle(
            (int)(x * scaleX), (int)((y - h) * scaleY),
            (int)(w * scaleX), (int)(h * scaleY)
        );
    }
}