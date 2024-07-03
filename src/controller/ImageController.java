package controller;

import config.DirectoryConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class ImageController {
    private String generateName(String ext) {
        UUID uuid = UUID.randomUUID();

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

    public static void saveImage(File image, String fileName, String path) {
        File directory = new File(DirectoryConfig.VICTUAL_IMAGES);

        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Directory created successfully."); //buat logging :)
            } else {
                System.out.println("Failed to create directory."); //buat logging :)
                return;
            }
        }

        try {
            Files.copy(image.toPath(), (new File(DirectoryConfig.VICTUAL_IMAGES + "\\" + fileName)).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
