package com.bignerdranch.android.starwars.ui.splash
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bignerdranch.android.starwars.BuildConfig
import com.bignerdranch.android.starwars.R
import com.bignerdranch.android.starwars.ui.MainActivity
import com.bignerdranch.android.starwars.ui.details.DetailsViewModel
import com.bignerdranch.android.starwars.ui.home.HomeViewModel
import com.bignerdranch.android.starwars.utils.subscribe
import io.reactivex.rxjava3.internal.jdk8.FlowableFlatMapStream.subscribe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()

    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }
}