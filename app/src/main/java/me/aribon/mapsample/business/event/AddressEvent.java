package me.aribon.mapsample.business.event;

import android.location.Address;
import android.support.annotation.NonNull;

/**
 * @Author: aribon
 * @Date: 21/01/2018
 */

public class AddressEvent extends Event<Address, AddressEvent.AddressEventType> {

  public enum AddressEventType {
    ALL,
    FROM_INPUT,
    FROM_LOCATION;
  }

  public AddressEvent(Address address, @NonNull AddressEventType addressEventType) {
    super(address, addressEventType);
  }

  public AddressEvent(Address address) {
    super(address, AddressEventType.ALL);
  }

}
