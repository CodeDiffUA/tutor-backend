package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "stylistics_text_language_development")
public class StylisticsTextLanguageDevelopment {
    @Id
    @Column(name = "student_ukrmova_grades")
    private String StudentstylisticsTextLanguageDevelopmentGrades;
    private Integer analysisOfTheTextBlueAppleForIlonka;
    private Integer analysisOfTheTextRulesOfGoodTroll;
    private Integer analysisOfTheTextBlackLettersOnAWhiteBackground;
    private Integer analysisOfTheTextCityWhereYouWouldLikeToLive;
    private Integer analysisOfTheTextMuralsOfKyiv;
    private Integer analysisOfTheTextIHadTheHappiestLife;
    private Integer analysisOfTheTextVeteranOfTheSky;
    private Integer analysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary;
    private Integer analysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality;
    private Integer analysisOfTheTextChanceToRealizeYourself;
    private Integer analysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians;
    private Integer analysisOfTheTextWarmHumanityWithYourHeart;
    private Integer analysisOfTheTextMusicalGiftToTheWorld;
    private Integer analysisOfTheTextSmallPathNotForPeople;
    private Integer analysisOfTheTextReadMe;
    private Integer analysisOfTheTextFolkMedicine;
    private Integer analysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine;
    private Integer analysisOfTheTextSteppeEldorado;
    private Integer analysisOfTheTextPaintedStoriesUkrainianVersion;
    private Integer analysisOfTheTextDetectiveAsAnIntellectualExercise;
    private Integer analysisOfTheTextPhilosophicalToolOfDanyloDemutsky;
    private Integer analysisOfTheTextMagicBox;
    private Integer analysisOfTheTextOlenaTeliha;
    private Integer analysisOfTheTextAntoineDeSaintExupery;
    private Integer analysisOfTheTextTheThirdRoostersHaveAlreadySung;
    private Integer analysisOfTheTextOurAmulets;
    private Integer analysisOfTheTextMysticismOfKyivFloods;
    private Integer analysisOfTheTextPathToNationalSelfAwareness;
    private Integer analysisOfTheTextVasylSymonenko;
    private Integer analysisOfTheTextArtNotSubjectToTime;
    private Integer analysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko;
    private Integer analysisOfTheTextSergeiParajanov;
    private Integer analysisOfTheTextGalileoGalilei;
    private Integer analysisOfTheTextFlowerOnTheAsphalt;
    private Integer analysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders;
    private Integer analysisOfTheTextLearnToLive;
    private Integer analysisOfTheTextWhoAreWeOurselves;
    private Integer analysisOfTheTextPoetessOfUkrainianRenaissance;
    private Integer analysisOfTheTextWaste;
    private Integer analysisOfTheTextDownloadForFree;
    private Integer analysisOfTheTextAttitudeToAnimals;
    private Integer analysisOfTheTextUkrainianCinema;
    private Integer analysisOfTheTextHigherEducation;
    private Integer analysisOfTheTextFur;
    private Integer analysisOfTheTextTheater;
    @OneToOne(mappedBy = "stylisticsTextLanguageDevelopmentEntity", fetch = FetchType.LAZY)
    private UkrMovaGrades ukrMovaGrades;

    public Integer getAnalysisOfTheTextBlueAppleForIlonka() {
        return analysisOfTheTextBlueAppleForIlonka;
    }
    public Integer getAnalysisOfTheTextRulesOfGoodTroll() {
        return analysisOfTheTextRulesOfGoodTroll;
    }
    public Integer getAnalysisOfTheTextBlackLettersOnAWhiteBackground() {
        return analysisOfTheTextBlackLettersOnAWhiteBackground;
    }
    public Integer getAnalysisOfTheTextCityWhereYouWouldLikeToLive() {
        return analysisOfTheTextCityWhereYouWouldLikeToLive;
    }
    public Integer getAnalysisOfTheTextMuralsOfKyiv() {
        return analysisOfTheTextMuralsOfKyiv;
    }
    public Integer getAnalysisOfTheTextIHadTheHappiestLife() {
        return analysisOfTheTextIHadTheHappiestLife;
    }
    public Integer getAnalysisOfTheTextVeteranOfTheSky() {
        return analysisOfTheTextVeteranOfTheSky;
    }
    public Integer getAnalysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary() {
        return analysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary;
    }
    public Integer getAnalysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality() {
        return analysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality;
    }
    public Integer getAnalysisOfTheTextChanceToRealizeYourself() {
        return analysisOfTheTextChanceToRealizeYourself;
    }
    public Integer getAnalysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians() {
        return analysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians;
    }
    public Integer getAnalysisOfTheTextWarmHumanityWithYourHeart() {
        return analysisOfTheTextWarmHumanityWithYourHeart;
    }
    public Integer getAnalysisOfTheTextMusicalGiftToTheWorld() {
        return analysisOfTheTextMusicalGiftToTheWorld;
    }
    public Integer getAnalysisOfTheTextSmallPathNotForPeople() {
        return analysisOfTheTextSmallPathNotForPeople;
    }
    public Integer getAnalysisOfTheTextReadMe() {
        return analysisOfTheTextReadMe;
    }
    public Integer getAnalysisOfTheTextFolkMedicine() {
        return analysisOfTheTextFolkMedicine;
    }
    public Integer getAnalysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine() {
        return analysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine;
    }
    public Integer getAnalysisOfTheTextSteppeEldorado() {
        return analysisOfTheTextSteppeEldorado;
    }
    public Integer getAnalysisOfTheTextPaintedStoriesUkrainianVersion() {
        return analysisOfTheTextPaintedStoriesUkrainianVersion;
    }
    public Integer getAnalysisOfTheTextDetectiveAsAnIntellectualExercise() {
        return analysisOfTheTextDetectiveAsAnIntellectualExercise;
    }
    public Integer getAnalysisOfTheTextPhilosophicalToolOfDanyloDemutsky() {
        return analysisOfTheTextPhilosophicalToolOfDanyloDemutsky;
    }
    public Integer getAnalysisOfTheTextMagicBox() {
        return analysisOfTheTextMagicBox;
    }
    public Integer getAnalysisOfTheTextOlenaTeliha() {
        return analysisOfTheTextOlenaTeliha;
    }
    public Integer getAnalysisOfTheTextAntoineDeSaintExupery() {
        return analysisOfTheTextAntoineDeSaintExupery;
    }
    public Integer getAnalysisOfTheTextTheThirdRoostersHaveAlreadySung() {
        return analysisOfTheTextTheThirdRoostersHaveAlreadySung;
    }
    public Integer getAnalysisOfTheTextOurAmulets() {
        return analysisOfTheTextOurAmulets;
    }
    public Integer getAnalysisOfTheTextMysticismOfKyivFloods() {
        return analysisOfTheTextMysticismOfKyivFloods;
    }
    public Integer getAnalysisOfTheTextPathToNationalSelfAwareness() {
        return analysisOfTheTextPathToNationalSelfAwareness;
    }
    public Integer getAnalysisOfTheTextVasylSymonenko() {
        return analysisOfTheTextVasylSymonenko;
    }
    public Integer getAnalysisOfTheTextArtNotSubjectToTime() {
        return analysisOfTheTextArtNotSubjectToTime;
    }
    public Integer getAnalysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko() {
        return analysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko;
    }
    public Integer getAnalysisOfTheTextSergeiParajanov() {
        return analysisOfTheTextSergeiParajanov;
    }
    public Integer getAnalysisOfTheTextGalileoGalilei() {
        return analysisOfTheTextGalileoGalilei;
    }
    public Integer getAnalysisOfTheTextFlowerOnTheAsphalt() {
        return analysisOfTheTextFlowerOnTheAsphalt;
    }
    public Integer getAnalysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders() {
        return analysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders;
    }
    public Integer getAnalysisOfTheTextLearnToLive() {
        return analysisOfTheTextLearnToLive;
    }
    public Integer getAnalysisOfTheTextWhoAreWeOurselves() {
        return analysisOfTheTextWhoAreWeOurselves;
    }
    public Integer getAnalysisOfTheTextPoetessOfUkrainianRenaissance() {
        return analysisOfTheTextPoetessOfUkrainianRenaissance;
    }
    public Integer getAnalysisOfTheTextWaste() {
        return analysisOfTheTextWaste;
    }
    public Integer getAnalysisOfTheTextDownloadForFree() {
        return analysisOfTheTextDownloadForFree;
    }
    public Integer getAnalysisOfTheTextAttitudeToAnimals() {
        return analysisOfTheTextAttitudeToAnimals;
    }
    public Integer getAnalysisOfTheTextUkrainianCinema() {
        return analysisOfTheTextUkrainianCinema;
    }
    public Integer getAnalysisOfTheTextHigherEducation() {
        return analysisOfTheTextHigherEducation;
    }
    public Integer getAnalysisOfTheTextFur() {
        return analysisOfTheTextFur;
    }
    public Integer getAnalysisOfTheTextTheater() {
        return analysisOfTheTextTheater;
    }
}
