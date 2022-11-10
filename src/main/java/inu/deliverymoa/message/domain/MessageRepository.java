package inu.deliverymoa.message.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAllByRoomIdAndCreatedAtIsBeforeOrderByCreatedAtDesc(Long roomId, LocalDateTime createdAt, Pageable pageable);
}
