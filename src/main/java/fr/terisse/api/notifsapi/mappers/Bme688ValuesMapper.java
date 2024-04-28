package fr.terisse.api.notifsapi.mappers;

import fr.terisse.api.notifsapi.beans.Bme688Values;
import fr.terisse.api.notifsapi.persistence.entities.Bme688ValuesDAO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface Bme688ValuesMapper {

    Bme688ValuesDAO toDAO(Bme688Values bme688Values);
    Bme688Values toBean(Bme688ValuesDAO bme688ValuesDAO);
}
