import org.hbrs.ia.code.ManagePersonal;
import org.hbrs.ia.code.ManagePersonalException;
import org.hbrs.ia.code.ManagePersonalImplementation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) {

        ManagePersonal manager = null;

        try {
            MongoClient client;
            Dotenv dotenv = Dotenv.load();
            String db_uri = dotenv.get("DB_URI");
            String db_name = dotenv.get("DB_NAME");
            client = MongoClients.create(db_uri);
            manager = new ManagePersonalImplementation(client, db_name);
            manager.deleteSalesMan(1);

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
