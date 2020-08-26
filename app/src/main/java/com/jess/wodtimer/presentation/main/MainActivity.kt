package com.jess.wodtimer.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.jess.wodtimer.R
import com.jess.wodtimer.presentation.record.RecordActivity
import kotlinx.android.synthetic.main.main_activity.*

/**
 * @author jess
 * @since 2020.08.06
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        bt_record.setOnClickListener {
            val intent = Intent(this@MainActivity, RecordActivity::class.java)
            // startActivityForResult deprecated 대체
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            }.launch(intent)
        }
//        registerForActivityResult(RequestPermission()) { isGranted ->
//            Timber.d("isGranted $isGranted")
//        }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        // code review 2
    }

}
