package me.aribon.mapsample.utils.suggestion;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import me.aribon.mapsample.utils.AddressUtils;
import me.aribon.mapsample.utils.TextUtils;

/**
 * @Author: aribon
 * @Date: 20/01/2018
 */

public class AddressSuggestion implements SearchSuggestion {

    private String number;
    private String street;
    private String postalCode;
    private String city;
    private String country;

    public AddressSuggestion() {}

    public AddressSuggestion(Parcel in) {
        number = in.readString();
        street = in.readString();
        postalCode = in.readString();
        city = in.readString();
        country = in.readString();
    }

    @Override
    public String getBody() {
        return AddressUtils.formatAddress(number, street, postalCode, city);
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
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(number)
                && TextUtils.isEmpty(street)
                && TextUtils.isEmpty(postalCode)
                && TextUtils.isEmpty(city);
    }
}
