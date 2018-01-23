package me.aribon.mapsample.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: aribon
 * @Date: 21/01/2018
 */

public class ListUtils {

  public static <O> List<O> addToTop(O o, List<O> list) {
    List<O> newList = new ArrayList<>();
    newList.add(o);
    newList.addAll(list);
    return newList;
  }
}
