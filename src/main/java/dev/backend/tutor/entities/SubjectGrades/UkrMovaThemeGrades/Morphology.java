package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "morphology")
public class Morphology {
    @Id
    @Column(name = "student_ukrmova_grades")
    private String studentMorphologyGrades;
    private Integer classificationOfPartsOfSpeech;
    private Integer nounGenderAndNumberOfNouns;
    private Integer nounDeclensionOfNouns;
    private Integer nounWritingPatronymics;
    private Integer adjective;
    private Integer numeral;
    private Integer pronoun;
    private Integer verb;
    private Integer participle;
    private Integer adverb;
    private Integer preposition;
    private Integer conjunction;
    private Integer particle;
    @OneToOne(mappedBy = "morphologyEntity", fetch = FetchType.LAZY)
    private UkrMovaGrades ukrMovaGrades;

    public Integer getClassificationOfPartsOfSpeech() {
        return classificationOfPartsOfSpeech;
    }
    public Integer getNounGenderAndNumberOfNouns() {
        return nounGenderAndNumberOfNouns;
    }
    public Integer getNounDeclensionOfNouns() {
        return nounDeclensionOfNouns;
    }
    public Integer getNounWritingPatronymics() {
        return nounWritingPatronymics;
    }
    public Integer getAdjective() {
        return adjective;
    }
    public Integer getNumeral() {
        return numeral;
    }
    public Integer getPronoun() {
        return pronoun;
    }
    public Integer getVerb() {
        return verb;
    }
    public Integer getParticiple() {
        return participle;
    }
    public Integer getAdverb() {
        return adverb;
    }
    public Integer getPreposition() {
        return preposition;
    }
    public Integer getConjunction() {
        return conjunction;
    }
    public Integer getParticle() {
        return particle;
    }
}
