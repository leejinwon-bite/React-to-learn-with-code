package org.zerock.mallapi.whatIs;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.TodoDTO;
import org.zerock.mallapi.repository.TodoRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class whatIsForTodo {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void whatIsTodoAndResultsultAndDtoAndModelMapper(){

        Optional<Todo> result = todoRepository.findById(2L);

        Todo todo = result.orElseThrow();

        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);

        Todo dto2 = modelMapper.map(dto, Todo.class);

        log.info("result란? "+result+"이란다.");
        log.info("todo란? "+todo+ "이란다.");
        log.info("dto란? "+dto+"ModelMapper 라이부로리이란다.");
        log.info("modelMapper로 타입 원상복귀 시키기: "+dto2);

        // model mapper는 배열의 타입을 바꿔주는 애임. 왼쪽 매개가 변함 당하는 쪽, 오른쪽 매개가 변함 시키는 쪽
    }

    @Test
    public void whatIsResultAndPageableAndGetContentAndStream() {

        Pageable pageable = PageRequest.of(5,2,Sort.by("tno").ascending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info("result란? "+result+"이란다.");
        log.info("Page 3 of 0 여기서 3는 현재 페이지 번호(number+1)를 의미함. 0부터 시작, 0은 페이지 리스트 V가 구현안되서 0임"+
         " 0은 총 페이지 갯수(getTotalPages)");
        log.info("pageable이란? "+result.getPageable()+"이란다");
        log.info("getTotalPages= "+result.getTotalPages());
        log.info("result.getContent이란? "+result.getContent()+"이란다");
        log.info("result.getContent.stram()이란?"+result.getContent().stream()+"이란다");
        result.getContent().stream().forEach(todo -> log.info(todo));
        //굳이 stream().forEach(todo -> log.info(todo)); 안쓰고 result.getContent()얘만 써도 될것 같다.
    }

}
