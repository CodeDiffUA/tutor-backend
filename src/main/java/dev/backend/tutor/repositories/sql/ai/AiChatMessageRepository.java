package dev.backend.tutor.repositories.sql.ai;

import dev.backend.tutor.dtos.ai.AiChatMessageDto;
import dev.backend.tutor.dtos.ai.AiChatResponseDto;
import dev.backend.tutor.entities.ai.AiChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AiChatMessageRepository extends JpaRepository<AiChatMessage, Long> {


    @Query("select new dev.backend.tutor.dtos.ai.AiChatMessageDto(m.id, c.name, s.username, m.role, m.content) from AiChatMessage m " +
            "left join m.chat c " +
            "left join c.student s " +
            "where c.name=:chat and s.username=:student " +
            "order by m.id asc")
    Page<AiChatMessageDto> getAiChatMessageByStudentByChat(@Param("student") String student, @Param("chat") String chat, Pageable pageable);

    @Query("select new dev.backend.tutor.dtos.ai.AiChatMessageDto(m.id, c.name, s.username, m.role, m.content) from AiChatMessage m " +
            "left join m.chat c " +
            "left join c.student s " +
            "where c.id=:chatId " +
            "order by m.id asc")
    Page<AiChatMessageDto> getAiChatMessagesByChatId(@Param("chatId") int chatId, Pageable pageable);

}
