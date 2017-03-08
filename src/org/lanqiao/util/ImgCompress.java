package org.lanqiao.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgCompress {
	private Image img;
	private int width;
	private int height;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	/**
	 * ���캯��
	 */
	public ImgCompress(String fileName) throws IOException {
		File file = new File(fileName);// �����ļ�
		setImg(ImageIO.read(file)); // ����Image����
		setWidth(img.getWidth(null)); // �õ�Դͼ��
		setHeight(img.getHeight(null)); // �õ�Դͼ��
	}

	/**
	 * ���տ�Ȼ��Ǹ߶Ƚ���ѹ��
	 * 
	 * @param w
	 *            int �����
	 * @param h
	 *            int ���߶�
	 */
	public void resizeFix(int w, int h, String path) throws IOException {
		if (getWidth() / getHeight() > w / h) {
			resizeByWidth(w, path);
		} else {
			resizeByHeight(h, path);
		}
	}

	/**
	 * �Կ��Ϊ��׼���ȱ�������ͼƬ
	 * 
	 * @param w
	 *            int �¿��
	 */
	public void resizeByWidth(int w, String path) throws IOException {
		int h = (int) (getHeight() * w / getWidth());
		resize(w, h, path);
	}

	/**
	 * �Ը߶�Ϊ��׼���ȱ�������ͼƬ
	 * 
	 * @param h
	 *            int �¸߶�
	 */
	public void resizeByHeight(int h, String path) throws IOException {
		int w = (int) (getWidth() * h / getHeight());
		resize(w, h, path);
	}

	/**
	 * ǿ��ѹ��/�Ŵ�ͼƬ���̶��Ĵ�С
	 * 
	 * @param w
	 *            int �¿��
	 * @param h
	 *            int �¸߶�
	 */
	public void resize(int w, int h, String path) throws IOException {
		// SCALE_SMOOTH �������㷨 ��������ͼƬ��ƽ���ȵ� ���ȼ����ٶȸ� ���ɵ�ͼƬ�����ȽϺ� ���ٶ���
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(getImg(), 0, 0, w, h, null); // ������С���ͼ
		ImageIO.write(image, "png", new File(path));
		/*File destFile = new File(path);
		FileOutputStream out = new FileOutputStream(destFile); // ������ļ���
		// ��������ʵ��bmp��png��gifתjpg
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image); // JPEG����
		out.close();*/
	}
}
