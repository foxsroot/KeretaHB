package controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class ImageController {
    private static String getImageExtension(File image) {
        return image.getName().substring(image.getName().lastIndexOf("."));
    }

    public static String generateName(File image) {
        UUID uuid = UUID.randomUUID();

        String ext = getImageExtension(image);

        return String.format("%s%s", uuid, ext);
    }

    public static Image resizeImage(String path, int width, int height) {
        Image dimg = null;

        try {
            BufferedImage img = ImageIO.read(new File(path));
            dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return dimg;
    }

    public static boolean deleteImage(String path) {
        File file = new File(path);

        if (file.delete()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean saveImage(File image, String fileName, String path) {
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            Files.copy(image.toPath(), (new File(path + fileName)).toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
