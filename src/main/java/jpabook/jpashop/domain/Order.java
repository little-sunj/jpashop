package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") //foreign key가 멤버ID가 된다. 연관관계의 주인
    private Member member;
    // XToOne은 기본이 즉시로딩이다. fetch를 LAZY로 변경해줄것
    // JPQL : select o From order o; -> SQL : select * from order  : n+1문제

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //persist 전파
    private List<OrderItem> orderItems = new ArrayList<>();
    // XToMany는 기본 fetch전략이 LAZY다.

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //java 8

    //EnumType ORDINAL은 1,2,3,4.. 숫자로 들어간다. 꼭 STRING으로 쓸 것
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 편의 메서드 start == 양방향일때 사용//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    //==연관관계 편의 메서드 end == 양방향일때 사용//
}
