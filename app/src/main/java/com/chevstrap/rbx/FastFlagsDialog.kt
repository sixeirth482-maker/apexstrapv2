package com.chevstrap.rbx

import android.app.Activity
import android.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

/** Simple Fast Flags editor: add or update a flag, tap a row to delete it. */
object FastFlagsDialog {

    fun show(activity: Activity?) {
        val act = activity ?: return

        val root = LinearLayout(act).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 30, 40, 30)
        }

        val listLayout = LinearLayout(act).apply {
            orientation = LinearLayout.VERTICAL
        }
        root.addView(listLayout)

        val nameInput = EditText(act).apply {
            hint = "Flag name, e.g. FFlagDebugDisplayFPS"
        }
        val valueInput = EditText(act).apply {
            hint = "Value, e.g. true or 4"
        }
        root.addView(nameInput)
        root.addView(valueInput)

        fun refresh() {
            listLayout.removeAllViews()
            val json = FastFlagsManager.load()
            json.keys().asSequence().toList().sorted().forEach { key ->
                val row = TextView(act).apply {
                    text = "$key = ${json.get(key)}   (tap to delete)"
                    setPadding(0, 16, 0, 16)
                    setOnClickListener {
                        FastFlagsManager.remove(key)
                        refresh()
                    }
                }
                listLayout.addView(row)
            }
        }
        refresh()

        AlertDialog.Builder(act)
            .setTitle("Fast Flags")
            .setView(ScrollView(act).apply { addView(root) })
            .setPositiveButton("Save") { _, _ ->
                val name = nameInput.text.toString().trim()
                if (name.isNotEmpty()) {
                    FastFlagsManager.set(name, valueInput.text.toString().trim())
                }
            }
            .setNegativeButton("Close", null)
            .show()
    }
}
