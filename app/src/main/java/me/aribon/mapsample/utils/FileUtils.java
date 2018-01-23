package me.aribon.mapsample.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

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
  public static Completable writeToFile(File file, String fileContent) {
    return Completable.create(
        new CompletableOnSubscribe() {
          @Override
          public void subscribe(CompletableEmitter emitter) throws Exception {
            try {
              if (!file.exists()) {
                file.createNewFile();
              }

              FileOutputStream fop = new FileOutputStream(file, false);

              if (fileContent != null)
                fop.write(fileContent.getBytes());
              fop.flush();
              fop.close();
            } catch (IOException e) {
              emitter.onError(e);
            } finally {
              emitter.onComplete();
            }
          }
        })
        .subscribeOn(Schedulers.io());
  }

  /**
   * Reads a content from a file.
   * This is an I/O operation and this method executes in the main thread, so it is recommended to
   * perform the operation using another thread.
   *
   * @param file The file to read from.
   * @return A string with the content of the file.
   */
  public static Single<String> readFileContent(File file) {
    return Single.create(
        new SingleOnSubscribe<String>() {
          @Override
          public void subscribe(SingleEmitter<String> emitter) throws Exception {
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
                emitter.onError(e);
              } catch (IOException e) {
                emitter.onError(e);
              }
            }

            emitter.onSuccess(fileContentBuilder.toString());
          }
        })
        .subscribeOn(Schedulers.io());
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
