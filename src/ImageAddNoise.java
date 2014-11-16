/**
 * @File ImageAddNoise.java
 * Add noise to a binary image.
 * @Author Jiewei Wei
 * @Student id 12330318
 * @Copyright Jiewei Wei
 * @Date 2014.11.16
 * @Github https://github.com/JieweiWei/Binary-image-de-noising
 *
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import java.awt.Color;

public class ImageAddNoise {
  /* Noise ratio. */
  static private final double RATIO_OF_NOISE = 0.1;

  /* File information. */
  private String sourceFileName, objectFileName, pictureType;

  /* Constructor. */
  public ImageAddNoise(String _sourceFileName, String _objectFileName, String _pictureType) {
    sourceFileName = _sourceFileName;
    objectFileName = _objectFileName;
    pictureType = _pictureType;
  }

  /**
   * @AddNoise
   * Adding random noise in binary image.
   *
   */
  public void AddNoise() throws IOException {
    BufferedImage bi = ImageIO.read(new File(sourceFileName));
    int height = bi.getHeight();
    int width = bi.getWidth();
    int numOfNoise = (int) (width * height * RATIO_OF_NOISE);
    Random random = new Random();
    for (int i = 0; i < numOfNoise; ++i) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      Color c = new Color(bi.getRGB(x, y));
      bi.setRGB(x, y, c.getRed() > 127 ? 0xFF000000 : 0xFFFFFFFF);
    }
    ImageIO.write(bi, pictureType, new File(objectFileName));
  }
};
