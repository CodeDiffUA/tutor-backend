package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.FriendShipRequestDto;
import dev.backend.tutor.exceptions.AlreadyFriendsException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService implements FriendshipRequestService{

    private final FriendshipRequestService friendshipRequestService;

    public FriendshipService(FriendshipRequestServiceImpl friendshipRequestService) {
        this.friendshipRequestService = friendshipRequestService;
    }

    @Override
    public void requestFriendShip(FriendShipRequestDto friendShipRequestDto)
            throws NotFoundUserException, AlreadyFriendsException {
        friendshipRequestService.requestFriendShip(friendShipRequestDto);
    }
}
