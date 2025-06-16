package com.company.framework.utils;

import java.io.*;
import java.nio.file.*;

public class AssetCopier {

    private static final String SOURCE_DIR = "src/main/resources/extent-assets";
    private static final String DEST_DIR = "reports/extent-assets";

    public static void copyExtentAssets() {
        try {
            Path srcDir = Paths.get(SOURCE_DIR);
            Path destDir = Paths.get(DEST_DIR);

            // Create destination folder if not exists
            if (!Files.exists(destDir)) {
                Files.createDirectories(destDir);
            }

            Files.walk(srcDir).forEach(source -> {
                try {
                    Path destination = destDir.resolve(srcDir.relativize(source));
                    if (Files.isDirectory(source)) {
                        if (!Files.exists(destination)) {
                            Files.createDirectory(destination);
                        }
                    } else {
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("✅ extent-assets copied to reports folder");

        } catch (IOException e) {
            System.err.println("❌ Failed to copy extent-assets: " + e.getMessage());
        }
    }
}
