package com.shop.shopping.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopping.dto.ResponseDto;
import com.shop.shopping.model.Board;
import com.shop.shopping.service.BoardService;

@RestController
@RequestMapping("/api/board")
@CrossOrigin(origins = "http://localhost:3000")
public class BoardController {
	private final BoardService boardService; // 가정: 주문 서비스

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    
    @PostMapping("/save")
    public ResponseDto<Integer> save(@RequestBody Board board){
    	board.setState(false);
    	board.setReply(null);
        int result = boardService.saveBoard(board);
        return new ResponseDto<>(HttpStatus.OK.value(), result);
    }
    
    @PutMapping("/check/{id}")
    public ResponseEntity<ResponseDto<String>> checkBoard(@PathVariable Integer id, @RequestParam boolean orderStatus, @RequestParam String reply){
    	try {
    		boardService.updateBoard(id, orderStatus, reply);
            return ResponseEntity.ok(new ResponseDto<>(1, "Board state updated"));
        } catch (IllegalArgumentException e) {
        	return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto<>(-1, "Invalid board state"));
        }
    }
    
    @GetMapping("/allBoards")
    public List<Board> allBoards(){
    	return boardService.getAllBoards();
    }
    
    @GetMapping("/detail/{id}")
    public List<Board> boardDetail(@PathVariable Integer id){
    	return boardService.getBoardByUserCode(id);
    }
    
    @GetMapping("/{id}")
    public Board getProductById(@PathVariable Integer id) {
        return boardService.getBoardById(id);
    }
}
