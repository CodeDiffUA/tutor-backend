package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;


@Entity
@Table(name = "syntax_punctuation")
public class SyntaxPunctuation {
    @Id
    private String username;
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

    @OneToOne(mappedBy = "lexicology")
    private UkrMovaGrades ukrMovaGrades;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getPhrase() {
        return phrase;
    }
    public void setPhrase(Integer phrase) {
        this.phrase = phrase;
    }
    public Integer getClassificationOfSentences() {
        return classificationOfSentences;
    }
    public void setClassificationOfSentences(Integer classificationOfSentences) {
        this.classificationOfSentences = classificationOfSentences;
    }
    public Integer getMainSentenceElements() {
        return mainSentenceElements;
    }
    public void setMainSentenceElements(Integer mainSentenceElements) {
        this.mainSentenceElements = mainSentenceElements;
    }
    public Integer getSubordinateSentenceElements() {
        return subordinateSentenceElements;
    }
    public void setSubordinateSentenceElements(Integer subordinateSentenceElements) {
        this.subordinateSentenceElements = subordinateSentenceElements;
    }
    public Integer getSimpleSentences() {
        return simpleSentences;
    }
    public void setSimpleSentences(Integer simpleSentences) {
        this.simpleSentences = simpleSentences;
    }
    public Integer getComplexSentencesCoordinatingSentenceElements() {
        return complexSentencesCoordinatingSentenceElements;
    }
    public void setComplexSentencesCoordinatingSentenceElements(Integer complexSentencesCoordinatingSentenceElements) {
        this.complexSentencesCoordinatingSentenceElements = complexSentencesCoordinatingSentenceElements;
    }
    public Integer getComplexSentencesAddressingInsertedWordsPhrasesSentences() {
        return complexSentencesAddressingInsertedWordsPhrasesSentences;
    }
    public void setComplexSentencesAddressingInsertedWordsPhrasesSentences(Integer complexSentencesAddressingInsertedWordsPhrasesSentences) {
        this.complexSentencesAddressingInsertedWordsPhrasesSentences = complexSentencesAddressingInsertedWordsPhrasesSentences;
    }
    public Integer getSeparatedSentenceElementsSeparatedAttribute() {
        return separatedSentenceElementsSeparatedAttribute;
    }
    public void setSeparatedSentenceElementsSeparatedAttribute(Integer separatedSentenceElementsSeparatedAttribute) {
        this.separatedSentenceElementsSeparatedAttribute = separatedSentenceElementsSeparatedAttribute;
    }
    public Integer getSeparatedSentenceElementsSeparatedAdverbialModifier() {
        return separatedSentenceElementsSeparatedAdverbialModifier;
    }
    public void setSeparatedSentenceElementsSeparatedAdverbialModifier(Integer separatedSentenceElementsSeparatedAdverbialModifier) {
        this.separatedSentenceElementsSeparatedAdverbialModifier = separatedSentenceElementsSeparatedAdverbialModifier;
    }
    public Integer getSeparatedSentenceElementsSeparatedObject() {
        return separatedSentenceElementsSeparatedObject;
    }
    public void setSeparatedSentenceElementsSeparatedObject(Integer separatedSentenceElementsSeparatedObject) {
        this.separatedSentenceElementsSeparatedObject = separatedSentenceElementsSeparatedObject;
    }
    public Integer getComplexSentenceCompoundSentence() {
        return complexSentenceCompoundSentence;
    }
    public void setComplexSentenceCompoundSentence(Integer complexSentenceCompoundSentence) {
        this.complexSentenceCompoundSentence = complexSentenceCompoundSentence;
    }
    public Integer getComplexSentenceComplexSubordinateClause(){
        return complexSentenceComplexSubordinateClause;
    }
    public void setComplexSentenceComplexSubordinateClause(Integer complexSentenceComplexSubordinateClause) {
        this.complexSentenceComplexSubordinateClause = complexSentenceComplexSubordinateClause;
    }
    public Integer getComplexSentenceComplexClauseWithoutAConjunction() {
        return complexSentenceComplexClauseWithoutAConjunction;
    }
    public void setComplexSentenceComplexClauseWithoutAConjunction(Integer complexSentenceComplexClauseWithoutAConjunction) {
        this.complexSentenceComplexClauseWithoutAConjunction = complexSentenceComplexClauseWithoutAConjunction;
    }
    public Integer getComplexSentenceComplexSentenceWithDifferentTypesOfConnection() {
        return complexSentenceComplexSentenceWithDifferentTypesOfConnection;
    }
    public void setComplexSentenceComplexSentenceWithDifferentTypesOfConnection(Integer complexSentenceComplexSentenceWithDifferentTypesOfConnection) {
        this.complexSentenceComplexSentenceWithDifferentTypesOfConnection = complexSentenceComplexSentenceWithDifferentTypesOfConnection;
    }
    public Integer getForeignSpeech() {
        return foreignSpeech;
    }
    public void setForeignSpeech(Integer foreignSpeech) {
        this.foreignSpeech = foreignSpeech;
    }
    public Integer getMeansOfInterphraseUnity() {
        return meansOfInterphraseUnity;
    }
    public void setMeansOfInterphraseUnity(Integer meansOfInterphraseUnity) {
        this.meansOfInterphraseUnity = meansOfInterphraseUnity;
    }
}
