package dev.backend.tutor.utills.student;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.messegeEntities.Notification;

public class NotificationFactory {

    public static final String FRIENDSHIP_ACCEPTED_TEMPLATE = "%s, your friendship request has been accepted";
    public static final String FRIENDSHIP_DECLINED_TEMPLATE = "%s, your friendship request has been declined";
    public static final String FRIENDSHIP_REQUEST_TEMPLATE = "%s, user %s wants to become your friend";

    public static Notification acceptRequestNotification(Student recipient) {
        var recipientUsername = recipient.getUsername();
        var content = String.format(FRIENDSHIP_ACCEPTED_TEMPLATE, recipientUsername);
        return getNotification(recipient, content);
    }

    public static Notification declineRequestNotification(Student recipient) {
        var recipientUsername = recipient.getUsername();
        var content = String.format(FRIENDSHIP_DECLINED_TEMPLATE, recipientUsername);
        return getNotification(recipient, content);
    }

    public static Notification requestNotification(Student sender, Student recipient) {
        var senderUsername = sender.getUsername();
        var recipientUsername = recipient.getUsername();
        var content = String.format(FRIENDSHIP_REQUEST_TEMPLATE, recipientUsername, senderUsername);
        return getNotification(recipient, content);
    }

    private static Notification getNotification(Student recipient, String content) {
        return Notification.builder()
                .withContent(content)
                .withRecipient(recipient)
                .build();
    }}
