package board.board.mapstruct;

import board.board.dto.BoardReqDto;
import board.board.dto.BoardResDto;
import board.board.entity.BoardEntity;
import board.common.mapper.EntityResMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardResMapStruct extends EntityResMapper<BoardEntity, BoardResDto> {

    @Override
    BoardResDto toDto(BoardEntity entity);

    @Override
    List<BoardResDto> toDto(List<BoardEntity> entities);
    BoardResMapStruct MAPPER = Mappers.getMapper(BoardResMapStruct.class);

}
