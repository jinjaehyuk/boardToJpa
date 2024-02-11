package com.example.board.controller;

import com.example.board.domain.Board;
import com.example.board.dto.LoginInfo;
import com.example.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


//HTTP요청을 받아서 응답을 하는 컴포넌트. 스프링부트가 자동으로 Bean을 생성
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/")
    public String list(@RequestParam(name="page",defaultValue = "0") int page, HttpSession session, Model model){
        //페이징 처리
        LoginInfo loginInfo = (LoginInfo)  session.getAttribute("loginInfo");
        model.addAttribute("loginInfo",loginInfo);

        long totalCount = boardService.getTotalCount();
        List<Board> list = boardService.getBoards(page);
        long pageCount = totalCount/10;
        if(totalCount % 10 > 0 ){
            pageCount++;
        }
        int currentPage = page;
        model.addAttribute("list",list);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("currentPage",currentPage);
        return "list";
    }

    @GetMapping("/board")
    public String board( @RequestParam("boardId") int boardId, Model model){
        System.out.println("boardId = "+ boardId);
        //id에 해당하는 게시글을 읽어옴.

        //id에 조회수도 1증가.

        Board board =boardService.getBoard(boardId);
        model.addAttribute("board",board);
        return "board";
    }

    @GetMapping("/writeForm")
    public String writeForm(HttpSession session, Model model){
        //로그인한 사용자만 글을 써야한다. 로그인을 하지 않았다면 리스트보기로 자동이동 시킨다.
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginform";
        }

        model.addAttribute("loginInfo",loginInfo);

        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session
    ){
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginform";
        }
        boardService.addBoard(loginInfo.getUserId(), title,content);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("boardId") int boardId, HttpSession session){
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginform";
        }

        Board board = boardService.getBoard(boardId);

        List<String> roles = loginInfo.getRoles();

        if(roles.contains("ROLE_ADMIN")){
            boardService.deleteBoard(boardId);
        }else{
            boardService.deleteBoard(loginInfo.getUserId(), boardId);
        }

        return "redirect:/";
    }

    @GetMapping("/updateform")
    public String updateform(@RequestParam("boardId") int boardId, Model model, HttpSession session){
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginform";
        }
        Board board = boardService.getBoard(boardId,false);
        model.addAttribute("board", board);
        model.addAttribute("loginInfo", loginInfo);
        return "updateform";
    }

    @PostMapping("/update")
    public String update(
            @RequestParam("boardId") int boardId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session
    ){
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginform";
        }

        Board board = boardService.getBoard(boardId,false);
        if(board.getUser().getUserId() != loginInfo.getUserId()){
            return "redirect:/board?boardId="+boardId;
        }
        boardService.updateBoard(boardId,title,content);
        return "redirect:/board?boardId="+boardId;
    }
}
