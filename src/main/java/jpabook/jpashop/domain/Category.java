package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; //셀프 양방

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); //셀프 양방

    //==연관관계 편의 메서드 start == 양방향일때 사용//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
    //==연관관계 편의 메서드 end == 양방향일때 사용//

}
