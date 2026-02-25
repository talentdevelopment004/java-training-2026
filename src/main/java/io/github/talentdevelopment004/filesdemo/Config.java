package io.github.talentdevelopment004.filesdemo;

public class Config {
    private String username;
    private String password;
    private String dbHost;
    private String dbPort;
    private String dbName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "Config{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dbHost='" + dbHost + '\'' +
                ", dbPort='" + dbPort + '\'' +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
