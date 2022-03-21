package UML.Diagrammer.desktop;
import org.apache.batik.transcoder.TranscoderOutput;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BufferedImageTransCoderTest {
    private BufferedImageTranscoder buff;

    @BeforeEach
    public void setUp(){
        buff = new BufferedImageTranscoder();
    }

    @Test
    public void testCreateImage(){
        BufferedImage test = buff.createImage(10,10);
        assertNotNull(test);
    }

    @Test
    public void testWriteImage(){
        TranscoderOutput output = new TranscoderOutput();
        BufferedImage test = buff.createImage(10,10);
        buff.writeImage(test,output);
        assertNotNull(buff.getBufferedImage());

    }
}
