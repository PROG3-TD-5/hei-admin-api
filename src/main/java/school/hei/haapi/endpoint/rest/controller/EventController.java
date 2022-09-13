package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.hei.haapi.endpoint.rest.mapper.EventMapper;
import school.hei.haapi.model.Event;
import school.hei.haapi.service.EventService;

import java.util.List;

@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/event")
public class EventController {

  private final EventService eventService;
  private final EventMapper eventMapper;

  @GetMapping("/{id}")
  public Event findEventById (
          @PathVariable String id) {
    return eventService.getEventById(id);
  }
  @GetMapping
  public List<Event> getAllEvent(
          @RequestParam int page,
          @RequestParam int pageSize
  ){
    return eventService.getAll(page, pageSize);
  }
  @PutMapping
  public List<Event> saveAllEvent(
          @RequestBody List<Event> eventList
  ){
    return eventService.updateEvent(eventList);
  }
}
