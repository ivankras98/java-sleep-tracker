package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analysis.impl.ChronotypeAnalyzer;
import ru.yandex.practicum.sleeptracker.analysis.impl.SleeplessNightsAnalyzer;
import ru.yandex.practicum.sleeptracker.analysis.impl.TotalSleepSessionsAnalyzer;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.analysis.SleepAnalysisResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepTrackerAppTest {

    @Test
    void totalSessionsTest() {
        TotalSleepSessionsAnalyzer analyzer = new TotalSleepSessionsAnalyzer();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0), SleepQuality.BAD)
        );

        assertEquals(2L, ((Number) analyzer.apply(sessions).getValue()).longValue());
    }

    @Test
    void sleeplessNightTest() {
        SleeplessNightsAnalyzer analyzer = new SleeplessNightsAnalyzer();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 13, 0),
                        LocalDateTime.of(2025, 10, 1, 14, 0), SleepQuality.GOOD)
        );

        assertEquals(0L, ((Number) analyzer.apply(sessions).getValue()).longValue());
    }

    @Test
    void chronotypeTest() {
        ChronotypeAnalyzer analyzer = new ChronotypeAnalyzer();

        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30), SleepQuality.GOOD)
        );

        assertEquals("Сова", analyzer.apply(sessions).getValue());
    }
}