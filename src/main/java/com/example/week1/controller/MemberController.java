package com.example.week1.controller;

import com.example.week1.vo.Member;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {
    private List<Member> members = new ArrayList<>();

    @PostConstruct
    public void init() {
        members.add(new Member("spring", "1234"));
        members.add(new Member("summer", "1234"));
    }

    @GetMapping("/member/login")
    public ModelAndView login() {
        return new ModelAndView("member/login");
    }

    @PostMapping("/member/login")
    public ModelAndView loign(@RequestParam String username, @RequestParam String password, HttpSession session) {
         for(Member m:members) {
             // 로그인에 성공한 경우 세션에 아이디를 저장한 다음 루트페이지로 이동(이때 아이디를 가지고 이동해야 nav.html에 로그인 확인이 가능)
             if(m.getUsername().equals(username) && m.getPassword().equals(password)) {
                 session.setAttribute("username", username);
                 return new ModelAndView("redirect:/");
             }
        }
        return new ModelAndView("redirect:/?error");
    }

    @PostMapping("/member/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/");
    }

}
