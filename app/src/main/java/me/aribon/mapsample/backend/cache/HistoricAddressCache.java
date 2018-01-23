package me.aribon.mapsample.backend.cache;

import android.location.Address;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.aribon.mapsample.utils.AddressUtils;
import me.aribon.mapsample.utils.FileUtils;
import me.aribon.mapsample.utils.ListUtils;

/**
 * @Author: aribon
 * @Date: 20/01/2018
 */

public class HistoricAddressCache extends Cache {

  private static final String TAG = HistoricAddressCache.class.getSimpleName();

  private static final String FILE_NAME = "historic_address";

  private static final int MAX_SIZE = 15;

  @Override
  String getFileName() {
    return FILE_NAME;
  }

  public Single<List<Address>> getAll() {
    File addressFile = buildFile();

    return FileUtils.readFileContent(addressFile)
        .map(new Function<String, List<Address>>() {
               @Override
               public List<Address> apply(String fileContent) throws Exception {
                 Gson gson = new Gson();
                 JsonReader reader = new JsonReader(new StringReader(fileContent));
                 reader.setLenient(true);
                 Type listType = new TypeToken<ArrayList<Address>>() {
                 }.getType();
                 return gson.fromJson(reader, listType);
               }
             }
        );

  }

  public void put(@NonNull Address address) {
    File addressFile = buildFile();
    Gson gson = new Gson();

    FileUtils.readFileContent(addressFile)
        .flatMapCompletable(
            new Function<String, CompletableSource>() {
              @Override
              public CompletableSource apply(String fileContent) throws Exception {
                Type listType = new TypeToken<ArrayList<Address>>() {
                }.getType();

                JsonReader reader = new JsonReader(new StringReader(fileContent));
                reader.setLenient(true);
                List<Address> addresses = gson.fromJson(reader, listType);

                if (addresses != null) {
                  Log.d(TAG, "HISTORIC_CACHE_SIZE -> " + addresses.size());

                  for (int i = 0; i < addresses.size(); i++) {
                    Address tmpAddress = addresses.get(i);

                    if (!AddressUtils.isEmpty(address) && AddressUtils.equals(tmpAddress, address)) {
                      addresses.remove(i);
                      break;
                    }
                  }

                  if (addresses.size() < MAX_SIZE) {
                    addresses = ListUtils.addToTop(address, addresses);
                  } else {
                    addresses.remove(MAX_SIZE - 1);
                    addresses = ListUtils.addToTop(address, addresses);
                  }

                } else {
                  addresses = new ArrayList<>();
                  addresses.add(address);
                }


                deleteAll();

                String addressesJson = gson.toJson(addresses, listType);
                return FileUtils.writeToFile(addressFile, addressesJson);
              }
            }
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            new Action() {
              @Override
              public void run() throws Exception {
                Log.d(TAG, "HISTORIC_CACHE_WRITE_SUCCESS");
              }
            },
            new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "HISTORIC_CACHE_WRITE_ERROR");
              }
            }
        );

  }

  public void deleteAll() {
    File file = buildFile();
    file.delete();
  }
}
