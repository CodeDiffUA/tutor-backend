package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "word_formation_word_derivation")
public class WordFormationWordDerivation {
    @Id
    private String username;
    private Integer wordStructureWordFormation;

    @OneToOne(mappedBy = "word_formation_word_derivation")
    private UkrMovaGrades ukrMovaGrades;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getWordStructureWordFormation() {
        return wordStructureWordFormation;
    }
    public void setWordStructureWordFormation(Integer wordStructureWordFormation) {
        this.wordStructureWordFormation = wordStructureWordFormation;
    }
}
