import java.io.IOException;

public class ImageRunner {
  private static final String PICTURE_TYPE = "png";
  private static final String ORIGINAL_PICTURE = "pic/test4.png";
  private static final String BINARY_PICTURE = "pic/test4_binary.png";
  private static final String NOISE_PICTURE = "pic/test4_noise.png";
  private static final String DENOISE_PICTURE = "pic/test4_denoise.png";

  public static void main(String[] args) throws IOException {
    (new ImageBinarization(ORIGINAL_PICTURE, BINARY_PICTURE, PICTURE_TYPE)).generateBinaryimage();
    (new ImageAddNoise(BINARY_PICTURE, NOISE_PICTURE, PICTURE_TYPE)).AddNoise();
    (new ImageDeNoise(NOISE_PICTURE, DENOISE_PICTURE, PICTURE_TYPE)).deNoise();
  }
};
