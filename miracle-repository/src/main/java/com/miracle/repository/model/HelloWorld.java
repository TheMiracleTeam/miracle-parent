package com.miracle.repository.model;

import com.miracle.common.base.BaseModel;
import javax.persistence.*;

@Table(name = "hello_world")
public class HelloWorld extends BaseModel {
    /**
     * ID，自增主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 世界名称
     */
    @Column(name = "world_name")
    private String worldName;

    /**
     * 人数
     */
    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    /**
     * 创建时间，10位时间戳
     */
    @Column(name = "created_at")
    private Integer createdAt;

    /**
     * 创建者，外键
     */
    @Column(name = "created_by")
    private Integer createdBy;

    /**
     * 修改时间，10位时间戳
     */
    @Column(name = "modified_at")
    private Integer modifiedAt;

    /**
     * 修改者，外键
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
     * 获取世界名称
     *
     * @return world_name - 世界名称
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * 设置世界名称
     *
     * @param worldName 世界名称
     */
    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    /**
     * 获取人数
     *
     * @return number_of_people - 人数
     */
    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * 设置人数
     *
     * @param numberOfPeople 人数
     */
    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
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
     * 获取创建者，外键
     *
     * @return created_by - 创建者，外键
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建者，外键
     *
     * @param createdBy 创建者，外键
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
     * 获取修改者，外键
     *
     * @return modified_by - 修改者，外键
     */
    public Integer getModifiedBy() {
        return modifiedBy;
    }

    /**
     * 设置修改者，外键
     *
     * @param modifiedBy 修改者，外键
     */
    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}