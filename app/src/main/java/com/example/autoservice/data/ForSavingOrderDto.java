package com.example.autoservice.data;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForSavingOrderDto {
    private String name;
    private Long clientId;
    private String date;
}