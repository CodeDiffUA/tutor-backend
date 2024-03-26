package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.friendship.RequestFriendshipRequestDto;
import dev.backend.tutor.dtos.friendship.RequestFriendshipResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;
import dev.backend.tutor.sevices.friendship.FriendshipService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("31.144.169.9")
@RequestMapping("/api/v1/friendship")
public class FriendController{

    private final FriendshipService friendshipService;

    public FriendController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping("/request")
    @RolesAllowed("STUDENT")
    public ResponseEntity<String> onCreatingFriendshipRequest(@RequestBody RequestFriendshipRequestDto friendShipRequestDto)
            throws FriendshipException, NotFoundUserException {
        friendshipService.requestFriendShip(friendShipRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/response")
    @RolesAllowed("STUDENT")
    public ResponseEntity<String> responseRequest(@RequestBody RequestFriendshipResponseDto friendshipResponseDto)
            throws FriendshipException, NotFoundUserException {
        friendshipService.responseFriendship(friendshipResponseDto);
        return ResponseEntity.ok().build();
    }
}
