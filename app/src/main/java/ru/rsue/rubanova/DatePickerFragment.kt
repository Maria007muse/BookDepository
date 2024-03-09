package ru.rsue.rubanova

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ru.rsue.rubanova.BookFragment.Companion.REQUEST_DATE
import java.util.*

class DatePickerFragment: DialogFragment() {
    companion object {
        const val EXTRA_DATE = "ru.rsue.rubanova.bookdepository.date"
        private const val ARG_DATE = "date"
        fun newInstance(date: Date?) =
            DatePickerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_DATE, date)
                }
            }
    }
    private lateinit var datePicker: DatePicker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_date, container, false)
        datePicker = view.findViewById(R.id.dialog_date_date_picker)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.ok_button).setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month
            val day = datePicker.dayOfMonth
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val selectedDate = calendar.time
            sendResult(selectedDate)
        }
    }

    private fun sendResult(date: Date) {
        setFragmentResult(
            REQUEST_DATE,
            Bundle().apply {
                putSerializable(EXTRA_DATE, date)
            }
        )
        dismiss()
    }
}