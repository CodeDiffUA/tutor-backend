package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;


@Entity
@Table(name = "syntax_punctuation")
public class SyntaxPunctuation {
    @Id
    @Column(name = "student_ukrmova_grades")
    private String studnetSyntaxPunctuationGrades;
    private Integer phrase;
    private Integer classificationOfSentences;
    private Integer mainSentenceElements;
    private Integer subordinateSentenceElements;
    private Integer simpleSentences;
    private Integer complexSentencesCoordinatingSentenceElements;
    private Integer complexSentencesAddressingInsertedWordsPhrasesSentences;
    private Integer separatedSentenceElementsSeparatedAttribute;
    private Integer separatedSentenceElementsSeparatedAdverbialModifier;
    private Integer separatedSentenceElementsSeparatedObject;
    private Integer complexSentenceCompoundSentence;
    private Integer complexSentenceComplexSubordinateClause;
    private Integer complexSentenceComplexClauseWithoutAConjunction;
    private Integer complexSentenceComplexSentenceWithDifferentTypesOfConnection;
    private Integer foreignSpeech;
    private Integer meansOfInterphraseUnity;

    @OneToOne(mappedBy = "syntaxPunctuationEntity", fetch = FetchType.LAZY)
    private UkrMovaGrades ukrMovaGrades;

    public Integer getPhrase() {
        return phrase;
    }
    public Integer getClassificationOfSentences() {
        return classificationOfSentences;
    }
    public Integer getMainSentenceElements() {
        return mainSentenceElements;
    }
    public Integer getSubordinateSentenceElements() {
        return subordinateSentenceElements;
    }
    public Integer getSimpleSentences() {
        return simpleSentences;
    }
    public Integer getComplexSentencesCoordinatingSentenceElements() {
        return complexSentencesCoordinatingSentenceElements;
    }
    public Integer getComplexSentencesAddressingInsertedWordsPhrasesSentences() {
        return complexSentencesAddressingInsertedWordsPhrasesSentences;
    }
    public Integer getSeparatedSentenceElementsSeparatedAttribute() {
        return separatedSentenceElementsSeparatedAttribute;
    }
    public Integer getSeparatedSentenceElementsSeparatedAdverbialModifier() {
        return separatedSentenceElementsSeparatedAdverbialModifier;
    }
    public Integer getSeparatedSentenceElementsSeparatedObject() {
        return separatedSentenceElementsSeparatedObject;
    }
    public Integer getComplexSentenceCompoundSentence() {
        return complexSentenceCompoundSentence;
    }
    public Integer getComplexSentenceComplexSubordinateClause(){
        return complexSentenceComplexSubordinateClause;
    }
    public Integer getComplexSentenceComplexClauseWithoutAConjunction() {
        return complexSentenceComplexClauseWithoutAConjunction;
    }
    public Integer getComplexSentenceComplexSentenceWithDifferentTypesOfConnection() {
        return complexSentenceComplexSentenceWithDifferentTypesOfConnection;
    }
    public Integer getForeignSpeech() {
        return foreignSpeech;
    }
    public Integer getMeansOfInterphraseUnity() {
        return meansOfInterphraseUnity;
    }
}
