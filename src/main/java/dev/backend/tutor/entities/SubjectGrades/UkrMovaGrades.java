package dev.backend.tutor.entities.SubjectGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades.*;
import jakarta.persistence.*;

@Entity
@Table(name = "ukr_mova_grades")
public class UkrMovaGrades {

    @Id
    @Column(name = "student_grades")
    private String studentUkrmovaGrades;
    private Integer phoneticsGraphemicsOrthoepy;
    private Integer orthography;
    private Integer lexicology;
    private Integer wordFormationWordDerivation;
    private Integer morphology;
    private Integer syntaxPunctuation;
    private Integer stylisticsTextLanguageDevelopment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_phonetics_graphemics_orthoepy_grades", referencedColumnName = "student_ukrmova_grades")
    private PhoneticsGraphemicsOrthoepy phoneticsGraphemicsOrthoepyEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_orphography_grades", referencedColumnName = "student_ukrmova_grades")
    private Orthography orthographyEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_lexicology_grades", referencedColumnName = "student_ukrmova_grades")
    private Lexicology lexicologyEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_word_formation_word_derivation_grades", referencedColumnName = "student_ukrmova_grades")
    private WordFormationWordDerivation wordFormationWordDerivationEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_morphology_grades", referencedColumnName = "student_ukrmova_grades")
    private Morphology morphologyEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_syntax_punctuation_grades", referencedColumnName = "student_ukrmova_grades")
    private SyntaxPunctuation syntaxPunctuationEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_stylistics_text_language_development_grades", referencedColumnName = "student_ukrmova_grades")
    private StylisticsTextLanguageDevelopment stylisticsTextLanguageDevelopmentEntity;

    public Integer getPhoneticsGraphemicsOrthoepy() {
        return phoneticsGraphemicsOrthoepy;
    }

    public void setPhoneticsGraphemicsOrthoepy(Integer phoneticsGraphemicsOrthoepy) {
        this.phoneticsGraphemicsOrthoepy = phoneticsGraphemicsOrthoepy;
    }

    public void calculateAndSetPhoneticsGraphemicsOrthoepy() {
        Integer calculatedValue = phoneticsGraphemicsOrthoepyEntity.getSyllableStress()
                + phoneticsGraphemicsOrthoepyEntity.getSoundLetterCorrespondence();
        setPhoneticsGraphemicsOrthoepy(calculatedValue);
    }

    public Integer getOrthography() {
        return orthography;
    }
    public void setOrthography(Integer orthography) {
        this.orthography = orthography;
    }

    public void calculateAndSetOrthography() {
        Integer calculatedValue = orthographyEntity.getSpellingOfUnstressedVowelsEUO()
                + orthographyEntity.getPrefixSpelling()
                + orthographyEntity.getConsonantClusterSimplification()
                + orthographyEntity.getConsonantChangesInWordFormation()
                + orthographyEntity.getApostrophe()
                + orthographyEntity.getSoftSign()
                + orthographyEntity.getCombinationOfYoYot()
                + orthographyEntity.getLetterDoubling()
                + orthographyEntity.getSpellingOfWordsOfForeignOrigin()
                + orthographyEntity.getCapitalizationAndQuotationMarksInProperNouns()
                + orthographyEntity.getCompoundWords()
                + orthographyEntity.getSpellingWithDifferentPartsOfSpeech()
                + orthographyEntity.getBasicCasesOfAlternationYVIU();
        setOrthography(calculatedValue);
    }

    public Integer getLexicology() {
        return lexicology;
    }
    public void setLexicology(Integer lexicology) {
        this.lexicology = lexicology;
    }

    public void calculateAndSetLexicology() {
        Integer calculatedValue = lexicologyEntity.getLexicalMeaningOfAWord()
                + lexicologyEntity.getSynonymus()
                + lexicologyEntity.getLexicalError()
                + lexicologyEntity.getPhraseology();
        setLexicology(calculatedValue);
    }

    public Integer getWordFormationWordDerivation() {
        return wordFormationWordDerivation;
    }
    public void setWordFormationWordDerivation(Integer wordFormationWordDerivation) {
        this.wordFormationWordDerivation = wordFormationWordDerivation;
    }

    public void calculateAndSetWordFormationWordDerivation(){
        Integer calculatedValue = wordFormationWordDerivationEntity.getWordStructureWordFormation();
        setWordFormationWordDerivation(calculatedValue);
    }

    public Integer getMorphology() {
        return morphology;
    }

    public void setMorphology(Integer morphology) {
        this.morphology = morphology;
    }

    public void calculateAndSetMorphology() {
        Integer calculatedValue = morphologyEntity.getClassificationOfPartsOfSpeech()
                + morphologyEntity.getNounGenderAndNumberOfNouns()
                + morphologyEntity.getNounDeclensionOfNouns()
                + morphologyEntity.getNounWritingPatronymics()
                + morphologyEntity.getAdjective()
                + morphologyEntity.getNumeral()
                + morphologyEntity.getPronoun()
                + morphologyEntity.getVerb()
                + morphologyEntity.getParticiple()
                + morphologyEntity.getAdverb()
                + morphologyEntity.getPreposition()
                + morphologyEntity.getConjunction()
                + morphologyEntity.getParticle();
        setMorphology(calculatedValue);
    }

    public Integer getSyntaxPunctuation() {
        return syntaxPunctuation;
    }

    public void setSyntaxPunctuation(Integer syntaxPunctuation) {
        this.syntaxPunctuation = syntaxPunctuation;
    }

    public void calculateAndSetSyntaxPunctuation(){
        Integer calculatedValue = syntaxPunctuationEntity.getPhrase()
                + syntaxPunctuationEntity.getClassificationOfSentences()
                + syntaxPunctuationEntity.getMainSentenceElements()
                + syntaxPunctuationEntity.getSubordinateSentenceElements()
                + syntaxPunctuationEntity.getSimpleSentences()
                + syntaxPunctuationEntity.getComplexSentencesCoordinatingSentenceElements()
                + syntaxPunctuationEntity.getComplexSentencesAddressingInsertedWordsPhrasesSentences()
                + syntaxPunctuationEntity.getSeparatedSentenceElementsSeparatedAttribute()
                + syntaxPunctuationEntity.getSeparatedSentenceElementsSeparatedAdverbialModifier()
                + syntaxPunctuationEntity.getSeparatedSentenceElementsSeparatedObject()
                + syntaxPunctuationEntity.getComplexSentenceCompoundSentence()
                + syntaxPunctuationEntity.getComplexSentenceComplexSubordinateClause()
                + syntaxPunctuationEntity.getComplexSentenceComplexClauseWithoutAConjunction()
                + syntaxPunctuationEntity.getComplexSentenceComplexSentenceWithDifferentTypesOfConnection()
                + syntaxPunctuationEntity.getForeignSpeech()
                + syntaxPunctuationEntity.getMeansOfInterphraseUnity();
        setSyntaxPunctuation(calculatedValue);
    }

    public Integer getStylisticsTextLanguageDevelopment() {
        return stylisticsTextLanguageDevelopment;
    }

    public void setStylisticsTextLanguageDevelopment(Integer stylisticsTextLanguageDevelopment) {
        this.stylisticsTextLanguageDevelopment = stylisticsTextLanguageDevelopment;
    }

    public void calculateAndSetStylisticsTextLanguageDevelopment(){
        Integer calculatedValue = stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextBlueAppleForIlonka() +
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextRulesOfGoodTroll()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextBlackLettersOnAWhiteBackground()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextCityWhereYouWouldLikeToLive()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextMuralsOfKyiv()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextIHadTheHappiestLife()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextVeteranOfTheSky()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextBookcrossingTransformingTheWorldIntoALibrary()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextCyberbullyingInTheNetworkTheEndOfVirtuality()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextChanceToRealizeYourself()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextNobelPleiadeOfNonUkrainianUkrainians()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextWarmHumanityWithYourHeart()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextMusicalGiftToTheWorld()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextSmallPathNotForPeople()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextReadMe()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextFolkMedicine()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextEasternCultureAsAKeyToUnderstandingUkraine()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextSteppeEldorado()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextPaintedStoriesUkrainianVersion()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextDetectiveAsAnIntellectualExercise()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextPhilosophicalToolOfDanyloDemutsky()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextMagicBox()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextOlenaTeliha()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextAntoineDeSaintExupery()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextTheThirdRoostersHaveAlreadySung()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextOurAmulets()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextMysticismOfKyivFloods()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextPathToNationalSelfAwareness()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextVasylSymonenko()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextArtNotSubjectToTime()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextMySincereFriendLifeFateOfIvanSoshenko()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextSergeiParajanov()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextGalileoGalilei()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextFlowerOnTheAsphalt()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextLiveSoThatPigeonsWouldSitOnYourShoulders()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextLearnToLive()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextWhoAreWeOurselves()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextPoetessOfUkrainianRenaissance()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextWaste()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextDownloadForFree()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextAttitudeToAnimals()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextUkrainianCinema()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextHigherEducation()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextFur()
                + stylisticsTextLanguageDevelopmentEntity.getAnalysisOfTheTextTheater();
        setStylisticsTextLanguageDevelopment(calculatedValue);
    }
}
