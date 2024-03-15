package dev.backend.tutor.entities.SubjectGrades.UkrMovaThemeGrades;

import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;


@Entity
@Table(name = "lexicology")
public class Lexicology {
    @Id
    @Column(name = "student_ukrmova_grades")
    private String studentLexicologyGrades;
    private Integer lexicalMeaningOfAWord;
    private Integer synonymus;
    private Integer lexicalError;
    private Integer phraseology;
    @OneToOne(mappedBy = "lexicologyEntity", fetch = FetchType.LAZY)
    private UkrMovaGrades ukrMovaGrades;

    public Integer getLexicalMeaningOfAWord() {
        return lexicalMeaningOfAWord;
    }
    public Integer getSynonymus() {
        return synonymus;
    }
    public Integer getLexicalError() {
        return lexicalError;
    }
    public Integer getPhraseology() {
        return phraseology;
    }
}
