package com.example.demo.mvc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import jakarta.servlet.http.HttpServletResponse;

public class CaptchaUtil {
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int WIDTH = 200;
    private static final int HEIGHT = 50;
    private static final int LENGTH = 6;
    private static final int LINES = 5;

    public static String generateCaptcha(HttpServletResponse response) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        Random random = new Random();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 繪製隨機線條
        for (int i = 0; i < LINES; i++) {
            g.setColor(getRandomColor());
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 繪製隨機字符
        g.setFont(new Font("Arial", Font.BOLD, 40));
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            char ch = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            captcha.append(ch);
            g.setColor(getRandomColor());
            g.drawString(String.valueOf(ch), (i * 30) + 10, 40);
        }

        g.dispose();

        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return captcha.toString();
    }

    private static Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
