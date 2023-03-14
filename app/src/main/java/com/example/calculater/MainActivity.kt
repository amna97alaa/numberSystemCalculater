package com.example.calculater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.widget.EditText
import android.text.Editable
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var decimalEditText: EditText
    private lateinit var binaryEditText: EditText
    private lateinit var octalEditText: EditText
    private lateinit var hexEditText: EditText
    private var focusedEditText :EditText? = null
    private var textWatcher :TextWatcher? = null
    private lateinit var clearButton :Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        addCallBacks()
    }

    private fun addCallBacks(){
        textWatcher = CustomTextWatcher()
        decimalEditText.onFocusChangeListener = CustomFocusListener()
        binaryEditText.onFocusChangeListener = CustomFocusListener()
        octalEditText.onFocusChangeListener = CustomFocusListener()
        hexEditText.onFocusChangeListener = CustomFocusListener()
        clearButton.setOnClickListener {
            // Clear all the text in the EditTexts
            clearInputs()
        }
    }

    private fun clearInputs(){
        binaryEditText.setText("")
        decimalEditText.setText("")
        octalEditText.setText("")
        hexEditText.setText("")
    }

    private fun setupViews(){
        clearButton = findViewById<Button>(R.id.buttonClear)
        decimalEditText = findViewById(R.id.decimal)
        binaryEditText = findViewById(R.id.binaryEditText)
        octalEditText = findViewById(R.id.octal)
        hexEditText = findViewById(R.id.hex)
    }

    inner class CustomTextWatcher : TextWatcher{
        override fun beforeTextChanged(
            s: CharSequence?, start: Int, count: Int, after: Int ) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            updateUiState(s.toString())
        }
        private fun updateUiState(s: String){
            when (focusedEditText?.id) {
                decimalEditText.id -> {
                        binaryEditText.setText(convertToBase(s, 10, 2))
                        octalEditText.setText(convertToBase(s, 10, 8))
                        hexEditText.setText(convertToBase(s, 10, 16))
                    }

                octalEditText.id -> {
                        binaryEditText.setText(convertToBase(s,8 , 2))
                        decimalEditText.setText(convertToBase(s, 8, 10))
                        hexEditText.setText(convertToBase(s, 8, 16))
                    }

                binaryEditText.id -> {
                        decimalEditText.setText(convertToBase(s, 2, 10))
                        octalEditText.setText(convertToBase(s, 2, 8))
                        hexEditText.setText(convertToBase(s, 2, 16))
                    }

                hexEditText.id -> {
                        binaryEditText.setText(convertToBase(s, 16, 2))
                        decimalEditText.setText(convertToBase(s, 16, 10))
                        octalEditText.setText(convertToBase(s, 16, 8))
                    }
            }
        }
    }

    inner class CustomFocusListener: View.OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {

            focusedEditText?.removeTextChangedListener(textWatcher)

            focusedEditText = v as EditText

            focusedEditText?.addTextChangedListener(textWatcher)

        }

    }
}

private fun convertToBase(
    input: String,
    sourceBase: Int,
    targetBase: Int) : String {
    return if(validateInput(input,sourceBase)) input.toLong(sourceBase).toString(targetBase) else ""
}

private fun validateInput(s: String, sourceBase: Int):Boolean{
    return when (sourceBase) {
        10 -> {
            (s.matches(Regex("[0-9]+")))
        }
        2 -> {
            (s.matches("[01]+".toRegex()))
        }
        8 -> {
            (s.matches(Regex("[0-7]+")))
        }
        16 -> {
            (s.matches(Regex("[0-9A-Fa-f]+")))
        }
        else -> {
            false
      }
    }
}