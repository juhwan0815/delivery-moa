package inu.deliverymoa.chat.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select distinct r from ChatRoom r join fetch r.chatRoomUsers where r.id =:roomId")
    Optional<ChatRoom> findWithChatRoomUsersById(@Param("roomId") Long roomId);
}
