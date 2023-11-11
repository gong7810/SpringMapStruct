package board.common.mapper;

import java.util.List;

public interface FileEntityReqMapper<E,D> {

    E toEntity(D dto);
    List<E> toEntity(List<D> dtos);
}
