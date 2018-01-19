package me.aribon.mapsample.utils;

import android.location.Address;
import android.support.annotation.NonNull;

/**
 * Created by anthony.ribon
 * On 18/01/2018
 */

public class AddressUtils {

  public static String getRawAddress(@NonNull Address address) {
    StringBuilder strAddress = new StringBuilder();
    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
      strAddress.append(address.getAddressLine(i)).append(" ");
    }

    return strAddress.toString();
  }

  public static String formatAddress(@NonNull Address address) {
    StringBuilder strAddress = new StringBuilder();

    strAddress.append(address.getSubThoroughfare()).append(" "); //The street number
    strAddress.append(address.getThoroughfare()).append(" "); //todo The street name
    strAddress.append(address.getPostalCode()).append(" "); //The postal code
    strAddress.append(address.getLocality()).append(" "); //The city name


    return strAddress.toString();
  }

}
