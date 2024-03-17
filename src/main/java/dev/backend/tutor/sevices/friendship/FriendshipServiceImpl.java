package dev.backend.tutor.sevices.friendship;

import dev.backend.tutor.dtos.friendship.RequestFriendshipRequestDto;
import dev.backend.tutor.dtos.friendship.RequestFriendshipResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;
import dev.backend.tutor.sevices.friendship.request.FriendshipRequestService;
import dev.backend.tutor.sevices.friendship.request.FriendshipRequestServiceImpl;
import dev.backend.tutor.sevices.friendship.response.FriendshipResponseService;
import dev.backend.tutor.sevices.friendship.response.FriendshipResponseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FriendshipServiceImpl implements FriendshipService{

    private final FriendshipRequestService friendshipRequestService;
    private final FriendshipResponseService friendshipResponseService;

    public FriendshipServiceImpl(FriendshipRequestServiceImpl friendshipRequestService, FriendshipResponseServiceImpl friendshipResponseService) {
        this.friendshipRequestService = friendshipRequestService;
        this.friendshipResponseService = friendshipResponseService;
    }

    @Override
    public void requestFriendShip(RequestFriendshipRequestDto friendShipRequestDto)
            throws NotFoundUserException, FriendshipException {
        friendshipRequestService.requestFriendShip(friendShipRequestDto);
    }

    @Override
    public void responseFriendship(RequestFriendshipResponseDto friendshipResponseDto) throws NotFoundUserException, FriendshipException {
        friendshipResponseService.responseFriendship(friendshipResponseDto);

    }
}
