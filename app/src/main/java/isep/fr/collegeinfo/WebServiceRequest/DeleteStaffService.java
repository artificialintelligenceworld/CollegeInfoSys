package isep.fr.collegeinfo.WebServiceRequest;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import isep.fr.collegeinfo.Constants;
import isep.fr.collegeinfo.WebServiceUtil.ServerResponseListener;
import isep.fr.collegeinfo.WebServiceUtil.VolleySigleton;
import isep.fr.collegeinfo.model.StudentModel;

public class DeleteStaffService {

    private static final String TAG = "CheckUser";
    Context mContext;
    private ServerResponseListener mServerResponseListener = null;
    private StudentModel mRegisterDetailsResponseData;
    private String staffId;
    /*private String staffName;
    private String staffDept;*/

    public DeleteStaffService(Context context, final String staffId, ServerResponseListener aServerResponseListener) {

        mContext = context;
        this.staffId = staffId;
       /* this.staffName = staffName;
        this.staffDept = staffDept;*/
        mServerResponseListener = aServerResponseListener;
    }

    public void DeleteStaffService() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.DELETE_STAF_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with response string
                        Log.d(TAG, "print responce String" + String.valueOf(response));

                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("status");

                            if (status.equals("0")) {

                                mServerResponseListener.onResponseData(object);

                            } else {
                                mServerResponseListener.onResponseData(object);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.d(TAG, "image sending response error " + e);
                            JSONObject obj = new JSONObject();

                            try {
                                obj.put("status", 1);
                                obj.put("message", e.toString());
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }

                            mServerResponseListener.onResponseData(obj);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when get error
                        Log.e(TAG, error.toString());
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(Constants.STAFF_ID, staffId);
                /*params.put(Constants.STAFF_NAME, staffName);
                params.put(Constants.STAFF_DEPT, staffDept);*/
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response.headers == null) {
                    // cant just set a new empty map because the member is final.
                    response = new NetworkResponse(
                            response.statusCode,
                            response.data,
                            Collections.<String, String>emptyMap(), // this is the important line, set an empty but non-null map.
                            response.notModified,
                            response.networkTimeMs);


                }

                return super.parseNetworkResponse(response);
            }
        };

        // Add StringRequest to the RequestQueue
        VolleySigleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }
}