package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "phonetics_graphemics_orthoepy")
public class PhoneticsGraphemicsOrthoepy {
    @Id
    @Column(name = "student_ukrmova_grades")
    private String studentPhoneticsGraphemicsOrthoepyGrades;
    private Integer syllableStress;
    private Integer soundLetterCorrespondence;

    @OneToOne(mappedBy = "phoneticsGraphemicsOrthoepyEntity", fetch = FetchType.LAZY)
    private UkrMovaGrades ukrMovaGrades;

    public Integer getSyllableStress() {
        return syllableStress;
    }
    public Integer getSoundLetterCorrespondence() {
        return soundLetterCorrespondence;
    }
}
