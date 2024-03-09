package ru.rsue.rubanova

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            val newFragment = DatePickerFragment.newInstance(null)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, newFragment)
                .commit()
        }
    }
}
