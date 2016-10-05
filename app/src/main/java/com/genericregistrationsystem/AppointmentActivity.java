package com.genericregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * This is the AppointmentActivity class.
 * For the appointments of the patient and doctors.
 *
 * Created by Alan C. Dimaano on 8/14/2015.
 */
public class AppointmentActivity extends ActionBarActivity
{
    /**
     * This is the onCreate method.
     * A native method of Android Activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
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
     * This is the contact activity link
     * @param view
     */
    public void tvContact(View view)
    {
        Intent Contact = new Intent(this, ContactUsActivity.class);
        startActivity(Contact);
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
     * This is the Overview activity link
     * @param view
     */
    public void tvOverview(View view)
    {
        Intent Overview = new Intent(this, OverviewActivity.class);
        startActivity(Overview);
    }
}
