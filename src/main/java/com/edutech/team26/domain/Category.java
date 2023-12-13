package com.edutech.team26.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
    //private Long parentId;

/*    public Category(String categoryName, Long parentId) {
        this.categoryName = categoryName;
        this.parentId = parentId;
    }*/

    public Category() {}


    // 내 부모 - 부모는 하나임
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    // 다대다 (lectures <-> Category)
    @ManyToMany
    @JoinTable(name = "CATEGORY_LECTURE",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "LECTURE_NO")
    )
    private List<Lecture> lectures = new ArrayList<>();


    // 연관관계 메서드
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }


    @Builder
    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}