package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "word_formation_word_derivation")
public class WordFormationWordDerivation {
    @Id
    @Column(name = "student_ukrmova_grades")
    private String studentWordFormationWordDerivationGrades;
    private Integer wordStructureWordFormation;

    @OneToOne(mappedBy = "wordFormationWordDerivationEntity", fetch = FetchType.LAZY)
    private UkrMovaGrades ukrMovaGrades;

    public Integer getWordStructureWordFormation() {
        return wordStructureWordFormation;
    }
}
