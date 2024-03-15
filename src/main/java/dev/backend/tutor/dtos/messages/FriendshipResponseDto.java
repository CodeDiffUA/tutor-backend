package dev.backend.tutor.dtos.messages;

public record FriendshipResponseDto(
        String sender, String recipient, boolean acceptedFriendship
) {
}
