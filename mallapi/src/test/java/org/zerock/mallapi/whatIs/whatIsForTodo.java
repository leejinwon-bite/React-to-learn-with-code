package org.zerock.mallapi.whatIs;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void whatIsTodoAndResultsultAndDto(){

        Optional<Todo> result = todoRepository.findById(2L);

        Todo todo = result.orElseThrow();

        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);

        log.info("dto란? "+dto+"라이부로리이란다.");
        log.info("result란? "+result+"이란다.");
        log.info("todo란? "+todo+ "이란다.");
    }

}
