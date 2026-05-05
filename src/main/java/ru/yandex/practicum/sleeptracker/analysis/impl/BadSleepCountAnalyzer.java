package ru.yandex.practicum.sleeptracker.analysis.impl;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadSleepCountAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String DESCRIPTION = "Количество сессий с плохим сном";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        long count = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult(DESCRIPTION, count);
    }
}