package com.example.kalorihesabi

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextGender = findViewById<EditText>(R.id.editTextGender)
        val editTextAge = findViewById<EditText>(R.id.editTextAge)
        val editTextHeight = findViewById<EditText>(R.id.editTextHeight)
        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val spinnerActivityLevel = findViewById<Spinner>(R.id.spinnerActivityLevel)
        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        // Spinner için veri adaptörü tanımlama
        ArrayAdapter.createFromResource(
            this,
            R.array.activity_levels_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerActivityLevel.adapter = adapter
        }

        buttonCalculate.setOnClickListener {
            val gender = editTextGender.text.toString()
            val age = editTextAge.text.toString().toIntOrNull() ?: 0
            val height = editTextHeight.text.toString().toIntOrNull() ?: 0
            val weight = editTextWeight.text.toString().toIntOrNull() ?: 0
            val activityLevel = spinnerActivityLevel.selectedItem.toString()

            val bmr = calculateBMR(gender, age, height, weight)
            val calorieNeeds = calculateCalorieNeeds(bmr, activityLevel)

            textViewResult.text = "Günlük kalori ihtiyacınız: $calorieNeeds kcal"
        }
    }

    private fun calculateBMR(gender: String, age: Int, height: Int, weight: Int): Double {
        return if (gender.equals("Erkek", ignoreCase = true)) {
            88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
        } else {
            447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
        }
    }

    private fun calculateCalorieNeeds(bmr: Double, activityLevel: String): Double {
        return when (activityLevel) {
            "Az Aktif" -> bmr * 1.2
            "Hafif Aktif" -> bmr * 1.375
            "Orta Aktif" -> bmr * 1.55
            "Çok Aktif" -> bmr * 1.725
            "Aşırı Aktif" -> bmr * 1.9
            else -> bmr * 1.2
        }
    }
}


