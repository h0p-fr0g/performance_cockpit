package org.hbrs.ia.solutions.model;

import org.bson.Document;

import java.util.Objects;

public class SocialPerformanceRecord {
    private String leadershipIST; // better: int!
    private String leadershipSOLL;
    private String opennessIST;
    private String opennessSOLL;
    private String year;

    public String getLeadershipIST() {
        return leadershipIST;
    }

    public void setLeadershipIST(String leadershipIST) {
        this.leadershipIST = leadershipIST;
    }

    public String getLeadershipSOLL() {
        return leadershipSOLL;
    }

    public void setLeadershipSOLL(String leadershipSOLL) {
        this.leadershipSOLL = leadershipSOLL;
    }

    public String getOpennessIST() {
        return opennessIST;
    }

    public void setOpennessIST(String opennessIST) {
        this.opennessIST = opennessIST;
    }

    public String getOpennessSOLL() {
        return opennessSOLL;
    }

    public void setOpennessSOLL(String opennessSOLL) {
        this.opennessSOLL = opennessSOLL;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append( Constants.YEAR , this.year );
        document.append("leadershipSOLL" , this.leadershipSOLL );
        document.append("leadershipIST" , this.leadershipIST );
        document.append("opennessSOLL" , this.opennessSOLL);
        document.append("opennessIST" , this.opennessIST);
        return document;
    }

    public class Constants {
        public static final String LEADERSHIPIST = "leadershipIST";
        public static final String LEADERSHIPSOLL = "leadershipSOLL";
        public static final String OPENNESSIST = "opennessIST";
        public static final String OPENNESSSOLL = "opennessSOLL";
        public static final String YEAR = "year";
        public static final String KEY_OF_RECORD = "record";
    }

    @Override
    public String toString() {
        return "SocialPerformanceRecord{" +
                "leadershipIST='" + leadershipIST + '\'' +
                ", leadershipSOLL='" + leadershipSOLL + '\'' +
                ", opennessIST='" + opennessIST + '\'' +
                ", opennessSOLL='" + opennessSOLL + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialPerformanceRecord record = (SocialPerformanceRecord) o;
        return Objects.equals(leadershipIST, record.leadershipIST) && Objects.equals(leadershipSOLL, record.leadershipSOLL) && Objects.equals(opennessIST, record.opennessIST) && Objects.equals(opennessSOLL, record.opennessSOLL) && Objects.equals(year, record.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leadershipIST, leadershipSOLL, opennessIST, opennessSOLL, year);
    }
}
