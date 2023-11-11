package board.board.mapstruct;

import board.board.dto.BoardFileResDto;
import board.board.entity.BoardFileEntity;
import board.common.mapper.FileEntityResMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardFileResMapStruct extends FileEntityResMapper<BoardFileEntity, BoardFileResDto> {

    BoardFileResDto toDto(BoardFileEntity fileEntiy);

    List<BoardFileResDto> toDto(List<BoardFileEntity> fileEntities);
}