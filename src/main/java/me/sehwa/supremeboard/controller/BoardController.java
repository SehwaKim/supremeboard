package me.sehwa.supremeboard.controller;

import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import me.sehwa.supremeboard.domain.Board;
import me.sehwa.supremeboard.domain.Comment;
import me.sehwa.supremeboard.service.BoardService;
import me.sehwa.supremeboard.service.CommentService;
import me.sehwa.supremeboard.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/boards")
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String getBoards(@RequestParam(name = "category", defaultValue = "1") Long categoryId,
                            @RequestParam(name = "p", defaultValue = "1") int currentPage,
                            @RequestParam(name = "type", required = false) String searchType,
                            @RequestParam(name = "str", required = false) String searchStr,
                            ModelMap modelMap) {

        Pagination pagination = new Pagination(currentPage);
        String[] searchTypes = searchType != null ? searchType.split("[+]") : null;
        List<Board> boards = boardService.getBoards(pagination, categoryId, searchTypes, searchStr);

        modelMap.addAttribute("boards", boards);
        modelMap.addAttribute("pagination", pagination);
        modelMap.addAttribute("type", searchType);
        modelMap.addAttribute("str", searchStr);
        return "boards/list";
    }

    @GetMapping("/writeform")
    public String writeForm() {
        return "boards/writeform";
    }

    @GetMapping("/replyform")
    public String replyForm(@RequestParam(name = "pId") Long parentId,
                            ModelMap modelMap) {

        Board parentBoard = boardService.getBoardById(parentId);
        modelMap.addAttribute("replyTitle", "Re: " + parentBoard.getTitle());
        modelMap.addAttribute("parentId", parentId);
        return "boards/replyform";
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable(value = "id") Long id, ModelMap modelMap) {
        Board theBoard = boardService.getBoardById(id);
        Pagination pagination = new Pagination();
        List<Comment> comments = commentService.getCommentsByBoard(pagination, theBoard.getId());
        modelMap.addAttribute("board", theBoard);
        modelMap.addAttribute("comments", comments);
        return "boards/view";
    }

    @PostMapping
    public String writeBoard(@ModelAttribute Board board) {
        boardService.writeBoard(board);
        return "redirect:/boards";
    }
}
