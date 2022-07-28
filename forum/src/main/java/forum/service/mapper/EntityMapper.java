package forum.service.mapper;

import java.util.List;

public interface EntityMapper<E, D> {
    E toEntity(D dto);
    D toDto(E dto);

    List<E> toEntity(List<D> dtoList);
    List<D> toDto(List<E> entityList);
}
