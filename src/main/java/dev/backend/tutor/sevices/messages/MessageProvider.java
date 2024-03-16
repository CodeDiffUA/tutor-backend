package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.dtos.messages.MessageDto;
import dev.backend.tutor.dtos.messages.SystemMessageDto;
import dev.backend.tutor.utills.student.DateUtil;

public class MessageProvider {

    public static final String FRIENDSHIP_ACCEPTED_TEMPLATE = "%s, your friendship request has been accepted";
    public static final String FRIENDSHIP_DECLINED_TEMPLATE = "%s, your friendship request has been declined";
    public static final String FRIENDSHIP_REQUEST_TEMPLATE = "%s, user %s wants to become your friend";

    public static SystemMessageDto messageDtoForAcceptingFriendshipRequest(String recipientLogin) {
        String content = String.format(FRIENDSHIP_ACCEPTED_TEMPLATE, recipientLogin);
        return createSystemMessageDto(recipientLogin, content);
    }

    public static SystemMessageDto messageDtoForDecliningFriendshipRequest(String recipientLogin) {
        String content = String.format(FRIENDSHIP_DECLINED_TEMPLATE, recipientLogin);
        return createSystemMessageDto(recipientLogin, content);
    }

    public static MessageDto messageDtoForFriendshipRequest(String senderLogin, String recipientLogin) {
        String content = String.format(FRIENDSHIP_REQUEST_TEMPLATE, recipientLogin, senderLogin);
        return createMessageDto(senderLogin, recipientLogin, content);
    }

    public static ExceptionDto exceptionDtoForFriendshipRequest(String exceptionMessage) {
        return new ExceptionDto(exceptionMessage, generateTimestamp());
    }

    private static SystemMessageDto createSystemMessageDto(String recipientLogin, String content) {
        return new SystemMessageDto(recipientLogin, content, generateTimestamp());
    }

    private static MessageDto createMessageDto(String senderLogin, String recipientLogin, String content) {
        return new MessageDto(senderLogin, recipientLogin, content, generateTimestamp());
    }

    private static String generateTimestamp() {
        return DateUtil.currentTimeStamp();
    }
}

