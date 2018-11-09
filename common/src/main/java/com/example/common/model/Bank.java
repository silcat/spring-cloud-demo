package com.example.common.model;

import javax.persistence.*;

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
     * @return rrd_code
     */
    public String getRrdCode() {
        return rrdCode;
    }

    /**
     * @param rrdCode
     */
    public void setRrdCode(String rrdCode) {
        this.rrdCode = rrdCode;
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


    /**
     * 获取银行编码
     *
     * @return code - 银行编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置银行编码
     *
     * @param code 银行编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取0:无效1:有效
     *
     * @return enable - 0:无效1:有效
     */
    public Byte getEnable() {
        return enable;
    }

    /**
     * 设置0:无效1:有效
     *
     * @param enable 0:无效1:有效
     */
    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}