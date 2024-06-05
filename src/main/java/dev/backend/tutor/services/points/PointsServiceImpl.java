package dev.backend.tutor.services.points;

import dev.backend.tutor.dtos.points.PointsDto;
import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.entities.SubjectGrades.UkrMovaTest;
import dev.backend.tutor.exceptions.NoSubjectException;
import dev.backend.tutor.exceptions.NoThemeException;
import dev.backend.tutor.repositories.sql.points.UkrMovaTestRepository;
import dev.backend.tutor.repositories.sql.student.StudentRepository;
import dev.backend.tutor.services.security.jwt.JwtParser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PointsServiceImpl implements PointsService{
    private final StudentRepository studentRepository;
    private final JwtParser jwtParser;
    private final UkrMovaTestRepository ukrMovaTestRepository;

    public PointsServiceImpl(StudentRepository studentRepository, JwtParser jwtParser, UkrMovaTestRepository ukrMovaTestRepository){
        this.studentRepository = studentRepository;
        this.jwtParser = jwtParser;
        this.ukrMovaTestRepository = ukrMovaTestRepository;
    }

    @Override
    @Transactional
    public void putPointsToDB(PointsDto pointsDto) throws NoSubjectException {
        String token = pointsDto.accessToken();
        String themeName = pointsDto.themeName();
        Integer points = pointsDto.points();
        String subjectName = pointsDto.subjectName();
        switch (subjectName) {
            case "ukrMova":
                String username = jwtParser.extractUsername(token);
                Optional<Student> studentOptional = studentRepository.findStudentByUsername(username);
                if (studentOptional.isPresent()) {
                    Student student = studentOptional.get();
                    UkrMovaTest ukrMovaTest = student.getUkrMovaTest();
                    if (ukrMovaTest == null) {
                        ukrMovaTest = new UkrMovaTest();
                        ukrMovaTest.setUserUkrMovaGrades(username);
                        ukrMovaTestRepository.save(ukrMovaTest);
                    }
                    ukrMovaTest.addPoints(themeName, points);
                    ukrMovaTestRepository.save(ukrMovaTest);
                }
                break;
            default:
                throw new NoSubjectException("No such subject");
        }
    }
}
