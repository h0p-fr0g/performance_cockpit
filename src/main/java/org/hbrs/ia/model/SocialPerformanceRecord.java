package org.hbrs.ia.model;

import org.bson.Document;

public class SocialPerformanceRecord {
    private Integer rid;
    private Integer sid;
    private Integer year;
    private Integer leadershipCompetence;
    private Integer openessEmployees;
    private Integer socialBehaviour;
    private Integer attitudeClient;
    private Integer communication;
    private Integer integrity;

    public SocialPerformanceRecord(Integer id, Integer salesmanId, Integer year,
                                   Integer leadershipCompetence, Integer openessEmployees,
                                   Integer socialBehaviour, Integer attitudeClient,
                                   Integer communication, Integer integrity) {
        this.rid = id;
        this.sid = salesmanId;
        this.year = year;
        this.leadershipCompetence = leadershipCompetence;
        this.openessEmployees = openessEmployees;
        this.socialBehaviour = socialBehaviour;
        this.attitudeClient = attitudeClient;
        this.communication = communication;
        this.integrity = integrity;
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

    public Integer getLeadershipCompetence() {
        return leadershipCompetence;
    }

    public void setLeadershipCompetence(Integer leadershipCompetence) {
        this.leadershipCompetence = leadershipCompetence;
    }

    public Integer getOpenessEmployees() {
        return openessEmployees;
    }

    public void setOpenessEmployees(Integer openessEmployees) {
        this.openessEmployees = openessEmployees;
    }

    public Integer getSocialBehaviour() {
        return socialBehaviour;
    }

    public void setSocialBehaviour(Integer socialBehaviour) {
        this.socialBehaviour = socialBehaviour;
    }

    public Integer getAttitudeClient() {
        return attitudeClient;
    }

    public void setAttitudeClient(Integer attitudeClient) {
        this.attitudeClient = attitudeClient;
    }

    public Integer getCommunication() {
        return communication;
    }

    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    public Integer getIntegrity() {
        return integrity;
    }

    public void setIntegrity(Integer integrity) {
        this.integrity = integrity;
    }
    public Document toDocument() {
        Document document = new Document();
        document.append("id", this.rid);
        document.append("salesman", this.sid);
        document.append("year", this.year);
        document.append("leadershipCompetence", this.leadershipCompetence);
        document.append("openessEmployees", this.openessEmployees);
        document.append("socialBehaviour", this.socialBehaviour);
        document.append("attitudeClient", this.attitudeClient);
        document.append("communication", this.communication);
        document.append("integrity", this.integrity);
        return document;
    }
}
