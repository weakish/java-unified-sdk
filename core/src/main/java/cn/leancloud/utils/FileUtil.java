package cn.leancloud.utils;

import cn.leancloud.AVFile;

import java.util.regex.Pattern;

public class FileUtil {
  public static final int DEFAULT_FILE_KEY_LEN = 40;
  public static final String DEFAULTMIMETYPE = "application/octet-stream";

  public static interface MimeTypeDetector {
    String getMimeTypeFromUrl(String url);
    String getMimeTypeFromPath(String filePath);
    String getMimeTypeFromExtension(String extension);
  }
  private static MimeTypeDetector detector = new DefaultMimeTypeDetector();
  public static void config(MimeTypeDetector mimeTypeDetector) {
    detector = mimeTypeDetector;
  }

  public static String generateFileKey(String name) {
    String key = StringUtil.getRandomString(DEFAULT_FILE_KEY_LEN);
    int idx = 0;
    if (!StringUtil.isEmpty(name)) {
      idx = name.lastIndexOf(".");
    }
    if (idx > 0) {
      String postFix = name.substring(idx);
      key += postFix;
    }
    return key;
  }

  public static String getExtensionFromFilename(String filename) {
    if (!StringUtil.isEmpty(filename) && Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", filename)) {
      int dotPos = filename.lastIndexOf('.');
      if (0 <= dotPos) {
        return filename.substring(dotPos + 1);
      }
    }
    return "";
  }

  public static String getFileMimeType(AVFile avFile) {
    String fileName = avFile.getName();
    String fileUrl = avFile.getUrl();
    String mimeType = DEFAULTMIMETYPE;
    if (!StringUtil.isEmpty(fileName)) {
      mimeType = getMimeTypeFromFilename(fileName);
    } else if (!StringUtil.isEmpty(fileUrl)) {
      mimeType = getMimeTypeFromUrl(fileUrl);
    }
    return mimeType;
  }

  public static String getMimeTypeFromFilename(String fileName) {
    String extension = getExtensionFromFilename(fileName);
    if (!StringUtil.isEmpty(extension)) {
      return detector.getMimeTypeFromExtension(extension);
    }
    return "";
  }

  public static String getMimeTypeFromPath(String localPath) {
    if (!StringUtil.isEmpty(localPath)) {
      return detector.getMimeTypeFromPath(localPath);
    }
    return "";
  }

  public static String getMimeTypeFromUrl(String url) {
    if (!StringUtil.isEmpty(url)) {
      return detector.getMimeTypeFromUrl(url);
    }
    return "";
  }
}
