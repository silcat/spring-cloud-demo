package com.example.common.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rrd_code")
    private String rrdCode;

    /**
     * 银行名称
     */
    private String name;


    /**
     * 银行编码
     */
    private String code;

    private String icon;

    /**
     * 0:无效1:有效
     */
    private Byte enable;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Integer createTime;

}