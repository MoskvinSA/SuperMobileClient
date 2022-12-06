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
import com.example.autoservice.data.LoginFormDto;
import com.example.autoservice.data.RegistrationFormDto;
import com.example.autoservice.databinding.FragmentLoginFormBinding;
import com.example.autoservice.databinding.FragmentRegistrationFormBinding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class RegistrationForm extends Fragment {

    private FragmentRegistrationFormBinding binding;
    public static long userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistrationFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (binding.username.getText().toString().equals("")
                        || binding.password.getText().toString().equals("")
                        || binding.password2.getText().toString().equals("")
                        || binding.fullName.getText().toString().equals("")
                        || binding.phone.getText().toString().equals("")
                        || binding.email.getText().toString().equals("")) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка",
                            "Все поля обязательны.\n" +
                                    "Заполните эти поля и попробуйте снова.");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    return;
                }
                if (!binding.password.getText().toString().equals(
                        binding.password2.getText().toString())) {
                    CustomDialogFragment dialog = new CustomDialogFragment(
                            "",
                            "Ошибка регистрации",
                            "Пароли не совпадают");
                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    return;
                }

                RegistrationFormDto registrationForm =
                        new RegistrationFormDto(
                                binding.username.getText().toString(),
                                binding.password.getText().toString(),
                                binding.fullName.getText().toString(),
                                binding.phone.getText().toString(),
                                binding.email.getText().toString()
                        );

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                try  {
                    HttpClient client = new DefaultHttpClient();

                    ObjectMapper objectMapper = new ObjectMapper();
                    String requestBody = "Test";
                    requestBody = objectMapper.writeValueAsString(registrationForm);
                    StringEntity entity = new StringEntity(requestBody);

                    HttpPost request = new HttpPost();
                    request.setURI(new URI("http://192.168.1.4:8080/registration"));
                    request.setHeader("Content-Type", "application/json");
                    request.setEntity(entity);

                    HttpResponse response = client.execute(request);
                    String str_response = EntityUtils.toString(response.getEntity());

                    if(response.equals("Success")){
                        CustomDialogFragment dialog = new CustomDialogFragment(
                                "",
                                "Успех",
                                "Вы успешно зарегистрированы, " +
                                        "пожалуйста, войдите в систему.");
                        dialog.show(getActivity().getSupportFragmentManager(), "custom");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                NavHostFragment.findNavController(RegistrationForm.this)
                        .navigate(R.id.action_registrationform_to_loginform);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}