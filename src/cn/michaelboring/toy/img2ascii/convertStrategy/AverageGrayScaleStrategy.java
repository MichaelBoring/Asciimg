package cn.michaelboring.toy.img2ascii.convertStrategy;

import java.awt.image.BufferedImage;

import cn.michaelboring.toy.img2ascii.convertStrategy.ConvertStrategy;

public class AverageGrayScaleStrategy implements ConvertStrategy {

	@Override
	public BufferedImage getBestFitImg(BufferedImage sourceImg, int i, int j, int fontWidth, int fontHeight,
			BufferedImage[] comparison) {
		// TODO Auto-generated method stub
		int gray = 0;
		int count = 0;
		int width = sourceImg.getWidth();
		int height = sourceImg.getHeight();
		for (int tempX = i * fontWidth, m = 0; m < fontWidth; tempX++, m++) {
			for (int tempY = j * fontHeight, n = 0; n < fontHeight; tempY++, n++) {
				try {
					if (tempX < width && tempY < height) {
						int pixelGray = convertRGBToGrayscale(sourceImg.getRGB(tempX, tempY));
						gray += pixelGray;
						count++;
					}
				} catch (Exception e) {
//					System.out.println("boom");
				}
			}
		}
		gray /= (count == 0 ? 1 : count);
		return comparison[(gray * comparison.length - 1) / 255];
	}

	private int convertRGBToGrayscale(int rgbColor) {
		int red = (rgbColor >> 16) & 0xFF;
		int green = (rgbColor >> 8) & 0xFF;
		int blue = rgbColor & 0xFF;
		return (int) (0.3f * red + 0.59f * green + 0.11f * blue);
	}

}
