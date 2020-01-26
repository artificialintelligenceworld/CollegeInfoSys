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

public class CreateNewStaff {
    private static final String TAG = "CreateNewStaff";
    private Context mContext;
    private ServerResponseListener mServerResponseListener = null;
    private String staffName;
    private String staffMail;
    private String staffQualification;
    private String staffDealSub;
    private String staffMobile;
    private String staffGender;
    private String profileImg;
    private String staffDept;

    public CreateNewStaff(Context context, final String staffName, final String staffMail, final String staffQualification, final String staffDealSub, final String staffMobile, final String staffGender, final String profileImg, final String staffDept, ServerResponseListener aServerResponseListener) {

        mContext = context;
        this.staffName = staffName;
        this.staffMail = staffMail;
        this.staffQualification = staffQualification;
        this.staffDealSub = staffDealSub;
        this.staffMobile = staffMobile;
        this.staffGender = staffGender;
        this.profileImg = profileImg;
        this.staffDept = staffDept;
        mServerResponseListener = aServerResponseListener;
    }

    public void CreateNewStaff() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        File profileFile = new File(profileImg);
        String fileName = profileFile.getName();
        try {

            String charset = "UTF-8";
            MultipartUtility multipart = new MultipartUtility(Constants.CREATE_NEW_STAF_URL, charset);
            //add parameters
            multipart.addFormField(Constants.STAFF_NAME, staffName);
            multipart.addFormField(Constants.STAFF_MAIL_ID, staffMail);
            multipart.addFormField(Constants.STAFF_QUALIFICATION, staffQualification);
            multipart.addFormField(Constants.STAFF_DEAL_SUB, staffDealSub);
            multipart.addFormField(Constants.STAFF_MOBILE_NUM, staffMobile);
            multipart.addFormField(Constants.STAFF_GENDER, staffGender);
            multipart.addFormField(Constants.STAFF_DEPT, staffDept);

            if (profileImg != null) {
                multipart.addFilePart(Constants.STAFF_PROFILE, fileName);
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
