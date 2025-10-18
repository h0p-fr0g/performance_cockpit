
public class Main {

    public static void main(String[] args) {

        try {
            ManagePersonalImplementation manager = new ManagePersonalImplementation();

            manager.deleteSalesMan(5);

            manager.close();
        }
        catch(ManagePersonalException e){
            System.out.println(e.getMessage());
        }
    }
}
