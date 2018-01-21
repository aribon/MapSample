package me.aribon.mapsample.utils;

import android.location.Address;
import android.support.annotation.NonNull;

import java.util.Locale;

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

  @NonNull
  public static Address transposeToAddress(AddressSuggestion addressSuggestion) {
    Address address = new Address(Locale.FRANCE);
    address.setSubThoroughfare(addressSuggestion.getNumber());
    address.setThoroughfare(addressSuggestion.getStreet());
    address.setPostalCode(addressSuggestion.getPostalCode());
    address.setLocality(addressSuggestion.getCity());
    address.setCountryName(addressSuggestion.getCountry());
    address.setLatitude(addressSuggestion.getLatitude());
    address.setLongitude(addressSuggestion.getLongitude());
    return address;
  }

  @NonNull
  public static AddressSuggestion transposeToAddressSuggestion(Address address) {
    AddressSuggestion addressSuggestion = new AddressSuggestion();
    addressSuggestion.setNumber(address.getSubThoroughfare());
    addressSuggestion.setStreet(address.getThoroughfare());
    addressSuggestion.setPostalCode(address.getPostalCode());
    addressSuggestion.setCity(address.getLocality());
    addressSuggestion.setCountry(address.getCountryName());
    addressSuggestion.setLatitude(address.getLatitude());
    addressSuggestion.setLongitude(address.getLongitude());
    return addressSuggestion;
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
    return (number != null && street != null ? number + ", " : "")
        + (street != null ? street + " " : "")
        + (postalCode != null ? postalCode + " " : "")
        + (city != null ? city : "");
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
        + "<font color=\"#616161\"> <small>" + cityLine + "</small> </font>";
  }
}
