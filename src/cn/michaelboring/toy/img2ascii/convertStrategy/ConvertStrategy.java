package cn.michaelboring.toy.img2ascii.convertStrategy;

import java.awt.image.BufferedImage;

public interface ConvertStrategy {
	public BufferedImage getBestFitImg(BufferedImage source, int i, int j, int width, int height,
			BufferedImage[] comparison);
}
