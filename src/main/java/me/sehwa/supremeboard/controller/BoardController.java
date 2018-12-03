package me.sehwa.supremeboard.controller;

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

    private static final int BOARD_POST_SIZE = 3;
    private static final int BOARD_PAGE_SIZE = 5;
    private static final int COMMENT_POST_SIZE = 3;
    private static final int COMMENT_PAGE_SIZE = 5;

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

        String[] searchTypes = searchType != null ? searchType.split("[+]") : null;

        int totalPostSize = boardService.getTotalPostSize(searchTypes, searchStr);
        Pagination pagination = new Pagination(currentPage, totalPostSize, BOARD_POST_SIZE, BOARD_PAGE_SIZE);
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
        Pagination pagination = new Pagination(0, COMMENT_POST_SIZE, COMMENT_PAGE_SIZE); // 댓글목록에 대한 페이징 처리
        List<Comment> comments = commentService.getCommentsByBoard(pagination, theBoard.getId());
//        pagination.setTotalPostSize(comments.size());
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
