package board.board.mapstruct;

import board.board.dto.BoardFileResDto;
import board.board.entity.BoardFileEntity;
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
public class BoardFileResMapStructImpl implements BoardFileResMapStruct {

    @Override
    public BoardFileResDto toDto(BoardFileEntity fileEntiy) {
        if ( fileEntiy == null ) {
            return null;
        }

        BoardFileResDto boardFileResDto = new BoardFileResDto();

        boardFileResDto.setIdx( fileEntiy.getIdx() );
        boardFileResDto.setOriginalFileName( fileEntiy.getOriginalFileName() );
        boardFileResDto.setStoredFilePath( fileEntiy.getStoredFilePath() );
        boardFileResDto.setFileSize( fileEntiy.getFileSize() );
        boardFileResDto.setCreatorId( fileEntiy.getCreatorId() );
        boardFileResDto.setCreatedDatetime( fileEntiy.getCreatedDatetime() );
        boardFileResDto.setUpdaterId( fileEntiy.getUpdaterId() );
        boardFileResDto.setUpdatedDatetime( fileEntiy.getUpdatedDatetime() );

        return boardFileResDto;
    }

    @Override
    public List<BoardFileResDto> toDto(List<BoardFileEntity> fileEntities) {
        if ( fileEntities == null ) {
            return null;
        }

        List<BoardFileResDto> list = new ArrayList<BoardFileResDto>( fileEntities.size() );
        for ( BoardFileEntity boardFileEntity : fileEntities ) {
            list.add( toDto( boardFileEntity ) );
        }

        return list;
    }
}
