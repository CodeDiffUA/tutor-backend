package dev.backend.tutor.sevices.friendship.response;


import dev.backend.tutor.dtos.messages.FriendshipResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;

public interface FriendshipResponseService {
    void responseFriendship(FriendshipResponseDto friendshipResponseDto) throws NotFoundUserException, FriendshipException;

}
