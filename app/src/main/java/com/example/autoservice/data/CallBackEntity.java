package com.example.autoservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallBackEntity {
    private Long id;
    private String name;
    private String phone;
    private String comment;
}
