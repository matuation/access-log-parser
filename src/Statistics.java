import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    long totalTraffic;
    LocalDateTime minTime;
    LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "totalTraffic=" + totalTraffic +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                '}';
    }

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getResponseSize();
        if (minTime == null && maxTime == null) {
            minTime = entry.getDateAndTime();
            maxTime = entry.getDateAndTime();
            return;
        }
        if (entry.getDateAndTime().isBefore(minTime)) {
            minTime = entry.getDateAndTime();
        }
        if (entry.getDateAndTime().isAfter(maxTime)) {
            maxTime = entry.getDateAndTime();
        }
    }

    public long getTrafficRate() {
        Duration duration = Duration.between(minTime, maxTime);
        long durationHours = duration.toHours();
        return totalTraffic / durationHours;
    }
}
