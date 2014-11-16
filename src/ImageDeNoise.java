import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.awt.Color;

public class ImageDeNoise {
  private class pair {
    public int x;
    public int y;
    public pair(int _x, int _y) {
      x = _x;
      y = _y;
    }
  }
  private static int[][] dirs = {
    {0, -1},
    {1, -1},
    {1, 0},
    {1, 1},
    {0, 1},
    {-1, 1},
    {-1, 0},
    {-1, -1},
  };
  private static final int DENOISE_TIME = 10;
  private String objectFileName, pictureType;
  private BufferedImage bi;
  public ImageDeNoise(String _sourceFileName, String _objectFileName, String _pictureType) throws IOException {
    objectFileName = _objectFileName;
    bi = ImageIO.read(new File(_sourceFileName));
    pictureType = _pictureType;
  }
  private boolean isValidPos(int i, int j) {
    return i >= 0 && i < bi.getWidth() && j >= 0 && j < bi.getHeight();
  }
  private int getValue(int rgb) {
    Color c = new Color(rgb);
    return c.getRed() > 127 ? 1 : -1;
  }
  private double
  getEnergy(int[][] rgb, int i, int j, int x) {
    int sum1 = 0;
    int sum2 = 0;
    for (int k = 0; k < 8; ++k) {
      int is = i + dirs[k][0], js = j + dirs[k][1];
      if (isValidPos(is, js)) {
        int xj = getValue(x),
            xi = getValue(bi.getRGB(is,js)),
            yi = getValue(bi.getRGB(is, js));
        sum1 += xi * xj;
        sum2 += xi * yi;
      }
    }
    return -1.0 * sum1 - 2.1 * sum2;
  }
  public void deNoise() throws IOException {
    int height = bi.getHeight();
    int width = bi.getWidth();
    int[][] objectRGB = new int[width][height];
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
