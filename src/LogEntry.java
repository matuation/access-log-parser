import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    final String ipAddress;
    final LocalDateTime dateAndTime;
    final HttpMethod httpMethod;
    final String path;
    final int responseCode;
    final int responseSize;
    final String referer;
    final UserAgent userAgent;

    public LogEntry(String line) {
        String dateTime = "";
        HttpMethod method = null;//иниц
        String reqPath = "";
        int respCode = 0;
        int respSize = 0;
        String ref = "";
        String userA = "";
        int firstSpace = line.indexOf(" ");//первый пробел
        int firstSqBrackets = line.indexOf("[");//первая квадратная скобка
        int secondSqBrackets = line.indexOf(" ", firstSqBrackets + 1);//вторая кв скобка
        int firstQot = line.indexOf("\"");//первые кавычки
        int firstSpaceAfterFirstQot = line.indexOf(" ", firstQot + 1);//первый пробел после кавычек
        int secondSpaceAfterFirstQot = line.indexOf(" ", firstSpaceAfterFirstQot + 1);//второй пробел после кавычек
        int spaceBeforeResponseCode = line.indexOf(" ", secondSpaceAfterFirstQot + 1);
        int spaceAfterResponseCode = line.indexOf(" ", spaceBeforeResponseCode + 1);
        int spaceAfterResponseSize = line.indexOf(" ", spaceAfterResponseCode + 1);
        int QoutAfterResponseSize = line.indexOf("\"", spaceAfterResponseSize);
        int QoutAfterRef = line.indexOf("\"", QoutAfterResponseSize + 1);
        int lastQut = line.lastIndexOf("\"");
        int preLastQot = line.lastIndexOf("\"", lastQut - 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);//форматируем лог под условия задания

        if (firstSqBrackets != -1 && secondSqBrackets != -1) {
            dateTime = line.substring(firstSqBrackets + 1, secondSqBrackets);
        }

        if (firstQot != -1 && firstSpaceAfterFirstQot != -1) {
            method = HttpMethod.valueOf(line.substring(firstQot + 1, firstSpaceAfterFirstQot));
        }

        if (firstSpaceAfterFirstQot != -1 && secondSpaceAfterFirstQot != -1) {
            reqPath = line.substring(firstSpaceAfterFirstQot + 1, secondSpaceAfterFirstQot);
        }

        if (spaceBeforeResponseCode != -1 && spaceAfterResponseCode != -1) {
            respCode = Integer.parseInt(line.substring(spaceBeforeResponseCode + 1, spaceAfterResponseCode));
        }

        if (spaceBeforeResponseCode != -1 && spaceAfterResponseCode != -1) {
            respSize = Integer.parseInt(line.substring(spaceAfterResponseCode + 1, spaceAfterResponseSize));
        }
        if (QoutAfterResponseSize != -1 && QoutAfterRef != -1) {
            ref = line.substring(QoutAfterResponseSize + 1, QoutAfterRef);
        }
        if (lastQut != -1 && preLastQot != -1) {
            userA = line.substring(preLastQot + 1, lastQut);
        }

        this.ipAddress = line.substring(0, firstSpace);
        this.dateAndTime = LocalDateTime.parse(dateTime, formatter);
        this.httpMethod = method;
        this.path = reqPath;
        this.responseCode = respCode;
        this.responseSize = respSize;
        this.referer = ref;
        this.userAgent = new UserAgent(userA);
    }


    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAddress='" + ipAddress + '\'' +
                ", dateAndTime=" + dateAndTime +
                ", httpMethod=" + httpMethod +
                ", path='" + path + '\'' +
                ", responseCode=" + responseCode +
                ", responseSize=" + responseSize +
                ", referer='" + referer + '\'' +
                ", userAgent=" + userAgent +
                '}';
    }
}
