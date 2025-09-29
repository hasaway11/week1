package com.example.week1.vo;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private long bno;
    private String title;
    private String content;
    private String writer;
    private LocalDate writeDay=LocalDate.now();
    private long readCnt=0;
}
