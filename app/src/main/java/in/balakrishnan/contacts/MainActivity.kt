package `in`.balakrishnan.contacts

import `in`.balakrishnan.contacts.model.Status
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Injection.provideRepository().getContacts().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> Log.d(TAG, "Size of contacts : " + it.data?.size)
                Status.ERROR -> Log.d(TAG, ": Error ${it.error}")
            }
        })

    }
}
