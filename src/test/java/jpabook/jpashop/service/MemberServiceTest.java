package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional //기본적으로 DB인서트 안하고 롤백한다.
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test //@Rollback(false) //<- 이렇게 하면 롤백을 막을 수 있다.
    void join() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        //em.flush(); //DB반영할 수 있음
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    void duplicate_member_reserve() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        //try {
        //    memberService.join(member2); //예외가 발생해야 한다!
        //} catch (IllegalStateException e) {
        //    return;
        //}

        //then
        //fail("예외가 발생해야 한다.");
        IllegalStateException thrown = assertThrows(IllegalStateException.class, ()-> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }

}