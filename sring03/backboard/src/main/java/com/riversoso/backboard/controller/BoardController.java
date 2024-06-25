package com.riversoso.backboard.controller;

import java.security.Principal;

// import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riversoso.backboard.entity.Board;
import com.riversoso.backboard.entity.Category;
import com.riversoso.backboard.entity.Member;
import com.riversoso.backboard.service.BoardService;
import com.riversoso.backboard.service.CategoryService;
import com.riversoso.backboard.service.MemberService;
import com.riversoso.backboard.validation.BoardForm;
import com.riversoso.backboard.validation.ReplyForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/board") // Restful URL은 /board로 시작
@Controller
@Log4j2
public class BoardController {
    
    private final BoardService boardService; // 중간 연결책 
    private final MemberService memberService;   // 사용자 정보
    private final CategoryService categoryService; // 카테고리 사용

      // @RequestMapping("list", method=RequestMethod.GET) 아래와 동일
//     @GetMapping("/list")
//     // @RequestMapping("/list", method=RequestMethod.GET)
//     // Model -> controller에 있는 객체를 View로 보내주는 역할을 하는 객체
//     public String list(Model model, @RequestParam(value ="page", defaultValue = "0") int page) {
//         // List<Board> boardList = this.boardservice.getList();
//         // model.addAttribute("boardList", boardList); // thymeleaf, mustache, jsp 등 view로 보내는 기능
    
//     Page<Board> paging = this.boardService.getList(page);   
//     model.addAttribute("paging", paging);    // 페이징된 보드를 view로 전달
//     return "board/list"; // templates/board/list.html 렌더링해서 리턴하라
//   }

    // 24.06.24 list 새로 변경
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value ="page", defaultValue = "0") int page,
                        @RequestParam(value = "kw", defaultValue = "") String keyword) {
    Page<Board> paging = this.boardService.getList(page, keyword);   
    model.addAttribute("paging", paging); 
    model.addAttribute("kw", keyword);
    return "board/list";
    }

    // 24.06.25 카테고리 추가
    @GetMapping("/list/{category}")
    public String list(Model model, 
                        @PathVariable(value = "category") String category,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "kw", defaultValue = "") String keyword) {
    Category cate = this.categoryService.getCategory(category);
    Page<Board> paging = this.boardService.getList(page, keyword, cate);  // 검색 및 카테고리 추가  
    model.addAttribute("paging", paging); 
    model.addAttribute("kw", keyword);
    model.addAttribute("category", category);

    return "board/list";
    }


    // 댓글 검증을 추가하려면 매개변수로 ReplyForm을 전달!!
    @GetMapping("/detail/{bno}")
    public String detail(Model model, @PathVariable("bno") Long bno, ReplyForm replyForm, HttpServletRequest request) {
        // 이전페이지 변수에 담기
        String prevUrl = request.getHeader("referrer");
        log.info(String.format("▶▶▶▶현재 이전 페이지 : %s", prevUrl));
        Board board = this.boardService.getBoard(bno);
        model.addAttribute("board", board);
        model.addAttribute("prevUrl", prevUrl);     // 이전 페이지 URL 뷰어 전달
        return "board/detail";
    }

    @PreAuthorize("isAuthenticated()")  // 로그인시만 작성 가능
    @GetMapping("/create")
    public String create(BoardForm boardForm) {
        return "board/create";
    }
    
    @PreAuthorize("isAuthenticated()")  // 로그인시만 작성 가능
    @PostMapping("/create")
    public String create(@Valid BoardForm boardForm,
                         BindingResult bindingResult, Principal principal) throws Exception {
        
        if (bindingResult.hasErrors()) {
            return "board/create";  // 현재 html에 그대로 머무르기
        }

        Member writer = this.memberService.getMember(principal.getName());  // 현재 로그인 사용자 아이디
        // this.boardService.setBoard(title, content);
        this.boardService.setBoard(boardForm.getTitle(), boardForm.getContent(), writer);
        return "redirect:/board/list";
    }

    // 카테고리 추가
    @PreAuthorize("isAuthenticated()")  // 로그인시만 작성 가능
    @GetMapping("/create/{category}")
    public String create(Model model, 
                         @PathVariable("category") String category, 
                         BoardForm boardForm) {
        model.addAttribute("category", category);
        return "board/create";
    }
    
    // category 추가
    @PreAuthorize("isAuthenticated()")  // 로그인시만 작성 가능
    @PostMapping("/create/{category}")
    public String create(Model model, 
                         @PathVariable("category") String category,
                         @Valid BoardForm boardForm,
                         BindingResult bindingResult, 
                         Principal principal) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", category);
            return "board/create";  // 현재 html에 그대로 머무르기
        }

        Member writer = this.memberService.getMember(principal.getName());  // 현재 로그인 사용자 아이디
        // this.boardService.setBoard(title, content);
        Category cate = this.categoryService.getCategory(category);
        this.boardService.setBoard(boardForm.getTitle(), boardForm.getContent(), writer, cate);
        return String.format("redirect:/board/list/%s", category);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{bno}")
    public String modify(BoardForm boardForm, @PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoard(bno); // 기존 게시글 가져옴
        if(!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        return "board/create";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{bno}")
    public String modify(@Valid BoardForm boardForm,
                        BindingResult bindingResult,
                        Principal principal,
                        @PathVariable("bno") Long bno) throws Exception {
    if (bindingResult.hasErrors()) {
        return "board/create";  // 현재 html에 그대로 머무르기
    }
    Board board = this.boardService.getBoard(bno);
    if(!board.getWriter().getUsername().equals(principal.getName())){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }
    this.boardService.modBoard(board, boardForm.getTitle(), boardForm.getContent());
        return String.format("redirect:/board/detail/%s", bno);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{bno}")
    public String delete(@PathVariable("bno") Long bno, Principal principal) {
        Board board = this.boardService.getBoard(bno);
        if(!board.getWriter().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        this.boardService.remBoard(board);
        return "redirect:/board/list";
    }
    
}
