package com.genericregistrationsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/*
 * Created by Alan Dimaano on 8/19/2015.
 */

public class MainActivity extends ActionBarActivity {

    protected GRSDBLoader grsDB;

    /**
     * This is the onCreate method.
     * A native method of Android Activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate DB
        grsDB = new GRSDBLoader(this);
        grsDB.getWritableDatabase();
        grsDB.close();

    }

    public void btnReturnPat(View view)
    {
        //Check for network connectivity
        if (!NetworkUtility.isNetWorking(MainActivity.this)) {
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    MainActivity.this).create();
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
            Intent ReturnPat = new Intent(this, ExistingPatientActivity.class);
            startActivity(ReturnPat);
        }

    }

    public void btnNewPat(View view)
    {
        //Check for network connectivity
        if (!NetworkUtility.isNetWorking(MainActivity.this)) {
            final AlertDialog alertDialog = new AlertDialog.Builder(
                    MainActivity.this).create();
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
            Intent NewPat = new Intent(this, NewPatientActivity.class);
            startActivity(NewPat);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //code to handle on press action to the icon in action bar
        switch(item.getItemId()){
            case R.id.icon_appointment:
                Intent Appointment = new Intent(this, AppointmentActivity.class);
                startActivity(Appointment);
                return true;
            case R.id.icon_specialty:
                Intent Specialty = new Intent(this, SpecialtyActivity.class);
                startActivity(Specialty);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
