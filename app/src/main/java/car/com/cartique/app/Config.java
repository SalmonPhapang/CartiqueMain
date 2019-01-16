package car.com.cartique.app;

import java.lang.ref.SoftReference;

/**
 * Created by napster on 2/3/18.
 */

public class Config {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
    public static final String PREF_USER_FIRST_TIME = "PREF_USER_FIRST_TIME";
    public static final String SIGNUP_USER = "SignUp_User";
    public  static final String USER_OBJECT = "User_Oject";
    public  static final String LOCATION_STRING = "Location_String";
    public static final String firebaseStrorage = "gs://cartique-1516308965713.appspot.com/";
    public static final String GENERIC_RESULT_MESSAGE = "Yey! Your Request has been submited\\nCheck Notifiction Panel for updates";
    public static final String LEGACY_KEY = "LegacyKey";
    public static final String NEW_SERVICE_LOG = "New Service has been booked for order ";
    public static final String NOTIFICATION_TOKEN = "NotificationToken";
    public static final String NEW_SCHEDUALED_DATE = "Schedualed date has been set for order ";
    public static final String ACCEPTED_QUOTATION = "Quotation has been Accepted for order ";
    public static final String DECLINED_QUOTATION = "Quotation has been Declined for order ";
}
