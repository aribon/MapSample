package me.aribon.mapsample.ui.address;

import me.aribon.mapsample.R;
import me.aribon.mapsample.ui.base.BasePlaceAutocompleteFragment;

/**
 * @Author: aribon
 * @Date: 17/01/2018
 */

public class SearchAddressFragment extends BasePlaceAutocompleteFragment implements SearchAddressContract.View {

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_search_address;
    }

}
