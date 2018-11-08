package me.sehwa.supremeboard.controller;

import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import me.sehwa.supremeboard.domain.Board;
import me.sehwa.supremeboard.service.BoardService;
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

    @GetMapping
    public String getBoards(@RequestParam(name = "category", defaultValue = "1") Long categoryId,
                            @RequestParam(name = "p", defaultValue = "1") int page,
                            @RequestParam(name = "type", required = false) String searchType,
                            @RequestParam(name = "str", required = false) String searchStr,
                            ModelMap modelMap) {

        Pagination pagination = new Pagination();
        String[] searchTypes = searchType != null ? searchType.split("[+]") : null;
        List<Board> boards = boardService.getBoards(pagination, categoryId, searchTypes, searchStr);
        log.info("boards size: {}", boards.size());
        modelMap.addAttribute("boards", boards);
        return "boards/list";
    }

    @GetMapping("/writeform")
    public String writeForm() {
        return "boards/writeform";
    }

    @PostMapping
    public String writeBoard(@ModelAttribute Board board) {
        boardService.writeBoard(board);
        return "redirect:/boards";
    }
}
