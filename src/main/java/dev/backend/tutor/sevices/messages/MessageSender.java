package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.dtos.messages.MessageDto;
import dev.backend.tutor.dtos.messages.SystemMessageDto;

/**
 * Service for sending various types of messages to users.
 */
public interface MessageSender {

    /**
     * Send a message to a user.
     *
     * @param recipientLogin The login of the recipient user.
     * @param messageDto     The message DTO containing the message details.
     */
    void sendMessageToUser(String recipientLogin, MessageDto messageDto);

    /**
     * Send a system message to a user.
     *
     * @param recipientLogin The login of the recipient user.
     * @param messageDto     The system message DTO containing the message details.
     */
    void sendSystemMessageToUser(String recipientLogin, SystemMessageDto messageDto);

    /**
     * Send an exception message to a user.
     *
     * @param recipientLogin The login of the recipient user.
     * @param exceptionDto   The exception DTO containing the exception details.
     */
    void sendExceptionToUser(String recipientLogin, ExceptionDto exceptionDto);
}