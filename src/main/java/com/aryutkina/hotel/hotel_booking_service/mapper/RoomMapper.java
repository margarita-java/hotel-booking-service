package com.aryutkina.hotel.hotel_booking_service.mapper;

import com.aryutkina.hotel.hotel_booking_service.dto.request.RoomCreateRequestDto;
import com.aryutkina.hotel.hotel_booking_service.dto.response.RoomResponseDto;
import com.aryutkina.hotel.hotel_booking_service.model.DateRange;
import com.aryutkina.hotel.hotel_booking_service.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDate.class, DateTimeFormatter.class})
public interface RoomMapper {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true) // устанавливается в сервисе
    @Mapping(target = "unavailableDates", source = "unavailableDates", qualifiedByName = "mapDateRangeDtoListToDateRangeList")
    Room toEntity(RoomCreateRequestDto dto);

    @Mapping(target = "hotelId", source = "hotel.id")
    @Mapping(target = "hotelName", source = "hotel.name")
    @Mapping(target = "unavailableDates", source = "unavailableDates", qualifiedByName = "mapDateRangeListToDtoList")
    RoomResponseDto toResponseDto(Room room);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "unavailableDates", source = "unavailableDates", qualifiedByName = "mapDateRangeDtoListToDateRangeList")
    void updateEntityFromDto(RoomCreateRequestDto dto, @MappingTarget Room room);

    @Named("mapDateRangeDtoListToDateRangeList")
    default List<DateRange> mapDateRangeDtoListToDateRangeList(List<RoomCreateRequestDto.DateRangeDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(dto -> new DateRange(
                        LocalDate.parse(dto.getStartDate(), DATE_FORMATTER),
                        LocalDate.parse(dto.getEndDate(), DATE_FORMATTER)))
                .toList();
    }

    @Named("mapDateRangeListToDtoList")
    default List<RoomResponseDto.DateRangeDto> mapDateRangeListToDtoList(List<DateRange> ranges) {
        if (ranges == null) return null;
        return ranges.stream()
                .map(range -> {
                    RoomResponseDto.DateRangeDto dto = new RoomResponseDto.DateRangeDto();
                    dto.setStartDate(range.getStartDate().format(DATE_FORMATTER));
                    dto.setEndDate(range.getEndDate().format(DATE_FORMATTER));
                    return dto;
                })
                .toList();
    }
}