package com.bmstu.iu9.swimrunners.androidrk1.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bmstu.iu9.swimrunners.androidrk1.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemID: Int = item.itemId
        if (itemID == R.id.settings_menu_item) {
            startActivity(Intent(this, SettingsActivity::class.java))
        } else if (itemID == R.id.refresh_menu_item) {
            Toast.makeText(this, "FIXME!", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
}