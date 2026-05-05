package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.analysis.impl.*;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static void main(String[] args) throws IOException {

        String filePath = args.length > 0 ? args[0] : "sleep_log.txt";

        InputStream inputStream = SleepTrackerApp.class
                .getClassLoader()
                .getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new RuntimeException("Файл не найден: " + filePath);
        }

        List<SleepingSession> sessions = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .map(SleepTrackerApp::parseLine)
                .toList();

        List<Function<List<SleepingSession>, SleepAnalysisResult>> analyzers = List.of(
                new TotalSleepSessionsAnalyzer(),
                new MinDurationAnalyzer(),
                new MaxDurationAnalyzer(),
                new AvgDurationAnalyzer(),
                new BadSleepCountAnalyzer(),
                new SleeplessNightsAnalyzer(),
                new ChronotypeAnalyzer()
        );

        analyzers.stream()
                .map(analyzer -> analyzer.apply(sessions))
                .forEach(result ->
                        System.out.println(result.getDescription() + ": " + result.getValue())
                );
    }

    private static SleepingSession parseLine(String line) {
        String[] parts = line.split(";");

        return new SleepingSession(
                LocalDateTime.parse(parts[0], FORMATTER),
                LocalDateTime.parse(parts[1], FORMATTER),
                SleepQuality.valueOf(parts[2].trim()) // 👈 важно!
        );
    }
}