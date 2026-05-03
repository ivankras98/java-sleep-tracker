package ru.yandex.practicum.sleeptracker.analysis.impl;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        Map<String, Long> counts = sessions.stream()
                .filter(this::isNightSession)
                .collect(Collectors.groupingBy(
                        this::detectType,
                        Collectors.counting()
                ));

        long owls = counts.getOrDefault("Сова", 0L);
        long larks = counts.getOrDefault("Жаворонок", 0L);
        long pigeons = counts.getOrDefault("Голубь", 0L);

        String result;

        if (owls > larks && owls > pigeons) {
            result = "Сова";
        } else if (larks > owls && larks > pigeons) {
            result = "Жаворонок";
        } else {
            result = "Голубь";
        }

        return new SleepAnalysisResult("Хронотип пользователя", result);
    }

    private boolean isNightSession(SleepingSession s) {
        return s.getStart().toLocalDate().isBefore(s.getEnd().toLocalDate())
                || s.getEnd().toLocalTime().isBefore(LocalTime.of(6, 0));
    }

    private String detectType(SleepingSession s) {
        LocalTime start = s.getStart().toLocalTime();
        LocalTime end = s.getEnd().toLocalTime();

        if (start.isAfter(LocalTime.of(23, 0)) && end.isAfter(LocalTime.of(9, 0))) {
            return "Сова";
        }

        if (start.isBefore(LocalTime.of(22, 0)) && end.isBefore(LocalTime.of(7, 0))) {
            return "Жаворонок";
        }

        return "Голубь";
    }
}