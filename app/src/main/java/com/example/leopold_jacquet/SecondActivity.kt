package com.example.leopold_jacquet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.leopold_jacquet.databinding.ActivitySecondBinding
import com.example.leopold_jacquet.databinding.FragmentSecondBinding

class SecondActivity : AppCompatActivity() {
    private var _binding: ActivitySecondBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        WebService.startAction(this)
    }
}