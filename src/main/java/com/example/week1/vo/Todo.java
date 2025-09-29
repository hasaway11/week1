package com.example.week1.vo;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private long tno;
    private String title;
    // 자바는 날짜 클래스가 여럿 : Date -> Calendar -> LocalDate, LocalDateTime
    private LocalDate writeday = LocalDate.now();
    private boolean finish;
}
