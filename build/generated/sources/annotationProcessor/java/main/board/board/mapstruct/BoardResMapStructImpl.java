package board.board.mapstruct;

import board.board.dto.BoardFileResDto;
import board.board.dto.BoardResDto;
import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-11T17:53:52+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-5.2.1.jar, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class BoardResMapStructImpl implements BoardResMapStruct {

    @Override
    public BoardResDto toDto(BoardEntity entity) {
        if ( entity == null ) {
            return null;
        }

        BoardResDto boardResDto = new BoardResDto();

        boardResDto.setBoardIdx( entity.getBoardIdx() );
        boardResDto.setTitle( entity.getTitle() );
        boardResDto.setContents( entity.getContents() );
        boardResDto.setHitCnt( entity.getHitCnt() );
        boardResDto.setCreatorId( entity.getCreatorId() );
        if ( entity.getCreatedDatetime() != null ) {
            boardResDto.setCreatedDatetime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( entity.getCreatedDatetime() ) );
        }
        boardResDto.setUpdaterId( entity.getUpdaterId() );
        if ( entity.getUpdatedDatetime() != null ) {
            boardResDto.setUpdatedDatetime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( entity.getUpdatedDatetime() ) );
        }
        boardResDto.setFileList( boardFileEntityListToBoardFileResDtoList( entity.getFileList() ) );

        return boardResDto;
    }

    @Override
    public List<BoardResDto> toDto(List<BoardEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<BoardResDto> list = new ArrayList<BoardResDto>( entities.size() );
        for ( BoardEntity boardEntity : entities ) {
            list.add( toDto( boardEntity ) );
        }

        return list;
    }

    protected BoardFileResDto boardFileEntityToBoardFileResDto(BoardFileEntity boardFileEntity) {
        if ( boardFileEntity == null ) {
            return null;
        }

        BoardFileResDto boardFileResDto = new BoardFileResDto();

        boardFileResDto.setIdx( boardFileEntity.getIdx() );
        boardFileResDto.setOriginalFileName( boardFileEntity.getOriginalFileName() );
        boardFileResDto.setStoredFilePath( boardFileEntity.getStoredFilePath() );
        boardFileResDto.setFileSize( boardFileEntity.getFileSize() );
        boardFileResDto.setCreatorId( boardFileEntity.getCreatorId() );
        boardFileResDto.setCreatedDatetime( boardFileEntity.getCreatedDatetime() );
        boardFileResDto.setUpdaterId( boardFileEntity.getUpdaterId() );
        boardFileResDto.setUpdatedDatetime( boardFileEntity.getUpdatedDatetime() );

        return boardFileResDto;
    }

    protected List<BoardFileResDto> boardFileEntityListToBoardFileResDtoList(List<BoardFileEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<BoardFileResDto> list1 = new ArrayList<BoardFileResDto>( list.size() );
        for ( BoardFileEntity boardFileEntity : list ) {
            list1.add( boardFileEntityToBoardFileResDto( boardFileEntity ) );
        }

        return list1;
    }
}
