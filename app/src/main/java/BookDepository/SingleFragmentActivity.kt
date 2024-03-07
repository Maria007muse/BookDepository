package BookDepository

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import ru.rsue.rubanova.R

abstract class SingleFragmentActivity: FragmentActivity() {
    protected abstract fun createFragment(): Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragmentContainer)
        if (fragment == null){
            fragment = createFragment()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment)
                .commit()
        }
    }
}