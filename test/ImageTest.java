/**
 * @File ImageTest.java
 * Calculate denoise ratio.
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
import java.awt.Color;

public class ImageTest {
  /* File information. */
  private static final String ORIGINAL_PICTURE = "pic/test4.png";
  private static final String DENOISE_PICTURE = "pic/test4_denoise.png";

  /**
   * @getValue
   * Map the color to value.
   * @param {rgb} the rgb value of color
   * @return the value of the model
   *
   */
  private static int getValue(int rgb) {
    Color c = new Color(rgb);
    return c.getRed() > 127 ? 1 : -1;
  }

  public static void main(String[] args) throws IOException {
    BufferedImage obi = ImageIO.read(new File(ORIGINAL_PICTURE));
    BufferedImage dbi = ImageIO.read(new File(DENOISE_PICTURE));
    double numOfSame = 0.0;
    for (int i = 0; i < obi.getWidth(); ++i) {
      for (int j = 0; j < obi.getHeight(); ++j) {
        if (getValue(obi.getRGB(i, j)) == getValue(dbi.getRGB(i, j))) {
          ++numOfSame;
        }
      }
    }
    System.out.println(numOfSame / (double) (obi.getWidth() * obi.getHeight()) * 100.0 + "%");
  }
};
