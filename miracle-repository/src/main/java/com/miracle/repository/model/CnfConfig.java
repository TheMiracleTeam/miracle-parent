package com.miracle.repository.model;

import com.miracle.common.base.BaseModel;
import javax.persistence.*;

@Table(name = "cnf_config")
public class CnfConfig extends BaseModel {
    /**
     * ID，自增主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 配置Key值
     */
    @Column(name = "conf_key")
    private String confKey;

    /**
     * 配置内容，JSON格式
     */
    @Column(name = "conf_value")
    private String confValue;

    /**
     * 配置类型，0：系统配置，1：平台配置
     */
    private Byte type;

    /**
     * 是否启用（0：否，1：是）
     */
    private Byte enable;

    /**
     * 创建时间，10位时间戳
     */
    @Column(name = "created_at")
    private Integer createdAt;

    /**
     * 创建者，auth_user表主键
     */
    @Column(name = "created_by")
    private Integer createdBy;

    /**
     * 修改时间，10位时间戳
     */
    @Column(name = "modified_at")
    private Integer modifiedAt;

    /**
     * 修改者，auth_user表主键
     */
    @Column(name = "modified_by")
    private Integer modifiedBy;

    /**
     * 获取ID，自增主键
     *
     * @return id - ID，自增主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID，自增主键
     *
     * @param id ID，自增主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取配置Key值
     *
     * @return conf_key - 配置Key值
     */
    public String getConfKey() {
        return confKey;
    }

    /**
     * 设置配置Key值
     *
     * @param confKey 配置Key值
     */
    public void setConfKey(String confKey) {
        this.confKey = confKey;
    }

    /**
     * 获取配置内容，JSON格式
     *
     * @return conf_value - 配置内容，JSON格式
     */
    public String getConfValue() {
        return confValue;
    }

    /**
     * 设置配置内容，JSON格式
     *
     * @param confValue 配置内容，JSON格式
     */
    public void setConfValue(String confValue) {
        this.confValue = confValue;
    }

    /**
     * 获取配置类型，0：系统配置，1：平台配置
     *
     * @return type - 配置类型，0：系统配置，1：平台配置
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置配置类型，0：系统配置，1：平台配置
     *
     * @param type 配置类型，0：系统配置，1：平台配置
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取是否启用（0：否，1：是）
     *
     * @return enable - 是否启用（0：否，1：是）
     */
    public Byte getEnable() {
        return enable;
    }

    /**
     * 设置是否启用（0：否，1：是）
     *
     * @param enable 是否启用（0：否，1：是）
     */
    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    /**
     * 获取创建时间，10位时间戳
     *
     * @return created_at - 创建时间，10位时间戳
     */
    public Integer getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间，10位时间戳
     *
     * @param createdAt 创建时间，10位时间戳
     */
    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取创建者，auth_user表主键
     *
     * @return created_by - 创建者，auth_user表主键
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建者，auth_user表主键
     *
     * @param createdBy 创建者，auth_user表主键
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取修改时间，10位时间戳
     *
     * @return modified_at - 修改时间，10位时间戳
     */
    public Integer getModifiedAt() {
        return modifiedAt;
    }

    /**
     * 设置修改时间，10位时间戳
     *
     * @param modifiedAt 修改时间，10位时间戳
     */
    public void setModifiedAt(Integer modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    /**
     * 获取修改者，auth_user表主键
     *
     * @return modified_by - 修改者，auth_user表主键
     */
    public Integer getModifiedBy() {
        return modifiedBy;
    }

    /**
     * 设置修改者，auth_user表主键
     *
     * @param modifiedBy 修改者，auth_user表主键
     */
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}