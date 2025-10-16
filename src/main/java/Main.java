import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

public class Main {

    public static void main(String[] args) {

        try {
            ManagePersonalImplementation manager = new ManagePersonalImplementation();
            SalesMan martin = new SalesMan("Martin", "Braun", 5);

            manager.createSalesMan(martin);
            SocialPerformanceRecord record = new SocialPerformanceRecord(50,5,2026,50);

            manager.addSocialPerformanceRecord(record, martin);
        }
        catch(ManagePersonalException e){
            System.out.println(e.getMessage());
        }
    }
}
