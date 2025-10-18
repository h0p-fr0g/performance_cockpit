
public class Main {

    public static void main(String[] args) {

        ManagePersonalImplementation manager = null;

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
