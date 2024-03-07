package ru.rsue.rubanova

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
import java.util.*


class BookFragment  : Fragment() {
    companion object {
        private const val ARG_BOOK_ID = "book_id"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super. onCreate(savedInstanceState)
        val bookId = requireArguments().getSerializable(ARG_BOOK_ID) as
                UUID?
        book = BookLab.get(requireActivity()).getBook(bookId as UUID)

    }

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
        dateButton.isEnabled = false

        isReadedCheckBox = v.findViewById(R.id.book_readed)
        isReadedCheckBox.setOnCheckedChangeListener{
                compoundButton: CompoundButton,
                isChecked: Boolean ->
            book?.isReaded = isChecked
        }
        return v
    }
}