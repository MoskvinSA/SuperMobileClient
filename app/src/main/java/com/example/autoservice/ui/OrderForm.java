package com.example.autoservice.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoservice.R;
import com.example.autoservice.data.ForSavingOrderDto;
import com.example.autoservice.data.LoginFormDto;
import com.example.autoservice.databinding.FragmentLoginFormBinding;
import com.example.autoservice.databinding.FragmentOrderFormBinding;
import com.example.autoservice.service.UtilClass;

import java.util.Date;

public class OrderForm extends Fragment {

    private FragmentOrderFormBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (binding.name.getText().equals("") ||
                        binding.clientId.getText().equals("") ||
                        binding.dateAndTime.getText().equals("")) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка",
                            "Все поля обязательны\n" +
                                    "Заполните эти поля и попробуйте снова.");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    return;
                }
                ForSavingOrderDto tmpDto=new ForSavingOrderDto(
                                binding.name.getText().toString(),
                                Long.parseLong(binding.clientId.getText().toString()),
                                new Date(binding.dateAndTime.getText().toString()));

                String response = UtilClass.sendRequestToSaveOrder(tmpDto);
                if (response.equals("Success")) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Успех",
                            "Заказ отправлен");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}