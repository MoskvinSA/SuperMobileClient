package com.example.autoservice.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.autoservice.data.OrderDto;
import com.example.autoservice.databinding.FragmentCustomerFormBinding;
import com.example.autoservice.databinding.FragmentLoginFormBinding;
import com.example.autoservice.service.UtilClass;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerForm extends Fragment {

    private FragmentCustomerFormBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomerFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.bookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewBookOrder();
            }
        });

        binding.ordersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavHostFragment.findNavController(CustomerForm.this)
                        .navigate(R.id.action_customerform_to_callbackform);
            }
        });
    }

    private void viewBookOrder() {
        try {
            binding.lines.removeAllViews();
            List<OrderDto> orders = UtilClass.ordersParser(UtilClass.sendRequestToGetAllUserOrders());
            for (OrderDto f: orders) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,20,0,30);
                params.gravity = Gravity.CENTER;
                TextView tv = new TextView(this.getContext());
                tv.setLayoutParams(params);
                tv.setWidth(850);
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

                binding.lines.addView(tv);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}