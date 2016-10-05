package com.genericregistrationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Rhiza roque on 8/14/2015.
 */
public class QueueNumberActivity extends ActionBarActivity
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
        setContentView(R.layout.activity_queue_number);
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
}
