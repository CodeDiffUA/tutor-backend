package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;


@Entity
@Table(name = "lexicology")
public class Lexicology {
    @Id
    private String username;
    private Integer lexicalMeaningOfAWord;
    private Integer synonymus;
    private Integer lexicalError;
    private Integer phraseology;
    @OneToOne(mappedBy = "lexicology")
    private UkrMovaGrades ukrMovaGrades;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getLexicalMeaningOfAWord() {
        return lexicalMeaningOfAWord;
    }
    public void setLexicalMeaningOfAWord(Integer lexicalMeaningOfAWord) {
        this.lexicalMeaningOfAWord = lexicalMeaningOfAWord;
    }
    public Integer getSynonymus() {
        return synonymus;
    }
    public void setSynonymus(Integer synonymus) {
        this.synonymus = synonymus;
    }
    public Integer getLexicalError() {
        return lexicalError;
    }
    public void setLexicalError(Integer lexicalError) {
        this.lexicalError = lexicalError;
    }
    public Integer getPhraseology() {
        return phraseology;
    }
    public void setPhraseology(Integer phraseology) {
        this.phraseology = phraseology;
    }
}
