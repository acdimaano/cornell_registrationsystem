package com.genericregistrationsystem;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is for the DoctorList Activity.
 * Showing the doctors lists.
 *
 * Created by Alan C. Dimaano on 8/4/2015.
 * 
 */
public class DoctorListActivity extends ActionBarActivity
{
    //Protected instance variables
    protected ProgressDialog mProgressDialog;
    protected HttpParams httpParameters = new BasicHttpParams();
    protected ListView listView;
    protected InputStream inputStream = null;
    protected StringBuilder stringBuilder = null;
    protected String result;
    protected JSONArray jArray;
    protected JSONObject jsonObject;

    //Private variables
    private ArrayList<String> specialtyList = new ArrayList<String>();
    private SQLiteDatabase mydb;
    private static final String DATABASE_NAME = "genericregistrationsystem.db";
    private String DB_USER = "tbuser";

    private int timeoutConnection = 5000;

    private int iSpecialty = -1;
    private int iPatientOid = -1;
    private String strDrOid = "";
    private ArrayList<Integer> doctorOids = new ArrayList<Integer>();

    /**
     * This is the onCreate method.
     * A native method of Android Activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        // use your custom layout
        listView = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            iPatientOid = bundle.getInt("patientOid");
            iSpecialty = bundle.getInt("specialty");
            Log.i("DoctorListActivity iPatientOid = ", "" + iPatientOid);
            Log.i("DoctorListActivity strSpecialty = ", "" + iSpecialty);
        }

        // get doctor list
        GetDoctorList doctorList = new GetDoctorList();
        doctorList.execute(specialtyList);
        specialtyList = getSpecialistRecords();

        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Symbian", "Alcatel", "Xiaomi"};

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < specialtyList.size(); ++i) {
            list.add(specialtyList.get(i));
            Log.i("onCreate - specialtyList -- " + i, specialtyList.get(i));
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, specialtyList);

        //setListAdapter(adapter);
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                //String values[] = itemValue.split("|");
                Log.i("Doctor OID = ", Integer.toString(doctorOids.get(position)));
                Log.i("itemValue = ", itemValue);
                Log.i("iPatientOid OID = ", "" + iPatientOid);
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position : "+itemPosition+"  Doctor OID : " + Integer.toString(doctorOids.get(position)) + " Patient OID : " + iPatientOid , Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

    /**
     * Inner class for StableArrayAdapter for the listview lists
     */
    private class StableArrayAdapter extends ArrayAdapter<String> {

        private Activity context = null;
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Activity context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            for (int i = 0; i < objects.size(); ++i) {
                Log.i("test = ", objects.get(i));
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public void tvBckSpecialty(View v)
    {
        Intent BackSpecialty = new Intent(this, SpecialtyActivity.class);
        startActivity(BackSpecialty);
    }
    /**
     * This is the about activity link.
     * @param view
     */
    public void tvAbout(View view)
    {
        Intent About = new Intent(this, AboutActivity.class);
        startActivity(About);
    }
    /**
     * This is the help activity link
     * @param view
     */
    public void tvHelp(View view)
    {
        Intent Help = new Intent(this, ContactUsActivity.class);
        startActivity(Help);
    }
    /**
     * This is the contact activity link
     * @param view
     */
    public void tvContact(View view)
    {
        Intent Contact = new Intent(this, ContactUsActivity.class);
        startActivity(Contact);
    }
    /**
     * This is the Overview activity link
     * @param view
     */
    public void tvOverview(View view)
    {
        Intent Overview = new Intent(this, OverviewActivity.class);
        startActivity(Overview);
    }
    /**
     * This is the onCreateOptionMenu for the menu item.
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_subpages, menu);

        return super.onCreateOptionsMenu(menu);
    }
    /**
     * This is for the option menu item selected
     * functionality.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //code to handle on press action to the icon in action bar
        switch(item.getItemId()){
            case R.id.icon_home:
                Intent Home = new Intent(this, MainActivity.class);
                startActivity(Home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Inner classes, Asynchronours call
    private class GetDoctorList extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {

        private final String TAG = "DoctorListActivity Class";
        private String exeption_triggered = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DoctorListActivity.this);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            // Set progressdialog title
            mProgressDialog.setTitle("Synching");
            // Set progressdialog message
            mProgressDialog.setMessage("Checking Specialist Data ....");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... params) {
            // TODO Auto-generated method stub

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            try {
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost("http://genericregistrationsystem.co.nf/api/get_specialist_list.php");
                JSONObject json = new JSONObject();

                json.put("record1", "");

                JSONArray postjson = new JSONArray();
                postjson.put(json);
                Log.e(TAG, "request :" + postjson);

                httppost.setHeader("json", json.toString());
                httppost.getParams().setParameter("jsonpost", postjson);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();
                if (inputStream != null) {

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(reader.readLine() + "\n");
                    String line = "0";

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    inputStream.close();
                    result = stringBuilder.toString();
                    if (result != null) {
                        //Establish DB connection for transaction
                        mydb = openOrCreateDatabase(DATABASE_NAME,
                                Context.MODE_PRIVATE, null);
                        //Delete current content of tbuser table
                        //to make a fresh record list
                        mydb.execSQL("delete from "+ DB_USER);
                        Log.e(TAG, "Login Result :" + result);
                        jsonObject = new JSONObject(result);
                        jArray = jsonObject.getJSONArray("posts");
                        JSONObject jsonItems = new JSONObject();
                        ContentValues newRecord = new ContentValues();

                        long result = -2;
                        for (int i = 0; i < jArray.length(); i++) {
                            jsonItems = jArray.getJSONObject(i);
                            String oid = jsonItems.getString("oid");
                            newRecord.put("oid",oid);

                            String first_name = jsonItems.getString("first_name");
                            newRecord.put("first_name", first_name);

                            String middle_name = jsonItems.getString("middle_name");
                            newRecord.put("middle_name", middle_name);

                            String last_name = jsonItems.getString("last_name");
                            newRecord.put("last_name", last_name);

                            String specialty = jsonItems.getString("specialty");
                            newRecord.put("specialty", specialty);

                            String room_no = jsonItems.getString("room_no");
                            newRecord.put("room_no", room_no);

                            String time_avail = jsonItems.getString("time_avail");
                            newRecord.put("time_avail", time_avail);

                            String days_avail = jsonItems.getString("days_avail");
                            newRecord.put("days_avail", days_avail);

                            String details = "Dr." + first_name + " " + ((middle_name.isEmpty() || middle_name == null) ? "" : middle_name) + " " + last_name + "\n" +
                                             "Specialty : " + specialty + "\n" +
                                             "Room No : " + room_no + "\n" +
                                             "Day(s) Available : " + days_avail + "\n" +
                                             "Time(s) Available " + time_avail + "\n";
                            params[0].add(details);
                            Log.i("first_name = ", first_name + " oid = " + oid);
                            result = mydb.insert(DB_USER, null, newRecord);
                            Log.i("Insert Result : ", "" + result);
                        }
                        mydb.close();
                    }

                }

            } catch (Exception e) {
                exeption_triggered = e.toString();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(ArrayList<String> specialtyList) {
            //super.onPostExecute(specialtyList);
            for (int i = 0; i < specialtyList.size(); ++i) {

                Log.i("specialtyList -- " + i, specialtyList.get(i));
            }
            setSpecialists(specialtyList);
            mProgressDialog.dismiss();
        }
    }

    private String checkValue(String strValue){
        return (strValue.isEmpty() || strValue == null || strValue.contains("null")) ? "" : strValue;
    }

    private void setSpecialists(ArrayList<String> specialtyList){
        this.specialtyList = specialtyList;
    }

    private ArrayList<String> getSpecialistRecords() {
        ArrayList<String> specialtyList = new ArrayList<String>();
        mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor doctorRecords = null;
        String strSpecialty = "";
        String strDoctor = "";
        String strDays = "";
        String strTime = "";

        Log.i("SPECIALTY = ", "" + iSpecialty);
        try {
            if (iSpecialty == 1){
                strSpecialty = "neuro";
                strDoctor = "Neurologist";
            }
            if (iSpecialty == 2){
                strSpecialty = "cardio";
                strDoctor = "Cardiologist";
            }
            if (iSpecialty == 3){
                strSpecialty = "int";
                strDoctor = "Internist";
            }
            if (iSpecialty == 4){
                strSpecialty = "natmed";
                strDoctor = "Natural Medicine";
            }
            if (iSpecialty == 5){
                strSpecialty = "dentist";
                strDoctor = "Dentist";
            }
            if (iSpecialty == 6){
                strSpecialty = "lab";
                strDoctor = "Laboratory";
            }
            if (iSpecialty == 7){
                strSpecialty = "ortho";
                strDoctor = "Orthopedic";
            }
            if (iSpecialty == 8){
                strSpecialty = "pulmo";
                strDoctor = "Pulmonologist";
            }
            if (iSpecialty == 9){
                strSpecialty = "ob";
                strDoctor = "OB Gyne";
            }
            if (iSpecialty == 10){
                strSpecialty = "pedia";
                strDoctor = "Pediatrician";
            }
            if (iSpecialty == 11){
                strSpecialty = "fammed";
                strDoctor = "Family Medicine";
            }
            if (iSpecialty == 12){
                strSpecialty = "eye";
                strDoctor = "Eye Specialist";
            }

            String query = "SELECT * FROM tbuser where specialty = '" + strSpecialty + "'";
            Log.i("QUERY = ", query);
            doctorRecords = mydb.rawQuery(query, null);
        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }
        if (doctorRecords != null) {
            if (doctorRecords.getCount() > 0) {
                while (doctorRecords.moveToNext()) {

                    if(doctorRecords.getString(8).contains("m")){
                        strDays = strDays + "Mon ";
                    }
                    if(doctorRecords.getString(8).contains("tu")){
                        strDays = strDays + "Tues ";
                    }
                    if(doctorRecords.getString(8).contains("w")){
                        strDays = strDays + "Wed ";
                    }
                    if(doctorRecords.getString(8).contains("th")){
                        strDays = strDays + "Thu ";
                    }
                    if(doctorRecords.getString(8).contains("f")){
                        strDays = strDays + "Fri ";
                    }
                    if(doctorRecords.getString(8).contains("s")){
                        strDays = strDays + "Sat ";
                    }

                    if(doctorRecords.getString(7).contains("am")){
                        strTime = "8am to 12nn";
                    }
                    if(doctorRecords.getString(7).contains("pm")){
                        strTime = "1pm to 5pm";
                    }
                    if(doctorRecords.getString(7).contains("fm")){
                        strTime = "9am to 4pm";
                    }

                    Log.i("Record ", doctorRecords.getString(0) + " OID = " + doctorRecords.getString(1) + " " + doctorRecords.getString(2));
                    String strRecord = "\nDr. " + doctorRecords.getString(2) + " " + doctorRecords.getString(3) + " " + checkValue(doctorRecords.getString(4)) +
                            "\nSpecialty : " + strDoctor +
                            "\nRoom No : " + doctorRecords.getString(6) +
                            "\nTime : " + strTime +
                            "\nDays : " + strDays + "\n";
                    doctorOids.add(Integer.parseInt(doctorRecords.getString(1)));
                    //strDrOid = doctorRecords.getString(1);
                    specialtyList.add(strRecord);
                }
            }
        }
        doctorRecords.close();
        mydb.close();
        return specialtyList;
    }


}
