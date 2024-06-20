package com.riversoso.backboard.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Correct import for Spring MVC Model
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.riversoso.backboard.entity.Board;
import com.riversoso.backboard.service.BoardService;
import com.riversoso.backboard.validation.BoardForm;
import com.riversoso.backboard.validation.ReplyForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@RequestMapping("/board") // Restful URL은 /board로 시작
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardservice;

    // @RequestMapping("list", method=RequestMethod.GET) 아래와 동일
    @GetMapping("/list")
    // @RequestMapping("/list", method=RequestMethod.GET)
    // Model -> controller에 있는 객체를 View로 보내주는 역할을 하는 객체
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        // List<Board> boardList = this.boardservice.getList();
        // model.addAttribute("boardList", boardList); // thymeleaf, mustache, jsp 등
        // view로 보내는 기능

        Page<Board> paging = this.boardservice.getList(page);
        model.addAttribute("paging", paging); // 페이징된 보드를 view로 전달
        return "board/list"; // templates/board/list.html 렌더링해서 리턴하라
    }

    // 댓글 검증을 추가하려면 매개변수로 ReplyForm을 전달!!
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno, ReplyForm replyform) throws Exception {
        Board board = this.boardservice.getBoard(bno);
        model.addAttribute("board", board);

        return "board/detail";
    }

    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board/create";
    }

    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/create"; // 현재 html에 그대로 머무르기
        }
        // this.boardservice.setBoard(title, content);
        this.boardservice.setBoard(boardForm.getTitle(), boardForm.getContent());
        return "redirect:/board/list";
    }
}
