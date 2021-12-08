package com.livestack.login;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.TransparentBackgroundProducer;

public class CaptchaGenerator {

	// captcha class object
	public static Captcha createCaptcha(int width, int height) {
		Captcha cap = new Captcha.Builder(width, height).addBackground(new TransparentBackgroundProducer()).addText().addNoise().build();
		return cap;
	}

	// convert into image
	public static void createImage(Captcha captcha) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(captcha.getImage(), "png", os);
			FileOutputStream fos = new FileOutputStream("C:/Practice/captcha/mycp.png");
			fos.write(os.toByteArray());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// convert to binary string
	public static String encodeBase64(Captcha captcha) {
		String imageData = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(captcha.getImage(), "png", os);
			byte[] arr = Base64.getEncoder().encode(os.toByteArray());
			imageData = new String(arr);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return imageData;
	}
}
