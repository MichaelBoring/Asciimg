package cn.michaelboring.toy.img2ascii;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import cn.michaelboring.toy.img2ascii.convert.AsciImgConvert;

public class Example {
	public static void main(String[] args) throws Exception {
		BufferedImage source = ImageIO.read(new File("originalSrc/1.png"));
		int width = source.getWidth();
		int height = source.getHeight();
		BufferedImage dist = new BufferedImage(width, height, source.getType());
		new AsciImgConvert().Img2AsciImg(source, dist);
		ImageIO.write(dist, "png", new File("output/1.jpg"));
	}
}
