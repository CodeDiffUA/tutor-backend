package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "stylistics_text_language_development")
public class StylisticsTextLanguageDevelopment {
    @Id
    private String username;
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
    @OneToOne(mappedBy = "stylisticsTextLanguageDevelopment")
    private UkrMovaGrades ukrMovaGrades;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getAnalysisOfTheTextBlueAppleForIlonka() {
        return analysisOfTheTextBlueAppleForIlonka;
    }
    public void setAnalysisOfTheTextBlueAppleForIlonka(Integer analysisOfTheTextBlueAppleForIlonka) {
        this.analysisOfTheTextBlueAppleForIlonka = analysisOfTheTextBlueAppleForIlonka;
    }
    public Integer getAnalysisOfTheTextRulesOfGoodTroll() {
        return analysisOfTheTextRulesOfGoodTroll;
    }
    public void setAnalysisOfTheTextRulesOfGoodTroll(Integer analysisOfTheTextRulesOfGoodTroll) {
        this.analysisOfTheTextRulesOfGoodTroll = analysisOfTheTextRulesOfGoodTroll;
    }
    public Integer getAnalysisOfTheTextBlackLettersOnAWhiteBackground() {
        return analysisOfTheTextBlackLettersOnAWhiteBackground;
    }
    public void setAnalysisOfTheTextBlackLettersOnAWhiteBackground(Integer analysisOfTheTextBlackLettersOnAWhiteBackground) {
        this.analysisOfTheTextBlackLettersOnAWhiteBackground = analysisOfTheTextBlackLettersOnAWhiteBackground;
    }
    public Integer getAnalysisOfTheTextCityWhereYouWouldLikeToLive() {
        return analysisOfTheTextCityWhereYouWouldLikeToLive;
    }
    public void setAnalysisOfTheTextCityWhereYouWouldLikeToLive(Integer analysisOfTheTextCityWhereYouWouldLikeToLive) {
        this.analysisOfTheTextCityWhereYouWouldLikeToLive = analysisOfTheTextCityWhereYouWouldLikeToLive;
    }
    public Integer getAnalysisOfTheTextMuralsOfKyiv() {
        return analysisOfTheTextMuralsOfKyiv;
    }
    public void setAnalysisOfTheTextMuralsOfKyiv(Integer analysisOfTheTextMuralsOfKyiv) {
        this.analysisOfTheTextMuralsOfKyiv = analysisOfTheTextMuralsOfKyiv;
    }
    public Integer getAnalysisOfTheTextIHadTheHappiestLife() {
        return analysisOfTheTextIHadTheHappiestLife;
    }
    public void setAnalysisOfTheTextIHadTheHappiestLife(Integer analysisOfTheTextIHadTheHappiestLife) {
        this.analysisOfTheTextIHadTheHappiestLife = analysisOfTheTextIHadTheHappiestLife;
    }
    public Integer getAnalysisOfTheTextVeteranOfTheSky() {
        return analysisOfTheTextVeteranOfTheSky;
    }
    public void setAnalysisOfTheTextVeteranOfTheSky(Integer analysisOfTheTextVeteranOfTheSky) {
        this.analysisOfTheTextVeteranOfTheSky = analysisOfTheTextVeteranOfTheSky;
    }
    public Integer getAnalysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary() {
        return analysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary;
    }
    public void setAnalysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary(Integer analysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary) {
        this.analysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary = analysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary;
    }
    public Integer getAnalysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality() {
        return analysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality;
    }
    public void setAnalysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality(Integer analysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality) {
        this.analysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality = analysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality;
    }
    public Integer getAnalysisOfTheTextChanceToRealizeYourself() {
        return analysisOfTheTextChanceToRealizeYourself;
    }
    public void setAnalysisOfTheTextChanceToRealizeYourself(Integer analysisOfTheTextChanceToRealizeYourself) {
        this.analysisOfTheTextChanceToRealizeYourself = analysisOfTheTextChanceToRealizeYourself;
    }
    public Integer getAnalysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians() {
        return analysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians;
    }
    public void setAnalysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians(Integer analysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians) {
        this.analysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians = analysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians;
    }
    public Integer getAnalysisOfTheTextWarmHumanityWithYourHeart() {
        return analysisOfTheTextWarmHumanityWithYourHeart;
    }
    public void setAnalysisOfTheTextWarmHumanityWithYourHeart(Integer analysisOfTheTextWarmHumanityWithYourHeart) {
        this.analysisOfTheTextWarmHumanityWithYourHeart = analysisOfTheTextWarmHumanityWithYourHeart;
    }
    public Integer getAnalysisOfTheTextMusicalGiftToTheWorld() {
        return analysisOfTheTextMusicalGiftToTheWorld;
    }
    public void setAnalysisOfTheTextMusicalGiftToTheWorld(Integer analysisOfTheTextMusicalGiftToTheWorld) {
        this.analysisOfTheTextMusicalGiftToTheWorld = analysisOfTheTextMusicalGiftToTheWorld;
    }
    public Integer getAnalysisOfTheTextSmallPathNotForPeople() {
        return analysisOfTheTextSmallPathNotForPeople;
    }
    public void setAnalysisOfTheTextSmallPathNotForPeople(Integer analysisOfTheTextSmallPathNotForPeople) {
        this.analysisOfTheTextSmallPathNotForPeople = analysisOfTheTextSmallPathNotForPeople;
    }
    public Integer getAnalysisOfTheTextReadMe() {
        return analysisOfTheTextReadMe;
    }
    public void setAnalysisOfTheTextReadMe(Integer analysisOfTheTextReadMe) {
        this.analysisOfTheTextReadMe = analysisOfTheTextReadMe;
    }
    public Integer getAnalysisOfTheTextFolkMedicine() {
        return analysisOfTheTextFolkMedicine;
    }
    public void setAnalysisOfTheTextFolkMedicine(Integer analysisOfTheTextFolkMedicine) {
        this.analysisOfTheTextFolkMedicine = analysisOfTheTextFolkMedicine;
    }
    public Integer getAnalysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine() {
        return analysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine;
    }
    public void setAnalysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine(Integer analysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine) {
        this.analysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine = analysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine;
    }
    public Integer getAnalysisOfTheTextSteppeEldorado() {
        return analysisOfTheTextSteppeEldorado;
    }
    public void setAnalysisOfTheTextSteppeEldorado(Integer analysisOfTheTextSteppeEldorado) {
        this.analysisOfTheTextSteppeEldorado = analysisOfTheTextSteppeEldorado;
    }
    public Integer getAnalysisOfTheTextPaintedStoriesUkrainianVersion() {
        return analysisOfTheTextPaintedStoriesUkrainianVersion;
    }
    public void setAnalysisOfTheTextPaintedStoriesUkrainianVersion(Integer analysisOfTheTextPaintedStoriesUkrainianVersion) {
        this.analysisOfTheTextPaintedStoriesUkrainianVersion = analysisOfTheTextPaintedStoriesUkrainianVersion;
    }
    public Integer getAnalysisOfTheTextDetectiveAsAnIntellectualExercise() {
        return analysisOfTheTextDetectiveAsAnIntellectualExercise;
    }
    public void setAnalysisOfTheTextDetectiveAsAnIntellectualExercise(Integer analysisOfTheTextDetectiveAsAnIntellectualExercise) {
        this.analysisOfTheTextDetectiveAsAnIntellectualExercise = analysisOfTheTextDetectiveAsAnIntellectualExercise;
    }
    public Integer getAnalysisOfTheTextPhilosophicalToolOfDanyloDemutsky() {
        return analysisOfTheTextPhilosophicalToolOfDanyloDemutsky;
    }
    public void setAnalysisOfTheTextPhilosophicalToolOfDanyloDemutsky(Integer analysisOfTheTextPhilosophicalToolOfDanyloDemutsky) {
        this.analysisOfTheTextPhilosophicalToolOfDanyloDemutsky = analysisOfTheTextPhilosophicalToolOfDanyloDemutsky;
    }
    public Integer getAnalysisOfTheTextMagicBox() {
        return analysisOfTheTextMagicBox;
    }
    public void setAnalysisOfTheTextMagicBox(Integer analysisOfTheTextMagicBox) {
        this.analysisOfTheTextMagicBox = analysisOfTheTextMagicBox;
    }
    public Integer getAnalysisOfTheTextOlenaTeliha() {
        return analysisOfTheTextOlenaTeliha;
    }
    public void setAnalysisOfTheTextOlenaTeliha(Integer analysisOfTheTextOlenaTeliha) {
        this.analysisOfTheTextOlenaTeliha = analysisOfTheTextOlenaTeliha;
    }
    public Integer getAnalysisOfTheTextAntoineDeSaintExupery() {
        return analysisOfTheTextAntoineDeSaintExupery;
    }
    public void setAnalysisOfTheTextAntoineDeSaintExupery(Integer analysisOfTheTextAntoineDeSaintExupery) {
        this.analysisOfTheTextAntoineDeSaintExupery = analysisOfTheTextAntoineDeSaintExupery;
    }
    public Integer getAnalysisOfTheTextTheThirdRoostersHaveAlreadySung() {
        return analysisOfTheTextTheThirdRoostersHaveAlreadySung;
    }
    public void setAnalysisOfTheTextTheThirdRoostersHaveAlreadySung(Integer analysisOfTheTextTheThirdRoostersHaveAlreadySung) {
        this.analysisOfTheTextTheThirdRoostersHaveAlreadySung = analysisOfTheTextTheThirdRoostersHaveAlreadySung;
    }
    public Integer getAnalysisOfTheTextOurAmulets() {
        return analysisOfTheTextOurAmulets;
    }
    public void setAnalysisOfTheTextOurAmulets(Integer analysisOfTheTextOurAmulets) {
        this.analysisOfTheTextOurAmulets = analysisOfTheTextOurAmulets;
    }
    public Integer getAnalysisOfTheTextMysticismOfKyivFloods() {
        return analysisOfTheTextMysticismOfKyivFloods;
    }
    public void setAnalysisOfTheTextMysticismOfKyivFloods(Integer analysisOfTheTextMysticismOfKyivFloods) {
        this.analysisOfTheTextMysticismOfKyivFloods = analysisOfTheTextMysticismOfKyivFloods;
    }
    public Integer getAnalysisOfTheTextPathToNationalSelfAwareness() {
        return analysisOfTheTextPathToNationalSelfAwareness;
    }
    public void setAnalysisOfTheTextPathToNationalSelfAwareness(Integer analysisOfTheTextPathToNationalSelfAwareness) {
        this.analysisOfTheTextPathToNationalSelfAwareness = analysisOfTheTextPathToNationalSelfAwareness;
    }
    public Integer getAnalysisOfTheTextVasylSymonenko() {
        return analysisOfTheTextVasylSymonenko;
    }
    public void setAnalysisOfTheTextVasylSymonenko(Integer analysisOfTheTextVasylSymonenko) {
        this.analysisOfTheTextVasylSymonenko = analysisOfTheTextVasylSymonenko;
    }
    public Integer getAnalysisOfTheTextArtNotSubjectToTime() {
        return analysisOfTheTextArtNotSubjectToTime;
    }
    public void setAnalysisOfTheTextArtNotSubjectToTime(Integer analysisOfTheTextArtNotSubjectToTime) {
        this.analysisOfTheTextArtNotSubjectToTime = analysisOfTheTextArtNotSubjectToTime;
    }
    public Integer getAnalysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko() {
        return analysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko;
    }
    public void setAnalysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko(Integer analysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko) {
        this.analysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko = analysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko;
    }
    public Integer getAnalysisOfTheTextSergeiParajanov() {
        return analysisOfTheTextSergeiParajanov;
    }
    public void setAnalysisOfTheTextSergeiParajanov(Integer analysisOfTheTextSergeiParajanov) {
        this.analysisOfTheTextSergeiParajanov = analysisOfTheTextSergeiParajanov;
    }
    public Integer getAnalysisOfTheTextGalileoGalilei() {
        return analysisOfTheTextGalileoGalilei;
    }
    public void setAnalysisOfTheTextGalileoGalilei(Integer analysisOfTheTextGalileoGalilei) {
        this.analysisOfTheTextGalileoGalilei = analysisOfTheTextGalileoGalilei;
    }
    public Integer getAnalysisOfTheTextFlowerOnTheAsphalt() {
        return analysisOfTheTextFlowerOnTheAsphalt;
    }
    public void setAnalysisOfTheTextFlowerOnTheAsphalt(Integer analysisOfTheTextFlowerOnTheAsphalt) {
        this.analysisOfTheTextFlowerOnTheAsphalt = analysisOfTheTextFlowerOnTheAsphalt;
    }
    public Integer getAnalysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders() {
        return analysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders;
    }
    public void setAnalysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders(Integer analysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders) {
        this.analysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders = analysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders;
    }
    public Integer getAnalysisOfTheTextLearnToLive() {
        return analysisOfTheTextLearnToLive;
    }
    public void setAnalysisOfTheTextLearnToLive(Integer analysisOfTheTextLearnToLive) {
        this.analysisOfTheTextLearnToLive = analysisOfTheTextLearnToLive;
    }
    public Integer getAnalysisOfTheTextWhoAreWeOurselves() {
        return analysisOfTheTextWhoAreWeOurselves;
    }
    public void setAnalysisOfTheTextWhoAreWeOurselves(Integer analysisOfTheTextWhoAreWeOurselves) {
        this.analysisOfTheTextWhoAreWeOurselves = analysisOfTheTextWhoAreWeOurselves;
    }
    public Integer getAnalysisOfTheTextPoetessOfUkrainianRenaissance() {
        return analysisOfTheTextPoetessOfUkrainianRenaissance;
    }
    public void setAnalysisOfTheTextPoetessOfUkrainianRenaissance(Integer analysisOfTheTextPoetessOfUkrainianRenaissance) {
        this.analysisOfTheTextPoetessOfUkrainianRenaissance = analysisOfTheTextPoetessOfUkrainianRenaissance;
    }
    public Integer getAnalysisOfTheTextWaste() {
        return analysisOfTheTextWaste;
    }
    public void setAnalysisOfTheTextWaste(Integer analysisOfTheTextWaste) {
        this.analysisOfTheTextWaste = analysisOfTheTextWaste;
    }
    public Integer getAnalysisOfTheTextDownloadForFree() {
        return analysisOfTheTextDownloadForFree;
    }
    public void setAnalysisOfTheTextDownloadForFree(Integer analysisOfTheTextDownloadForFree) {
        this.analysisOfTheTextDownloadForFree = analysisOfTheTextDownloadForFree;
    }
    public Integer getAnalysisOfTheTextAttitudeToAnimals() {
        return analysisOfTheTextAttitudeToAnimals;
    }
    public void setAnalysisOfTheTextAttitudeToAnimals(Integer analysisOfTheTextAttitudeToAnimals) {
        this.analysisOfTheTextAttitudeToAnimals = analysisOfTheTextAttitudeToAnimals;
    }
    public Integer getAnalysisOfTheTextUkrainianCinema() {
        return analysisOfTheTextUkrainianCinema;
    }
    public void setAnalysisOfTheTextUkrainianCinema(Integer analysisOfTheTextUkrainianCinema) {
        this.analysisOfTheTextUkrainianCinema = analysisOfTheTextUkrainianCinema;
    }
    public Integer getAnalysisOfTheTextHigherEducation() {
        return analysisOfTheTextHigherEducation;
    }
    public void setAnalysisOfTheTextHigherEducation(Integer analysisOfTheTextHigherEducation) {
        this.analysisOfTheTextHigherEducation = analysisOfTheTextHigherEducation;
    }
    public Integer getAnalysisOfTheTextFur() {
        return analysisOfTheTextFur;
    }
    public void setAnalysisOfTheTextFur(Integer analysisOfTheTextFur) {
        this.analysisOfTheTextFur = analysisOfTheTextFur;
    }
    public Integer getAnalysisOfTheTextTheater() {
        return analysisOfTheTextTheater;
    }
    public void setAnalysisOfTheTextTheater(Integer analysisOfTheTextTheater) {
        this.analysisOfTheTextTheater = analysisOfTheTextTheater;
    }
}
