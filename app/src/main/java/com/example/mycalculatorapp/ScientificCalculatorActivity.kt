package com.example.mycalculatorapp

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

class ScientificCalculatorActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var currentExpression: String = ""
    private var lastAnswer: Double = 0.0
    private var isRadianMode = true // Default to radians

    // Math Tutor Views
    private lateinit var robotBubble: FrameLayout
    private lateinit var mathTutorWindow: View
    private lateinit var tvTutorExplanation: TextView
    private lateinit var btnCloseTutor: ImageButton
    private lateinit var typingIndicator: View
    private val mathTutorService = MathTutorService()
    private lateinit var markwon: Markwon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Force landscape orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContentView(R.layout.activity_scientific_calculator)

        display = findViewById(R.id.txt_sci_display)

        // Initialize Math Tutor Views
        robotBubble = findViewById(R.id.robot_bubble_sci)
        mathTutorWindow = findViewById(R.id.math_tutor_window_sci)
        tvTutorExplanation = mathTutorWindow.findViewById(R.id.tv_tutor_explanation)
        btnCloseTutor = mathTutorWindow.findViewById(R.id.btn_close_tutor)
        typingIndicator = mathTutorWindow.findViewById(R.id.typing_indicator)

        // Initialize Markwon for markdown rendering
        markwon = Markwon.create(this)

        setupButtons()
        setupMathTutor()
    }

    private fun setupButtons() {
        // Back button
        findViewById<ImageButton>(R.id.btn_sci_back).setOnClickListener {
            finish()
        }

        // Number buttons
        val numberIds = listOf(
            R.id.btn_sci_0, R.id.btn_sci_1, R.id.btn_sci_2, R.id.btn_sci_3, R.id.btn_sci_4,
            R.id.btn_sci_5, R.id.btn_sci_6, R.id.btn_sci_7, R.id.btn_sci_8, R.id.btn_sci_9
        )
        numberIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener { appendToExpression((it as Button).text.toString()) }
        }

        // Special number buttons
        findViewById<Button>(R.id.btn_sci_00).setOnClickListener { appendToExpression("00") }
        findViewById<Button>(R.id.btn_sci_dot).setOnClickListener { appendToExpression(".") }

        // Basic operations
        findViewById<Button>(R.id.btn_sci_add).setOnClickListener { appendToExpression("+") }
        findViewById<Button>(R.id.btn_sci_sub).setOnClickListener { appendToExpression("-") }
        findViewById<Button>(R.id.btn_sci_mul).setOnClickListener { appendToExpression("*") }
        findViewById<Button>(R.id.btn_sci_div).setOnClickListener { appendToExpression("/") }
        findViewById<Button>(R.id.btn_sci_equals).setOnClickListener { onEquals() }

        // Control buttons
        findViewById<Button>(R.id.btn_sci_clear).setOnClickListener { onClear() }
        findViewById<Button>(R.id.btn_sci_backspace).setOnClickListener { onBackspace() }
        findViewById<Button>(R.id.btn_sci_percent).setOnClickListener { appendToExpression("%") }
        findViewById<Button>(R.id.btn_sci_plus_minus).setOnClickListener { toggleSign() }

        // Trigonometric functions
        findViewById<Button>(R.id.btn_sci_sin).setOnClickListener { appendFunction("sin") }
        findViewById<Button>(R.id.btn_sci_cos).setOnClickListener { appendFunction("cos") }
        findViewById<Button>(R.id.btn_sci_tan).setOnClickListener { appendFunction("tan") }

        // Logarithmic functions
        findViewById<Button>(R.id.btn_sci_log).setOnClickListener { appendFunction("log10") }
        findViewById<Button>(R.id.btn_sci_ln).setOnClickListener { appendFunction("log") }

        // Power and root functions
        findViewById<Button>(R.id.btn_sci_sqrt).setOnClickListener { appendFunction("sqrt") }
        findViewById<Button>(R.id.btn_sci_square).setOnClickListener { appendToExpression("^2") }
        findViewById<Button>(R.id.btn_sci_power).setOnClickListener { appendToExpression("^") }
        findViewById<Button>(R.id.btn_sci_cube).setOnClickListener { appendToExpression("^3") }
        findViewById<Button>(R.id.btn_sci_cbrt).setOnClickListener { appendFunction("cbrt") }

        // Additional functions
        findViewById<Button>(R.id.btn_sci_factorial).setOnClickListener { appendFactorial() }
        findViewById<Button>(R.id.btn_sci_inverse).setOnClickListener { appendToExpression("^-1") }
        findViewById<Button>(R.id.btn_sci_exp).setOnClickListener { appendFunction("exp") }
        findViewById<Button>(R.id.btn_sci_10x).setOnClickListener { append10Power() }

        // Constants
        findViewById<Button>(R.id.btn_sci_pi).setOnClickListener { appendToExpression("π") }
        findViewById<Button>(R.id.btn_sci_e).setOnClickListener { appendToExpression("e") }

        // Parentheses
        findViewById<Button>(R.id.btn_sci_left_paren).setOnClickListener { appendToExpression("(") }
        findViewById<Button>(R.id.btn_sci_right_paren).setOnClickListener { appendToExpression(")") }

        // RAD/DEG toggle button
        findViewById<Button>(R.id.btn_sci_rad_deg).setOnClickListener { toggleAngleMode() }

        // Rand button - Random whole number
        findViewById<Button>(R.id.btn_sci_rand).setOnClickListener { insertRandomNumber() }

        // ANS button - recall last answer
        findViewById<Button>(R.id.btn_sci_ans).setOnClickListener {
            if (lastAnswer != 0.0) {
                appendToExpression(lastAnswer.toString())
            }
        }

        // MOD operation
        findViewById<Button>(R.id.btn_sci_mod).setOnClickListener { appendToExpression("%") }

        // EXP button - scientific notation
        findViewById<Button>(R.id.btn_sci_exp_button).setOnClickListener { appendToExpression("E") }

        // y√x button
        findViewById<Button>(R.id.btn_sci_yroot).setOnClickListener { appendToExpression("^(1/") }
    }

    private fun appendToExpression(text: String) {
        if (currentExpression == "0" || currentExpression == "Error") {
            currentExpression = text
        } else {
            currentExpression += text
        }
        updateDisplay()
    }

    private fun appendFunction(funcName: String) {
        if (currentExpression == "0") {
            currentExpression = "$funcName("
        } else {
            currentExpression += "$funcName("
        }
        updateDisplay()
    }

    private fun appendFactorial() {
        currentExpression += "!"
        updateDisplay()
    }

    private fun append10Power() {
        if (currentExpression == "0") {
            currentExpression = "10^("
        } else {
            currentExpression += "10^("
        }
        updateDisplay()
    }

    private fun toggleSign() {
        if (currentExpression.isNotEmpty() && currentExpression != "0") {
            currentExpression = "(-$currentExpression)"
        }
        updateDisplay()
    }

    private fun onBackspace() {
        if (currentExpression.isNotEmpty() && currentExpression != "0") {
            currentExpression = currentExpression.dropLast(1)
            if (currentExpression.isEmpty()) {
                currentExpression = "0"
            }
        }
        updateDisplay()
    }

    private fun onClear() {
        currentExpression = "0"
        updateDisplay()
    }

    private fun insertRandomNumber() {
        // Generate random whole number between 0 and 100
        val random = (Math.random() * 101).toInt()
        appendToExpression(random.toString())
    }

    private fun toggleAngleMode() {
        isRadianMode = !isRadianMode
        val radDegButton = findViewById<Button>(R.id.btn_sci_rad_deg)
        radDegButton.text = if (isRadianMode) "RAD" else "DEG"

        // Optional: Change button color to indicate mode
        if (isRadianMode) {
            radDegButton.setTextColor(0xFF202124.toInt())
        } else {
            radDegButton.setTextColor(0xFF1A73E8.toInt())
        }
    }

    private fun onEquals() {
        if (currentExpression.isEmpty() || currentExpression == "0") return

        val originalExpression = currentExpression

        try {
            var expr = currentExpression
                .replace("π", Math.PI.toString())
                .replace("e", Math.E.toString())
                .replace("E", "e") // Scientific notation
                .replace("×", "*")
                .replace("÷", "/")
                .replace("exp10(", "10^(") // Handle 10^x function

            // Handle angle conversion for trigonometric functions
            if (!isRadianMode) {
                expr = convertDegreesToRadians(expr)
            }

            // Handle factorial
            expr = handleFactorial(expr)

            // Handle percentage
            expr = expr.replace("%", "/100")

            // Build and evaluate expression
            val result = ExpressionBuilder(expr)
                .build()
                .evaluate()

            lastAnswer = result
            val resultString = formatResult(result)
            currentExpression = resultString

            // Update Math Tutor with explanation
            updateMathTutorExplanation(originalExpression, resultString)
        } catch (_: Exception) {
            currentExpression = "Error"
        }

        updateDisplay()
    }

    private fun convertDegreesToRadians(expr: String): String {
        var result = expr

        // Convert degrees to radians for sin, cos, tan
        // Pattern: sin(number), cos(number), tan(number)
        val trigFunctions = listOf("sin", "cos", "tan", "asin", "acos", "atan")

        for (func in trigFunctions) {
            // For inverse functions (asin, acos, atan), we don't convert input
            if (func.startsWith("a")) continue

            val pattern = "$func\\(([^)]+)\\)".toRegex()
            result = pattern.replace(result) { matchResult ->
                val angle = matchResult.groupValues[1]
                // Convert degrees to radians: radians = degrees * π / 180
                "$func(($angle)*${Math.PI}/180)"
            }
        }

        return result
    }

    private fun handleFactorial(expr: String): String {
        var result = expr
        val factorialRegex = "(\\d+)!".toRegex()

        factorialRegex.findAll(result).forEach { match ->
            val number = match.groupValues[1].toIntOrNull()
            if (number != null && number >= 0 && number <= 20) {
                var factorial = 1L
                for (i in 2..number) {
                    factorial *= i
                }
                result = result.replace(match.value, factorial.toString())
            }
        }

        return result
    }

    private fun formatResult(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            String.format(java.util.Locale.US, "%.10f", value).trimEnd('0').trimEnd('.')
        }
    }

    private fun updateDisplay() {
        display.text = if (currentExpression.isEmpty()) "0" else currentExpression
    }

    private fun setupMathTutor() {
        // Toggle Math Tutor window when robot bubble is clicked
        robotBubble.setOnClickListener {
            if (mathTutorWindow.visibility == View.GONE) {
                showMathTutorWindow()
            } else {
                hideMathTutorWindow()
            }
        }

        // Close button in Math Tutor window
        btnCloseTutor.setOnClickListener {
            hideMathTutorWindow()
        }
    }

    private fun showMathTutorWindow() {
        mathTutorWindow.visibility = View.VISIBLE
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        mathTutorWindow.startAnimation(slideIn)
    }

    private fun hideMathTutorWindow() {
        val slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)
        mathTutorWindow.startAnimation(slideOut)
        mathTutorWindow.visibility = View.GONE
    }

    private fun startTypingAnimation() {
        val dot1 = mathTutorWindow.findViewById<View>(R.id.typing_dot1)
        val dot2 = mathTutorWindow.findViewById<View>(R.id.typing_dot2)
        val dot3 = mathTutorWindow.findViewById<View>(R.id.typing_dot3)

        val anim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.typing_dot_anim)

        dot1.startAnimation(anim)
        dot2.postDelayed({ dot2.startAnimation(anim) }, 200)
        dot3.postDelayed({ dot3.startAnimation(anim) }, 400)
    }

    private fun updateMathTutorExplanation(expression: String, result: String) {
        // Show typing indicator
        typingIndicator.visibility = View.VISIBLE
        tvTutorExplanation.visibility = View.GONE

        // Start typing animation
        startTypingAnimation()

        // Fetch explanation from AI
        lifecycleScope.launch {
            try {
                val explanation = mathTutorService.getStepByStepExplanation(expression, result)

                // Hide typing indicator and show explanation
                typingIndicator.visibility = View.GONE
                tvTutorExplanation.visibility = View.VISIBLE

                // Use Markwon to render markdown formatting
                markwon.setMarkdown(tvTutorExplanation, explanation)
            } catch (_: Exception) {
                typingIndicator.visibility = View.GONE
                tvTutorExplanation.visibility = View.VISIBLE
                markwon.setMarkdown(tvTutorExplanation, "Calculation: $expression = $result")
            }
        }
    }
}

