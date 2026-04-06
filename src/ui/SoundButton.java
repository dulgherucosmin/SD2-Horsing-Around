package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.PauseButtons.*;
import utilz.LoadSave;

public class SoundButton extends PauseButton {
    private BufferedImage[][] soundImgs;
    public SoundButton(int x, int y, int width, int height){
        super(x, y, width, height);
        loadSoundImgs();
    }
    private void loadSoundImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
        //two rows 3 columns
        soundImgs = new BufferedImage[2][3];
        for(int i =0; i<soundImgs.length; i++)
            for(int j =0; j<soundImgs[i].length;j++){
                soundImgs[i][j] = temp.getSubimage(j*SOUND_SIZE_DEFAULT, i*SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
        }

    }

    public void update(){
        
    }
    public void draw(Graphics g){
        g.drawImage(soundImgs[0][0], x, y, width, height, null);
    }

}
