package ui;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import utilz.LoadSave;
import main.Game;

public class OptionsOverlay {
    private Game game;

    private int panelW = 380;
    private int panelH = 220;
    private int panelX = GAME_WIDTH / 2 - panelW / 2;
    private int panelY = GAME_HEIGHT / 2 - panelH / 2;

    private int selectedFPS = 120;
    private int[] fpsOptions = {30, 60, 120};
    private java.awt.Rectangle[] fpsButtons = new Rectangle[3];

    private Rectangle[] p1ColourButtons = new Rectangle[8];
    private Rectangle[] p2ColourButtons = new Rectangle[8];

    private int p1SelectedHorse = 0;
    private int p2SelectedHorse = 1;

    private BufferedImage[] horsePreviews = new BufferedImage[8];

    private int hoveredFPS = -1;
    private int hoveredP1 = -1;
    private int hoveredP2 = -1;

    public OptionsOverlay(Game game) {
        this.game = game;
        loadHorsePreviews();
        createButtons();
    }

    public void loadHorsePreviews() {
        for (int i = 0; i < 8; i++) {
            BufferedImage sheet = LoadSave.GetSpriteAtlas(LoadSave.ALL_HORSES[i]);
            if (sheet != null)
                horsePreviews[i] = sheet.getSubimage(0, 0, 64, 48);
        }
    }

    private void createButtons() {
        int fpsY = panelY + 30;
        int fpsStartX = panelX + panelW / 2 - 90;

        for (int i = 0; i < 3; i++)
            fpsButtons[i] = new java.awt.Rectangle(fpsStartX + i * 65, fpsY, 55, 20);

        int horseSize = 36;
        int horseGap = 8;

        int p1StartX = panelX + 15;
        int p1Row1Y = panelY + 80;
        int p1Row2Y = panelY + 125;

        for (int i = 0; i < 8; i++) {
            int row = i / 4;
            int col = i % 4;
            int x = p1StartX + col * (horseSize + horseGap);
            int y = (row == 0) ? p1Row1Y : p1Row2Y;
            p1ColourButtons[i] = new Rectangle(x, y, horseSize, horseSize);
        }

        int p2StartX = panelX + panelW / 2 + 10;
        int p2Row1Y = panelY + 80;
        int p2Row2Y = panelY + 125;

        for (int i = 0; i < 8; i++) {
            int row = i / 4;
            int col = i % 4;
            int x = p2StartX + col * (horseSize + horseGap);
            int y = (row == 0) ? p2Row1Y : p2Row2Y;
            p2ColourButtons[i] = new Rectangle(x, y, horseSize, horseSize);
        }
    }

    public void update() {}

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(new Color(30, 30, 30, 230));
        g.fillRoundRect(panelX, panelY, panelW, panelH, 12, 12);

        drawTitle(g);
        drawFPSButtons(g);
        drawPlayerLabels(g);
        drawHorseColourButtons(g);
    }

    private void drawTitle(Graphics g) {
        g.setFont(new Font("Comic Sans", Font.BOLD, 12));
        g.setColor(Color.WHITE);
        String title = "OPTIONS";
        g.drawString(title, GAME_WIDTH / 2 - 20, panelY + 18);
    }

    private void drawFPSButtons(Graphics g) {
        g.setFont(new Font("Comic Sans", Font.BOLD, 8));
        g.setColor(Color.WHITE);
        g.drawString("FPS", panelX + panelW / 2 - 105, fpsButtons[0].y + 14);

        for (int i = 0; i < 3; i++) {
            Rectangle btn = fpsButtons[0];

            if (fpsOptions[i] == selectedFPS)
                g.setColor(new Color(255, 215, 0));
            else if (hoveredFPS == i)
                g.setColor(new Color(180, 180, 180));
            else
                g.setColor(new Color(60, 60, 60));

            g.fillRoundRect(btn.x, btn.y, btn.width, btn.height, 6, 6);
            g.setColor(Color.WHITE);
            g.drawString(fpsOptions[i] + "FPS", btn.x + 6, btn.y + 13);
        }
    }

    private void drawPlayerLabels(Graphics g) {
        g.setFont(new Font("Comic Sans", Font.BOLD, 8));
        g.setColor(Color.WHITE);
        g.drawString("PLAYER 1", panelX + 15, panelY + 72);
        g.drawString("PLAYER 2", panelX + panelW / 2 + 10, panelY + 72);
    }

    private void drawHorseColourButtons(Graphics g) {
        for (int i = 0; i < 8; i++) {
            drawHorseButton(g, p1ColourButtons[i], i, p1SelectedHorse, hoveredP1, p2SelectedHorse);
            drawHorseButton(g, p2ColourButtons[i], i, p2SelectedHorse, hoveredP2, p1SelectedHorse);
        }
    }

    private void drawHorseButton(Graphics g, Rectangle btn, int i, int selectedHorse, int hoveredHorse, int selectedHorse2) {
        if (horsePreviews[i] != null)
            g.drawImage(horsePreviews[i], btn.x, btn.y, btn.width, btn.height, null);
        else {
            g.setColor(new Color(80, 80, 80));
            g.fillRect(btn.x, btn.y, btn.width, btn.height);
        }

        if (i == selectedHorse) {
            g.setColor(new Color(255, 215, 0));
            g.drawRoundRect(btn.x - 1, btn.y - 1, btn.width + 2, btn.height + 2, 3, 3);
        } else if (i == hoveredHorse) {
            g.setColor(Color.WHITE);
            g.drawRoundRect(btn.x - 1, btn.y - 1, btn.width + 2, btn.height + 2, 3, 3);
        }

        if (i == selectedHorse2) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(btn.x, btn.y, btn.width, btn.height);
            g.setFont(new Font("Comic Sans", Font.BOLD, 10));
            g.setColor(Color.RED);
            g.drawString("X", btn.x + btn.width / 2 - 4, btn.height / 2 + 4);
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {
        int mx = getScaledX(e);
        int my = getScaledY(e);

        for (int i = 0; i < 3; i++) {
            if (fpsButtons[i].contains(mx, my)) {
                selectedFPS = fpsOptions[i];
                game.setFPS(selectedFPS);
                return;
            }
        }

        for (int i = 0; i < 8; i++) {
            if (p1ColourButtons[i].contains(mx, my) && i != p2SelectedHorse) {
                p1SelectedHorse = i;
                game.getPlaying().getPlayer1().setColour(LoadSave.ALL_HORSES[i]);
                return;
            }
        }

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

        for (int i = 0; i < 3; i++) {
            if (fpsButtons[i].contains(mx, my)) {
                hoveredFPS = i;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (p1ColourButtons[i].contains(mx, my)) {
                hoveredP1 = i;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (p2ColourButtons[i].contains(mx, my)) {
                hoveredP2 = i;
            }
        }
    }

    private int getScaledX(MouseEvent e) {
        float scaleX = (float) game.getGamePanel().getWidth() / GAME_WIDTH;
        return (int) (e.getX() / scaleX);
    }

    private int getScaledY(MouseEvent e) {
        float scaleY = (float) game.getGamePanel().getHeight() / GAME_HEIGHT;
        return (int) (e.getY() / scaleY);
    }
}
