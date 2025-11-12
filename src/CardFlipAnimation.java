import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class CardFlipAnimation extends Thread {

    private int x, y, offsetX, offsetY;
    private BufferedImage cardFace, cardBack;
    private Graphics g;
    private ImageObserver obs;
    private int width = 62;
    private double scale;
    private boolean running;
    private GameSystem system;


    public CardFlipAnimation(Graphics g, BufferedImage card, int x, int y, double scale, int offsetX, int offsetY, ImageObserver obs, GameSystem system) throws IOException {
        this.g = g;
        cardFace = card;
        cardBack = ImageIO.read(new File("src/resources/images/cards/card_back.png"));
        this.x = x;
        this.y = y;
        this.obs = obs;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.scale = scale;
        running = true;
        this.system = system;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void run() {
        int i = 0;
        while (i < 40) {
            if (width >= 0) {
                g.drawImage(cardBack, offsetX + (int) (x * scale), offsetY + (int) (y * scale), (int) (width * scale), (int) (90 * scale), obs);
                width = (int) Math.sin(i);
                i++;
            }
            else if (width > -62) {
                g.drawImage(cardBack, offsetX + (int)(x*scale), offsetY + (int)(y*scale), (int) (-1 * width * scale), (int) (90 * scale), obs);
                width = (int) Math.sin(i);
                i++;
            }
            else {
                g.drawImage(cardBack, offsetX + (int)(x*scale), offsetY + (int)(y*scale), (int) (62 * scale), (int) (90 * scale), obs);
                width = -62;
                i++;
            }


            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
