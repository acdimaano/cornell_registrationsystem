package com.genericregistrationsystem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

/*
 * Created by Alan Dimaano on 8/19/2015.
 */
public class ExistingPatientActivity extends ActionBarActivity
{
    protected ProgressDialog mProgressDialog;
    protected HttpParams httpParameters = new BasicHttpParams();

    protected InputStream inputStream = null;
    protected StringBuilder stringBuilder = null;
    protected String result;
    protected JSONArray jArray;
    protected JSONObject jsonObject;

    private EditText etNHI;
    private int timeoutConnection = 5000;
    private String mainUrl = null;

    /**
     * This is the onCreate method.
     * A native method of Android Activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_existing_patient);
        setContentView(R.layout.activity_existing_patient);

        etNHI = (EditText) findViewById(R.id.txtNHI);
        checkNHI();
    }

    protected void checkNHI(){
        CheckNHI nhi = new CheckNHI();
        nhi.execute();
    }

    public void btnReturnSubmit(View view)
    {

        if (!NetworkUtility.isNetWorking(ExistingPatientActivity.this)) {
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    ExistingPatientActivity.this).create();
            // Setting Dialog Title
            alertDialog.setTitle("NETWORK ERROR");
            // Setting Dialog Message
            alertDialog
                    .setMessage("Please Check Your Network Connection..");
            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.warning);
            // Setting OK Button
            alertDialog.setButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            alertDialog.dismiss();
                        }
                    });
            // Showing Alert Message
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            return;
        }
        else {
            Intent ReturnSubmit = new Intent(this, SpecialtyActivity.class);
            startActivity(ReturnSubmit);
        }
    }

    public void btnCancel(View view)
    {
        Intent CancelMsg = new Intent(this, MainActivity.class);
        startActivity(CancelMsg);
    }

    public void tvAbout(View view)
    {
        Intent About = new Intent(this, AboutActivity.class);
        startActivity(About);
    }

    public void tvContact(View view)
    {
        Intent Contact = new Intent(this, ContactUsActivity.class);
        startActivity(Contact);
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

    //Inner classes, Asynchronours call
    private class CheckNHI extends AsyncTask<Void, Void, Void> {

        private final String TAG = "ExistingPatientActivity Class";
        private String exeption_triggered = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ExistingPatientActivity.this);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            // Set progressdialog title
            mProgressDialog.setTitle("Synching");
            // Set progressdialog message
            mProgressDialog.setMessage("Checking Data ....");
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
                HttpPost httppost = new HttpPost("http://genericregistrationsystem.co.nf/api/get_user_list.php");
                JSONObject json = new JSONObject();

                String strNHI = etNHI.getText().toString();

                Log.i(TAG, "NHI : " + strNHI);

                json.put("record1", strNHI);

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
                        Log.e(TAG, "Login Result :" + result);
                        jsonObject = new JSONObject(result);
                        jArray = jsonObject.getJSONArray("posts");
                        JSONObject jsonItems = new JSONObject();
                        for (int i = 0; i < jArray.length(); i++) {
                            jsonItems = jArray.getJSONObject(i);
                            String oid = jsonItems.getString("oid");
                            String email = jsonItems.getString("email");
                            Log.i("Email = ", email + " oid = " + oid);
                        }
                    }

                }

            } catch (Exception e) {
                exeption_triggered = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            mProgressDialog.dismiss();

        }
    }

}
