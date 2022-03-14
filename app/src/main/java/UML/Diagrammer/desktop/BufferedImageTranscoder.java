package UML.Diagrammer.desktop;

import java.awt.image.BufferedImage;
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.ImageTranscoder;

public class BufferedImageTranscoder extends ImageTranscoder {
    private BufferedImage img = null;
    public BufferedImage createImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
    @Override
    public void writeImage(BufferedImage img, TranscoderOutput to) {
        this.img = img;
    }
    public BufferedImage getBufferedImage() {
        return img;
    }

}
