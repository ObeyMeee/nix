package ua.com.andromeda.model;

import java.time.LocalDateTime;

public class UserInfo {
    private String ip;
    private String userAgent;
    private LocalDateTime localDateTime;

    public UserInfo(String ip, String userAgent, LocalDateTime localDateTime) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.localDateTime = localDateTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
