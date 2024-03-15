package dev.backend.tutor.exceptions.friendship;

public class BlockedUsersException extends FriendshipException{
    public BlockedUsersException(String message) {
        super(message);
    }
}
