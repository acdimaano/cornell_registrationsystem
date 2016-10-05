package com.genericregistrationsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

/**
 * Created by Rhiza roque on 8/3/2015.
 */
public class NewPatientActivity extends ActionBarActivity
{
    protected ProgressDialog mProgressDialog;
    protected HttpParams httpParameters = new BasicHttpParams();
    protected ListView listView;
    protected InputStream inputStream = null;
    protected StringBuilder stringBuilder = null;
    protected String result;
    protected JSONArray jArray;
    protected JSONObject jsonObject;

    private ArrayList<String> specialtyList = new ArrayList<String>();
    private SQLiteDatabase mydb;
    private static final String DATABASE_NAME = "genericregistrationsystem.db";
    private String DB_USER = "tbuser";
    private String DB_CUSTOMER = "tbcustomerdetails";


    private int timeoutConnection = 5000;

    private EditText txtFName;
    private EditText txtLName;
    private EditText txtHomeAdd;
    private EditText txtPatPhone;
    private EditText txtNHI;
    private String strGender;
    private String strResult;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    /**
     * This is the onCreate method.
     * A native method of Android Activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        txtFName = (EditText) findViewById(R.id.txtFName);
        txtLName = (EditText) findViewById(R.id.txtLName);
        txtHomeAdd = (EditText) findViewById(R.id.txtHomeAdd);
        txtPatPhone = (EditText) findViewById(R.id.txtPatPhone);
        txtNHI = (EditText) findViewById(R.id.txtNHI);

        radioSexGroup = (RadioGroup) findViewById(R.id.gender);


    }

    public void btnNewSubmit(View view)
    {
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        Log.i("Radio button = ", "" + radioSexButton.getText());

        //Set gender value
        if(radioSexButton.getText().toString().equalsIgnoreCase("male")){
            strGender = "m";
        } else if(radioSexButton.getText().toString().equalsIgnoreCase("female")){
            strGender = "f";
        } else if(radioSexButton.getText().toString().equalsIgnoreCase("other")){
            strGender = "o";
        }

        // Save patient data
        SavePatientData savePatient = new SavePatientData();
        savePatient.execute();

    }

    public void btnCancel(View view)
    {
        Intent CancelMsg = new Intent(this, MainActivity.class);
        startActivity(CancelMsg);
    }

    public void tvContact(View view)
    {
        Intent Contact = new Intent(this, ContactUsActivity.class);
        startActivity(Contact);
    }

    public void tvAbout(View view)
    {
        Intent About = new Intent(this, AboutActivity.class);
        startActivity(About);
    }

    public void tvHelp(View view)
    {
        Intent Help = new Intent(this, ContactUsActivity.class);
        startActivity(Help);
    }

    public void tvOverview(View view)
    {
        Intent Overview = new Intent(this, OverviewActivity.class);
        startActivity(Overview);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_subpages, menu);

        return super.onCreateOptionsMenu(menu);
    }

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

    //Asynchronous call, saving new patient data
    //Inner classes, Asynchronours call
    private class SavePatientData extends AsyncTask<Void, Void, Void> {

        private final String TAG = "NewPatientActivity Class";
        private String exeption_triggered = "";

        String strOid = "";
        private String strFirstName = "";
        private String strLastName = "";
        private String strAddress = "";
        private String strPhone = "";
        private String gender = "";
        private String strNHI = "";
        private int iResult = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(NewPatientActivity.this);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            // Set progressdialog title
            mProgressDialog.setTitle("Saving");
            // Set progressdialog message
            mProgressDialog.setMessage("Saving Patient Data ....");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            try {
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost("http://genericregistrationsystem.co.nf/api/save_patient_details.php");
                JSONObject json = new JSONObject();

                json.put("firstName", txtFName.getText().toString());
                json.put("lastName", txtLName.getText().toString());
                json.put("address", txtHomeAdd.getText().toString());
                json.put("phone", txtPatPhone.getText().toString());
                json.put("gender", strGender);
                json.put("nhi", txtNHI.getText().toString());

                JSONArray postjson = new JSONArray();
                postjson.put(json);
                Log.i(TAG, "request :" + postjson);

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
                        //mydb.execSQL("delete from "+ DB_USER);
                        Log.i(TAG, "Login Result : " + result);
                        jsonObject = new JSONObject(result);
                        iResult = jsonObject.getInt("result");
                        //jArray = jsonObject.getJSONArray("result");
                        //JSONObject jsonItems = new JSONObject();
                        //for (int i = 0; i < jArray.length(); i++) {
                            //jsonItems = jArray.getJSONObject(0);
                            //iResult = jsonItems.getInt("result");
                        //}

                        //iResult = jsonObject.getInt("result");
                        Log.i(TAG, "OID strResult : " + iResult);
                        strOid = strResult.toString();
                        //JSONObject jsonItems = new JSONObject();
                        ContentValues newRecord = new ContentValues();

                        if(iResult >= 0){
                            newRecord.put("oid", iResult);
                            strOid = strResult.toString();
                            newRecord.put("first_name", txtFName.getText().toString());
                            strFirstName = txtFName.getText().toString();
                            newRecord.put("last_name", txtLName.getText().toString());
                            strLastName = txtLName.getText().toString();
                            newRecord.put("address", txtHomeAdd.getText().toString());
                            strAddress = txtHomeAdd.getText().toString();
                            newRecord.put("phone", txtPatPhone.getText().toString());
                            strPhone = txtPatPhone.getText().toString();
                            newRecord.put("gender", strGender);
                            gender = strGender;
                            newRecord.put("nhi", txtNHI.getText().toString());
                            strNHI = txtNHI.getText().toString();

                            mydb.insert(DB_CUSTOMER, null, newRecord);
                        }

                        mydb.close();
                    }

                }

            } catch (Exception e) {
                exeption_triggered = e.toString();
                //Log.e("Error : ", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            mProgressDialog.dismiss();
            Intent NewSubmit = new Intent(getBaseContext(), SpecialtyActivity.class);

            Log.i("strOid = ", "" + iResult);
            Log.i("strFirstName = ", txtFName.getText().toString());
            Log.i("strLastName = ", txtLName.getText().toString());
            Log.i("strAddress = ", txtHomeAdd.getText().toString());
            Log.i("strPhone = ", txtPatPhone.getText().toString());
            Log.i("strGender = ", strGender);
            Log.i("strNHI = ", txtNHI.getText().toString());

            NewSubmit.putExtra("oid", iResult);
            NewSubmit.putExtra("first_name", txtFName.getText().toString());
            NewSubmit.putExtra("last_name", txtLName.getText().toString());
            NewSubmit.putExtra("address", txtHomeAdd.getText().toString());
            NewSubmit.putExtra("phone", txtPatPhone.getText().toString());
            NewSubmit.putExtra("gender", strGender);
            NewSubmit.putExtra("nhi", txtNHI.getText().toString());

            startActivity(NewSubmit);
        }
    }


}
