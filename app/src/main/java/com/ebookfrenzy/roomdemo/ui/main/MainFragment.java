package com.ebookfrenzy.roomdemo.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebookfrenzy.roomdemo.Product;
import com.ebookfrenzy.roomdemo.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    private MainViewModel mViewModel;
    private FragmentMainBinding binding;
    private ProductListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        listenerSetup();
        observerSetup();
        recyclerSetup();
    }

    private void listenerSetup() {
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.productName.getText().toString();
                String quantity = binding.productQuantity.getText().toString();
                if (!name.equals("") && !quantity.equals("")) {
                    Product product = new Product(name,
                            Integer.parseInt(quantity));
                    mViewModel.insertProduct(product);
                    clearFields();
                } else {
                    binding.productID.setText("Incomplete information");
                }
            }
        });
        binding.findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.findProduct(binding.productName.getText().toString());
            }
        });
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteProduct(binding.productName.getText().
                        toString());
                clearFields();
            }
        });
    }

    private void clearFields() {
        binding.productID.setText("");
        binding.productName.setText("");
        binding.productQuantity.setText("");
    }
}