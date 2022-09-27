package com.mrugesh1996.waraclecoding.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cake")
public class Cake {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer cakeId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "desc", nullable = true, length = 100)
    private String desc;

    @Column(name = "image", nullable = true, length = 300)
    private String image;

    public Cake() {
    }

    public Cake(String title, String description, String image) {
        this.title = title;
        this.desc = description;
        this.image = image;
    }

    public Integer getCakeId() {
        return cakeId;
    }

    public void setCakeId(Integer cakeId) {
        this.cakeId = cakeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "cakeId=" + cakeId +
                ", title='" + title + '\'' +
                ", description='" + desc + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cake cake = (Cake) o;
        return title.equals(cake.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
