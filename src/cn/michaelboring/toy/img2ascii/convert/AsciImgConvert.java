package cn.michaelboring.toy.img2ascii.convert;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

import cn.michaelboring.toy.img2ascii.convertStrategy.AverageGrayScaleStrategy;
import cn.michaelboring.toy.img2ascii.convertStrategy.ConvertStrategy;

public class AsciImgConvert {

	private Dimension dimension;
	private BufferedImage[] fontImg;
	private String pattern;
	private Font font;
	private ConvertStrategy strategy;

	public AsciImgConvert() {
		this(null, null, null);
	}

	public AsciImgConvert(Font font, String pattern, ConvertStrategy strategy) {
		if (font == null) {
			this.font = new Font("Courier", 0, 8);
		} else {
			this.font = font;
		}

		if (pattern == null) {
			this.pattern = "@MW#+*. ";
		} else {
			this.pattern = pattern;
		}

		if (strategy == null) {
			this.strategy = new AverageGrayScaleStrategy();
		} else {
			this.strategy = strategy;
		}

		this.fontImg = getFontImg(this.font, this.pattern);
	}

	private Dimension calculateCharacterRectangle(Font font, String pattern) {
		char[] characters = pattern.toCharArray();
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setFont(font);
		FontMetrics fm = graphics.getFontMetrics();

		Dimension maxCharacter = new Dimension();
		for (int i = 0; i < characters.length; i++) {
			String character = Character.toString(characters[i]);

			Rectangle characterRectangle = new TextLayout(character, fm.getFont(), fm.getFontRenderContext())
					.getOutline(null).getBounds();

			if (maxCharacter.width < characterRectangle.getWidth()) {
				maxCharacter.width = (int) characterRectangle.getWidth();
			}

			if (maxCharacter.height < characterRectangle.getHeight()) {
				maxCharacter.height = (int) characterRectangle.getHeight();
			}
		}
		return maxCharacter;
	}

	private BufferedImage[] getFontImg(Font font, String pattern) {
		dimension = calculateCharacterRectangle(font, pattern);
		char[] characters = pattern.toCharArray();
		int width = (int) dimension.getWidth();
		int height = (int) dimension.getHeight();

		BufferedImage[] fontImg = new BufferedImage[characters.length];

		for (int i = 0; i < characters.length; i++) {
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics g = img.getGraphics();
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics.setFont(font);
			FontMetrics fm = graphics.getFontMetrics();

			String character = Character.toString(characters[i]);
			g.setColor(new Color(255, 255, 255, 255));
			g.fillRect(0, 0, width, height);
			g.setColor(new Color(0, 0, 0, 255));
			Rectangle rect = new TextLayout(character, fm.getFont(), fm.getFontRenderContext()).getOutline(null)
					.getBounds();
			g.drawString(character, 0, (int) (rect.getHeight() - rect.getMaxY()));
			fontImg[i] = img;
		}
		return fontImg;
	}

	public BufferedImage Img2AsciImg(BufferedImage sourceImg, BufferedImage asciImg) {

		int fontHeight = (int) dimension.getHeight();
		int fontWidth = (int) dimension.getHeight();
		int x = asciImg.getWidth() / fontWidth;
		x += (asciImg.getWidth() % fontWidth == 0 ? 0 : 1);
		int y = asciImg.getHeight() / fontHeight;
		y += (asciImg.getHeight() % fontHeight == 0 ? 0 : 1);
		Graphics g = asciImg.getGraphics();
		Graphics2D graphics = (Graphics2D) g;

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				graphics.drawImage(strategy.getBestFitImg(sourceImg, i, j, fontWidth, fontHeight, fontImg),
						i * fontWidth, j * fontHeight,
						(i + 1) * fontWidth, (j + 1) * fontHeight, 0, 0, fontWidth, fontHeight, null);
			}
		}
		return sourceImg;
	}

}
