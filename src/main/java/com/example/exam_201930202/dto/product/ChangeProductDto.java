package com.example.exam_201930202.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeProductDto {
    private Long number;
    private String name;
    private int price;
    private int stock;

}
