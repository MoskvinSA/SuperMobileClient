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
import com.example.autoservice.data.UpdateOrderDto;
import com.example.autoservice.databinding.FragmentChangeOrderStatusFormBinding;
import com.example.autoservice.databinding.FragmentEditOrderFormBinding;
import com.example.autoservice.databinding.FragmentOrderFormBinding;
import com.example.autoservice.service.UtilClass;

import java.util.Date;

public class EditOrderForm extends Fragment {
    private FragmentEditOrderFormBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditOrderFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (binding.orderNumber.getText().toString().equals("") ||
                        binding.time.getText().toString().equals("")) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка",
                            "Все поля обязательны\n" +
                                    "Заполните эти поля и попробуйте снова.");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    return;
                }
                try {
                    UpdateOrderDto tmpDto = new UpdateOrderDto(
                            Long.parseLong(binding.orderNumber.getText().toString()),
                            new Date(binding.time.getText().toString()));

                    System.out.println(tmpDto);

                    String response = UtilClass.sendRequestToUpdateOrder(tmpDto);
                    System.out.println(response);
                    if (response.equals("Success")) {
                        CustomDialogFragment dialog = new CustomDialogFragment(
                                "",
                                "Успех",
                                "Заказ обновлён");
                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                        NavHostFragment.findNavController(EditOrderForm.this).popBackStack();

                    }
                } catch (Exception e) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка",
                            "Неверный формат даты\n" +
                            "Пример формата: 12/12/2012 12:12");
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