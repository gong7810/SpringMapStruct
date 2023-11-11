package board.board.mapstruct;

import board.board.dto.BoardReqDto;
import board.board.entity.BoardEntity;
import board.common.mapper.EntityReqMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardReqMapStruct extends EntityReqMapper<BoardEntity, BoardReqDto> {

//    @Mapping(source = "userName", target = "name")  //dto와 entity가 매핑할때 userName은 target으로 매핑 해라
//    @Mapping(target = "createDate", ignore = true)
    @Override
    BoardEntity toEntity(BoardReqDto dto);

    @Override
    List<BoardEntity> toEntity(List<BoardReqDto> dtos);
    BoardReqMapStruct MAPPER = Mappers.getMapper(BoardReqMapStruct.class);

}
