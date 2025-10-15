package org.hbrs.ia.model;

import org.bson.Document;

public class SocialPerformanceRecord {
    private Integer rid;
    private Integer sid;
    private Integer year;
    private Integer socialScore;

    public SocialPerformanceRecord(Integer id, Integer salesmanId, Integer year, Integer socialScore) {
        this.rid = id;
        this.sid = salesmanId;
        this.year = year;
        this.socialScore = socialScore;
    }

    public Integer getID() {
        return this.rid;
    }

    public void setID(Integer id) {
        this.rid = id;
    }

    public Integer getSID() {
        return this.sid;
    }

    public void setSID(Integer sid) {
        this.sid = sid;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSocialScore() {
        return this.socialScore;
    }

    public void setSocialScore(Integer score) {
        this.socialScore = score;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("id", this.rid);
        document.append("salesman", this.sid);
        document.append("year", this.year);
        document.append("socialScore", this.socialScore);
        return document;
    }
}
