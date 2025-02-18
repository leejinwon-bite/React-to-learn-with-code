package org.zerock.mallapi.whatIs;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;
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

    PageRequestDTO pageRequestDTO;

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

    @Test
    public void whatIsCollectAndCollectors(){

        Pageable pageable = PageRequest.of(0,
        10, Sort.by("tno").ascending());

        Page<Todo> result = todoRepository.findAll(pageable);

        List<TodoDTO> dtoList = result.getContent().stream().map(todo -> modelMapper.
        map(todo, TodoDTO.class)).collect(Collectors.toList());

        log.info("collect랑 Collectors 이란? "+dtoList+"이란다");
        log.info("ModelMapper로 타입을 바꾼애에다가 []씌워서, 2차원 배열로 만든거임");
      
    }

    @Test
    public void whatIsBuilderAndWhatDoesDTOHave(){

        TodoDTO todoDTO = TodoDTO.builder()
        .tno(1L)
        .title("제목")
        .writer("나")
        .complete(true)
        .build();

        log.info("bulider 사용후 DTO안에 있는 애들이란? "+todoDTO+"이란다");
        log.info("DTO타입의 필드 = 필드값 이 들어가 있는 배열임. LocalDate 자료형의 초기값은 null이다.");
    }

}
