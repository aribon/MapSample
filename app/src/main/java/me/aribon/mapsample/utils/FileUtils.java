package me.aribon.mapsample.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author: aribon
 * @Date: 15/01/2018
 */

public class FileUtils {

  /**
   * Writes a file to Disk.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform this operation using another thread.
   *
   * @param file The file to write to Disk.
   */
  public static void writeToFile(File file, String fileContent) {
    if (!file.exists()) {
      try {
        FileWriter writer = new FileWriter(file);
        writer.write(fileContent);
        writer.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {

      }
    }
  }

  /**
   * Reads a content from a file.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform the operation using another thread.
   *
   * @param file The file to read from.
   * @return A string with the content of the file.
   */
  public static String readFileContent(File file) {
    StringBuilder fileContentBuilder = new StringBuilder();
    if (file.exists()) {
      String stringLine;
      try {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((stringLine = bufferedReader.readLine()) != null) {
          fileContentBuilder.append(stringLine + "\n");
        }
        bufferedReader.close();
        fileReader.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return fileContentBuilder.toString();
  }

  /**
   * Returns a boolean indicating whether this file can be found on the underlying file system.
   *
   * @param file The file to check existence.
   * @return true if this file exists, false otherwise.
   */
  public static boolean exists(File file) {
    return file.exists();
  }

  /**
   * Warning: Deletes the content of a directory.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform the operation using another thread.
   *
   * @param directory The directory which its content will be deleted.
   */
  public void clearDirectory(File directory) {
    if (directory.exists()) {
      for (File file : directory.listFiles()) {
        file.delete();
      }
    }
  }

  public static File buildFile(Object... path) {
    StringBuilder fileNameBuilder = new StringBuilder();
    for (Object part : path) {
      fileNameBuilder.append(part);
    }
    return new File(fileNameBuilder.toString());
  }
}
