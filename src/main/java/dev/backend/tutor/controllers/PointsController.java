package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.points.PointsDto;
import dev.backend.tutor.exceptions.NoSubjectException;
import dev.backend.tutor.services.points.PointsService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/v1/points")
public class PointsController {
    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @PostMapping("/put")
    public void putPointsToDB(@RequestBody PointsDto pointsDto) throws NoSubjectException {
        pointsService.putPointsToDB(pointsDto);
    }
}
