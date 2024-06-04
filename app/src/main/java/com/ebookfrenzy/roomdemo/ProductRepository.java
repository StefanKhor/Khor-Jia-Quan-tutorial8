package com.ebookfrenzy.roomdemo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import android.app.Application;

public class ProductRepository {
    private final MutableLiveData<List<Product>> searchResults =
            new MutableLiveData<>();
    private List<Product> results;
    private final ProductDao productDao;
    public ProductRepository(Application application) {
        ProductRoomDatabase db;
        db = ProductRoomDatabase.getDatabase(application);
        productDao = db.productDao();
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override public void handleMessage(Message msg) {
            searchResults.setValue(results);
        }
    };

    public void insertProduct(Product newproduct) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            productDao.insertProduct(newproduct);
        });
        executor.shutdown();
    }
    public void deleteProduct(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            productDao.deleteProduct(name);
        });
        executor.shutdown();
    }
    public void findProduct(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            results = productDao.findProduct(name);
            handler.sendEmptyMessage(0);
        });
        executor.shutdown();
    }
}