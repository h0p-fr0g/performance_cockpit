package org.hbrs.ia.model;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SalesMan {
    private String firstname;
    private String lastname;
    private Integer sid;
    private List<SocialPerformanceRecord> performanceRecords;

    public SalesMan(String firstname, String lastname, Integer sid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sid = sid;
        this.performanceRecords = new ArrayList<>();
    }

    public SalesMan(String firstname, String lastname, Integer sid,  List<SocialPerformanceRecord> performanceRecords) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sid = sid;
        this.performanceRecords = performanceRecords;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getId() {
        return sid;
    }

    public void setId(Integer sid) {
        this.sid = sid;
    }

    public List<SocialPerformanceRecord> getPerformanceRecords() {
        return performanceRecords;
    }

    public SocialPerformanceRecord getPerformanceRecordByYear(int year) {

        return performanceRecords.stream().filter(r -> r.getYear() == year).findFirst().orElse(null);
    }

    public void setPerformanceRecords(List<SocialPerformanceRecord> performanceRecords) {
        this.performanceRecords = performanceRecords;
    }

    public void addPerformanceRecord(SocialPerformanceRecord record) {
        this.performanceRecords.add(record);
    }

    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("firstname" , this.firstname );
        document.append("lastname" , this.lastname );
        document.append("sid" , this.sid);

        List<Document> performanceDocs = this.performanceRecords.stream()
                .map(SocialPerformanceRecord::toDocument)
                .collect(Collectors.toList());

        document.append("performanceRecords", performanceDocs);

        return document;
    }
}
