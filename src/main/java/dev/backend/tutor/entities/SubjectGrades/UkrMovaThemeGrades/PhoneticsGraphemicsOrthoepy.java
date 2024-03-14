package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "phonetics_graphemics_orthoepy")
public class PhoneticsGraphemicsOrthoepy {
    @Id
    private String username;
    private Integer syllableStress;
    private Integer soundLetterCorrespondence;

    @OneToOne(mappedBy = "phoneticsGraphemicsOrthoepy")
    private UkrMovaGrades ukrMovaGrades;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getSyllableStress() {
        return syllableStress;
    }
    public void setSyllableStress(Integer syllableStress) {
        this.syllableStress = syllableStress;
    }
    public Integer getSoundLetterCorrespondence() {
        return soundLetterCorrespondence;
    }
    public void setSoundLetterCorrespondence(Integer soundLetterCorrespondence) {
        this.soundLetterCorrespondence = soundLetterCorrespondence;
    }
}
