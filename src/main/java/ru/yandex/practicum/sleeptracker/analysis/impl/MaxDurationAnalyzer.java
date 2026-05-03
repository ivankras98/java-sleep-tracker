package ru.yandex.practicum.sleeptracker.analysis.impl;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MaxDurationAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        long max = sessions.stream()
                .map(SleepingSession::getDurationMinutes)
                .max(Comparator.naturalOrder())
                .orElse(0L);

        return new SleepAnalysisResult(
                "Максимальная продолжительность сна (мин)",
                max
        );
    }
}