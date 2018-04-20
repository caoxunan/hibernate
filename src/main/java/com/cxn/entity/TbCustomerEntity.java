package com.cxn.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @program: hibernate
 * @description: ${description}
 * @author: cxn
 * @create: 2018-04-20 14:32
 * @Version v1.0
 */
@Entity
@Table(name = "tb_customer", schema = "hibernate_test", catalog = "")
public class TbCustomerEntity {
    private int id;
    private String name;
    private Integer age;
    private String city;

    public TbCustomerEntity(){}

    public TbCustomerEntity(int id, String name, Integer age, String city) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.city = city;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}

        if (o == null || getClass() != o.getClass()) {return false;}
        TbCustomerEntity that = (TbCustomerEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(age, that.age) &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, age, city);
    }
}
