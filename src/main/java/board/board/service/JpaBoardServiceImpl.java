package board.board.service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import board.board.dto.BoardFileReqDto;
import board.board.dto.BoardFileResDto;
import board.board.dto.BoardReqDto;
import board.board.dto.BoardResDto;
import board.board.mapper.BoardMapper;
import board.board.mapstruct.BoardFileReqMapStruct;
import board.board.mapstruct.BoardFileResMapStruct;
import board.board.mapstruct.BoardReqMapStruct;
import board.board.mapstruct.BoardResMapStruct;
import board.common.FileJpaUtils;
import board.common.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;
import board.board.repository.JpaBoardRepository;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class JpaBoardServiceImpl implements JpaBoardService {

    @Autowired
    JpaBoardRepository jpaBoardRepository;
    @Autowired
    BoardReqMapStruct boardReqMapStruct;
    @Autowired
    BoardResMapStruct boardResMapStruct;
    @Autowired
    BoardFileReqMapStruct boardFileReqMapStruct;
    @Autowired
    BoardFileResMapStruct boardFileResMapStruct;
    @Autowired
    FileUtils fileUtils;
    @Autowired
    FileJpaUtils fileJpaUtils;

    @Override
    public List<BoardResDto> selectBoardList() throws Exception {
        List<BoardEntity> boardEntityList = jpaBoardRepository.findAll();

        return boardResMapStruct.toDto(boardEntityList);
    }

    @Override public List<String> selectBoardFilePathList(int idx) throws Exception{
        List<BoardFileEntity> boardFileEntityList = jpaBoardRepository.selectBoardFile(idx);
        List<String> filePathList = new ArrayList<>();

        for(BoardFileEntity file : boardFileEntityList){
            String storedFilePath = Paths.get("").toAbsolutePath() + "\\" + file.getStoredFilePath();
            filePathList.add(storedFilePath);
        }

        return filePathList;
    }

    @Override
    public List<BoardFileResDto> selectBoardFileList(int boardIdx) throws Exception {
        List<BoardFileEntity> fileEntityList = jpaBoardRepository.findAllbyBoardFile(boardIdx);


        return boardFileResMapStruct.toDto(fileEntityList);
    }

//    클라이언트에서 BoardReqDto로 데이터를 받아 Entity로 변환후 DB에 전달
    public void saveBoard(BoardReqDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        List<BoardFileReqDto> list = fileJpaUtils.parseFileInfo(multipartHttpServletRequest);

        if(!CollectionUtils.isEmpty(list)){
            board.setFileList(list);
        }
//      파일이 있는지 확인
        board.setCreatorId("admin");

        jpaBoardRepository.save(boardReqMapStruct.toEntity(board));
    }

    @Override
    public void updateBoard(int boardIdx, BoardReqDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        List<BoardFileEntity> postFileList = jpaBoardRepository.findAllbyBoardFile(boardIdx);
        List<BoardFileReqDto> dtolist = fileJpaUtils.parseFileInfo(multipartHttpServletRequest);

        BoardEntity inBoard = boardReqMapStruct.toEntity(board);

        if (!CollectionUtils.isEmpty(dtolist)){
            List<BoardFileEntity> Entitylist = boardFileReqMapStruct.toEntity(dtolist);
            postFileList.addAll(Entitylist);
        }
//      새로 추가된 파일이 있는지 확인

        int Count = jpaBoardRepository.findHitCount(boardIdx);

        inBoard.setFileList(postFileList);
        inBoard.setCreatorId("admin");
        inBoard.setHitCnt(Count);

        jpaBoardRepository.save(inBoard);
    }

    @Override
    public BoardResDto selectBoardDetail(int boardIdx) throws Exception {
        Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
        if (optional.isPresent()) {
            BoardEntity board = optional.get();

            board.setHitCnt(board.getHitCnt() + 1);
            jpaBoardRepository.save(board);
//			jpaBoardRepository.flush(); 도 가능

            return boardResMapStruct.toDto(board);
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void deleteBoard(int boardIdx) {
        jpaBoardRepository.deleteById(boardIdx);
    }

    @Override
    public void deleteBoardFile(int idx){
        jpaBoardRepository.deleteFileById(idx);
    }

    @Override
    public BoardFileResDto selectBoardFileInformation(int boardIdx, int idx) throws Exception {
        BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(boardIdx, idx);

        return boardFileResMapStruct.toDto(boardFile);
    }

    @Override
    public List<BoardResDto> searchingBoardByTitle(String title) throws Exception {
        List<BoardEntity> result = jpaBoardRepository.findAllByTitleContainingOrderByCreatedDatetime(title);

        return boardResMapStruct.toDto(result);
    }
}
