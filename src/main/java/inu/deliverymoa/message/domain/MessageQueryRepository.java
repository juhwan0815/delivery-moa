package inu.deliverymoa.message.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.deliverymoa.message.dto.MessageSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static inu.deliverymoa.message.domain.QMessage.message;

@Repository
@RequiredArgsConstructor
public class MessageQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Message> findByCondition(Long roomId, MessageSearchRequest request) {
        return queryFactory
                .selectFrom(message)
                .where(
                        message.roomId.eq(roomId),
                        message.createdAt.before(LocalDateTime.parse(request.getLastMessageDate()))
                )
                .orderBy(message.id.desc())
                .limit(request.getSize())
                .fetch();
    }
}
