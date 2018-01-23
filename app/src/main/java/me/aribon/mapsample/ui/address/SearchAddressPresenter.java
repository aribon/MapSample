package me.aribon.mapsample.ui.address;

import android.location.Address;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.aribon.mapsample.backend.AddressManager;
import me.aribon.mapsample.business.bus.AddressBus;
import me.aribon.mapsample.business.bus.LocationBus;
import me.aribon.mapsample.business.event.AddressEvent;
import me.aribon.mapsample.business.event.LocationEvent;
import me.aribon.mapsample.ui.base.BasePresenter;
import me.aribon.mapsample.utils.AddressUtils;
import me.aribon.mapsample.utils.suggestion.AddressSuggestion;

/**
 * @Author: aribon
 * @Date: 17/01/2018
 */

public class SearchAddressPresenter extends BasePresenter
    implements SearchAddressContract.Presenter {

  private final int MAX_SUGGESTIONS_NBR = 10;

  private SearchAddressContract.View<AddressSuggestion> mvpView;

  private Disposable locationBus;

  private List<AddressSuggestion> addressSuggestionList;

  SearchAddressPresenter(SearchAddressContract.View<AddressSuggestion> mvpView) {
    this.mvpView = mvpView;
  }

  @Override
  public void subscribe() {
    super.subscribe();

    locationBus = LocationBus.getInstance().register()
        .flatMap(
            new Function<LocationEvent, ObservableSource<Address>>() {
              @Override
              public ObservableSource<Address> apply(LocationEvent locationEvent) throws Exception {
                if (locationEvent != null && locationEvent.hasContent()) {
                  return new AddressManager()
                      .fetchAddressFromLocation(locationEvent.getEventContent());
                } else {
                  return Observable.error(new Exception("No location")); //todo custom exception
                }
              }
            }
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Consumer<Address>() {
              @Override
              public void accept(Address address) throws Exception {
                mvpView.showAddress(AddressUtils.formatAddress(address));
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                throwable.getMessage();
              }
            }
        );
  }

  @Override
  public void unsubscribe() {
    if (addressSuggestionList != null) {
      addressSuggestionList.clear();
      addressSuggestionList = null;
    }

    if (locationBus != null && !locationBus.isDisposed()) {
      locationBus.dispose();
    }

    locationBus = null;
    mvpView = null;
    super.unsubscribe();
  }

  @Override
  public void searchSuggestions(String query) {
    mvpView.showProgress();
    new AddressManager()
        .fetchAddressesFromName(query, MAX_SUGGESTIONS_NBR)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Consumer<List<Address>>() {
              @Override
              public void accept(List<Address> addresses) throws Exception {
                if (!addresses.isEmpty()) {
                  if (addressSuggestionList != null)
                    addressSuggestionList.clear();
                  addressSuggestionList = new ArrayList<>();

                  for (Address address : addresses) {
                    if (address != null) {
                      AddressSuggestion addressSuggestion = AddressUtils.transposeToAddressSuggestion(address);
                      if (!addressSuggestion.isEmpty())
                        addressSuggestionList.add(addressSuggestion);
                    }
                  }

                  mvpView.showSuggestions(addressSuggestionList);
                }

                mvpView.hideProgress();
              }
            },
            new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                mvpView.hideProgress();
                throwable.printStackTrace();
              }
            }
        );
  }

  @Override
  public void selectAddress(String suggestionUuid) {
    mvpView.hideSuggestions();
    Address address = findAddressFromSuggestions(suggestionUuid);

    if (address != null) {
      mvpView.showAddress(AddressUtils.formatAddress(address));
      sendAddress(address, AddressEvent.AddressEventType.FROM_INPUT);
    }
  }

  @Nullable
  private Address findAddressFromSuggestions(String uuid) {
    Address address = null;
    for (int i = 0; i < addressSuggestionList.size(); i++) {
      AddressSuggestion addressSuggestion = addressSuggestionList.get(i);
      if (TextUtils.equals(uuid, addressSuggestion.getUuid())) {
        address = AddressUtils.transposeToAddress(addressSuggestion);
        break;
      }
    }
    return address;
  }

  private void sendAddress(Address address, AddressEvent.AddressEventType type) {
    AddressBus.getInstance()
        .send(new AddressEvent(address, type));
  }
}
