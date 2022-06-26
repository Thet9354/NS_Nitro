package com.example.nsapplication;

public class IPPTModel {
    private int id;
    private String vocation;
    private String age;
    private String pushUpPoints;
    private String sitUpPoints;
    private String runPoints;
    private String totalPoints;
    private String status;

    public IPPTModel(int id, String vocation, String age, String pushUpPoints, String sitUpPoints, String runPoints, String totalPoints, String status) {
        this.id = id;
        this.vocation = vocation;
        this.age = age;
        this.pushUpPoints = pushUpPoints;
        this.sitUpPoints = sitUpPoints;
        this.runPoints = runPoints;
        this.totalPoints = totalPoints;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPushUpPoints() {
        return pushUpPoints;
    }

    public void setPushUpPoints(String pushUpPoints) {
        this.pushUpPoints = pushUpPoints;
    }

    public String getSitUpPoints() {
        return sitUpPoints;
    }

    public void setSitUpPoints(String sitUpPoints) {
        this.sitUpPoints = sitUpPoints;
    }

    public String getRunPoints() {
        return runPoints;
    }

    public void setRunPoints(String runPoints) {
        this.runPoints = runPoints;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "IPPTModel{" +
                "id=" + id +
                ", vocation='" + vocation + '\'' +
                ", age='" + age + '\'' +
                ", pushUpPoints='" + pushUpPoints + '\'' +
                ", sitUpPoints='" + sitUpPoints + '\'' +
                ", runPoints='" + runPoints + '\'' +
                ", totalPoints='" + totalPoints + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
