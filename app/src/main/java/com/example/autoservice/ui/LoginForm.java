package com.example.autoservice.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoservice.R;
import com.example.autoservice.data.AuthDto;
import com.example.autoservice.data.LoginFormDto;
import com.example.autoservice.databinding.FragmentLoginFormBinding;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginForm extends Fragment {

    private FragmentLoginFormBinding binding;
    public static long userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendRequest( new LoginFormDto( binding.username.getText().toString(),
                                                    binding.password.getText().toString()));
            }
        });
        binding.register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginForm.this)
                        .navigate(R.id.action_loginform_to_registrationform);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void sendRequest(LoginFormDto loginForm) {
        if (loginForm.getUsername().isEmpty() ||
                loginForm.getPassword().isEmpty()) {
            CustomDialogFragment dialog = new CustomDialogFragment(
                    "",
                    "Ошибка",
                    "Вы не ввели логин или пароль.\n" +
                            "Заполните эти поля и попробуйте снова.");
            dialog.show(getActivity().getSupportFragmentManager(), "custom");
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try  {
            HttpClient client = new DefaultHttpClient();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = "Test";
            requestBody = objectMapper.writeValueAsString(loginForm);
            StringEntity entity = new StringEntity(requestBody);

            HttpPost request = new HttpPost();
            request.setURI(new URI("http://192.168.1.4:8080/auth"));
            request.setHeader("Content-Type", "application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            binding.username.setText("");
            binding.password.setText("");
            createFormAfterLogin(str_response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFormAfterLogin(String response) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AuthDto authDto = objectMapper.readValue(response, AuthDto.class);
            userId = authDto.getId();
            if (authDto.getRoleName().equals("MANAGER")) {
                NavHostFragment.findNavController(LoginForm.this)
                        .navigate(R.id.action_loginform_to_managerform);
            } else if (authDto.getRoleName().equals("MASTER")) {
                NavHostFragment.findNavController(LoginForm.this)
                        .navigate(R.id.action_loginform_to_masterform);
            } else if (authDto.getRoleName().equals("CUSTOMER")) {
                NavHostFragment.findNavController(LoginForm.this)
                        .navigate(R.id.action_loginform_to_customerform);
            } else {
                CustomDialogFragment dialog = new CustomDialogFragment(
                        "",
                        "Ошибка",
                        "Вы не ввели не верный или не сущестующий аккаунт.\n" +
                                "Заполните эти поля и попробуйте снова.");
                dialog.show(getActivity().getSupportFragmentManager(), "custom");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}