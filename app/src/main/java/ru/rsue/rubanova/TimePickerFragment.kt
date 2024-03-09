package ru.rsue.rubanova

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.*

class TimePickerFragment : DialogFragment() {
    companion object {
        const val EXTRA_TIME = "ru.rsue.rubanova.bookdepository.time"
        private const val ARG_TIME = "time"
        fun newInstance(time: Date?) =
            TimePickerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TIME, time)
                }
            }
    }
    private lateinit var timePicker: TimePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_time, null)

        val time = arguments?.getSerializable(ARG_TIME) as Date
        val calendar = Calendar.getInstance()
        calendar.time = time
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        timePicker = view.findViewById(R.id.dialog_time_time_picker)
        timePicker.setIs24HourView(true)
        timePicker.hour = hour
        timePicker.minute = minute

        return AlertDialog.Builder(requireActivity())
            .setView(view)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                val hour = timePicker.hour
                val minute = timePicker.minute
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                sendResult(BookFragment.REQUEST_TIME, calendar.time)
            }
            .create()
    }

    private fun sendResult(requestKey: String, time: Date) {
        setFragmentResult(
            requestKey,
            bundleOf(EXTRA_TIME to time)
        )
    }
}
