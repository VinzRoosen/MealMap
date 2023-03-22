package be.LarsVinz.OurApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import be.LarsVinz.OurApp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Edit Recept
        binding.btnEdit.setOnClickListener{
        }

        //New Recept
        binding.btnNew.setOnClickListener{
        }

    }
}