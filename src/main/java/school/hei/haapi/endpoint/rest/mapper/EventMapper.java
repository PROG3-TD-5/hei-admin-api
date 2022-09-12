package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.model.Event;
import school.hei.haapi.model.Place;
import school.hei.haapi.service.EventParticipantService;
import school.hei.haapi.service.PlaceService;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EventMapper {
  private final PlaceService placeService;
  public Event toRest(school.hei.haapi.endpoint.rest.model.Event toCreate){
    Place place = placeService.getPlaceById(toCreate.getPlace().getId());
    return Event.builder()
            .id(toCreate.getId())
            .endTime(toCreate.getEndTime())
            .startTime(toCreate.getStartTime())
            .eventType(toCreate.getEventType())
            .place(place)
            .build();
  }

  public List<Event> toDomainList(List<school.hei.haapi.endpoint.rest.model.Event> event){
    return event.stream().map(this::toRest).collect(Collectors.toUnmodifiableList());
  }

  public Event toDomain(school.hei.haapi.endpoint.rest.model.Event event){
    Place place = placeService.getPlaceById(event.getPlace().getId());
    return Event.builder()
            .id(event.getId())
            .place(place)
            .eventType(event.getEventType())
            .startTime(event.getStartTime())
            .endTime(event.getEndTime())
            .build();
  }
}
