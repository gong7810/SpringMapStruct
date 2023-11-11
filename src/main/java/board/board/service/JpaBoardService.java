package board.board.service;

import java.util.List;

import board.board.dto.BoardFileResDto;
import board.board.dto.BoardReqDto;
import board.board.dto.BoardResDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;

public interface JpaBoardService {

	List<BoardResDto> selectBoardList() throws Exception;

	List<String> selectBoardFilePathList(int idx) throws Exception;

	List<BoardFileResDto> selectBoardFileList(int boardIdx) throws Exception;

	void saveBoard(BoardReqDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

	void updateBoard(int boardIdx, BoardReqDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

	BoardResDto selectBoardDetail(int boardIdx) throws Exception;

	void deleteBoard(int boardIdx);

	void deleteBoardFile(int idx);
	BoardFileEntity selectBoardFileInformation(int boardIdx, int idx) throws Exception;

//	List<BoardEntity> searchingBoardByTitle(String title) throws Exception;
	List<BoardResDto> searchingBoardByTitle(String title) throws Exception;

}
