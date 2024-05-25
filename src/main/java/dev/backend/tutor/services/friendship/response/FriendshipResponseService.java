package dev.backend.tutor.services.friendship.response;


import dev.backend.tutor.dtos.friendship.RequestFriendshipResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;

public interface FriendshipResponseService {
    void responseFriendship(RequestFriendshipResponseDto friendshipResponseDto) throws NotFoundUserException, FriendshipException;

}
