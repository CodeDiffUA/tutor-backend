package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "morphology")
public class Morphology {
    @Id
    private String username;
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
    @OneToOne(mappedBy = "morphology")
    private UkrMovaGrades ukrMovaGrades;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getClassificationOfPartsOfSpeech() {
        return classificationOfPartsOfSpeech;
    }
    public void setClassificationOfPartsOfSpeech(Integer classificationOfPartsOfSpeech) {
        this.classificationOfPartsOfSpeech = classificationOfPartsOfSpeech;
    }
    public Integer getNounGenderAndNumberOfNouns() {
        return nounGenderAndNumberOfNouns;
    }
    public void setNounGenderAndNumberOfNouns(Integer nounGenderAndNumberOfNouns) {
        this.nounGenderAndNumberOfNouns = nounGenderAndNumberOfNouns;
    }
    public Integer getNounDeclensionOfNouns() {
        return nounDeclensionOfNouns;
    }
    public void setNounDeclensionOfNouns(Integer nounDeclensionOfNouns) {
        this.nounDeclensionOfNouns = nounDeclensionOfNouns;
    }
    public Integer getNounWritingPatronymics() {
        return nounWritingPatronymics;
    }
    public void setNounWritingPatronymics(Integer nounWritingPatronymics) {
        this.nounWritingPatronymics = nounWritingPatronymics;
    }
    public Integer getAdjective() {
        return adjective;
    }
    public void setAdjective(Integer adjective) {
        this.adjective = adjective;
    }
    public Integer getNumeral() {
        return numeral;
    }
    public void setNumeral(Integer numeral) {
        this.numeral = numeral;
    }
    public Integer getPronoun() {
        return pronoun;
    }
    public void setPronoun(Integer pronoun) {
        this.pronoun = pronoun;
    }
    public Integer getVerb() {
        return verb;
    }
    public void setVerb(Integer verb) {
        this.verb = verb;
    }
    public Integer getParticiple() {
        return participle;
    }
    public void setParticiple(Integer participle) {
        this.participle = participle;
    }
    public Integer getAdverb() {
        return adverb;
    }
    public void setAdverb(Integer adverb) {
        this.adverb = adverb;
    }
    public Integer getPreposition() {
        return preposition;
    }
    public void setPreposition(Integer preposition) {
        this.preposition = preposition;
    }
    public Integer getConjunction() {
        return conjunction;
    }
    public void setConjunction(Integer conjunction) {
        this.conjunction = conjunction;
    }
    public Integer getParticle() {
        return particle;
    }
    public void setParticle(Integer particle) {
        this.particle = particle;
    }
}
