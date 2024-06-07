package dev.backend.tutor.entities.SubjectGrades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Entity
@Table(name = "ukr_mova")
public class UkrMovaTest {
    @Id
    @Column(name = "user_ukr_mova_grades")
    private String userUkrMovaGrades;
    @Column(name = "Lexical-error")
    private Integer lexicalError;
    @Column(name = "The-lexical-meaning-of-the-word")
    private Integer theLexicalMeaningOfTheWord;
    @Column(name = "Synonyms")
    private Integer synonyms;
    @Column(name = "Phraseology")
    private Integer phraseology;
    @Column(name = "Storage-Emphasis")
    private Integer storageEmphasis;
    @Column(name = "The-ratio-of-sounds-and-letters")
    private Integer theRatioOfSoundsAndLetters;
    @Column(name = "Phrase")
    private Integer phrase;
    @Column(name = "Secondary-members-of-the-sentence")
    private Integer secondaryMembersOfTheSentence;
    @Column(name = "Classification-of-sentences")
    private Integer classificationOfSentences;
    @Column(name = "Chief-members-of-the-sentence")
    private Integer chiefMembersOfTheSentence;
    @Column(name = "Separate-members-of-the-sentence-Separate-application")
    private Integer separateMembersOfTheSentenceSeparateApplication;
    @Column(name = "Monitor-sentences")
    private Integer monitorSentences;
    @Column(name = "Complicated-sentence-homogeneous-members-of-a-sentence")
    private Integer complicatedSentenceHomogeneousMembersOfASentence;
    @Column(name = "Separate-members-of-a-sentence-Separate-definition")
    private Integer separateMembersOfASentenceSeparateDefinition;
    @Column(name = "Complicated-sentence:-appeal,-insertion-words,-phrases,-sentences")
    private Integer complicatedSentenceAppealInsertionWordsPhrasesSentences;
    @Column(name = "Complex-sentence-A-complex-non-satellite-sentence")
    private Integer complexSentenceAComplexNonSatelliteSentence;
    @Column(name = "Separate-members-of-a-sentence-A-separate-circumstance")
    private Integer separateMembersOfASentenceASeparateCircumstance;
    @Column(name = "Complex-sentence:-A-complex-sentence")
    private Integer complexSentenceAComplexSentence;
    @Column(name = "Complex-sentence-A-compound-sentence")
    private Integer complexSentenceACompoundSentence;
    @Column(name = "Complex-sentence-complex-sentence-with-different-types-of-communication")
    private Integer complexSentenceComplexSentenceWithDifferentTypesOfCommunication;
    @Column(name = "Alien-speech")
    private Integer alienSpeech;
    @Column(name = "Means-of-interphrase-unity")
    private Integer meansOfInterphraseUnity;
    @Column(name = "The-structure-of-the-wordWord-formation")
    private Integer theStructureOfTheWordWordFormation;
    @Column(name = "Spelling-of-letters-denoting-unstressed-vowels-e,-and,-o")
    private Integer spellingOfLettersDenotingUnstressedVowelsEAndO;
    @Column(name = "Spelling-words-of-a-foreign--language-origin")
    private Integer spellingWordsOfAForeignLanguageOrigin;
    @Column(name = "Spelling-of-prefixes")
    private Integer spellingOfPrefixes;
    @Column(name = "Simplification-in-groups-of-consonants")
    private Integer simplificationInGroupsOfConsonants;
    @Column(name = "Changes-of-consonants-when-creating-words")
    private Integer changesOfConsonantsWhenCreatingWords;
    @Column(name = "Apostrophe")
    private Integer apostrophe;
    @Column(name = "A-soft-sign")
    private Integer aSoftSign;
    @Column(name = "Double-letters")
    private Integer doubleLetters;
    @Column(name = "Combine-yo,-wo")
    private Integer combineYoWo;
    @Column(name = "Complex-words")
    private Integer complexWords;
    @Column(name = "Big-letter-and-quotes-in-own-names")
    private Integer bigLetterAndQuotesInOwnNames;
    @Column(name = "Spelling-is-not-with-different-parts-of-language")
    private Integer spellingIsNotWithDifferentPartsOfLanguage;
    @Column(name = "The-main-cases-of-duty-u--in,-i--th")
    private Integer theMainCasesOfDutyUInIth;
    @Column(name = "Verb")
    private Integer verb;
    @Column(name = "Classification-of-parts-of-language")
    private Integer classificationOfPartsOfLanguage;
    @Column(name = "Noun-genus-and-number-of-nouns")
    private Integer nounGenusAndNumberOfNouns;
    @Column(name = "Noun-Calculation-of-nouns")
    private Integer nounCalculationOfNouns;
    @Column(name = "Participle")
    private Integer participle;
    @Column(name = "Noun-Writing-patronymic-names")
    private Integer nounWritingPatronymicNames;
    @Column(name = "Adjective")
    private Integer adjective;
    @Column(name = "Numeral")
    private Integer numeral;
    @Column(name = "Pronoun")
    private Integer pronoun;
    @Column(name = "Verb-adverb")
    private Integer verbAdverb;
    @Column(name = "Adverb")
    private Integer adverb;
    @Column(name = "Preposition")
    private Integer preposition;
    @Column(name = "Fraction")
    private Integer fraction;
    @Column(name = "Conjunction")
    private Integer conjunction;

    public void setUserUkrMovaGrades(String userUkrMovaGrades) {
        this.userUkrMovaGrades = userUkrMovaGrades;
    }

    public void setLexicalError(Integer lexicalError) {
        this.lexicalError = lexicalError;
    }

    public void setTheLexicalMeaningOfTheWord(Integer theLexicalMeaningOfTheWord) {
        this.theLexicalMeaningOfTheWord = theLexicalMeaningOfTheWord;
    }

    public void setSynonyms(Integer synonyms) {
        this.synonyms = synonyms;
    }

    public void setPhraseology(Integer phraseology) {
        this.phraseology = phraseology;
    }

    public void setStorageEmphasis(Integer storageEmphasis) {
        this.storageEmphasis = storageEmphasis;
    }

    public void setTheRatioOfSoundsAndLetters(Integer theRatioOfSoundsAndLetters) {
        this.theRatioOfSoundsAndLetters = theRatioOfSoundsAndLetters;
    }

    public void setPhrase(Integer phrase) {
        this.phrase = phrase;
    }

    public void setSecondaryMembersOfTheSentence(Integer secondaryMembersOfTheSentence) {
        this.secondaryMembersOfTheSentence = secondaryMembersOfTheSentence;
    }

    public void setClassificationOfSentences(Integer classificationOfSentences) {
        this.classificationOfSentences = classificationOfSentences;
    }

    public void setChiefMembersOfTheSentence(Integer chiefMembersOfTheSentence) {
        this.chiefMembersOfTheSentence = chiefMembersOfTheSentence;
    }

    public void setSeparateMembersOfTheSentenceSeparateApplication(Integer separateMembersOfTheSentenceSeparateApplication) {
        this.separateMembersOfTheSentenceSeparateApplication = separateMembersOfTheSentenceSeparateApplication;
    }

    public void setMonitorSentences(Integer monitorSentences) {
        this.monitorSentences = monitorSentences;
    }

    public void setComplicatedSentenceHomogeneousMembersOfASentence(Integer complicatedSentenceHomogeneousMembersOfASentence) {
        this.complicatedSentenceHomogeneousMembersOfASentence = complicatedSentenceHomogeneousMembersOfASentence;
    }

    public void setSeparateMembersOfASentenceSeparateDefinition(Integer separateMembersOfASentenceSeparateDefinition) {
        this.separateMembersOfASentenceSeparateDefinition = separateMembersOfASentenceSeparateDefinition;
    }

    public void setComplicatedSentenceAppealInsertionWordsPhrasesSentences(Integer complicatedSentenceAppealInsertionWordsPhrasesSentences) {
        this.complicatedSentenceAppealInsertionWordsPhrasesSentences = complicatedSentenceAppealInsertionWordsPhrasesSentences;
    }

    public void setComplexSentenceAComplexNonSatelliteSentence(Integer complexSentenceAComplexNonSatelliteSentence) {
        this.complexSentenceAComplexNonSatelliteSentence = complexSentenceAComplexNonSatelliteSentence;
    }

    public void setSeparateMembersOfASentenceASeparateCircumstance(Integer separateMembersOfASentenceASeparateCircumstance) {
        this.separateMembersOfASentenceASeparateCircumstance = separateMembersOfASentenceASeparateCircumstance;
    }

    public void setComplexSentenceAComplexSentence(Integer complexSentenceAComplexSentence) {
        this.complexSentenceAComplexSentence = complexSentenceAComplexSentence;
    }

    public void setComplexSentenceACompoundSentence(Integer complexSentenceACompoundSentence) {
        this.complexSentenceACompoundSentence = complexSentenceACompoundSentence;
    }

    public void setComplexSentenceComplexSentenceWithDifferentTypesOfCommunication(Integer complexSentenceComplexSentenceWithDifferentTypesOfCommunication) {
        this.complexSentenceComplexSentenceWithDifferentTypesOfCommunication = complexSentenceComplexSentenceWithDifferentTypesOfCommunication;
    }

    public void setAlienSpeech(Integer alienSpeech) {
        this.alienSpeech = alienSpeech;
    }

    public void setMeansOfInterphraseUnity(Integer meansOfInterphraseUnity) {
        this.meansOfInterphraseUnity = meansOfInterphraseUnity;
    }

    public void setTheStructureOfTheWordWordFormation(Integer theStructureOfTheWordWordFormation) {
        this.theStructureOfTheWordWordFormation = theStructureOfTheWordWordFormation;
    }

    public void setSpellingOfLettersDenotingUnstressedVowelsEAndO(Integer spellingOfLettersDenotingUnstressedVowelsEAndO) {
        this.spellingOfLettersDenotingUnstressedVowelsEAndO = spellingOfLettersDenotingUnstressedVowelsEAndO;
    }

    public void setSpellingWordsOfAForeignLanguageOrigin(Integer spellingWordsOfAForeignLanguageOrigin) {
        this.spellingWordsOfAForeignLanguageOrigin = spellingWordsOfAForeignLanguageOrigin;
    }

    public void setSpellingOfPrefixes(Integer spellingOfPrefixes) {
        this.spellingOfPrefixes = spellingOfPrefixes;
    }

    public void setSimplificationInGroupsOfConsonants(Integer simplificationInGroupsOfConsonants) {
        this.simplificationInGroupsOfConsonants = simplificationInGroupsOfConsonants;
    }

    public void setChangesOfConsonantsWhenCreatingWords(Integer changesOfConsonantsWhenCreatingWords) {
        this.changesOfConsonantsWhenCreatingWords = changesOfConsonantsWhenCreatingWords;
    }

    public void setApostrophe(Integer apostrophe) {
        this.apostrophe = apostrophe;
    }

    public void setaSoftSign(Integer aSoftSign) {
        this.aSoftSign = aSoftSign;
    }

    public void setDoubleLetters(Integer doubleLetters) {
        this.doubleLetters = doubleLetters;
    }

    public void setCombineYoWo(Integer combineYoWo) {
        this.combineYoWo = combineYoWo;
    }

    public void setComplexWords(Integer complexWords) {
        this.complexWords = complexWords;
    }

    public void setBigLetterAndQuotesInOwnNames(Integer bigLetterAndQuotesInOwnNames) {
        this.bigLetterAndQuotesInOwnNames = bigLetterAndQuotesInOwnNames;
    }

    public void setSpellingIsNotWithDifferentPartsOfLanguage(Integer spellingIsNotWithDifferentPartsOfLanguage) {
        this.spellingIsNotWithDifferentPartsOfLanguage = spellingIsNotWithDifferentPartsOfLanguage;
    }

    public void setTheMainCasesOfDutyUInIth(Integer theMainCasesOfDutyUInIth) {
        this.theMainCasesOfDutyUInIth = theMainCasesOfDutyUInIth;
    }

    public void setVerb(Integer verb) {
        this.verb = verb;
    }

    public void setClassificationOfPartsOfLanguage(Integer classificationOfPartsOfLanguage) {
        this.classificationOfPartsOfLanguage = classificationOfPartsOfLanguage;
    }

    public void setNounGenusAndNumberOfNouns(Integer nounGenusAndNumberOfNouns) {
        this.nounGenusAndNumberOfNouns = nounGenusAndNumberOfNouns;
    }

    public void setNounCalculationOfNouns(Integer nounCalculationOfNouns) {
        this.nounCalculationOfNouns = nounCalculationOfNouns;
    }

    public void setParticiple(Integer participle) {
        this.participle = participle;
    }

    public void setNounWritingPatronymicNames(Integer nounWritingPatronymicNames) {
        this.nounWritingPatronymicNames = nounWritingPatronymicNames;
    }

    public void setAdjective(Integer adjective) {
        this.adjective = adjective;
    }

    public void setNumeral(Integer numeral) {
        this.numeral = numeral;
    }

    public void setPronoun(Integer pronoun) {
        this.pronoun = pronoun;
    }

    public void setVerbAdverb(Integer verbAdverb) {
        this.verbAdverb = verbAdverb;
    }

    public void setAdverb(Integer adverb) {
        this.adverb = adverb;
    }

    public void setPreposition(Integer preposition) {
        this.preposition = preposition;
    }

    public void setFraction(Integer fraction) {
        this.fraction = fraction;
    }

    public void setConjunction(Integer conjunction) {
        this.conjunction = conjunction;
    }

    public Integer getLexicalError() {
        return lexicalError;
    }

    public Integer getTheLexicalMeaningOfTheWord() {
        return theLexicalMeaningOfTheWord;
    }

    public Integer getSynonyms() {
        return synonyms;
    }

    public Integer getPhraseology() {
        return phraseology;
    }

    public Integer getStorageEmphasis() {
        return storageEmphasis;
    }

    public Integer getTheRatioOfSoundsAndLetters() {
        return theRatioOfSoundsAndLetters;
    }

    public Integer getPhrase() {
        return phrase;
    }

    public Integer getSecondaryMembersOfTheSentence() {
        return secondaryMembersOfTheSentence;
    }

    public Integer getClassificationOfSentences() {
        return classificationOfSentences;
    }

    public Integer getChiefMembersOfTheSentence() {
        return chiefMembersOfTheSentence;
    }

    public Integer getSeparateMembersOfTheSentenceSeparateApplication() {
        return separateMembersOfTheSentenceSeparateApplication;
    }

    public Integer getMonitorSentences() {
        return monitorSentences;
    }

    public Integer getComplicatedSentenceHomogeneousMembersOfASentence() {
        return complicatedSentenceHomogeneousMembersOfASentence;
    }

    public Integer getSeparateMembersOfASentenceSeparateDefinition() {
        return separateMembersOfASentenceSeparateDefinition;
    }

    public Integer getComplicatedSentenceAppealInsertionWordsPhrasesSentences() {
        return complicatedSentenceAppealInsertionWordsPhrasesSentences;
    }

    public Integer getComplexSentenceAComplexNonSatelliteSentence() {
        return complexSentenceAComplexNonSatelliteSentence;
    }

    public Integer getSeparateMembersOfASentenceASeparateCircumstance() {
        return separateMembersOfASentenceASeparateCircumstance;
    }

    public Integer getComplexSentenceAComplexSentence() {
        return complexSentenceAComplexSentence;
    }

    public Integer getComplexSentenceACompoundSentence() {
        return complexSentenceACompoundSentence;
    }

    public Integer getComplexSentenceComplexSentenceWithDifferentTypesOfCommunication() {
        return complexSentenceComplexSentenceWithDifferentTypesOfCommunication;
    }

    public Integer getAlienSpeech() {
        return alienSpeech;
    }

    public Integer getMeansOfInterphraseUnity() {
        return meansOfInterphraseUnity;
    }

    public Integer getTheStructureOfTheWordWordFormation() {
        return theStructureOfTheWordWordFormation;
    }

    public Integer getSpellingOfLettersDenotingUnstressedVowelsEAndO() {
        return spellingOfLettersDenotingUnstressedVowelsEAndO;
    }

    public Integer getSpellingWordsOfAForeignLanguageOrigin() {
        return spellingWordsOfAForeignLanguageOrigin;
    }

    public Integer getSpellingOfPrefixes() {
        return spellingOfPrefixes;
    }

    public Integer getSimplificationInGroupsOfConsonants() {
        return simplificationInGroupsOfConsonants;
    }

    public Integer getChangesOfConsonantsWhenCreatingWords() {
        return changesOfConsonantsWhenCreatingWords;
    }

    public Integer getApostrophe() {
        return apostrophe;
    }

    public Integer getaSoftSign() {
        return aSoftSign;
    }

    public Integer getDoubleLetters() {
        return doubleLetters;
    }

    public Integer getCombineYoWo() {
        return combineYoWo;
    }

    public Integer getComplexWords() {
        return complexWords;
    }

    public Integer getBigLetterAndQuotesInOwnNames() {
        return bigLetterAndQuotesInOwnNames;
    }

    public Integer getSpellingIsNotWithDifferentPartsOfLanguage() {
        return spellingIsNotWithDifferentPartsOfLanguage;
    }

    public Integer getTheMainCasesOfDutyUInIth() {
        return theMainCasesOfDutyUInIth;
    }

    public Integer getVerb() {
        return verb;
    }

    public Integer getClassificationOfPartsOfLanguage() {
        return classificationOfPartsOfLanguage;
    }

    public Integer getNounGenusAndNumberOfNouns() {
        return nounGenusAndNumberOfNouns;
    }

    public Integer getNounCalculationOfNouns() {
        return nounCalculationOfNouns;
    }

    public Integer getParticiple() {
        return participle;
    }

    public Integer getNounWritingPatronymicNames() {
        return nounWritingPatronymicNames;
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

    public Integer getVerbAdverb() {
        return verbAdverb;
    }

    public Integer getAdverb() {
        return adverb;
    }

    public Integer getPreposition() {
        return preposition;
    }

    public Integer getFraction() {
        return fraction;
    }

    public Integer getConjunction() {
        return conjunction;
    }

    public void addPoints(String themeName, Integer points) {
        try {
            String fieldName = convertToCamelCase(themeName);
            Method getMethod = this.getClass().getMethod("get" + capitalize(fieldName));
            Integer currentValue = (Integer) getMethod.invoke(this);
            if (currentValue == null) {
                currentValue = 0;
            }
            Integer newValue = currentValue + points;
            Method setMethod = this.getClass().getMethod("set" + capitalize(fieldName), Integer.class);
            setMethod.invoke(this, newValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String convertToCamelCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toLowerCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }

    private String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
