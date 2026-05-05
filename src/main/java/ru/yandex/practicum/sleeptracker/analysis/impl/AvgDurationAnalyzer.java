package ru.yandex.practicum.sleeptracker.analysis.impl;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class AvgDurationAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Средняя продолжительность сна (мин)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        double avg = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0);

        return new SleepAnalysisResult(
                DESCRIPTION,
                Math.round(avg)
        );
    }
}