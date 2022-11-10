package inu.deliverymoa.chat.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inu.deliverymoa.common.domain.YN;
import inu.deliverymoa.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static inu.deliverymoa.chat.domain.QChatRoom.chatRoom;
import static inu.deliverymoa.chat.domain.QChatRoomUser.*;

@Repository
@RequiredArgsConstructor
public class ChatRoomQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ChatRoom> searchChatRoom(Long categoryId) {
        return queryFactory
                .selectFrom(chatRoom)
                .where(
                        categoryEq(categoryId),
                        chatRoom.delYn.eq(YN.N)
                )
                .orderBy(chatRoom.id.desc())
                .fetch();
    }

    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId != null ? chatRoom.category.id.eq(categoryId) : null;
    }

    public List<ChatRoom> findByUser(User user) {
        return queryFactory
                .selectFrom(chatRoom).distinct()
                .join(chatRoom.chatRoomUsers, chatRoomUser).fetchJoin()
                .where(
                        chatRoomUser.user.eq(user),
                        chatRoom.delYn.eq(YN.N)
                )
                .orderBy(chatRoom.id.desc())
                .fetch();
    }
}
