package org.hbrs.mongodb.test;

import org.hbrs.ia.code.ManagePersonal;
import org.hbrs.ia.code.ManagePersonalException;
import org.hbrs.ia.code.ManagePersonalImplementation;
import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagePersonalTest {

    private ManagePersonal manager;

    @BeforeEach
    void setup() {
        manager = new ManagePersonalImplementation();
    }

    @Test
    void createSalesman() {
        // test 1
        SalesMan s = new SalesMan("Patrick", "Saft", 90133);
        SocialPerformanceRecord r = new SocialPerformanceRecord(1, 90133, 2019, 1, 1, 1, 1, 1, 1);
        manager.createSalesMan(s);
        manager.addSocialPerformanceRecord(r, s);
        assertEquals(90133, manager.readSalesMan(90133).getId());
        assertEquals(90133 ,manager.readSocialPerformanceRecord(s).get(0).getSID());
        assertEquals(1 ,manager.readSocialPerformanceRecord(s).get(0).getID());
        manager.deleteSalesMan(90133);

        // test 2
        SalesMan s1 = new SalesMan("Patrick", "Saft", 90133);
        manager.createSalesMan(s1);
        assertThrows(ManagePersonalException.class, () -> {
            manager.createSalesMan(s1);
        });
        manager.deleteSalesMan(90133);

        manager.close();
    }

    @Test
    void deleteSalesman() {
        SalesMan s = new SalesMan("Patrick", "Saft", 90133);
        manager.createSalesMan(s);
        manager.deleteSalesMan(90133);
        assertThrows(ManagePersonalException.class, () -> {
            manager.readSalesMan(90133);
        });
        manager.close();
    }
    @Test
    void deleteRecord() {
        SalesMan s = new SalesMan("Patrick", "Saft", 90133);
        manager.createSalesMan(s);
        SocialPerformanceRecord r = new SocialPerformanceRecord(1, 90133, 2019, 1, 1, 1, 1, 1, 1);
        manager.addSocialPerformanceRecord(r, s);
        assertEquals(1, manager.readSocialPerformanceRecord(s).size());
        manager.deleteRecord(90133, 2019);
        assertEquals(0, manager.readSocialPerformanceRecord(s).size());
        manager.deleteSalesMan(90133);
        manager.close();
    }
    @Test
    void deleteAllRecordBySalesman() {
        SalesMan s = new SalesMan("Patrick", "Saft", 90133);
        manager.createSalesMan(s);
        SocialPerformanceRecord r = new SocialPerformanceRecord(1, 90133, 2019, 1, 1, 1, 1, 1, 1);
        manager.addSocialPerformanceRecord(r, s);
        manager.deleteAllRecordsBySalesMan(90133);
        assertEquals(0, manager.readSocialPerformanceRecord(s).size());
        manager.deleteSalesMan(90133);
        manager.close();
    }
}
