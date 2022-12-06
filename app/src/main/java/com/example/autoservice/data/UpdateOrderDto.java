package com.example.autoservice.data;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UpdateOrderDto {
    private Long id;
    private Date newDateAndTime;
}