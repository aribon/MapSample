package me.aribon.mapsample.ui.address;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import me.aribon.mapsample.backend.AddressManager;
import me.aribon.mapsample.ui.base.BasePresenter;
import me.aribon.mapsample.utils.suggestion.AddressSuggestion;

/**
 * @Author: aribon
 * @Date: 17/01/2018
 */

public class SearchAddressPresenter extends BasePresenter
        implements SearchAddressContract.Presenter {

    private final int MAX_SUGGESTIONS_NBR = 10;

    private SearchAddressContract.View<AddressSuggestion> mvpView;

    SearchAddressPresenter(SearchAddressContract.View<AddressSuggestion> mvpView) {
        this.mvpView = mvpView;
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
                                   List<AddressSuggestion> addressSuggestionList = new ArrayList<>();

                                   for (Address address : addresses) {
                                       if (address != null) {
                                           AddressSuggestion addressSuggestion = new AddressSuggestion();
                                           addressSuggestion.setNumber(address.getSubThoroughfare());
                                           addressSuggestion.setStreet(address.getThoroughfare());
                                           addressSuggestion.setPostalCode(address.getPostalCode());
                                           addressSuggestion.setCity(address.getLocality());
                                           addressSuggestion.setCountry(address.getCountryName());
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

                            }
                        }
                );

    }

    @Override
    public void selectAddress(String address) {
        mvpView.hideSuggestions();
        mvpView.showAddress(address);
    }
}
