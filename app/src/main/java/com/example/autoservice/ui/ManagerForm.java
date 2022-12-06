package com.example.autoservice.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.autoservice.R;
import com.example.autoservice.data.CallBackEntity;
import com.example.autoservice.data.LoginFormDto;
import com.example.autoservice.data.OrderDto;
import com.example.autoservice.data.UsernameDto;
import com.example.autoservice.databinding.FragmentCustomerFormBinding;
import com.example.autoservice.databinding.FragmentLoginFormBinding;
import com.example.autoservice.databinding.FragmentManagerFormBinding;
import com.example.autoservice.service.UtilClass;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class ManagerForm extends Fragment {

    private FragmentManagerFormBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentManagerFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getOrdersListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { getOrderList(); }
        });

        binding.getCallBacksButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { getCallBacks(); }
        });

        binding.addNewOrderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavHostFragment.findNavController(ManagerForm.this)
                        .navigate(R.id.action_managerform_to_neworderform);
            }
        });

        binding.changeOrderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavHostFragment.findNavController(ManagerForm.this)
                        .navigate(R.id.action_managerform_to_editorderform);
            }
        });

        binding.getClientIdButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (binding.userNameField.getText().toString().equals("")) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка",
                            "Вы не ввели данные");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    return;
                }
                String response = UtilClass.sendRequestToGetIdByUserName(new UsernameDto(
                        binding.userNameField.getText().toString()
                ));

                if(response.equals("-100")){
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка",
                            "Такого пользователя нет");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    return;
                }

                CustomDialogFragment dialog = new CustomDialogFragment(
                        "",
                        "Успех!",
                        "Id пользователя: " + response);
                dialog.show(getActivity().getSupportFragmentManager(), "custom");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getOrderList() {
        try {
            binding.allOrdersPane.removeAllViews();
            List<OrderDto> orders = UtilClass.ordersParser(
                    UtilClass.sendRequestToGetAllOrders());
            for (OrderDto f: orders) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,20,30,30);
                params.gravity = Gravity.CENTER;
                TextView tv = new TextView(this.getContext());
                tv.setLayoutParams(params);
                tv.setWidth(650);
                tv.setHeight(150);
                tv.setTextSize(30);
                tv.setGravity(Gravity.CENTER);
                String status = "";
                if (f.getStatus().toString().equals("CREATED")) {
                    tv.setBackgroundResource(R.color.create);
                    status = "В очереди";
                } else if (f.getStatus().toString().equals("IN_PROGRESS")) {
                    tv.setBackgroundResource(R.color.in_progress);
                    status = "В работе";
                } else if (f.getStatus().toString().equals("READY")) {
                    tv.setBackgroundResource(R.color.ready);
                    status = "Готово";
                }
                tv.setText( "№" + f.getId().toString() + " : " + status);

                String finalStatus = status;

                //tv.setText(f.toString());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialogFragment dialog = new CustomDialogFragment(
                                "",
                                "№" + f.getId().toString() + " : " + f.getName().toString(),
                                "Уважаемый " + f.getClientName().toString()
                                        + ",\nВаш заказ под номером: " + f.getId().toString()
                                        + " \nЗаказ в статусе: "
                                        + finalStatus + "\n" + f.getDate());
                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    }
                });
                binding.allOrdersPane.addView(tv);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void getCallBacks() {
        try {
            binding.callBackTextArea.removeAllViews();
            List<CallBackEntity> callBacks = UtilClass.callBackParser(
                    UtilClass.sendRequestToGetAllCallbacks());
            for (CallBackEntity f: callBacks) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,20,30,30);
                params.gravity = Gravity.CENTER;
                TextView tv = new TextView(this.getContext());
                tv.setWidth(650);
                tv.setHeight(150);
                tv.setTextSize(30);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.color.create);
                tv.setLayoutParams(params);
                tv.setText(" Новый заказ №" + f.getId().toString());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialogFragment dialog = new CustomDialogFragment(
                                "",
                                " Новый заказ №" + f.getId().toString() ,
                                         ",\nНовый заказ заказ под номером: " + f.getId().toString());
                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    }
                });
                binding.callBackTextArea.addView(tv);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}