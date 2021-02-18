package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor //사용시 생성자 만들어줌
@RequiredArgsConstructor
public class MemberService {

    //@Autowired
    private final MemberRepository memberRepository;
    //final을 넣음으로써 컴파일시점에 체크 가능. (값이 안들어오는 구조 방지)

    /*@Autowired   //setter injection
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

//    @Autowired //생성자 injection   @RequiredArgsConstructor 사용하면 final 값들 포함한 생성자 만들어줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
    
    //회원 가입
    @Transactional //기본 readOnly false
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    //@Transactional(readOnly = true) //읽기에는 이렇게 넣어주면 좋다.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //@Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
