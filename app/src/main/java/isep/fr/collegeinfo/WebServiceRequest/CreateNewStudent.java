package isep.fr.collegeinfo.WebServiceRequest;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import isep.fr.collegeinfo.Constants;
import isep.fr.collegeinfo.WebServiceUtil.MultipartUtility;
import isep.fr.collegeinfo.WebServiceUtil.ServerResponseListener;

public class CreateNewStudent {
    private static final String TAG = "CreateNewStudent";
    private String charset = "UTF-8";
    Context mContext;
    private ServerResponseListener mServerResponseListener = null;
    private String userID;
    private String username;
    private String userMail;
    private String password;
    private String userType;
    private String userMobile;
    private String userGender;
    private String profileImg;
    private String userCourse;

    public CreateNewStudent(Context context, final String userID, final String username, final String userMail, final String password, final String userType, final String userMobile, final String userGender, final String profileImg, final String userCourse, ServerResponseListener aServerResponseListener) {

        mContext = context;
        this.userID = userID;
        this.username = username;
        this.userMail = userMail;
        this.password = password;
        this.userType = userType;
        this.userMobile = userMobile;
        this.userGender = userGender;
        this.profileImg = profileImg;
        this.userCourse = userCourse;
        mServerResponseListener = aServerResponseListener;
    }

    public void CreateNewStudent() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        File profileFile = new File(profileImg);
        String fileName = profileFile.getName();
        try {

            MultipartUtility multipart = new MultipartUtility(Constants.CREATE_NEW_STUDENT_URL, charset);
            //add parameters
            multipart.addFormField(Constants.USER_ID, userID);
            multipart.addFormField(Constants.USER_NAME, username);
            multipart.addFormField(Constants.USER_MAIL_ID, userMail);
            multipart.addFormField(Constants.USER_PASSWORD, password);
            multipart.addFormField(Constants.USER_TYPE, userType);
            multipart.addFormField(Constants.USER_MOBILE_NUM, userMobile);
            multipart.addFormField(Constants.USER_GENDER, userGender);
            multipart.addFormField(Constants.USER_COURSE, userCourse);

            if (profileImg != null) {
                multipart.addFilePart(Constants.USER_PROFILE, fileName);
            }

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            long totalBytesRead = 0;

            FileInputStream inputStream = new FileInputStream(profileFile);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                multipart.writeFileBytes(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

            }

            inputStream.close();
            List<String> response = multipart.finish();

            for (String line : response) {

                if (line != null) {

                    try {

                        JSONObject obj = new JSONObject(line);
                        Log.d(TAG, "chat message response receiver image " + String.valueOf(obj));

                        String status = obj.getString("status");

                        if (status.equalsIgnoreCase("0")) {

                            mServerResponseListener.onResponseData(obj);

                        } else {
                            mServerResponseListener.onResponseData(obj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d(TAG, "image sending response error " + e);
                        JSONObject obj = new JSONObject();
                        obj.put("status", 1);
                        obj.put("message", e.toString());

                        mServerResponseListener.onResponseData(obj);

                    }
                }

            }
        } catch (Exception ex) {
            System.err.println(ex);
            Log.d(TAG, "image sending error " + ex);
            // sending responce success or not ....
            mServerResponseListener.onResponseData(ex);

        }
    }
}
