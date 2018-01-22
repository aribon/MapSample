package me.aribon.mapsample.ui.address;

import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;

import java.util.List;

import me.aribon.mapsample.R;
import me.aribon.mapsample.ui.base.BaseFragment;
import me.aribon.mapsample.utils.AddressUtils;
import me.aribon.mapsample.utils.suggestion.AddressSuggestion;

/**
 * @Author: aribon
 * @Date: 17/01/2018
 */

public class SearchAddressFragment extends BaseFragment
    implements SearchAddressContract.View<AddressSuggestion> {

  private static final String TAG = SearchAddressFragment.class.getSimpleName();

  private SearchAddressContract.Presenter presenter;

  FloatingSearchView searchAddressBar;

  @Override
  public int getLayoutResource() {
    return R.layout.fragment_search_address;
  }

  @Override
  public void initializePresenter() {
    super.initializePresenter();
    presenter = new SearchAddressPresenter(this);
  }

  @Override
  public void findView(View view) {
    super.findView(view);
    searchAddressBar = view.findViewById(R.id.search_address_bar);
  }

  @Override
  public void initializeData() {
    super.initializeData();
    presenter.subscribe();
  }

  @Override
  public void initializeView() {
    super.initializeView();

    searchAddressBar.setOnQueryChangeListener(
        new FloatingSearchView.OnQueryChangeListener() {
          @Override
          public void onSearchTextChanged(String oldQuery, final String newQuery) {
            if (!oldQuery.isEmpty() && newQuery.isEmpty()) {
              searchAddressBar.clearSuggestions();
            } else {
              presenter.searchSuggestions(newQuery);
            }
          }
        }
    );

    searchAddressBar.setOnBindSuggestionCallback(
        new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
          @Override
          public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
            textView.setText(Html.fromHtml(AddressUtils
                .formatAddressSuggestion((AddressSuggestion) item)));

            if (((AddressSuggestion) item).isHistoric()) {
              leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                  R.drawable.ic_history_black_24dp, null));
            } else {
              leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                  R.drawable.ic_search_black_24dp, null));
            }

            Util.setIconColor(leftIcon, Color.parseColor("#616161"));
          }
        }
    );

    searchAddressBar.setOnSearchListener(
        new FloatingSearchView.OnSearchListener() {
          @Override
          public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
            presenter.selectAddress(((AddressSuggestion) searchSuggestion).getUuid());
            searchAddressBar.setLeftMenuOpen(false);
          }

          @Override
          public void onSearchAction(String currentQuery) {

          }
        }
    );

    searchAddressBar.setOnLeftMenuClickListener(
        new FloatingSearchView.OnLeftMenuClickListener() {
          @Override
          public void onMenuOpened() {
            Log.e(TAG, "OPEN");
            presenter.loadHistoric();
          }

          @Override
          public void onMenuClosed() {
            Log.e(TAG, "CLOSE");
            hideSuggestions();
          }
        });

  }

  @Override
  public void showSuggestions(List<AddressSuggestion> searchSuggestions) {
    searchAddressBar.swapSuggestions(searchSuggestions);
  }

  @Override
  public void showHistoric(List<AddressSuggestion> historicAddresses) {
    searchAddressBar.setSearchFocused(true);
    searchAddressBar.clearSearchFocus();
    searchAddressBar.setLeftMenuOpen(true);
    searchAddressBar.swapSuggestions(historicAddresses);
  }

  @Override
  public void showAddress(String address) {
    searchAddressBar.setSearchBarTitle(address);
  }

  @Override
  public void hideSuggestions() {
    searchAddressBar.clearSearchFocus();
  }

  @Override
  public void showProgress() {
    searchAddressBar.showProgress();
  }

  @Override
  public void hideProgress() {
    searchAddressBar.hideProgress();
  }

  @Override
  public void onDestroy() {
    presenter.unsubscribe();
    super.onDestroy();
  }
}
