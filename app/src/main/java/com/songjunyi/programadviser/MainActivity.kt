package com.songjunyi.programadviser

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.songjunyi.programadviser.databinding.ActivityMainBinding

/** Controller: translates UI events into model queries and updates the View. */
class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding
    private val adviser = ProgramAdviserModel()
    private var entryCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            view.setPadding(0, insets.systemWindowInsetTop, 0, insets.systemWindowInsetBottom)
            insets
        }

        binding.addButton.setOnClickListener { addEntry() }
        binding.clearButton.setOnClickListener { clearEntries() }
        binding.queryButton.setOnClickListener { queryAdviser() }
        updateCount()
    }

    private fun addEntry() {
        val content = binding.entryInput.text.toString().trim()
        if (content.isBlank()) {
            binding.entryInput.error = getString(R.string.empty_entry_error)
            return
        }

        entryCount += 1
        val newView = TextView(this).apply {
            text = getString(R.string.entry_item_format, entryCount, content)
            textSize = 15f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            setPadding(dp(16), dp(13), dp(16), dp(13))
            background = GradientDrawable().apply {
                cornerRadius = dp(15).toFloat()
                setColor(Color.rgb(27, 42, 72))
                setStroke(dp(1), Color.rgb(50, 78, 117))
            }
        }
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { bottomMargin = dp(9) }
        binding.entriesContainer.addView(newView, params)
        binding.emptyState.visibility = View.GONE
        binding.entryInput.text.clear()
        updateCount()
        binding.entriesScroll.post { binding.entriesScroll.fullScroll(View.FOCUS_DOWN) }
    }

    private fun clearEntries() {
        binding.entriesContainer.removeViews(1, binding.entriesContainer.childCount - 1)
        entryCount = 0
        binding.emptyState.visibility = View.VISIBLE
        updateCount()
    }

    private fun updateCount() {
        binding.entryCount.text = getString(R.string.entry_count_format, entryCount)
    }

    private fun queryAdviser() {
        val direction = when (binding.directionGroup.checkedRadioButtonId) {
            R.id.directionAndroid -> ProgramAdviserModel.Direction.ANDROID
            R.id.directionWeb -> ProgramAdviserModel.Direction.WEB
            R.id.directionData -> ProgramAdviserModel.Direction.DATA
            R.id.directionAi -> ProgramAdviserModel.Direction.AI
            else -> null
        }

        if (direction == null) {
            binding.resultText.setText(R.string.choose_direction_first)
            return
        }
        binding.resultText.setText(adviser.recommendationFor(direction))
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()
}
