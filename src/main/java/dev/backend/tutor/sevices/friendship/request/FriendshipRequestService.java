package dev.backend.tutor.sevices.friendship.request;

import dev.backend.tutor.dtos.friendship.RequestFriendshipRequestDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.friendship.FriendshipException;

/**
 * Interface for sending messages related to friendships and general messaging.
 */
public interface FriendshipRequestService {

    /**
     * Accept a friendship request.
     *
     * @param friendshipRequestDto The DTO containing information about the friendship request.
     */

    void requestFriendShip(RequestFriendshipRequestDto friendshipRequestDto) throws NotFoundUserException, FriendshipException;
}
