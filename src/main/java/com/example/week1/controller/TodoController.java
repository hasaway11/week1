package com.example.week1.controller;

import com.example.week1.vo.Todo;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// MVC 컨트롤러 : View + 데이터
// REST 컨트롤러 : 데이터만 JSON으로(sample.bmaster...)

// MVC : GET으로 보여준다, POST로 처리한다
// REST : C(post) R(get) U(patch) D(delete)
@RestController
public class TodoController {
    private List<Todo> todos = new ArrayList<>();
    private long tno = 3;

    @PostConstruct
    public void init() {
        todos.add(new Todo(1, "친구들 모임", LocalDate.now(), false));
        todos.add(new Todo(2, "형님 결혼식", LocalDate.now(), false));
    }


    @GetMapping("/todos")
    public List<Todo> list() {
        return todos;
    }

    // @RequestParam : ?username=spring 형식(urlencoded)을 수신할 때
    // @RequestBody : {'username':'spring'} 형식(json)을 수신할 때
    @PostMapping("/todos")
    public @ResponseBody List<Todo> write(@RequestParam String title) {
        Todo newTodo = new Todo(tno++, title, LocalDate.now(), false);
        todos.add(newTodo);
        return todos;
    }

    // rest는 /todos/11과 같이 주소의 일부가 파라미터가 되는 방식을 즐겨서 사용한다
    @PatchMapping("/todos/{tno}")
    public ResponseEntity<?> toggle(@PathVariable long tno) {
       boolean success = false;
       for(Todo todo:todos) {
           if(todo.getTno()==tno) {
               todo.setFinish(!todo.isFinish());
               success = true;
           }
       }
       if(success)
           return ResponseEntity.ok(todos);
       else
           return ResponseEntity.status(409).body(null);
    }

    @DeleteMapping("/todos/{tno}")
    public ResponseEntity<List<Todo>> remove(@PathVariable long tno) {
        todos.removeIf(todo->todo.getTno()==tno);
        // 서버의 상태 코드(http status)
        // 200 : 성공(서버에서 오류가 발생하지 않았다)
        //      아이디 사용여부 확인 -> 응답이 200 -> 사용가능하다는 뜻?
        //      rest는 200을 "내가 원하는 결과"로 바꿔서 사용
        // 400 : 수신 거부
        // 401 : 로그인이 필요하다
        // 403 : 작업할 권한이 없다
        // 404 : 주소없음
        // 405 : 잘못된 method(get->post, post->get)
        // 409 : rest에서 작업 실패로 많이 쓰인다
        // 500 : 처리 중 오류

        // ReponseEntity = @ResponseBody + 사용자 정의 상태코드
        return ResponseEntity.ok(todos);
    }
}

/*
    @Controller, @Configuration, @RequestParam, @RequestBody, @ResponseBody
    @ModelAttribute, @PathVariable, @RestController
*/








