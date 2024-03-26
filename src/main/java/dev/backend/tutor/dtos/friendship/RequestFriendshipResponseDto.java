package dev.backend.tutor.dtos.friendship;

public record RequestFriendshipResponseDto(
        String sender, String recipient, boolean acceptedFriendship
) {
}
