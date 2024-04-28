package com.base.core.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hebing on 2017/6/7.
 */
public class ImageCompress {

    private Image image;
    private int width;
    private int height;

    public ImageCompress(File file) throws IOException {
        image = ImageIO.read(file);
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public ImageCompress(String fileUrl) throws IOException {
        File file = new File(fileUrl);
        image = ImageIO.read(file);
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public ImageCompress(InputStream input) throws IOException {
        image = ImageIO.read(input);
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public void resizeFix(int w, int h, String targetFile) throws IOException {
        if(width/height > w/h) {
            resizeByWidth(w, targetFile);
        } else {
            resizeByHeight(h, targetFile);
        }
    }

    public void resizeByWidth( int w, String targetFile) throws IOException {
        int h = (int)(height*w/width);
        resize(w, h, targetFile);

    }


    public void resizeByHeight(int h, String targetFile) throws IOException {
        int w = (int)(width*h/height);
        resize(w, h, targetFile);
    }

    public void resizeOrgin(String targetFile) throws IOException {
        int w = width;
        int h = height;
        resize(w, h, targetFile);
    }

    public void resize(int w, int h, String targetFile) throws IOException {
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bi.getGraphics().drawImage(image, 0, 0, w, h, null);
        File target = new File(targetFile);

        String ext = targetFile.substring(targetFile.lastIndexOf(".") + 1);
        ImageIO.write(bi, ext, target);
    }


    public static void main(String[] args) {
        try {
            String formPic = "\\data\\wwwroot\\mayn-wallpaper\\userIcon\\784469770.jpg";
           File file = new File(formPic);
           file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
