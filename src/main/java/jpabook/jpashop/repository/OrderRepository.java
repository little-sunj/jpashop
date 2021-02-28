package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {


        return em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status" +
                " and m.name like :name",  Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000)
                .getResultList();

//        QOrder order = QOrder.order;
//        QMember member = QMember.member;
//
//        return query
//                .select(order)
//                .from(order)
//                .join(order.member, member)
//                .where(statusEq(orderSearch.getOrderStatus()),
//                        namelike(orderSearch.getMemberName()))
//                .limit(1000)
//                .fetch();
    }

//    private BooleanExpresstion statusEq(OrderStatus statusCond) {
//        if (statusCond == null) {
//            return null;
//        }
//        return order.status.eq(statusCond);
//    }
//
//    private BooleanExpression namelike(String nameCond) {
//        if (!StringUtils.hasText(nameCond)) {
//            return null;
//        }
//        return member.name.like(nameCond);
//    }

}

