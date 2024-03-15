package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.messages.FriendshipRequestDto;
import dev.backend.tutor.dtos.messages.FriendshipResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.frienship.FriendshipException;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService implements FriendshipManager{

    private final FriendshipRequestService friendshipRequestService;
    private final FriendshipResponseService friendshipResponseService;

    public FriendshipService(FriendshipRequestServiceImpl friendshipRequestService, FriendshipResponseServiceImpl friendshipResponseService) {
        this.friendshipRequestService = friendshipRequestService;
        this.friendshipResponseService = friendshipResponseService;
    }

    @Override
    public void requestFriendShip(FriendshipRequestDto friendShipRequestDto)
            throws NotFoundUserException, FriendshipException {
        friendshipRequestService.requestFriendShip(friendShipRequestDto);
    }

    @Override
    public void responseFriendship(FriendshipResponseDto friendshipResponseDto) throws NotFoundUserException, FriendshipException {
        friendshipResponseService.responseFriendship(friendshipResponseDto);

    }
}
