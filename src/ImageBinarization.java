import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.io.File;

public class ImageBinarization {
  static private final int AVE = 127;
  private String sourceFileName, objectFileName, pictureType;
  public ImageBinarization(String _sourceFileName, String _objectFileName, String _pictureType) {
    sourceFileName = _sourceFileName;
    objectFileName = _objectFileName;
    pictureType = _pictureType;
  }
  private static int
  getAverageColor(int[][] gray, int x, int y, int w, int h) {
    int rs = gray[x][y]
             + (x == 0 ? 255 : gray[x - 1][y])
             + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
             + (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1])
             + (y == 0 ? 255 : gray[x][y - 1])
             + (y == h - 1 ? 255 : gray[x][y + 1])
             + (x == w - 1 ? 255 : gray[x + 1][ y])
             + (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
             + (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
    return rs / 9;
  }
  private static int getGray(int rgb) {
    Color c = new Color(rgb);
    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();
    return (r + g + b) / 3;
  }
  public void generateBinaryimage() throws IOException {
    BufferedImage bi = ImageIO.read(new File(sourceFileName));
    int height = bi.getHeight();
    int width = bi.getWidth();
    int[][] grayRGB = new int[width][height];
    for (int i = 0; i < width; ++i) {
      for (int j = 0; j < height; ++j) {
        grayRGB[i][j] = getGray(bi.getRGB(i, j));
      }
    }
    BufferedImage bbi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
    for (int i = 0; i < width; ++i) {
      for (int j = 0; j < height; ++j) {
        int ave = getAverageColor(grayRGB, i, j, width, height);
        bbi.setRGB(i, j, ave > AVE ? 0xFFFFFFFF : 0xFF000000);
      }
    }
    ImageIO.write(bbi, pictureType, new File(objectFileName));
  }
};
