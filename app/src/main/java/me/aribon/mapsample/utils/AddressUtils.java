package me.aribon.mapsample.utils;

import android.location.Address;
import android.support.annotation.NonNull;

import me.aribon.mapsample.utils.suggestion.AddressSuggestion;

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
    return formatAddress(
            address.getSubThoroughfare(),
            address.getThoroughfare(),
            address.getPostalCode(),
            address.getLocality()
    );
  }

  public static String formatAddress(String number, String street, String postalCode, String city) {
    return number + ", " +
            street + " " +
            postalCode + " " +
            city + " ";

  }

  public static String formatAddressSuggestion(AddressSuggestion addressSuggestion) {
    String streetLine = (addressSuggestion.getNumber() != null
            && addressSuggestion.getStreet() != null
            ? addressSuggestion.getNumber() + ", "
            : "")
            + (addressSuggestion.getStreet() != null
            ? addressSuggestion.getStreet()
            : "");

    String cityLine = (addressSuggestion.getPostalCode() != null
            ? addressSuggestion.getPostalCode() + " "
            : "")
            + (addressSuggestion.getCity() != null
            ? addressSuggestion.getCity()
            : "");

    return "<font color=\"#212121\">" + streetLine + "</font>"
            + (!cityLine.isEmpty() && !streetLine.isEmpty()
            ? "<br />"
            : "")
            + "<font color=\"#616161\"> <small>" +cityLine + "</small> </font>";
  }

}
