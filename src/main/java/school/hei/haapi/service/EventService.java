package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.Event;
import school.hei.haapi.repository.EventRepository;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@AllArgsConstructor
public class EventService {
  private final EventRepository eventRepository;

  public List<Event> getAll(int page , int pageSize){
    Pageable pageable = PageRequest.of(page,pageSize, Sort.by(ASC , "startTime"));
    return eventRepository.findAll(pageable).toList();
  }
}
