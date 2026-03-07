import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAgent {
    final String operatingSystem;
    final String browser;
    String thisUserAgent;
    boolean isThisBot;

    public UserAgent(String line) {
        this.thisUserAgent = line;
        Pattern linuxPattern = Pattern.compile(".*Linux.*");
        Pattern windowsPattern = Pattern.compile(".*Windows.*");
        Pattern macPattern = Pattern.compile(".*Mac OS.*");
        Matcher linuxMatcher = linuxPattern.matcher(line);
        Matcher windowsMatcher = windowsPattern.matcher(line);
        Matcher macMatcher = macPattern.matcher(line);
        if (linuxMatcher.find()) {
            this.operatingSystem = "Linux";
        } else if (windowsMatcher.find()) {
            this.operatingSystem = "Windows";
        } else if (macMatcher.find()) {
            this.operatingSystem = "Mac OS";
        } else {
            this.operatingSystem = "Other";
        }
        Pattern firefoxPattern = Pattern.compile(".*Firefox.*");
        Pattern chromePattern = Pattern.compile(".*Chrome.*");
        Pattern operaPattern = Pattern.compile(".*OPR.*");
        Pattern edgePattern = Pattern.compile(".*Edg.*");
        Pattern safariPattern = Pattern.compile(".*Safari.*");
        Matcher firefoxMatcher = firefoxPattern.matcher(line);
        Matcher chromeMatcher = chromePattern.matcher(line);
        Matcher operaMatcher = operaPattern.matcher(line);
        Matcher edgeMatcher = edgePattern.matcher(line);
        Matcher safariMatcher = safariPattern.matcher(line);
        if (firefoxMatcher.find()) {
            this.browser = "Firefox";
        } else if (chromeMatcher.find() && safariMatcher.find()) {
            this.browser = "Chrome";
        } else if (operaMatcher.find()) {
            this.browser = "Opera";
        } else if (edgeMatcher.find()) {
            this.browser = "Edge";
        } else if (safariMatcher.find() && !firefoxMatcher.find()
                && !chromeMatcher.find() && !operaMatcher.find() && !edgeMatcher.find()) {
            this.browser = "Safari";
        } else {
            this.browser = "Other";
        }


    }

    boolean isBot () {
        Pattern botPattern = Pattern.compile(".*bot/.*", Pattern.CASE_INSENSITIVE);
        Matcher botMatcher = botPattern.matcher(thisUserAgent);
        isThisBot = botMatcher.find();
        return isThisBot;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }



    @Override
    public String toString() {
        return "UserAgent{" +
                "operatingSystem='" + operatingSystem + '\'' +
                ", browser='" + browser + '\'' +
                '}';
    }

}
