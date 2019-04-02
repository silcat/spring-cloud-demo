package com.example.common.model.clienttwo1.dto;

import javax.persistence.*;

@Table(name = "storage_tbl")
public class StorageTbl {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "commodity_code")
    private String commodityCode;

    private Integer count;

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
     * @return commodity_code
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * @param commodityCode
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    /**
     * @return count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}