import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

public class Main {

    public static void main(String[] args) {

        ManagePersonalImplementation manager = new ManagePersonalImplementation();
        SalesMan martin = new SalesMan("Martin", "Braun", 4);

        manager.createSalesMan(martin);
        SocialPerformanceRecord record = new SocialPerformanceRecord(50,4,2025,50);

        manager.addSocialPerformanceRecord(record, martin);
    }
}
