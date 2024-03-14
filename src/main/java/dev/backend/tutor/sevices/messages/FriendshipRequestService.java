package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.messages.FriendshipRequestDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.frienship.FriendshipException;

/**
 * Interface for sending messages related to friendships and general messaging.
 */
public interface FriendshipRequestService {

    /**
     * Accept a friendship request.
     *
     * @param friendshipRequestDto The DTO containing information about the friendship request.
     */

    void requestFriendShip(FriendshipRequestDto friendshipRequestDto) throws NotFoundUserException, FriendshipException;
}
