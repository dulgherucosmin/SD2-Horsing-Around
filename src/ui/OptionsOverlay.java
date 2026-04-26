package ui;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import main.Game;

public class OptionsOverlay {
    private Game game;

    private Font pixelFont;

    // fps selection variables
    private int selectedFPS = 120;
    private int[] fpsOptions = {30, 60, 120};
    private Rectangle[] fpsButtons = new Rectangle[3];

    

    // horse selection
    private Rectangle[] p1ColourButtons = new Rectangle[8];
    private Rectangle[] p2ColourButtons = new Rectangle[8];
    private int p1SelectedHorse = 0;
    private int p2SelectedHorse = 1;

    private BufferedImage[] horsePreviews = new BufferedImage[8];

    private int hoveredFPS = -1;
    private int hoveredP1 = -1;
    private int hoveredP2 = -1;


    // animation
    private BufferedImage[][] horseIdleFrames = new BufferedImage[8][3];
    private int animTick = 0;
    private int animFrame = 0;
    private static final int ANIM_SPEED = 15;

    // small portrait boxes
    private Rectangle p1PortraitBox;
    private Rectangle p2PortraitBox;

    // back button
    private Rectangle backButton;
    private boolean hoveredBack = false;

    // layout
    private static final int TITLE_Y = (int)(GAME_HEIGHT * 0.10f);
    private static final int FPS_Y = (int)(GAME_HEIGHT * 0.13f);
    private static final int LABEL_Y = (int)(GAME_HEIGHT * 0.28f);
    private static final int PORTRAIT_Y = (int)(GAME_HEIGHT * 0.33f);
    private static final int GRID_Y = (int)(GAME_HEIGHT * 0.55f);
    private static final int BACK_Y = (int)(GAME_HEIGHT * 0.88f);

    private static final int HORSE_SIZE = (int)(GAME_WIDTH * 0.058f);
    private static final int HORSE_GAP = (int)(GAME_WIDTH * 0.012f);

    private static final int PORTRAIT_W = (int)(GAME_WIDTH * 0.09f);
    private static final int PORTRAIT_H = (int)(GAME_HEIGHT * 0.18f);

    private static final int P1_CENTER_X = (int)(GAME_WIDTH * 0.27f);
    private static final int P2_CENTER_X = (int)(GAME_WIDTH * 0.73f);

    public OptionsOverlay(Game game) {
        this.game = game;
        pixelFont = LoadSave.loadFont("PressStart2P-Regular.ttf");
        loadHorsePreviews();
        loadIdleFrames();
        createButtons();
    }

    // this loads the sprite of all the horses
    public void loadHorsePreviews() {
        for (int i = 0; i < 8; i++) {
            BufferedImage sheet = LoadSave.GetSpriteAtlas(LoadSave.ALL_HORSES[i]);
            if (sheet != null)
                horsePreviews[i] = sheet.getSubimage(0, 0, 64, 48);
        }
    }

    private void loadIdleFrames() {
        for (int i = 0; i < 8; i++) {
            BufferedImage sheet = LoadSave.GetSpriteAtlas(LoadSave.ALL_HORSES[i]);
            if (sheet != null) {
                for (int f = 0; f < 3; f++) {
                    horseIdleFrames[i][f] = sheet.getSubimage(f * 64, 48, 64, 48);
                }
            }
        }
    }

    // this method creates and positions buttons in the menu
    private void createButtons() {
        // fps buttons — centered at top
        int fpsTotalW = 3 * 60 + 2 * 10;
        int fpsStartX = GAME_WIDTH / 2 - fpsTotalW / 2;
        for (int i = 0; i < 3; i++)
            fpsButtons[i] = new Rectangle(fpsStartX + i * 70, FPS_Y, 60, 20);

        // small portrait boxes
        p1PortraitBox = new Rectangle(P1_CENTER_X - PORTRAIT_W / 2, PORTRAIT_Y, PORTRAIT_W, PORTRAIT_H);
        p2PortraitBox = new Rectangle(P2_CENTER_X - PORTRAIT_W / 2, PORTRAIT_Y, PORTRAIT_W, PORTRAIT_H);

        // horse grids
        int gridTotalW = 4 * HORSE_SIZE + 3 * HORSE_GAP;
        int p1GridStartX = P1_CENTER_X - gridTotalW / 2;
        int p2GridStartX = P2_CENTER_X - gridTotalW / 2;

        //this ensures theres not more than 4 hourses in a row for both horses
        for (int i = 0; i < 8; i++) {
            int row = i / 4;
            int col = i % 4;

            int xOff = col * (HORSE_SIZE + HORSE_GAP);
            int yOff = row * (HORSE_SIZE + HORSE_GAP);

            p1ColourButtons[i] = new Rectangle(p1GridStartX + xOff, GRID_Y + yOff, HORSE_SIZE, HORSE_SIZE);
            p2ColourButtons[i] = new Rectangle(p2GridStartX + xOff, GRID_Y + yOff, HORSE_SIZE, HORSE_SIZE);
        }

        // back button
        int backW = 80;
        int backH = 20;
        backButton = new Rectangle(GAME_WIDTH / 2 - backW / 2, BACK_Y, backW, backH);
    }

    public void update() {
        animTick++;
        if (animTick >= ANIM_SPEED) {
            animTick = 0;
            animFrame = (animFrame + 1) % 3;
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        g.setColor(new Color(15, 15, 15));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        drawTitle(g);
        drawFPSButtons(g);
        drawPlayerColumn(g, 1, p1ColourButtons, p1SelectedHorse, hoveredP1, p2SelectedHorse, p1PortraitBox);
        drawPlayerColumn(g, 2, p2ColourButtons, p2SelectedHorse, hoveredP2, p1SelectedHorse, p2PortraitBox);
        drawBackButton(g);
    }

    // this draws the options title and positions it at the top
    private void drawTitle(Graphics g) {
        g.setFont(pixelFont.deriveFont(12f));
        g.setColor(Color.WHITE);
        String title = "OPTIONS";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(title, (GAME_WIDTH - fm.stringWidth(title)) / 2, TITLE_Y);
    }

    private void drawFPSButtons(Graphics g) {
        g.setFont(pixelFont.deriveFont(5f));

        for (int i = 0; i < 3; i++) {
            Rectangle btn = fpsButtons[i];

            // this created a small features to show what fps buttons has been selected
            if (fpsOptions[i] == selectedFPS)
                g.setColor(new Color(255, 215, 0));
            else if (hoveredFPS == i)
                g.setColor(new Color(80, 80, 80));
            else
                g.setColor(new Color(40, 40, 40));

            g.fillRoundRect(btn.x, btn.y, btn.width, btn.height, 6, 6);

            g.setColor(fpsOptions[i] == selectedFPS ? Color.BLACK : Color.WHITE);
            String text = fpsOptions[i] + " FPS";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(text, btn.x + (btn.width - fm.stringWidth(text)) / 2, btn.y + 13);
        }
    }

    private void drawPlayerColumn(Graphics g, int playerNum, Rectangle[] buttons, int selected, int hovered, int otherSelected, Rectangle portraitBox) {
        Graphics2D g2d = (Graphics2D) g;

        // player label
        g.setFont(pixelFont.deriveFont(6f));
        g.setColor(Color.WHITE);
        String label = "PLAYER " + playerNum;
        FontMetrics fm = g.getFontMetrics();
        g.drawString(label, portraitBox.x + (portraitBox.width - fm.stringWidth(label)) / 2, LABEL_Y);

        // small portrait box background
        g.setColor(new Color(30, 30, 30));
        g.fillRoundRect(portraitBox.x, portraitBox.y, portraitBox.width, portraitBox.height, 6, 6);

        // portrait border
        g2d.setStroke(new BasicStroke(2));
        g.setColor(new Color(255, 215, 0));
        g.drawRoundRect(portraitBox.x, portraitBox.y, portraitBox.width, portraitBox.height, 6, 6);
        g2d.setStroke(new BasicStroke(1));

        // animated selected horse in portrait
        if (horseIdleFrames[selected][animFrame] != null) {
            g.drawImage(horseIdleFrames[selected][animFrame],
                        portraitBox.x, portraitBox.y,
                        portraitBox.width, portraitBox.height, null);
        }

        // horse grid
        for (int i = 0; i < 8; i++) {
            Rectangle btn = buttons[i];

            if (horsePreviews[i] != null)
                g.drawImage(horsePreviews[i], btn.x, btn.y, btn.width, btn.height, null);
            else {
                g.setColor(new Color(60, 60, 60));
                g.fillRect(btn.x, btn.y, btn.width, btn.height);
            }

            // selected border
            if (i == selected) {
                g2d.setStroke(new BasicStroke(2));
                g.setColor(new Color(255, 215, 0));
                g.drawRoundRect(btn.x - 2, btn.y - 2, btn.width + 4, btn.height + 4, 4, 4);
                g2d.setStroke(new BasicStroke(1));
            } else if (i == hovered) {
                g.setColor(Color.WHITE);
                g.drawRoundRect(btn.x - 1, btn.y - 1, btn.width + 2, btn.height + 2, 4, 4);
            }

            // taken by other player
            if (i == otherSelected) {
                g.setColor(new Color(0, 0, 0, 160));
                g.fillRect(btn.x, btn.y, btn.width, btn.height);
                g.setFont(pixelFont.deriveFont(7f));
                g.setColor(Color.RED);
                FontMetrics fm2 = g.getFontMetrics();
                g.drawString("X",
                    btn.x + (btn.width - fm2.stringWidth("X")) / 2,
                    btn.y + btn.height / 2 + fm2.getAscent() / 2 - 1);
            }
        }
    }

    private void drawBackButton(Graphics g) {
        g.setColor(hoveredBack ? new Color(80, 80, 80) : new Color(40, 40, 40));
        g.fillRoundRect(backButton.x, backButton.y, backButton.width, backButton.height, 6, 6);

        g.setFont(pixelFont.deriveFont(5f));
        g.setColor(Color.WHITE);
        String text = "BACK";
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text,
            backButton.x + (backButton.width - fm.stringWidth(text)) / 2,
            backButton.y + 13);
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {
        int mx = getScaledX(e);
        int my = getScaledY(e);

        if (backButton.contains(mx, my)) {
            gamestates.Gamestate.state = gamestates.Gamestate.MENU;
            return;
        }

        for (int i = 0; i < 3; i++) {
            if (fpsButtons[i].contains(mx, my)) {
                selectedFPS = fpsOptions[i];
                game.setFPS(selectedFPS);
                return;
            }
        }
        // checks player 1 button to ensure its not the same as player2 before changing the colour
        for (int i = 0; i < 8; i++) {
            if (p1ColourButtons[i].contains(mx, my) && i != p2SelectedHorse) {
                p1SelectedHorse = i;
                game.getPlaying().getPlayer1().setColour(LoadSave.ALL_HORSES[i]);
                return;
            }
        }
        // checks player 2 button to ensure its not the same as player1 before changing the colour
        for (int i = 0; i < 8; i++) {
            if (p2ColourButtons[i].contains(mx, my) && i != p1SelectedHorse) {
                p2SelectedHorse = i;
                game.getPlaying().getPlayer2().setColour(LoadSave.ALL_HORSES[i]);
                return;
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        int mx = getScaledX(e);
        int my = getScaledY(e);

        hoveredFPS = -1;
        hoveredP1 = -1;
        hoveredP2 = -1;
        hoveredBack = false;

        if (backButton.contains(mx, my)) hoveredBack = true;

        for (int i = 0; i < 3; i++)
            if (fpsButtons[i].contains(mx, my)) hoveredFPS = i;

        for (int i = 0; i < 8; i++)
            if (p1ColourButtons[i].contains(mx, my)) hoveredP1 = i;

        for (int i = 0; i < 8; i++)
            if (p2ColourButtons[i].contains(mx, my)) hoveredP2 = i;
    }

    private int getScaledX(MouseEvent e) {
        float scaleX = (float) game.getGamePanel().getWidth() / GAME_WIDTH;
        return (int) (e.getX() / scaleX);
    }

    private int getScaledY(MouseEvent e) {
        float scaleY = (float) game.getGamePanel().getHeight() / GAME_HEIGHT;
        return (int) (e.getY() / scaleY);
    }

    public int getP1SelectedHorse() { 
        return p1SelectedHorse; 
    }
    public void setP1SelectedHorse(int i) { 
        if (i != p2SelectedHorse) 
            p1SelectedHorse = i; 
        }
    public int getP2SelectedHorse() { 
        return p2SelectedHorse; 
    }
    public void setP2SelectedHorse(int i) { 
        if (i != p1SelectedHorse) 
            p2SelectedHorse = i; 
    }
}