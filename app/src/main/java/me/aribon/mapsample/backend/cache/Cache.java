package me.aribon.mapsample.backend.cache;

import java.io.File;

import me.aribon.mapsample.AppApplication;
import me.aribon.mapsample.utils.FileUtils;

/**
 * @Author: aribon
 * @Date: 20/01/2018
 */

abstract class Cache {

  public static final String TAG = Cache.class.getSimpleName();

  private File cacheDir;

  Cache() {
    this.cacheDir = AppApplication.getInstance().getCacheDir();
  }

  abstract String getFileName();

  protected File buildFile() {
    return FileUtils.buildFile(cacheDir, File.separator, getFileName());
  }
}
