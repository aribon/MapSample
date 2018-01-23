package me.aribon.mapsample.ui.address;

import java.util.List;

import me.aribon.mapsample.ui.base.BaseMvpPresenter;
import me.aribon.mapsample.ui.base.BaseMvpView;

/**
 * @Author: aribon
 * @Date: 17/01/2018
 */

public interface SearchAddressContract {

  interface View<S> extends BaseMvpView {

    void showSuggestions(List<S> searchSuggestions);

    void showHistoric(List<S> historicAddresses);

    void showAddress(String address);

    void hideSuggestions();

    void showProgress();

    void hideProgress();
  }

  interface Presenter extends BaseMvpPresenter {

    void searchSuggestions(String query);

    void loadHistoric();

    void selectAddress(String address);
  }
}
