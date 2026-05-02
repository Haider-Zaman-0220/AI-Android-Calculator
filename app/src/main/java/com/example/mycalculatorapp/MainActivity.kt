package com.example.mycalculatorapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    // Calculator Views
    private lateinit var display: TextView
    private lateinit var btnOpenConverter: ImageButton
    private lateinit var btnChatbot: ImageButton

    // Math Tutor Views
    private lateinit var robotBubble: FrameLayout
    private lateinit var mathTutorWindow: View
    private lateinit var tvTutorExplanation: TextView
    private lateinit var btnCloseTutor: ImageButton
    private lateinit var typingIndicator: View
    private val mathTutorService = MathTutorService()
    private lateinit var markwon: Markwon

    // Calculator State
    private var currentInput: String = "0"
    private var storedValue: Double? = null
    private var pendingOp: Char? = null
    private var justPressedOp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Calculator Views
        display = findViewById(R.id.txt_display)
        btnOpenConverter = findViewById(R.id.btn_open_converter)
        btnChatbot = findViewById(R.id.btn_chatbot)

        // Initialize Math Tutor Views
        robotBubble = findViewById(R.id.robot_bubble)
        mathTutorWindow = findViewById(R.id.math_tutor_window)
        tvTutorExplanation = mathTutorWindow.findViewById(R.id.tv_tutor_explanation)
        btnCloseTutor = mathTutorWindow.findViewById(R.id.btn_close_tutor)
        typingIndicator = mathTutorWindow.findViewById(R.id.typing_indicator)

        // Initialize Markwon for markdown rendering
        markwon = Markwon.create(this)

        setupCalculatorButtons()
        setupMathTutor()

        btnOpenConverter.setOnClickListener {
            val intent = Intent(this, CurrencyConverterActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btn_scientific).setOnClickListener {
            val intent = Intent(this, ScientificCalculatorActivity::class.java)
            startActivity(intent)
        }

        btnChatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupMathTutor() {
        // Toggle Math Tutor window when robot bubble is clicked
        robotBubble.setOnClickListener {
            if (mathTutorWindow.visibility == View.VISIBLE) {
                mathTutorWindow.visibility = View.GONE
            } else {
                mathTutorWindow.visibility = View.VISIBLE
            }
        }

        // Close Math Tutor window
        btnCloseTutor.setOnClickListener {
            mathTutorWindow.visibility = View.GONE
        }

        // Start typing animation
        startTypingAnimation()
    }

    private fun startTypingAnimation() {
        val dot1 = mathTutorWindow.findViewById<View>(R.id.typing_dot1)
        val dot2 = mathTutorWindow.findViewById<View>(R.id.typing_dot2)
        val dot3 = mathTutorWindow.findViewById<View>(R.id.typing_dot3)

        val anim = AnimationUtils.loadAnimation(this, R.anim.typing_dot_anim)

        dot1.startAnimation(anim)
        dot2.postDelayed({ dot2.startAnimation(anim) }, 200)
        dot3.postDelayed({ dot3.startAnimation(anim) }, 400)
    }

    private fun updateMathTutorExplanation(expression: String, result: String) {
        // Show typing indicator
        typingIndicator.visibility = View.VISIBLE
        tvTutorExplanation.visibility = View.GONE

        // Fetch explanation from AI
        lifecycleScope.launch {
            try {
                val explanation = mathTutorService.getStepByStepExplanation(expression, result)

                // Hide typing indicator and show explanation
                typingIndicator.visibility = View.GONE
                tvTutorExplanation.visibility = View.VISIBLE

                // Use Markwon to render markdown formatting
                markwon.setMarkdown(tvTutorExplanation, explanation)
            } catch (e: Exception) {
                typingIndicator.visibility = View.GONE
                tvTutorExplanation.visibility = View.VISIBLE
                markwon.setMarkdown(tvTutorExplanation, "Calculation: $expression = $result")
            }
        }
    }

    private fun setupCalculatorButtons() {
        val digitIds = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
            R.id.btn_00
        )
        digitIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onDigit((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btn_dot).setOnClickListener { onDot() }
        findViewById<Button>(R.id.btn_clear).setOnClickListener { onClear() }
        findViewById<Button>(R.id.btn_add).setOnClickListener { onOp('+') }
        findViewById<Button>(R.id.btn_sub).setOnClickListener { onOp('-') }
        findViewById<Button>(R.id.btn_mul).setOnClickListener { onOp('*') }
        findViewById<Button>(R.id.btn_div).setOnClickListener { onOp('/') }
        findViewById<Button>(R.id.btn_eq).setOnClickListener { onEquals() }
        findViewById<Button>(R.id.btn_percent).setOnClickListener { onPercent() }
        findViewById<Button>(R.id.btn_backspace).setOnClickListener { onBackspace() }
    }

    // --- Keypad Logic ---

    private fun onDigit(d: String) {
        if (justPressedOp) {
            currentInput = "0"
        }
        if (currentInput == "Error") {
            currentInput = d
        } else if (currentInput == "0") {
            if (d == "0" || d == "00") return
            currentInput = d
        } else {
            currentInput += d
        }
        justPressedOp = false
        render()
    }

    private fun onBackspace() {
        if (currentInput == "Error" || justPressedOp) {
        } else {
            currentInput = currentInput.dropLast(1)
            if (currentInput.isEmpty()) {
                currentInput = "0"
            }
        }
        render()
    }

    private fun onDot() {
        if (currentInput == "Error") return
        if (!currentInput.contains('.')) {
            currentInput += if (currentInput.isEmpty() || justPressedOp) "0." else "."
            justPressedOp = false
            render()
        }
    }

    private fun onClear() {
        currentInput = "0"
        storedValue = null
        pendingOp = null
        justPressedOp = false
        render()
    }

    private fun onOp(op: Char) {
        if (currentInput == "Error") return
        if (justPressedOp) {
            pendingOp = op
            render()
            return
        }
        val inputVal = currentInput.toDoubleOrNull()
        if (inputVal != null) {
            if (storedValue == null) {
                storedValue = inputVal
            } else if (pendingOp != null) {
                storedValue = compute(storedValue!!, inputVal, pendingOp!!)
            }
        }
        pendingOp = op
        justPressedOp = true
        render()
    }

    private fun onEquals() {
        if (currentInput == "Error" || storedValue == null || pendingOp == null) return
        val inputVal = currentInput.toDoubleOrNull()
        if (inputVal != null) {
            val expression = "${trimDouble(storedValue!!)} ${pendingOp} ${trimDouble(inputVal)}"
            val result = compute(storedValue!!, inputVal, pendingOp!!)
            if (result.isNaN()) {
                currentInput = "Error"
            } else {
                currentInput = trimDouble(result)
                // Update Math Tutor with explanation
                updateMathTutorExplanation(expression, currentInput)
            }
            pendingOp = null
            storedValue = null
        }
        justPressedOp = false
        render()
    }

    private fun onPercent() {
        if (currentInput == "Error") return
        val v = currentInput.toDoubleOrNull() ?: return
        currentInput = trimDouble(v / 100.0)
        justPressedOp = false
        render()
    }

    private fun compute(a: Double, b: Double, op: Char): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b == 0.0) Double.NaN else a / b
            else -> b
        }
    }

    private fun trimDouble(v: Double): String {
        val s = v.toString()
        return if (s.endsWith(".0")) s.dropLast(2) else s
    }

    private fun render() {
        if (pendingOp == null) {
            display.text = currentInput
        } else {
            val expressionPrefix = "${trimDouble(storedValue!!)}${pendingOp}"
            if (justPressedOp) {
                display.text = expressionPrefix
            } else {
                display.text = expressionPrefix + currentInput
            }
        }
    }
}
