package com.example.springCloud.util.model;

import lombok.Data;

/**
 * @Description: 人员资质的码表
 * @Author: jeecg-boot
 * @Date:   2022-05-18
 * @Version: V1.0
 */
@Data
public class PersonnelCertificateEnum  {
    private String code;
    private String name;
    private String parentCode;
}
