package com.example.week1.controller;

import com.example.week1.vo.Board;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// 객체를 스프링이 생성, 관리, 파괴 -> 객체 생명주기(life cycle)를 스프링이 관리
@Controller
public class BoardController {
    private List<Board> boards = new ArrayList<>();
    private long bno = 1;

    @PostConstruct
    public void init() {
        boards.add(new Board(bno++, "1번글입니다", "내용없음", "spring", LocalDate.now(), 0));
        boards.add(new Board(bno++, "2번글입니다", "내용없음", "summer", LocalDate.now(), 0));
    }

    @GetMapping("/")
    public ModelAndView index(HttpSession session) {
        String username = null;
        if(session.getAttribute("username")!=null)
            username = (String)session.getAttribute("username");
        return new ModelAndView("index").addObject("username", username);
    }

    @GetMapping("/board/list")
    public ModelAndView list() {
        return new ModelAndView("board/list").addObject("boards", boards);
    }

    @GetMapping("/board/read")
    public ModelAndView read(@RequestParam long bno) {
        for(Board b:boards) {
            if(bno==b.getBno()) {
                return new ModelAndView("board/read").addObject("board", b);
            }
        }
        return new ModelAndView("redirect:/board/list?404");
    }

    @PostMapping("/board/remove")
    public ModelAndView remove(@RequestParam long bno, HttpSession session) {
        // 글번호가 일치한다 + 글쓴이다
        String username = (String)session.getAttribute("username");
        boards.removeIf(b->b.getBno()==bno && b.getWriter().equals(username));
        return new ModelAndView("redirect:/board/list");
    }

    @GetMapping("/board/write")
    public void write() {
    }

    @PostMapping("/board/write")
    public ModelAndView write(@ModelAttribute Board board, HttpSession session) {
        System.out.println(board);
        board.setBno(bno++);
        board.setWriter((String)session.getAttribute("username"));
        boards.add(board);
        return new ModelAndView("redirect:/board/list");
    }

    // 번호, 제목, 내용
    @PostMapping("/board/update")
    public ModelAndView update(@ModelAttribute Board params, HttpSession session) {
        System.out.println(params);
        String loginId = (String)session.getAttribute("username");
        for(Board b:boards) {
            // 글을 찾아서 로그인 사용자가 글쓴이가 확인
            if(params.getBno()==b.getBno() && b.getWriter().equals(loginId)) {
                b.setTitle(params.getTitle());
                b.setContent(params.getContent());
            }
        }
        return new ModelAndView("redirect:/board/list");
    }
}
