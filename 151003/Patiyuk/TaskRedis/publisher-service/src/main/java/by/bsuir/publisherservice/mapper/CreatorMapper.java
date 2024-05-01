package by.bsuir.publisherservice.mapper;

import by.bsuir.publisherservice.dto.request.CreatorRequestTo;
import by.bsuir.publisherservice.dto.response.CreatorResponseTo;
import by.bsuir.publisherservice.entity.Creator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreatorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    Creator toEntity(CreatorRequestTo request);

    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    CreatorResponseTo toResponseTo(Creator entity);

    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    Creator updateEntity(@MappingTarget Creator entity, CreatorRequestTo request);
}
