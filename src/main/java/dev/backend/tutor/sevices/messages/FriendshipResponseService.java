package dev.backend.tutor.sevices.messages;


import dev.backend.tutor.dtos.messages.FriendshipRequestDto;
import dev.backend.tutor.dtos.messages.FriendshipResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.frienship.FriendshipException;

public interface FriendshipResponseService {
    void responseFriendship(FriendshipResponseDto friendshipResponseDto) throws NotFoundUserException, FriendshipException;

}
