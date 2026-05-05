package ru.yandex.practicum.sleeptracker.analysis.impl;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MinDurationAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Минимальная продолжительность сна (мин)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        long min = sessions.stream()
                .map(SleepingSession::getDurationMinutes)
                .min(Comparator.naturalOrder())
                .orElse(0L);

        return new SleepAnalysisResult(DESCRIPTION, min);
    }
}