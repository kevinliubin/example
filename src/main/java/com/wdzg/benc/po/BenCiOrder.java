package com.wdzg.benc.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenCiOrder implements Serializable {

    private Long id;

    private String phone;

    private String name;
}
