package com.example.autoservice.data;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String name;
    private String clientName;
    private Date date;
    private String status;
}
