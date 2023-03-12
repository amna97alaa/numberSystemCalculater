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




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Setupviews()
        textWatcher = CustomTextWatcher()
        decimalEditText.onFocusChangeListener = CustomFocusListener()
        binaryEditText.onFocusChangeListener = CustomFocusListener()
        octalEditText.onFocusChangeListener = CustomFocusListener()
        hexEditText.onFocusChangeListener = CustomFocusListener()

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener {
            // Clear all the text in the EditTexts
            binaryEditText.setText("")
            decimalEditText.setText("")
            octalEditText.setText("")
            hexEditText.setText("")

        }


    }

    private fun Setupviews(){
        decimalEditText = findViewById(R.id.decimal)
        binaryEditText = findViewById(R.id.binaryEditText)
        octalEditText = findViewById(R.id.octal)
        hexEditText = findViewById(R.id.hex)
    }



    inner class CustomTextWatcher : TextWatcher{
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            when (focusedEditText?.id) {
                decimalEditText.id -> {
                    if (s!!.matches(Regex("[0-9]+")) ) {
                   binaryEditText.setText(convertToBase(s.toString(), 10, 2))
                    octalEditText.setText(convertToBase(s.toString(), 10, 8))
                    hexEditText.setText(convertToBase(s.toString(), 10, 16))
                }
                }

                octalEditText.id -> {
                    if (s!!.matches(Regex("0[0-7]+"))) {
                    binaryEditText.setText(convertToBase(s.toString(),8 , 2))
                    decimalEditText.setText(convertToBase(s.toString(), 8, 10))
                    hexEditText.setText(convertToBase(s.toString(), 8, 16))
                }

                }



               binaryEditText.id -> {
                    if (s!!.matches("[01]+".toRegex())) {
                    decimalEditText.setText(convertToBase(s.toString(), 2, 10))
                    octalEditText.setText(convertToBase(s.toString(), 2, 8))
                    hexEditText.setText(convertToBase(s.toString(), 2, 16))
                }

                }


                hexEditText.id -> {
                    if(s!!.matches(Regex("[0-9A-Fa-f]+"))){
                    binaryEditText.setText(convertToBase(s.toString(), 16, 2))
                    decimalEditText.setText(convertToBase(s.toString(), 16, 10))
                    octalEditText.setText(convertToBase(s.toString(), 16, 8))
                }

                }

            }
        }

        override fun afterTextChanged(s: Editable?) {
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
    return input.toLong(sourceBase).toString(targetBase)
}

