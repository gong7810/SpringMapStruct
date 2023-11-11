package board.board.mapstruct;


import board.board.dto.BoardFileReqDto;
import board.board.entity.BoardFileEntity;
import board.common.mapper.FileEntityReqMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardFileReqMapStruct extends FileEntityReqMapper<BoardFileEntity, BoardFileReqDto> {

    BoardFileEntity toEntity(BoardFileReqDto fileDto);

    List<BoardFileEntity> toEntity(List<BoardFileReqDto> fileDtos);
}
