import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Statistics {
    long totalTraffic;
    int totalOpSys;
    LocalDateTime minTime;
    LocalDateTime maxTime;
    HashSet<String> existingPages;
    HashMap<String, Integer> opSysStatistics;



    public Statistics() {
        this.totalTraffic = 0;
        this.totalOpSys = 0;
        this.minTime = null;
        this.maxTime = null;
        this.existingPages = new HashSet<>();
        this.opSysStatistics = new HashMap<>();
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
        if(entry.getUserAgent().getOperatingSystem() != null) {
            totalOpSys++;
        }
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
        if (entry.getResponseCode() == 200){
            existingPages.add(entry.getRequestPath());
        }

        opSysStatistics.merge(entry.getUserAgent().operatingSystem, 1, Integer::sum);
    }

    public long getTrafficRate() {
        Duration duration = Duration.between(minTime, maxTime);
        long durationHours = duration.toHours();
        return totalTraffic / durationHours;
    }

    public HashSet<String> getExistingPages() {
        return existingPages;
    }

    public HashMap<String, Integer> getOpSysStatistics() {
        return opSysStatistics;
    }

    public HashMap<String, Double> getOpSysAmountStatistics() {

        HashMap<String, Double> opSysAmount = new HashMap<>();
        for (Map.Entry<String, Integer> entry : opSysStatistics.entrySet()) {
            opSysAmount.put(entry.getKey(), Math.round(((double) entry.getValue() / totalOpSys) * 100.0) / 100.0);
        }
        return opSysAmount;
    }
}
