package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "orthography")
public class Orthography {
    @Id
    private String username;
    private Integer spellingOfUnstressedVowelsEUO;
    private Integer prefixSpelling;
    private Integer consonantClusterSimplification;
    private Integer consonantChangesInWordFormation;
    private Integer apostrophe;
    private Integer softSign;
    private Integer combinationOfYoYot;
    private Integer letterDoubling;
    private Integer spellingOfWordsOfForeignOrigin;
    private Integer capitalizationAndQuotationMarksInProperNouns;
    private Integer compoundWords;
    private Integer spellingWithDifferentPartsOfSpeech;
    private Integer basicCasesOfAlternationYVIU;
    @OneToOne(mappedBy = "orthography")
    private UkrMovaGrades ukrMovaGrades;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getSpellingOfUnstressedVowelsEUO() {
        return spellingOfUnstressedVowelsEUO;
    }
    public void setSpellingOfUnstressedVowelsEUO(Integer spellingOfUnstressedVowelsEUO) {
        this.spellingOfUnstressedVowelsEUO = spellingOfUnstressedVowelsEUO;
    }
    public Integer getPrefixSpelling(){
        return prefixSpelling;
    }
    public void setPrefixSpelling(Integer prefixSpelling) {
        this.prefixSpelling = prefixSpelling;
    }
    public Integer getConsonantClusterSimplification() {
        return consonantClusterSimplification;
    }
    public void setConsonantClusterSimplification(Integer consonantClusterSimplification){
        this.consonantClusterSimplification = consonantClusterSimplification;
    }
    public Integer getConsonantChangesInWordFormation() {
        return consonantChangesInWordFormation;
    }
    public void setConsonantChangesInWordFormation(Integer consonantChangesInWordFormation) {
        this.consonantChangesInWordFormation = consonantChangesInWordFormation;
    }
    public Integer getApostrophe() {
        return apostrophe;
    }
    public void setApostrophe(Integer apostrophe){
        this.apostrophe = apostrophe;
    }
    public Integer getSoftSign() {
        return softSign;
    }
    public void setSoftSign(Integer softSign){
        this.softSign = softSign;
    }
    public Integer getCombinationOfYoYot() {
        return combinationOfYoYot;
    }
    public void setCombinationOfYoYot(Integer combinationOfYoYot) {
        this.combinationOfYoYot = combinationOfYoYot;
    }
    public Integer getLetterDoubling(){
        return letterDoubling;
    }
    public void setLetterDoubling(Integer letterDoubling) {
        this.letterDoubling = letterDoubling;
    }
    public Integer getSpellingOfWordsOfForeignOrigin(){
        return spellingOfWordsOfForeignOrigin;
    }
    public void setSpellingOfWordsOfForeignOrigin(Integer spellingOfWordsOfForeignOrigin) {
        this.spellingOfWordsOfForeignOrigin = spellingOfWordsOfForeignOrigin;
    }
    public Integer getCapitalizationAndQuotationMarksInProperNouns() {
        return capitalizationAndQuotationMarksInProperNouns;
    }
    public void setCapitalizationAndQuotationMarksInProperNouns(Integer capitalizationAndQuotationMarksInProperNouns) {
        this.capitalizationAndQuotationMarksInProperNouns = capitalizationAndQuotationMarksInProperNouns;
    }
    public Integer getCompoundWords() {
        return compoundWords;
    }
    public void setCompoundWords(Integer compoundWords) {
        this.compoundWords = compoundWords;
    }
    public Integer getSpellingWithDifferentPartsOfSpeech() {
        return spellingWithDifferentPartsOfSpeech;
    }
    public void setSpellingWithDifferentPartsOfSpeech(Integer spellingWithDifferentPartsOfSpeech) {
        this.spellingWithDifferentPartsOfSpeech = spellingWithDifferentPartsOfSpeech;
    }
    public Integer getBasicCasesOfAlternationYVIU() {
        return basicCasesOfAlternationYVIU;
    }
    public void setBasicCasesOfAlternationYVIU(Integer basicCasesOfAlternationYVIU) {
        this.basicCasesOfAlternationYVIU = basicCasesOfAlternationYVIU;
    }
}
