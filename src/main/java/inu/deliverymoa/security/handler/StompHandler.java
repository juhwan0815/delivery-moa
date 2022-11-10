package inu.deliverymoa.security.handler;

import inu.deliverymoa.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            if (!jwtUtil.isValidToken(extractToken(accessor))) {
                throw new AccessDeniedException("연결 거부");
            }
        }

        // 채팅방 구독
        else if(StompCommand.SUBSCRIBE == accessor.getCommand()){
            System.out.println("StompHandler.preSend");

            String sessionId = accessor.getNativeHeader("roomId").get(0);
            System.out.println("###subscribe: " + sessionId);
        }

        // 웹소켓 연결 종료
        else if(StompCommand.DISCONNECT == accessor.getCommand()){
            String sessionId = (String) message.getHeaders().get("roomId");
            System.out.println("###disconnect: " + sessionId);
        }
        return message;
    }

    private String extractToken(StompHeaderAccessor accessor) {
        String bearerToken = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf("/");
        return destination.substring(lastIndex + 1);
    }
}
