package dev.backend.tutor.services.points;

import dev.backend.tutor.dtos.points.PointsDto;
import dev.backend.tutor.exceptions.NoSubjectException;

import javax.transaction.Transactional;

public interface PointsService {
    @Transactional
    void putPointsToDB(PointsDto pointsDto) throws NoSubjectException;
}
