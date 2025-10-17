import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

public class Main {

    public static void main(String[] args) {

        try {
            ManagePersonalImplementation manager = new ManagePersonalImplementation();

            manager.deleteSalesMan(5);
        }
        catch(ManagePersonalException e){
            System.out.println(e.getMessage());
        }
    }
}
