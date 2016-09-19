package cn.michaelboring.toy.gif2ascii;

import java.awt.image.BufferedImage;

import cn.michaelboring.toy.img2ascii.convert.AsciImgConvert;
import cn.michaelboring.toy.img2ascii.util.AnimatedGifEncoder;
import cn.michaelboring.toy.img2ascii.util.GifDecoder;

public class GIFConvert {
	public static int convertGifToAscii(String srcFilePath, String disFilePath, AsciImgConvert asciImgConvert) {
		int delay = 1;
		GifDecoder decoder = new GifDecoder();
		int status = decoder.read(srcFilePath);
		if (status != 0) {
			return -1;// source file not exist or open failed!
		}
		AnimatedGifEncoder e = new AnimatedGifEncoder();
		boolean openStatus = e.start(disFilePath);
		if (openStatus) {
			e.setRepeat(0);
			int frameCount = decoder.getFrameCount();
			for (int i = 0; i < frameCount; i++) {
				delay = decoder.getDelay(i);
				e.setDelay(delay);
				BufferedImage temp = decoder.getFrame(i);
				e.addFrame(asciImgConvert.Img2AsciImg(temp, temp));
			}
			e.finish();
			return 1;
		}
		return 0;
	}
}
