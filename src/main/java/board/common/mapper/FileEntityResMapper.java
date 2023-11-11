package board.common.mapper;

import java.util.List;

public interface FileEntityResMapper<E,D> {

    D toDto(E fileEntiy);
    List<D> toDto(List<E> fileEntities);
}
