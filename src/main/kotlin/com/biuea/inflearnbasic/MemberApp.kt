package com.biuea.inflearnbasic

import com.biuea.inflearnbasic.member.Grade
import com.biuea.inflearnbasic.member.Member
import com.biuea.inflearnbasic.member.MemberService
import com.biuea.inflearnbasic.member.MemberServiceImpl
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class MemberApp

fun main(args: Array<String>) {
//        val appConfig = AppConfig()
//        val memberService = appConfig.memberService()
    // AppConfig의 환경설정 정보를 가지고, 스프링 컨테이너에 객체를 생성하고 관리한다.
    // ApplicationContext: 스프링 컨테이너
    // 스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정(구성) 정보로 사용한다.
    // 여기서 @Bean이 붙은 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.
    val applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
    // 스프링 컨테이너에서 memberService라는 이름으로 등록된 빈을 찾아서 꺼내온다.
    // key: value 형태
    val memberService = applicationContext.getBean("memberService", MemberService::class.java)
    val member = Member(
        id = 1L,
        name = "memberA",
        grade = Grade.VIP
    )
    memberService.join(member)
    val member1 = memberService.findMember(1L)
}