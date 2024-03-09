package ru.rsue.rubanova

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class BookFragment  : Fragment() {
    companion object {
        private const val ARG_BOOK_ID = "book_id"
        const val REQUEST_DATE = "REQUEST_DATE"
        const val REQUEST_TIME = "REQUEST_TIME"

        fun newInstance(bookId: UUID?) =
            BookFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_BOOK_ID, bookId)
                }
            }
    }
    private var book: Book? = null
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var isReadedCheckBox: CheckBox
    private val DIALOG_DATE = "DialogDate"

    private lateinit var timeButton: Button
    private val DIALOG_TIME = "DialogTime"

    override fun onCreate(savedInstanceState: Bundle?) {
        super. onCreate(savedInstanceState)
        val bookId = requireArguments().getSerializable(ARG_BOOK_ID) as
                UUID?
        book = BookLab.get(requireActivity()).getBook(bookId as UUID)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_book, container, false)
        titleField = v.findViewById(R.id.book_title)
        titleField.setText(book?.title)
        titleField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int) {
                book?.title = s.toString()
            }
            override fun afterTextChanged(c: Editable) {

            }
        })

        dateButton = v.findViewById(R.id.book_date)
        dateButton.text = DateFormat.getDateInstance(DateFormat.LONG, Locale("ru")).format(book?.date)
        dateButton.setOnClickListener{
            val manager = parentFragmentManager
            val dialog = DatePickerFragment.newInstance(book?.date)
            manager.setFragmentResultListener(REQUEST_DATE, this) {
                    requestKey, bundle ->
                val selectedDate =
                    bundle.getSerializable(DatePickerFragment.EXTRA_DATE)
                            as Date
                book?.date = selectedDate
                val formattedDate = DateFormat.getDateInstance(DateFormat.LONG, Locale("ru")).format(selectedDate)
                dateButton.text = formattedDate
            }
            dialog.show(manager, DIALOG_DATE)
        }

        timeButton = v.findViewById(R.id.book_time)
        timeButton.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(book?.time)
        timeButton.setOnClickListener{
            val manager = parentFragmentManager
            val dialog = TimePickerFragment.newInstance(book?.time)
            manager.setFragmentResultListener(REQUEST_TIME, this) { requestKey, bundle ->
                val selectedTime = bundle.getSerializable(TimePickerFragment.EXTRA_TIME) as Date
                book?.time = selectedTime
                val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedTime)
                timeButton.text = formattedTime
            }
            dialog.show(manager, DIALOG_TIME)
        }


        isReadedCheckBox = v.findViewById(R.id.book_readed)
        isReadedCheckBox.setOnCheckedChangeListener{
                compoundButton: CompoundButton,
                isChecked: Boolean ->
            book?.isReaded = isChecked
        }
        return v
    }
}