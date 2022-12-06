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
import com.example.autoservice.data.CallBackDto;
import com.example.autoservice.data.LoginFormDto;
import com.example.autoservice.databinding.FragmentCallBackFormBinding;
import com.example.autoservice.databinding.FragmentCustomerFormBinding;
import com.example.autoservice.databinding.FragmentLoginFormBinding;
import com.example.autoservice.service.UtilClass;

public class CallBackForm extends Fragment {

    private FragmentCallBackFormBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCallBackFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (binding.name.getText().toString().equals("") ||
                        binding.phone.getText().toString().equals("")) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка",
                            "Вы не ввели имя или телефон.\n" +
                                     "Заполните эти поля и попробуйте снова.");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    return;
                }
                String response = UtilClass.sendRequestToOrderCallback(new CallBackDto(
                        binding.name.getText().toString(),
                        binding.phone.getText().toString(),
                        binding.comment.getText().toString()));
                if (response.equals("Success")) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Успех!",
                            "Работа потверждена. ");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    NavHostFragment.findNavController(CallBackForm.this)
                            .navigate(R.id.action_callbackform_to_customerform);
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