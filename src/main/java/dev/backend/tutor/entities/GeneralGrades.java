package dev.backend.tutor.entities;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import jakarta.persistence.*;

@Entity
@Table(name = "general_grades")
public class GeneralGrades {

    @Id
    private String username;
    private Integer totalUkrMovaGrades;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Student student;
    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private UkrMovaGrades ukrMovaGrades;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTotalUkrMovaGrades() {
        return totalUkrMovaGrades;
    }

    public void setTotalUkrMovaGrades(Integer totalUkrMovaGrades) {
        this.totalUkrMovaGrades = totalUkrMovaGrades;
    }

    public void calculateAndSetTotalUkrMovaGrades() {
        Integer calculatedValue = ukrMovaGrades.getPhoneticsGraphemicsOrthoepy()
                + ukrMovaGrades.getOrthography()
                + ukrMovaGrades.getLexicology()
                + ukrMovaGrades.getWordFormationWordDerivation()
                + ukrMovaGrades.getMorphology()
                + ukrMovaGrades.getSyntaxPunctuation()
                + ukrMovaGrades.getStylisticsTextLanguageDevelopment();
        setTotalUkrMovaGrades(calculatedValue);
    }
}