package me.aribon.mapsample.utils.suggestion;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.UUID;

import me.aribon.mapsample.utils.TextUtils;

/**
 * @Author: aribon
 * @Date: 20/01/2018
 */

public class AddressSuggestion implements SearchSuggestion {

  private String uuid;
  private String number;
  private String street;
  private String postalCode;
  private String city;
  private String country;
  private double latitude;
  private double longitude;
  private boolean isHistoric;

  public AddressSuggestion() {
    this.uuid = UUID.randomUUID().toString();
  }

  public AddressSuggestion(Parcel in) {
    number = in.readString();
    street = in.readString();
    postalCode = in.readString();
    city = in.readString();
    country = in.readString();
    latitude = in.readDouble();
    longitude = in.readDouble();
    isHistoric = in.readByte() != 0;
  }

  @Override
  public String getBody() {
    return getUuid();
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public boolean isHistoric() {
    return isHistoric;
  }

  public void setHistoric(boolean historic) {
    isHistoric = historic;
  }

  public static final Creator<AddressSuggestion> CREATOR = new Creator<AddressSuggestion>() {
    @Override
    public AddressSuggestion createFromParcel(Parcel in) {
      return new AddressSuggestion(in);
    }

    @Override
    public AddressSuggestion[] newArray(int size) {
      return new AddressSuggestion[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(number);
    dest.writeString(street);
    dest.writeString(postalCode);
    dest.writeString(city);
    dest.writeString(country);
    dest.writeDouble(latitude);
    dest.writeDouble(longitude);
    dest.writeByte((byte) (isHistoric ? 1 : 0));
  }

  public boolean isEmpty() {
    return TextUtils.isEmpty(number)
        && TextUtils.isEmpty(street)
        && TextUtils.isEmpty(postalCode)
        && TextUtils.isEmpty(city)
        && TextUtils.isEmpty(country)
        && latitude == 0
        && longitude == 0;
  }
}
