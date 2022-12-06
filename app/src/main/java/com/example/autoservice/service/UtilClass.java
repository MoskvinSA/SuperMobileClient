package com.example.autoservice.service;

import android.os.StrictMode;

import com.example.autoservice.data.CallBackDto;
import com.example.autoservice.data.CallBackEntity;
import com.example.autoservice.data.ForSavingOrderDto;
import com.example.autoservice.data.IdDto;
import com.example.autoservice.data.OrderDto;
import com.example.autoservice.data.UpdateOrderDto;
import com.example.autoservice.data.UsernameDto;
import com.example.autoservice.ui.LoginForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@NoArgsConstructor
public class UtilClass {

    public static String sendRequestToGetAllOrders() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpClient client = new DefaultHttpClient();
            URI url = new URI("http://192.168.1.4:8080/orders/allOrders");
            HttpGet request = new HttpGet();
            request.setURI(url);
            request.setHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            return str_response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static String sendRequestToGetAllCallbacks() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpClient client = new DefaultHttpClient();
            URI url = new URI("http://192.168.1.4:8080/callBack/allCallbacks");
            HttpGet request = new HttpGet();
            request.setURI(url);
            request.setHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            return str_response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static String sendRequestToGetAllUserOrders() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody="Test";
            requestBody = objectMapper.writeValueAsString(
                    new IdDto(String.valueOf(LoginForm.userId)));
            System.out.println(LoginForm.userId);
            StringEntity entity = new StringEntity(requestBody);

            HttpClient client = new DefaultHttpClient();
            URI url = new URI("http://192.168.1.4:8080/customer/getUserOrders");
            HttpPost request = new HttpPost();
            request.setURI(url);
            request.setHeader("Content-Type", "application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            System.out.println(str_response);
            return str_response;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static String sendRequestToChangeStatus(String orderId, String status) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            StringEntity entity = new StringEntity(status);

            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost();
            request.setURI(new URI("http://192.168.1.4:8080/orders/" +
                    orderId + "/changeStatus"));
            request.setHeader("Content-Type", "application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            System.out.println(str_response);
            return str_response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static String sendRequestToSaveOrder(ForSavingOrderDto dto) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody="Test";
            requestBody = objectMapper.writeValueAsString(dto);
            StringEntity entity = new StringEntity(requestBody);

            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost();
            request.setURI(new URI("http://192.168.1.4:8080/orders/save"));
            request.setHeader("Content-Type", "application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            return str_response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static String sendRequestToGetIdByUserName(UsernameDto username) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody="Test";
            requestBody = objectMapper.writeValueAsString(username);
            StringEntity entity = new StringEntity(requestBody);

            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost();
            request.setURI(new URI("http://192.168.1.4:8080/customer/getUserId"));
            request.setHeader("Content-Type", "application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            return str_response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public static String sendRequestToUpdateOrder(UpdateOrderDto dto) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody="Test";
            requestBody = objectMapper.writeValueAsString(dto);
            System.out.println(dto);
            StringEntity entity = new StringEntity(requestBody);

            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost();
            request.setURI(new URI("http://192.168.1.4:8080/orders/update"));
            request.setHeader("Content-Type", "application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            return str_response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }


    public static String sendRequestToOrderCallback(CallBackDto dto) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try  {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody="Test";
            requestBody = objectMapper.writeValueAsString(dto);
            StringEntity entity = new StringEntity(requestBody);

            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost();
            request.setURI(new URI("http://192.168.1.4:8080/callBack/orderCallback"));
            request.setHeader("Content-Type", "application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            String str_response = EntityUtils.toString(response.getEntity());
            return str_response;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return "Error";
    }

    public static List<OrderDto> ordersParser(String orders) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Arrays.asList(objectMapper.readValue(orders, OrderDto[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CallBackEntity> callBackParser(String callBacks) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Arrays.asList(objectMapper.readValue(callBacks, CallBackEntity[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}