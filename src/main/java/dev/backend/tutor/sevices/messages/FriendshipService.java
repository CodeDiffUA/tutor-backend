package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.FriendShipRequestDto;
import dev.backend.tutor.exceptions.frienship.AlreadyFriendsException;
import dev.backend.tutor.exceptions.frienship.BlockedUsersException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.frienship.FriendshipException;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService implements FriendshipRequestService{

    private final FriendshipRequestService friendshipRequestService;

    public FriendshipService(FriendshipRequestServiceImpl friendshipRequestService) {
        this.friendshipRequestService = friendshipRequestService;
    }

    @Override
    public void requestFriendShip(FriendShipRequestDto friendShipRequestDto)
            throws NotFoundUserException, FriendshipException {
        friendshipRequestService.requestFriendShip(friendShipRequestDto);
    }
}
