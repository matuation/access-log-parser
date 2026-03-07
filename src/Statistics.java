import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics {
    long totalTraffic;
    int totalOpSys;
    int totalBrowser;
    int humanVisit;
    int errorRequest;
    LocalDateTime minTime;
    LocalDateTime maxTime;
    HashSet<String> existingPages;
    HashSet<String> notExistingPages;
    HashSet<String> uniqueReferrer;
    HashMap<String, Integer> uniqueOneIpRequests;
    HashMap<String, Integer> opSysStatistics;
    HashMap<String, Integer> browserStatistics;
    HashMap<LocalDateTime, Integer> peakVisitsPerSecond;


    public Statistics() {
        this.totalTraffic = 0;
        this.totalOpSys = 0;
        this.totalBrowser = 0;
        this.humanVisit = 0;
        this.errorRequest = 0;
        this.minTime = null;
        this.maxTime = null;
        this.existingPages = new HashSet<>();
        this.notExistingPages = new HashSet<>();
        this.uniqueReferrer = new HashSet<>();
        this.uniqueOneIpRequests = new HashMap<>();
        this.opSysStatistics = new HashMap<>();
        this.browserStatistics = new HashMap<>();
        this.peakVisitsPerSecond = new HashMap<>();

    }

    @Override
    public String toString() {
        return "Statistics{" + "totalTraffic=" + totalTraffic + ", minTime=" + minTime + ", maxTime=" + maxTime + '}';
    }

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getResponseSize();

        if (entry.getUserAgent().getOperatingSystem() != null) {
            totalOpSys++;
        }
        if (entry.getUserAgent().getBrowser() != null) {
            totalBrowser++;
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
        if (!entry.getIpAddress().equals("-") && !entry.getUserAgent().isBot()) {
            uniqueOneIpRequests.merge(entry.getIpAddress(), 1, Integer::sum);
        }
        if (entry.getDateAndTime() != null && !entry.getUserAgent().isBot()) {
            peakVisitsPerSecond.merge(entry.getDateAndTime(), 1, Integer::sum);
        }
        if (entry.getResponseCode() == 200) {
            existingPages.add(entry.getRequestPath());
        }
        if (entry.getResponseCode() == 404) {
            notExistingPages.add(entry.getRequestPath());
        }
        if (!entry.getReferer().equals("-")) {
            uniqueReferrer.add(entry.getReferer());
        }
        if ((entry.getResponseCode() >= 400) && (entry.getResponseCode() < 600)){
            errorRequest++;
        }
        if (!entry.getUserAgent().isBot()){
            humanVisit++;
        }

        opSysStatistics.merge(entry.getUserAgent().operatingSystem, 1, Integer::sum);
        browserStatistics.merge(entry.getUserAgent().browser, 1, Integer::sum);

    }



    public long getTrafficRate() {
        Duration duration = Duration.between(minTime, maxTime);
        long durationHours = duration.toHours();
        return totalTraffic / durationHours;
    }

    public HashSet<String> getExistingPages() {
        return existingPages;
    }

    public HashSet<String> getNotExistingPages() {
        return notExistingPages;
    }

    public HashMap<String, Integer> getOpSysStatistics() {
        return opSysStatistics;
    }

    public HashMap<String, Integer> getBrowserStatistics() {
        return browserStatistics;
    }

    public HashMap<String, Double> getOpSysAmountStatistics() {

        HashMap<String, Double> opSysAmount = new HashMap<>();
        for (Map.Entry<String, Integer> entry : opSysStatistics.entrySet()) {
            opSysAmount.put(entry.getKey(), Math.round(((double) entry.getValue() / totalOpSys) * 100.0) / 100.0);
        }
        return opSysAmount;
    }

    public HashMap<String, Double> getBrowserAmountStatistics() {

        HashMap<String, Double> browserAmount = new HashMap<>();
        for (Map.Entry<String, Integer> entry : browserStatistics.entrySet()) {
            browserAmount.put(entry.getKey(), Math.round(((double) entry.getValue() / totalBrowser) * 100.0) / 100.0);
        }
        return browserAmount;
    }

    public long getAverageVisitsPerHour(){
        Duration duration = Duration.between(minTime, maxTime);
        long durationHours = duration.toHours();
        return humanVisit / durationHours;
    }

    public long getAverageErrorRequestsPerHour(){
        Duration duration = Duration.between(minTime, maxTime);
        long durationHours = duration.toHours();
        return errorRequest / durationHours;
    }

    public long getAverageUniqueHumanVisit(){
        return humanVisit / uniqueOneIpRequests.size();
    }

    public int getPeakVisitsPerSecond(){
        return peakVisitsPerSecond.values().stream().max(Integer::compare).get();
    }

    public HashSet<String> getUniqueReferrer(){
         return uniqueReferrer.stream().map(o -> o.replace("https://", "")
                .replace("http://", "").replace("https%3A%2F%2F", "")
                .split("[/&]")[0]).collect(Collectors.toCollection(HashSet::new));
    }

    public long maxOneHumanVisit() {
        return uniqueOneIpRequests.values().stream().max(Integer::compare).get();
    }


}
