package com.genericregistrationsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.ActionBarActivity;

/**
 * This is the Contact us activity for the application.
 * This merely displays the contact information.
 *
 * Created by Rhiza roque on 8/10/2015.
 * Alan C. Dimaano
 */
public class ContactUsActivity extends ActionBarActivity
{
    /**
     * This is the onCreate method.
     * A native method of Android Activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }

    /**
     * Cancel msg method
     *
     * @param view
     */
    public void btnCancelMsg(View view)
    {
        Intent CancelMsg = new Intent(this, MainActivity.class);
        startActivity(CancelMsg);
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

}
