package com.example.ldrp;

public class putPDF {

    String semester;
    String department;
    String topic;
    String url;
    String name;

    public putPDF() {
    }

    public putPDF(String semester, String department, String topic, String url, String name) {
        this.semester = semester;
        this.department = department;
        this.topic = topic;
        this.url = url;
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
