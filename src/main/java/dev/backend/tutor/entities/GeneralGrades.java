package dev.backend.tutor.entities;
import dev.backend.tutor.entities.SubjectGrades.UkrMovaGrades;
import dev.backend.tutor.entities.student.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "general_grades")
public class GeneralGrades {

    @Id
    @Column(name = "student_grades_id")
    private String studentGrades;
    private Integer totalUkrMovaGrades;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_grades", referencedColumnName = "username")
    private Student student;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_ukrmova_grades", referencedColumnName = "student_grades")
    private UkrMovaGrades totalukrMovaGradesEntity;

    public void setTotalUkrMovaGrades(Integer totalUkrMovaGrades) {
        this.totalUkrMovaGrades = totalUkrMovaGrades;
    }

    public void calculateAndSetTotalUkrMovaGrades() {
        Integer calculatedValue = totalukrMovaGradesEntity.getPhoneticsGraphemicsOrthoepy()
                + totalukrMovaGradesEntity.getOrthography()
                + totalukrMovaGradesEntity.getLexicology()
                + totalukrMovaGradesEntity.getWordFormationWordDerivation()
                + totalukrMovaGradesEntity.getMorphology()
                + totalukrMovaGradesEntity.getSyntaxPunctuation()
                + totalukrMovaGradesEntity.getStylisticsTextLanguageDevelopment();
        setTotalUkrMovaGrades(calculatedValue);
    }
}