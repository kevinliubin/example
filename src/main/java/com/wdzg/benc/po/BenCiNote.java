package com.wdzg.benc.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenCiNote implements Serializable {

    @Id
    private Long id;

    private String phone;

    private Long endTime;

    private String note;

}
