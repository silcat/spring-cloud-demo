package com.example.common.model.clienttwo1.dto;

import javax.persistence.*;

@Table(name = "order_tbl")
public class OrderTbl {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "commodity_code")
    private String commodityCode;

    private Integer count;

    private Integer money;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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

    /**
     * @return money
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(Integer money) {
        this.money = money;
    }
}