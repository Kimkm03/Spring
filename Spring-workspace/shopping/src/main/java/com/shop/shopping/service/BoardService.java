package com.shop.shopping.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.shopping.model.Board;
import com.shop.shopping.repository.BoardRepository;

import jakarta.transaction.Transactional;

@Service
public class BoardService {
	private final BoardRepository boardRepository;
	
	@Autowired
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	@Transactional
	public int saveBoard(Board board) {
		try {
			boardRepository.save(board);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
    		System.out.println("BoardService : 게시글작성() : " + e.getMessage());
		}
		return -1;
	}
	
	public void updateBoard(Integer boardId, boolean State, String reply) {
    	Optional<Board> optionalBoard = boardRepository.findById(boardId);
    	Board board = optionalBoard.orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));

    	board.setReply(reply);
    	board.setState(State);
        
    	boardRepository.save(board);
    }
	
	public List<Board> getBoardByUserCode(Integer id){
		return boardRepository.findByUserCode(id);
	}
	
	public List<Board> getAllBoards(){
		return boardRepository.findAll();
	}
	
	public Board getBoardById(Integer id){
		return boardRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Board not found with id " + id));
	}
}
