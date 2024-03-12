package dev.backend.tutor.controllers.webSocketControllers;

import dev.backend.tutor.dtos.FriendShipRequestDto;
import dev.backend.tutor.exceptions.AlreadyFriendsException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.messages.FriendshipManager;
import dev.backend.tutor.sevices.messages.FriendshipService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class FriendController{

    private final FriendshipManager friendshipManager;

    public FriendController(FriendshipService friendshipManager) {
        this.friendshipManager = friendshipManager;
    }

    @MessageMapping("/friends.createFriendship")
    public void onCreatingFriendshipRequest(@Payload FriendShipRequestDto friendShipRequestDto) throws AlreadyFriendsException, NotFoundUserException {
        friendshipManager.requestFriendShip(friendShipRequestDto);
    }
}
