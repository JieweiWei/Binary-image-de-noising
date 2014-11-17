/**
 * @File ImageDeNoise.java
 * Reduce noise in a binary image.
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

public class ImageDeNoise {
  /* Neighbor direction. */
  private static final int[][] DIRS = {
    {0, -1}, {1, -1}, {1, 0}, {1, 1},
    {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}
  };

  /* The time of reduce noise. */
  private static final int DENOISE_TIME = 1;

  /* File information. */
  private String objectFileName, pictureType;

  /* Noise image. */
  private BufferedImage bi;

  /* Constructor. */
  public ImageDeNoise(String _sourceFileName, String _objectFileName, String _pictureType) throws IOException {
    objectFileName = _objectFileName;
    bi = ImageIO.read(new File(_sourceFileName));
    pictureType = _pictureType;
  }

  /**
   * @isValidPos
   * Determine the legality of the pixel location.
   * @param {i} pixel abscissa
   * @param {j} pixel ordinate
   * @return whether the pixel location is valid or not
   *
   */
  private boolean isValidPos(int i, int j) {
    return i >= 0 && i < bi.getWidth() && j >= 0 && j < bi.getHeight();
  }

  /**
   * @getValue
   * Map the color to value.
   * @param {rgb} the rgb value of color
   * @return the value of the model
   *
   */
  private int getValue(int rgb) {
    Color c = new Color(rgb);
    return c.getRed() > 127 ? 1 : -1;
  }

  /**
   * @getEnergy
   * Calculation the energy.
   * @param {rgb} Array of colors
   * @param {i} pixel abscissa
   * @param {j} pixel ordinate
   * @param {x} the rgb value of the pixel
   * @return the energy of pixel
   *
   */
  private double
  getEnergy(int[][] rgb, int i, int j, int x) {
    int e1 = 0;
    int e2 = 0;
    for (int k = 0; k < 8; ++k) {
      int is = i + DIRS[k][0], js = j + DIRS[k][1];
      if (isValidPos(is, js)) {
        int xj = getValue(x),
            xi = getValue(bi.getRGB(is,js)),
            yi = getValue(bi.getRGB(is, js));
        e1 += xi * xj;
        e2 += xi * yi;
      }
    }
    return -1.0 * (double) e1 - 2.1 * (double) e2;
  }

  /**
   * @deNoise
   * Reduce noise of the binary image.
   *
   */
  public void deNoise() throws IOException {
    int height = bi.getHeight();
    int width = bi.getWidth();
    int[][] objectRGB = new int[width][height];
    /* The initial state of object image is the same as source image. */
    for (int i = 0; i < width; ++i) {
      for (int j = 0; j < height; ++j) {
        objectRGB[i][j] = bi.getRGB(i, j);
      }
    }

    for (int m = 0; m < DENOISE_TIME; ++m) {
      for (int i = 0; i < width; ++i) {
        for (int j = 0; j < height; ++j) {
          double e1 = getEnergy(objectRGB, i, j, 0xFF000000);
          double e2 = getEnergy(objectRGB, i, j, 0xFFFFFFFF);
          /* The pixel is set to the one with smaller energy. */
          if (e1 < e2) {
            objectRGB[i][j] = 0xFF000000;
          } else if (e1 > e2) {
            objectRGB[i][j] = 0xFFFFFFFF;
          }
        }
      }
    }

    for (int i = 0; i < width; ++i) {
      for (int j = 0; j < height; ++j) {
        bi.setRGB(i, j, objectRGB[i][j]);
      }
    }
    ImageIO.write(bi, pictureType, new File(objectFileName));
  }
};
