package com.example.todolistubersnap.ui.component

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun ShowDatePicker(
    onDateSelected: (Long) -> Unit
) {
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    OutlinedButton(
        onClick = {
            val datePickerDialog = DatePickerDialog(
                mContext,
                { _, year, month, day ->
                    mCalendar.set(year, month, day)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(mCalendar.time)
                    mDate.value = formattedDate
                    onDateSelected(mCalendar.timeInMillis)
                },
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Select Due Date")
    }
}