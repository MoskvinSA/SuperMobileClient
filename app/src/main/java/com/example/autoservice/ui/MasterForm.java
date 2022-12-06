package com.example.autoservice.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.autoservice.R;
import com.example.autoservice.data.OrderDto;
import com.example.autoservice.data.UsernameDto;
import com.example.autoservice.databinding.FragmentManagerFormBinding;
import com.example.autoservice.databinding.FragmentMasterFormBinding;
import com.example.autoservice.service.UtilClass;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public class MasterForm extends Fragment {

    private FragmentMasterFormBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMasterFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.changeStatusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavHostFragment.findNavController(MasterForm.this)
                        .navigate(R.id.action_masterform_to_changeorderstatusform);
            }
        });

        binding.getOrdersListButtonMaster.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { getOrderList(); }
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
                binding.allOrdersPane.addView(tv);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}