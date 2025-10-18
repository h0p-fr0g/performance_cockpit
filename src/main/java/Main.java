import org.hbrs.ia.code.ManagePersonal;
import org.hbrs.ia.code.ManagePersonalException;
import org.hbrs.ia.code.ManagePersonalImplementation;

public class Main {

    public static void main(String[] args) {

        ManagePersonal manager = null;

        try {

            manager = new ManagePersonalImplementation();
            manager.deleteSalesMan(5);

        }
        catch(ManagePersonalException e){
            System.out.println(e.getMessage());
        }
        finally {
            if (manager != null) {
                manager.close();
            }
        }
    }
}
