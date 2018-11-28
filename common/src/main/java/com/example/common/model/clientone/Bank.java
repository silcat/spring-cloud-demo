package com.example.common.model.clientone;

import javax.persistence.*;

public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 银行名称
     */
    private String name;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取银行名称
     *
     * @return name - 银行名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置银行名称
     *
     * @param name 银行名称
     */
    public void setName(String name) {
        this.name = name;
    }
}