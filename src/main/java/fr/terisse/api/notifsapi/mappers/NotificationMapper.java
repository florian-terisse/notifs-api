package fr.terisse.api.notifsapi.mappers;

import fr.terisse.api.notifsapi.beans.Notification;
import fr.terisse.api.notifsapi.dto.NotificationDTO;
import fr.terisse.api.notifsapi.persistence.entities.NotificationDAO;
import org.mapstruct.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {


    @Mapping(target = "receivedAt", qualifiedByName = "dateToString")
    NotificationDTO toDto(Notification notification);
    NotificationDAO toDAO(Notification notification);
    Notification toBean(NotificationDAO notificationDAO);

    @Mapping(target = "receivedAt", qualifiedByName = "stringToDate")
    Notification toBean(NotificationDTO notificationDTO);

    @Named("stringToDate")
    static Date toDate(String date) throws ParseException {
        return new SimpleDateFormat("MMMM dd,yyyy' at 'hh:mmaaa", Locale.ENGLISH).parse(date);
    }

    @Named("dateToString")
    static String toString(Date date) throws ParseException {
        return new SimpleDateFormat("MMMM dd,yyyy' at 'hh:mmaaa", Locale.ENGLISH).format(date);
    }
}
