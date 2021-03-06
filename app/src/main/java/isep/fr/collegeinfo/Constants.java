package isep.fr.collegeinfo;

public class Constants {

    public static final String POST = "POST";
    public static final String GET = "GET";

    public static final String IMAGE_TYPE = "image/*";


    //** Main Url ** //
    private static final String GET_DATA_URL = "http://192.168.43.5:8080/isep/";
    //root url
    private static final String ROOT_URL = GET_DATA_URL + "services/";


    public static final String DATA = "data";

    public static final String LOGIN_USER = ROOT_URL + "login";

    //student related Url
    public static final String CREATE_NEW_STUDENT_URL = ROOT_URL + "createNewStudent";
    public static final String DELETE_STUDENT_URL = ROOT_URL + "deleteUser";
    public static final String UPDATE_STUDENT_URL = ROOT_URL + "updateUserInfo";
    public static final String GET_ALL_STUDENTS_URL = ROOT_URL + "getAllUserInfo";

    //Staff related Url
    public static final String CREATE_NEW_STAF_URL = ROOT_URL + "createNewStaff";
    public static final String DELETE_STAF_URL = ROOT_URL + "deleteStaff";
    public static final String UPDATE_STAF_URL = ROOT_URL + "updateStaffInfo";
    public static final String GET_ALL_STAF_URL = ROOT_URL + "getAllStaffInfo";

    //Staff related Url
    public static final String CREATE_NEW_DEPT_URL = ROOT_URL + "createNewDept";
    public static final String DELETE_DEPT_URL = ROOT_URL + "deleteDept";
    public static final String UPDATE_DEPT_URL = ROOT_URL + "updateDeptInfo";
    public static final String GET_ALL_DEPT_URL = ROOT_URL + "getDepartments";



    //Event related Url
    public static final String CREATE_NEW_EVENT_URL = ROOT_URL + "createNewEvent";
    public static final String DELETE_EVENT_URL = ROOT_URL + "deleteEvent";
    public static final String UPDATE_EVENT_URL = ROOT_URL + "updateEventInfo";
    public static final String GET_ALL_EVENTS_URL = ROOT_URL + "getAllEventInfo";


    private static final String GET_DATA_SERVER_URL = GET_DATA_URL + "static/";
    //img url  for user image,staf image
    public static final String PROFILE_IMG_URL = GET_DATA_SERVER_URL + "user_images/";
    public static final String PROFILE_STAFF_IMG_URL = GET_DATA_SERVER_URL + "staff_images/";

    public static final String EVENT_IMG_URL = GET_DATA_SERVER_URL + "event_images/";
    //documents download url
    public static final String SYL_DOC_DOWNLOAD_URL = GET_DATA_SERVER_URL + "dept_images/";

    public static final String SYL_MAIN_DOC_DOWNLOAD_URL = GET_DATA_SERVER_URL + "dept_images/vouchers.pdf";


    //database create tables
    //DB Table Name
    public static final String APP_ALL_USERS = "app_users_data";


    //WebRequest table variables params
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_MAIL_ID = "user_mail";
    public static final String USER_MOBILE_NUM = "user_mobile";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_GENDER = "user_gender";
    public static final String USER_TYPE = "user_type";
    public static final String USER_COURSE = "user_dept";
    public static final String USER_PROFILE = "user_image";

    //staff info related params
    public static final String STAFF_ID = "staff_id";
    public static final String STAFF_NAME = "staff_name";
    public static final String STAFF_MAIL_ID = "staff_mail";
    public static final String STAFF_MOBILE_NUM = "staff_mobile";
    public static final String STAFF_QUALIFICATION = "staff_qualification";
    public static final String STAFF_DEAL_SUB = "staff_deal_subj";
    public static final String STAFF_GENDER = "staff_gender";
    public static final String STAFF_DEPT = "staff_depart";
    public static final String STAFF_PROFILE = "staff_image";

    //event info related params
    public static final String EVENT_ID = "event_id";
    public static final String EVENT_TITLE = "event_title";
    public static final String EVENT_DATE_TIME = "event_date_time";
    public static final String EVENT_DISCRIPTION = "event_description";
    public static final String EVENT_TYPE = "event_type";
    public static final String EVENT_PLACE = "event_place";
    public static final String EVENT_HALL = "event_hall_num";
    public static final String EVENT_IMAGE = "event_image";

    //department info related params
    public static final String DEPT_ID = "deptId";
    public static final String DEPT_ID_LC = "dept_id";
    public static final String DEPT_NAME = "dept_name";
    public static final String DEPT_SHORT_NAME = "dept_short_name";
    public static final String DEPT_SYLL_DOC = "dept_syll_pdf";
    public static final String DEPT_SUBJECTS = "dept_subjects";



    //SQLITE table variables
    public static final String ID = "autogenerated_id";
    public static final String USER_LOCAL_ID = "user_id";
    public static final String USER_U_NAME = "user_name";
    public static final String USER_MAIL = "user_mail";
    public static final String USER_DEPT = "user_dept";
    public static final String USER_PRFLE_IMG = "user_profile";



    //App Shared prifereance this is a user related table info
    public static final String SP_USER_ID = "user_id";
    public static final String SP_USER_NAME = "user_name";
    public static final String SP_USER_MAIL = "user_mail_id";
    public static final String SP_USER_PASSWORD = "user_pass";
    public static final String SP_USER_TYPE = "user_type";
    public static final String SP_USER_MOBILE = "user_mobile_num";
    public static final String SP_USER_COURSE = "user_course";





    public static final String DOWNLOADS_LC = "Downloads";
    public static final String APPLICATION_TYPE_PDF = "application/pdf";
    public static final String CONTENT = "content://";
    public static final String FILE = "file://";


}
