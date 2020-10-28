package com.verbitsky.task4.util;

import java.io.File;

public class Helper {
    private Helper() {
    }

    public static String getRealPath(String path) {
        String result;
        File file = new File(path);
        if (file.exists()) {
            result = path;
        } else {
            String realPath = (Helper.class.getClassLoader().getResource(path).getFile());
            file = new File(realPath);
            if (file.exists()) {
                result = realPath;
            }
            else {
                throw new RuntimeException("File not found: "+path);
            }
        }
        return result;
    }
}
