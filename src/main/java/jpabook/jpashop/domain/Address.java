package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //내장타입이다.
@Getter
public class Address {
    // 값 타입으로 변경을 허용하지 않는 것이 좋은 설계다.
    // Setter를 아예 제공하지 않는다.

    private String city;
    private String street;
    private String zipcode;

    // 필요에 의해 기본 생성자를 만든다.
    // protected까지 JPA가 허용해준다.
    protected Address() { }
    /*
     * JPA 스펙상 엔티티나 임베디드 타입은 자바 기본 생성자를
     * public또는 protected로 설정해야 한다. protected가 그나마 더 안전하다.
     *
     * JPA가 이런 제약을 두는 이유는 JPA 구현 라이브러리가 객체를 생성할 때
     * 리플렉션 같은 기술을 사용할 수 있도록 지원해야 하기 때문이다.
     * */

    //생성자에서 값을 모두 초기화해서 변경 불가능한 클래스를 만든다.
    public Address (String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}


/*
*
* 1. 엔티티에는 가급적 SETT
* Setter가 모두 열려있으면 변경 포인트가 너무 많아 유지보수가 힘들다.
*
* */
