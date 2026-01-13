package com.example.quizdepartementsfrance
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import org.json.JSONArray

class QuizNumeroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        loadQuestion()
    }

    private fun loadQuestion() {
        val question = findViewById<TextView>(R.id.questionText)
        val buttons = listOf<Button>(
            findViewById(R.id.answer1),
            findViewById(R.id.answer2),
            findViewById(R.id.answer3)
        )

        val json = assets.open("departements.json").bufferedReader().use { it.readText() }
        val arr = JSONArray(json)
        val correct = arr.getJSONObject((0 until arr.length()).random())
        val correctNum = correct.getString("numero")

        question.text = "Quel est le numéro du département ${correct.getString("nom")} ?"

        val choices = mutableSetOf(correctNum)
        while (choices.size < 3) {
            choices.add(arr.getJSONObject((0 until arr.length()).random()).getString("numero"))
        }

        choices.shuffled().forEachIndexed { i, v ->
            buttons[i].text = v
            buttons[i].setOnClickListener {
                Toast.makeText(
                    this,
                    if (v == correctNum) "Bonne réponse" else "Mauvaise réponse",
                    Toast.LENGTH_SHORT
                ).show()
                loadQuestion()
            }
        }
    }
}
