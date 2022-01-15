package com.auth.auth.util.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface GenericMapper<D,E> {
    GenericMapper INSTANCE = Mappers.getMapper(GenericMapper.class);
    D toDto(E entity);
    E toEntity(D dto);

    List<E> toEntity(List<D> dtoList);
    List<D> toDto(List<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);
}
