package ru.yandex.practicum.sleeptracker.analysis.impl;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SleeplessNightsAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Количество бессонных ночей";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(DESCRIPTION, 0);
        }

        LocalDateTime firstStart = sessions.get(0).getStart();
        LocalDateTime lastEnd = sessions.get(sessions.size() - 1).getEnd();

        LocalDate startDate = firstStart.toLocalDate();
        LocalDate endDate = lastEnd.toLocalDate();

        long totalNights = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        List<LocalDate> nights = Stream.iterate(startDate, d -> d.plusDays(1))
                .limit(totalNights)
                .toList();

        long sleepless = nights.stream()
                .filter(night -> isSleeplessNight(night, sessions))
                .count();

        return new SleepAnalysisResult(DESCRIPTION, sleepless);
    }

    private boolean isSleeplessNight(LocalDate night, List<SleepingSession> sessions) {

        LocalDateTime nightStart = night.atTime(0, 0);
        LocalDateTime nightEnd = night.atTime(6, 0);

        return sessions.stream()
                .noneMatch(session ->
                        intersects(session.getStart(), session.getEnd(), nightStart, nightEnd)
                );
    }

    private boolean intersects(LocalDateTime s1, LocalDateTime e1,
                               LocalDateTime s2, LocalDateTime e2) {
        return !s1.isAfter(e2) && !e1.isBefore(s2);
    }
}