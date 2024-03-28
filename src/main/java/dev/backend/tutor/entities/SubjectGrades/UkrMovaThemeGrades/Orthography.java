package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "orthography")
public class Orthography {
    @Id
    @Column(name = "student_ukrmova_grades")
    private String studentOrthographyGrades;
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
    @OneToOne(mappedBy = "orthographyEntity", fetch = FetchType.LAZY)
    private UkrMovaGrades ukrMovaGrades;


    public Integer getSpellingOfUnstressedVowelsEUO() {
        return spellingOfUnstressedVowelsEUO;
    }
    public Integer getPrefixSpelling(){
        return prefixSpelling;
    }
    public Integer getConsonantClusterSimplification() {
        return consonantClusterSimplification;
    }
    public Integer getConsonantChangesInWordFormation() {
        return consonantChangesInWordFormation;
    }
    public Integer getApostrophe() {
        return apostrophe;
    }
    public Integer getSoftSign() {
        return softSign;
    }
    public Integer getCombinationOfYoYot() {
        return combinationOfYoYot;
    }
    public Integer getLetterDoubling(){
        return letterDoubling;
    }
    public Integer getSpellingOfWordsOfForeignOrigin(){
        return spellingOfWordsOfForeignOrigin;
    }
    public Integer getCapitalizationAndQuotationMarksInProperNouns() {
        return capitalizationAndQuotationMarksInProperNouns;
    }
    public Integer getCompoundWords() {
        return compoundWords;
    }
    public Integer getSpellingWithDifferentPartsOfSpeech() {
        return spellingWithDifferentPartsOfSpeech;
    }
    public Integer getBasicCasesOfAlternationYVIU() {
        return basicCasesOfAlternationYVIU;
    }
}
