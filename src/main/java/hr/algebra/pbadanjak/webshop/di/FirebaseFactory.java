package hr.algebra.pbadanjak.webshop.di;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreFactory;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@ApplicationScoped
public class FirebaseFactory {

    private static final String firebaseFirestoreName = "duchan-adfbe";
    private static final String firebaseFirestoreURL = String.format("https://%s.firebaseio.com", firebaseFirestoreName);

    private static final String firebaseProductImageStorageBucket = "duchan-product-images";

    private static FirebaseApp firebaseApp;

    public static void initialize() {}

    static {
        try {
            File firebaseConfigFile = new File("firebase-adminsdk-config.json");
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(firebaseConfigFile)))
                    .setDatabaseUrl(firebaseFirestoreURL)
                    .setStorageBucket(firebaseProductImageStorageBucket)
                    .build();

            firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Produces
    public static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Produces
    public static Firestore provideFirebaseFirestore() {
        return FirestoreClient.getFirestore(firebaseApp);
    }

    @Produces
    public static FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }
}
