package com.genericregistrationsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Alan C. Dimaano on 8/3/2015.
 */
public class SpecialtyActivity extends ActionBarActivity
{
    private static final String DATABASE_NAME = "genericregistrationsystem.db";
    private SQLiteDatabase mydb;
    protected String DB_USER = "tbuser";
    protected String DB_CUSTOMER = "tbcustomerdetails";
    protected int timeoutConnection = 5000;

    private String strOid = "";
    private int iOid = -1;
    private String strFirstName = "";
    private String strLastName = "";
    private String strAddress = "";
    private String strPhone = "";
    private String strGender = "";
    private String strNHI = "";

    /**
     * This is the onCreate method.
     * A native method of Android Activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            strFirstName = bundle.getString("first_name");
            strLastName = bundle.getString("last_name");
            strAddress = bundle.getString("address");
            strPhone = bundle.getString("phone");
            strGender = bundle.getString("gender");
            strNHI = bundle.getString("nhi");
            iOid = bundle.getInt("oid");

            Log.i("SpecialtyActivity strOid = ", "" + iOid);
            Log.i("SpecialtyActivity strFirstName = ", strFirstName);
            Log.i("SpecialtyActivity strLastName = ", strLastName);
            Log.i("SpecialtyActivity strAddress = ", strAddress);
            Log.i("SpecialtyActivity strPhone = ", strPhone);
            Log.i("SpecialtyActivity strGender = ", strGender);
            Log.i("SpecialtyActivity strNHI = ", strNHI);
        }
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

    public void btnNeuro (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 1);
        startActivity(Specialty);
    }

    public void btnCardio (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 2);
        startActivity(Specialty);
    }

    public void btnInternist (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 3);
        startActivity(Specialty);
    }

    public void btnNatMed (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 4);
        startActivity(Specialty);
    }

    public void btnDentist (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 5);
        startActivity(Specialty);
    }

    public void btnLab (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 6);
        startActivity(Specialty);
    }

    public void btnOrtho (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 7);
        startActivity(Specialty);
    }

    public void btnPulmo (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 8);
        startActivity(Specialty);
    }

    public void btnOB (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 9);
        startActivity(Specialty);
    }

    public void btnPedia (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 10);
        startActivity(Specialty);
    }

    public void btnFamMed (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 11);
        startActivity(Specialty);
    }

    public void btnEye (View view)
    {
        Intent Specialty = new Intent(this, DoctorListActivity.class );
        Specialty.putExtra("patientOid", iOid);
        Specialty.putExtra("specialty", 12);
        startActivity(Specialty);
    }

    private String getOid(String strNHI) {
        // TODO Auto-generated method stub
        String strOID = null;
        Log.i("NHI values : ", strNHI);
        mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor user = null;
        try {
            String query = "SELECT oid FROM tbuser where nhi = '" + strNHI + "'";
            Log.i("query : ", query);
            user = mydb.rawQuery(query, null);
        } catch (Exception e) {
            Log.e("Error!", e.getMessage());
        }

        if (user != null) {
            if (user.getCount() > 0) {
                while (user.moveToNext()) {
                    strOID = user.getString(0);
                }
            }
        }

        mydb.close();
        return strOID;
    }

}
