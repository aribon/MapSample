package me.aribon.mapsample.backend;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import me.aribon.mapsample.AppApplication;

/**
 * @Author: aribon
 * @Date: 19/01/2018
 */

public class AddressManager {

  /**
   * Search address list corresponding to query
   *
   * @param name the address to find
   * @param nbrResults the number of result wanted
   * @return list of address
   */
  public Observable<List<Address>> fetchAddressesFromName(String name, int nbrResults) {
    return Observable.create(
        new ObservableOnSubscribe<List<Address>>() {
          @Override
          public void subscribe(ObservableEmitter<List<Address>> emitter) throws Exception {
            List<Address> addresses = new ArrayList<>();

            Geocoder geocoder = new Geocoder(AppApplication.getInstance().getApplicationContext(),
                Locale.FRANCE);

            try {
              addresses = geocoder.getFromLocationName(restrictToFrance(name), nbrResults);
            } catch (IOException e) {
              e.printStackTrace();
            }

            emitter.onNext(addresses);
            emitter.onComplete();
          }
        }
    ).subscribeOn(Schedulers.io());
  }

  /**
   * Search an address corresponding to query
   *
   * @param name the address to find
   * @return first result of the search
   */
  public Observable<Address> fetchAddressFromName(String name) {
    return fetchAddressesFromName(name, 1)
        .map(addresses -> addresses != null && addresses.size() > 0
            ? addresses.get(0)
            : null);
  }

  /**
   * [If use Geocoder object]
   * Filter and restrict the request only in france
   *
   * @param query the query to restrict
   * @return the new query with filter parameters
   */
  public String restrictToFrance(String query) {
    String suffix = ", France";
    return query.toUpperCase().endsWith(suffix) ? query : query + suffix;
  }
}
