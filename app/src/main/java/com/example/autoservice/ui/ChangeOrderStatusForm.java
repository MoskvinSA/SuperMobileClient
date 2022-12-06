package com.example.autoservice.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.autoservice.R;
import com.example.autoservice.data.ForSavingOrderDto;
import com.example.autoservice.data.UpdateOrderDto;
import com.example.autoservice.databinding.FragmentChangeOrderStatusFormBinding;
import com.example.autoservice.databinding.FragmentOrderFormBinding;
import com.example.autoservice.service.UtilClass;

import java.util.Date;

public class ChangeOrderStatusForm extends Fragment {
    private FragmentChangeOrderStatusFormBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChangeOrderStatusFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] countries = { "Не выбрано", "Взят в работу", "Готов" };
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.status.setAdapter(adapter);

        binding.okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String tmpStatus = binding.status.getSelectedItem().toString();
                if(tmpStatus.equals("Взят в работу")){
                    tmpStatus="IN_PROGRESS";
                }
                else if(tmpStatus.equals("Готов")){
                    tmpStatus="READY";
                }
                else{
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка",
                            "Вы не выбрали статус." +
                                    "Попробуйте снова.");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    return;
                }
                UtilClass.sendRequestToChangeStatus(
                        binding.id.getText().toString(), tmpStatus
                );

                CustomDialogFragment dialog = new CustomDialogFragment(
                        "",
                        "Успех!",
                        "Статус заказа был изменен.");
                dialog.show(getActivity().getSupportFragmentManager(), "custom");

                NavHostFragment.findNavController(ChangeOrderStatusForm.this).popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}