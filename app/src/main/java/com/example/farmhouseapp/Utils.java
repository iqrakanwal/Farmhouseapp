package com.example.farmhouseapp;

import static java.lang.String.format;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;


import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;



public class Utils {

    public static final String UID = "uid";
    public static final String SYNDICATE = "ffffjs";
    public static final long MESSAGE_READER_CALLBACK_DELAY_SECONDS = 1;
    public static final int SENT = 2;
    public static final String NAME = "a";
    public static final String DESC = "d";
    public static final long TASK_CANCEL_POLICY = 30; // MINUTES
    private static final String Ads = "ads";
    public static final String ACTIVITY_LAUNCH_CODES = "activityLaunch";
    public static final int NONE = -1;
    public static final int EDIT_MODE = 2;
    public static final int SELECT_TASK_FOR_HIRING = 45;
    public static final String SKILL_TYPE = "sdkfj";
    public static final String WORKER = "worker";
    public static final String MAP_ARGUMENT = "mapArgument";
    public static final int LOCATION_REQUEST = 0;
    public static final String DATA = "data";
    public static final String LAUNCH_REQUEST_VIEW = "RSL";
    public static final CharSequence LAUNCH_SINGLE_CHAT = "singleChat";
    public static final long SEARCH_DELAY = 15;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 234;
    private static final String MESSAGES = "msgs";
    private static final String CATEGORIES = "x";
    private static final String ALL = "a";
    private static final String UPDATED = "u";
    private static final String TOTAL_CATEGORY_COUNTER = "ttl";

    // image manipulation

    public static final int SENDING = 1;
    private static final int IMAGE_QUALITY = 90; // %
    private static final String LAST_SEEN = "ls";
    private static final String ONLINE_NOW = "on";
    private static final String REQUESTS = "req";
    private static final String BIDS = "bds";
    private static final String REVIEWS = "rev";
    private static final String REVIEWS_COUNTER = "rc";
    private static final String FAVORITES = "fvs";
    private static final String TOTAL_TASKS_COMPLETED = "ttc";
    private static final String NEW_REQ = "nr";
    public static final int ROUTE = 0xfaf;
    public static final int TRANSIT_NAVIGATION = 0xafa;
    @org.jetbrains.annotations.Nullable
    public static final String APP_SETTINGS = "9ray";
    @NotNull
    public static final String ENGINE_ACTIVE = "engineActive";

    public static int spToPx(Resources resources, int pixels) {
        return Math.round(resources.getDisplayMetrics().scaledDensity * pixels);
    }

    public static int dpToPx(Resources resources, int pixels) {
        return Math.round(resources.getDisplayMetrics().density * pixels);
    }


    /**
     * meters to kms [string]
     *
     * @param distance
     * @return
     */
    public static String formatDistance(float distance) {
        String distanceStr = "Distance unknown";
        if (distance >= 0) {
            float distanceInKm = distance / 1000F;
            String formateed = format(Locale.US, "%.2f", distanceInKm);
            distanceStr = formateed + " km";
        }
        return distanceStr;

    }

    public static Location getLocationInstance(double lat, double longi) {
        Location targetLocation = new Location("");//provider name is unnecessary
        targetLocation.setLatitude(lat);//your coords of course
        targetLocation.setLongitude(longi);
        return targetLocation;
    }

    public static boolean isGooglePlayServicesAvailable(Activity activity, int code) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, code).show();
            }
            return false;
        }
        return true;
    }



    public static void enableEverything(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * @param versionCode Build.VERSION_CODES.HONEYCOMB
     */
    public static boolean isApiGreaterThanOrEqualTo(int versionCode) {
        return (Build.VERSION.SDK_INT >= versionCode);
    }

    public static Date currentDateTime() {
        Date currentDate = null;
        try {
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            String dateInString = new SimpleDateFormat("EEEE, dd/MM/yyyy/hh:mm:ss")
                    .format(cal.getTime());
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd/MM/yyyy/hh:mm:ss");
            currentDate = formatter.parse(dateInString);
        } catch (Exception ex) {
            Log.i(TAG, "currentDateTime:  exception due to ", ex);
        }
        return currentDate;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }


    public static class Refs {

        private static final String WORKERS_QUEUE = "wq";
        private static final String ACTIVE_WORKERS = "aw";
        private static final String WORKER = "W";

        public static String userAccount(String phone) {
            return USERS + '/' + phone + '/' + ACCOUNT + '/';
        }

        public static String userAccountField(String phone, String field) {
            return USERS + '/' + phone + '/' + ACCOUNT + '/' + field + '/';
        }

        public static String userChatsRoot(String phone) {
            return USERS + '/' + phone + '/' + CHATS + '/';
        }

        public static String userChatsMessages(String phone) {
            return USERS + '/' + phone + '/' + CHATS + '/' + MESSAGES + '/';
        }

        public static String userChatMessagesWithSpecificUser(String phone, String specificUser) {
            return USERS + '/' + phone + '/' + CHATS + '/' + MESSAGES + '/' + specificUser + '/';
        }

        public static String userChatListUser(String sourcePhone, String destPhone) {
            return USERS + '/' + sourcePhone + '/' + CHATS + '/' + USERS + '/' + destPhone + '/';
        }

        public static String userChatListRoot(String phone) {
            return USERS + '/' + phone + '/' + CHATS + '/' + USERS + '/';
        }

        public static String userDpImage(String phone) {
            return "users/" + phone + "/dp";
        }

        public static String usersMessageImageStore(String user) {
            return "commons/images/" + user + '/' + System.currentTimeMillis() + new SecureRandom().nextInt(1000);
        }

        public static String getUserLocation(String userPhone) {
            return USERS + '/' + userPhone + '/' + Utils.LOC + '/';
        }

        public static String getAllCatRefs() {
            return CATEGORIES + "/" + ALL;
        }

        public static String getUpdatedCatRefs() {
            return CATEGORIES + "/" + UPDATED;
        }

        public static String getCategoryIdRef(Integer id) {
            return CATEGORIES + "/" + ALL + "/" + id + "/";
        }

        public static String getTotalCategoryField() {
            return CATEGORIES + "/" + TOTAL_CATEGORY_COUNTER;
        }

        public static String getUserSkillsRef(String user) {
            return USERS + "/" + user + "/" + CATEGORIES + "/";
        }

        public static String getUserSpecificSkillRef(String id, String phone) {
            return USERS + "/" + phone + "/" + CATEGORIES + "/" + id;
        }

        public static String getCategoryImageRef(String id) {
            return "commons/categoryPics/_" + id + ".png";
        }

        public static String getWorkersQueueRef() {
            return WORKERS_QUEUE + '/';
        }

        public static String getWorkerReferenceInWorkerQueue(String city, long counter) {
            return WORKERS_QUEUE + '/' + city + '/' + WORKER + "-" + counter;
        }

        public static String getActiveWorkerInQueueCounter(String city) {
            return WORKERS_QUEUE + '/' + city + '/' + ACTIVE_WORKERS;
        }

        public static String getLastSeenRef(String phone) {
            return USERS + '/' + phone + '/' + ACCOUNT + '/' + Utils.LAST_SEEN;
        }

        public static String getOnlineRef(String phone) {
            return USERS + '/' + phone + '/' + ACCOUNT + '/' + Utils.ONLINE_NOW;
        }

        public static String getRequestsRef(String phone, String target) {
            return USERS + '/' + phone + '/' + Utils.REQUESTS + '/' + target;
        }


        public static String getUserReviewRefFroSpecificUser(String phone, String targetPhone) {
            return USERS + '/' + phone + '/' + Utils.REVIEWS + '/' + targetPhone;
        }

        public static String getUserReviewsRef(String source) {
            return USERS + '/' + source + '/' + REVIEWS;
        }

        public static String getUserTotalReviewsCounter(String source) {
            return USERS + '/' + source + '/' + REVIEWS + '/' + Utils.REVIEWS_COUNTER;
        }

        public static String getUserFavoritesRef(String favAdder) {
            return USERS + '/' + favAdder + '/' + Utils.FAVORITES;
        }

        public static String getSpecificUserFavoriteRef(String source, String targeg) {
            return USERS + '/' + source + '/' + Utils.FAVORITES + '/' + targeg;
        }

        public static String getTotalTasksCompletedRef(String source) {
            return USERS + '/' + source + '/' + Utils.TOTAL_TASKS_COMPLETED;
        }


    }


    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static String decodeDatabaseReference(DatabaseReference reference) {
        String refInDbs = null;
        try {
            final String urlEncodedRef = reference.toString().substring(FirebaseDatabase.getInstance().getReference().toString().length());
            refInDbs = URLDecoder.decode(urlEncodedRef, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.i(TAG, "deoceDatabaseDatabaseReference: ", e);
        }
        return refInDbs;
    }

    public static String storageRefToUrl(StorageReference reference) {
        return reference.toString();
    }


    public static final String LOC = "loc";
    public static final String ACCOUNT = "acc";
    public static final String DUMMY_REF = "dummyRef";
    public static final String IS_TASKER = "i";
    public static final String PHONE = "p";
    public static final String PASSWORD = "ps";
    public static final String EMAIL = "e";
    public static final String USER_NAME = "n";
    public static final String SECURITY_QUESTION = "sq";
    public static final String SECURITY_QUESTION_ANSWER = "sa";
    public static final String USERS = "uz";

    public static long ONE_MEGABYTE = 1024 * 1024;
    public static final int DEBUG = 0;
    public static final int RELEASE = 1;
    public static int MODE = DEBUG;
    public static final int SINGLE = 0xca;
    public static final int REPEAT = 0xabc2;
    public static final int FAIL = -43;
    public static final int COMPLETED = -45;
    public static final int CODE_SENT = -345;
    public static final int STARTED = 45;
    public static final String CHATS = "cts";

    public static final String MESSAGE_STORE_NAME = "com.kadogul.hrabachy";
    public static final int MESSAGE_STORE_VERSION = 1;
    public static final String MESSAGE_STORE_TABLE = "zzbx";
    public static final int TEXT = 0;
    public static final int IMAGE = 1;

    public static final String IMAGE_ = "jf";
    public static final String PAY_LOAD_TYPE = "jdl";
    public static final String DATE = "dt0";
    public static String TAG = "UTILS";
    public static final String MESSAGE_SENDER = "ms";
    public static final String MESSAGE_RECEIVER = "mr";
    public static final String PAY_LOAD = "pl";
    public static final String USER = "sjv.com.user";

    public static void loadGifDrawable(Context context, ImageView imageView, int id) {
        Glide.with(context).asGif().load(id).into(imageView);

    }

    public static Typeface loadTypeFace(Context context, String name) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + name);
    } // load TypeFace ...


    public static <T extends TextView> void applyTypeFace(Typeface typeface, T... views) {
        for (T view : views) {
            view.setTypeface(typeface);
        }
    }

    public static void log(String tag, String msg, Exception error) {
        if (MODE == DEBUG) {
            Log.i(tag, "log: " + msg, error);
        }
    }



    public static void hideView(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void unHideView(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void enableView(View... views) {
        for (View view : views) {
            if (view != null) {
                view.setEnabled(true);
            }
        }
    }

    public static void disableView(View... views) {
        for (View view : views) {
            if (view != null) {
                view.setEnabled(false);
            }
        }
    }







    public static void snackBar(Activity activity, String text) {
        Snackbar.make(activity.findViewById(android.R.id.content), text,
                Snackbar.LENGTH_SHORT).show();
    }

    public static void snackBar(Activity activity, int resId) {
        Snackbar.make(activity.findViewById(android.R.id.content), resId,
                Snackbar.LENGTH_SHORT).show();
    }









    public static void loadDpImage(Context context, Uri uri) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("stub");
    }

    @SuppressLint("WrongThread")
    @WorkerThread
    public static byte[] bmpToArr(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.WEBP, Utils.IMAGE_QUALITY, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            bmp.recycle();
        } catch (Exception ex) {
        }
        return byteArray;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int stretch_width = Math.round((float) width / (float) reqWidth);
        int stretch_height = Math.round((float) height / (float) reqHeight);

        if (stretch_width <= stretch_height)
            return stretch_height;
        else
            return stretch_width;
    }


    public static class Wrapper<T> {
        public T obj;

        public Wrapper(T obj) {
            this.obj = obj;
        }

        public Wrapper() {
        }

        public T getObj() {
            return obj;
        }

        public T setObj(T obj) {
            this.obj = obj;
            return this.obj;
        }
    }


    //just for fun...
//    public static boolean isDebug(Runnable ss) {
//        ConsumeAndReturn<Boolean> a = (s) -> {ss.run(); return s;};
//        return (BuildConfig.DEBUG ? a.accept(true): false);
//    }

    public static <T> boolean isDebug(Runnable ss) {
        if (BuildConfig.DEBUG) {
            ss.run();
            return true;
        } else {
            return false;
        }
    }

    public static boolean log(String tag, String message) {
        return isDebug(() -> Log.i(tag, message));
    }




    /**
     * make sure both implements serializable
     *
     * @param <T>
     * @param <V>
     */
    public static class TypeTwoWrapper<T, V> implements Serializable {
        public final T type1;
        public final V type2;

        public TypeTwoWrapper(T obj1, V obj2) {
            this.type1 = obj1;
            this.type2 = obj2;
        }

        public boolean equals(Object other) {
            if (!(other instanceof TypeTwoWrapper)) {
                return false;
            }
            TypeTwoWrapper anotherWrapper = (TypeTwoWrapper) other;
            return type1.equals(anotherWrapper.type1) && type2.equals(anotherWrapper.type2);
        }
    }





    public static void launchAppSettingsActivity(Activity context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
        context.finish();
    }


}
