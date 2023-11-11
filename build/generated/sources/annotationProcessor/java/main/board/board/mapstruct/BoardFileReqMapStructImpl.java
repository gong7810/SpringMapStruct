package board.board.mapstruct;

import board.board.dto.BoardFileReqDto;
import board.board.entity.BoardFileEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-11T20:23:31+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-5.2.1.jar, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class BoardFileReqMapStructImpl implements BoardFileReqMapStruct {

    @Override
    public BoardFileEntity toEntity(BoardFileReqDto fileDto) {
        if ( fileDto == null ) {
            return null;
        }

        BoardFileEntity boardFileEntity = new BoardFileEntity();

        boardFileEntity.setIdx( fileDto.getIdx() );
        boardFileEntity.setOriginalFileName( fileDto.getOriginalFileName() );
        boardFileEntity.setStoredFilePath( fileDto.getStoredFilePath() );
        boardFileEntity.setFileSize( fileDto.getFileSize() );
        boardFileEntity.setCreatorId( fileDto.getCreatorId() );
        boardFileEntity.setCreatedDatetime( fileDto.getCreatedDatetime() );
        boardFileEntity.setUpdaterId( fileDto.getUpdaterId() );
        boardFileEntity.setUpdatedDatetime( fileDto.getUpdatedDatetime() );

        return boardFileEntity;
    }

    @Override
    public List<BoardFileEntity> toEntity(List<BoardFileReqDto> fileDtos) {
        if ( fileDtos == null ) {
            return null;
        }

        List<BoardFileEntity> list = new ArrayList<BoardFileEntity>( fileDtos.size() );
        for ( BoardFileReqDto boardFileReqDto : fileDtos ) {
            list.add( toEntity( boardFileReqDto ) );
        }

        return list;
    }
}
