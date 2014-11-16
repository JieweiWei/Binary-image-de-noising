/**
 * @File ImageRunner.java
 * The main function of the project.
 * @Author Jiewei Wei
 * @Student id 12330318
 * @Copyright Jiewei Wei
 * @Date 2014.11.16
 * @Github https://github.com/JieweiWei/Binary-image-de-noising
 *
 */

import java.io.IOException;

public class ImageRunner {
  /* File information. */
  private static final String PICTURE_TYPE = "png";
  private static final String ORIGINAL_PICTURE = "pic/test4.png";
  private static final String BINARY_PICTURE = "pic/test4_binary.png";
  private static final String NOISE_PICTURE = "pic/test4_noise.png";
  private static final String DENOISE_PICTURE = "pic/test4_denoise.png";

  public static void main(String[] args) throws IOException {
    /* Image binarization. */
    (new ImageBinarization(ORIGINAL_PICTURE, BINARY_PICTURE, PICTURE_TYPE)).generateBinaryimage();

    /* Adding random noise to the binary picture. */
    (new ImageAddNoise(BINARY_PICTURE, NOISE_PICTURE, PICTURE_TYPE)).AddNoise();

    /* Reduce the noise width Markv random field. */
    (new ImageDeNoise(NOISE_PICTURE, DENOISE_PICTURE, PICTURE_TYPE)).deNoise();
  }
};
