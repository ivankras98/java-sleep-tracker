package ru.yandex.practicum.sleeptracker.analysis.impl;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeAnalyzer implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String OWL = "Сова";
    private static final String LARK = "Жаворонок";
    private static final String PIGEON = "Голубь";

    private static final String DESCRIPTION = "Хронотип пользователя";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        Map<String, Long> counts = sessions.stream()
                .filter(this::isNightSession)
                .collect(Collectors.groupingBy(
                        this::detectType,
                        Collectors.counting()
                ));

        long owls = counts.getOrDefault(OWL, 0L);
        long larks = counts.getOrDefault(LARK, 0L);
        long pigeons = counts.getOrDefault(PIGEON, 0L);

        String result;

        if (owls > larks && owls > pigeons) {
            result = OWL;
        } else if (larks > owls && larks > pigeons) {
            result = LARK;
        } else {
            result = PIGEON;
        }

        return new SleepAnalysisResult(DESCRIPTION, result);
    }

    private boolean isNightSession(SleepingSession s) {
        return s.getStart().toLocalDate().isBefore(s.getEnd().toLocalDate())
                || s.getEnd().toLocalTime().isBefore(LocalTime.of(6, 0));
    }

    private String detectType(SleepingSession s) {
        LocalTime start = s.getStart().toLocalTime();
        LocalTime end = s.getEnd().toLocalTime();

        if (start.isAfter(LocalTime.of(23, 0)) && end.isAfter(LocalTime.of(9, 0))) {
            return OWL;
        }

        if (start.isBefore(LocalTime.of(22, 0)) && end.isBefore(LocalTime.of(7, 0))) {
            return LARK;
        }

        return PIGEON;
    }
}