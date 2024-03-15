package dev.backend.tutor.controllers.webSocketControllers;

import dev.backend.tutor.dtos.messages.FriendshipRequestDto;
import dev.backend.tutor.dtos.messages.FriendshipResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;
import dev.backend.tutor.sevices.friendship.FriendshipService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class FriendController{

    private final FriendshipService friendshipService;

    public FriendController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @MessageMapping("/friends.createFriendship")
    public void onCreatingFriendshipRequest(@Payload FriendshipRequestDto friendShipRequestDto) throws FriendshipException, NotFoundUserException {
        friendshipService.requestFriendShip(friendShipRequestDto);
    }

    @MessageMapping("/friends.responseFriendship")
    public void onAcceptingFriendshipRequest(@Payload FriendshipResponseDto friendshipResponseDto) throws FriendshipException, NotFoundUserException {
        friendshipService.responseFriendship(friendshipResponseDto);
    }
}
