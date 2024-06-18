package com.riversoso.backboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riversoso.backboard.entity.Board;
import com.riversoso.backboard.service.BoardService;

import lombok.RequiredArgsConstructor;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;



@RequiredArgsConstructor
@RequestMapping("/board") // Restful URL은 /board로 시작
@Controller
public class BoardController {
    
    private final BoardService boardService; // 중간 연결책

    // @RequestMapping("/list", method=RequestMethod.GET) // 아래와 동일 가능
    // Model -> controller에 있는 객체를 View로 보내주는 역할을 하는 객체
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boardList = this.boardService.getList();
        model.addAttribute("boardList", boardList); // thymeleaf, mustache, jsp 등 view로 보내는 기능!!
        return "/board/list"; // templates/board/list.html 렌더링해서 리턴하라!
    } 
    
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno) throws Exception{
        Board board = this.boardService.getBoard(bno);
        model.addAttribute("board", board);
        return "board/detail";
    }
    
}
