package dev.backend.tutor.repositories.sql.ai;

import dev.backend.tutor.dtos.ai.AiChatResponseDto;
import dev.backend.tutor.entities.ai.AiChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AiChatRepository extends JpaRepository<AiChat, Long> {

    @Query("select new dev.backend.tutor.dtos.ai.AiChatResponseDto(c.id, :username, c.name) from AiChat c left join c.student s where s.username = :username")
    List<AiChatResponseDto> findAiChatByStudent(@Param("username") String username);

    AiChat findAiChatById(Long id);
}
