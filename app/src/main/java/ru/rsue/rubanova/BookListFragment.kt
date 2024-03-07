package ru.rsue.rubanova

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class BookListFragment : Fragment() {
    private var bookRecyclerView: RecyclerView? = null
    private var adapter: BookAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_book_list, container,
            false
        )
        bookRecyclerView = view
            .findViewById(R.id.book_recycler_view)
        bookRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        updateUI()
        return view
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val bookLab = BookLab.get(requireActivity())
        val books = bookLab.books
        if (adapter == null) {
            adapter = BookAdapter(books)
            bookRecyclerView!!.adapter = adapter
        }
        else
            adapter!!.notifyDataSetChanged()
    }

    private class BookHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private var titleTextView: TextView = itemView.findViewById(R.id.list_item_book_title_text_view)
        private var dateTextView: TextView = itemView.findViewById(R.id.list_item_book_date_text_view)
        private var readedCheckBox: CheckBox = itemView.findViewById(R.id.list_item_book_readed_check_box)
        private lateinit var book: Book

        fun bindBook(book: Book) {
            this.book = book
            titleTextView.text = book.title
            dateTextView.setText(book.date.toString())
            readedCheckBox.setChecked(book.isReaded)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = BookActivity.newIntent(context, book.id)
            context.startActivity(intent)
        }

    }

    private class BookAdapter(books: List<Book>?) :
        RecyclerView.Adapter<BookHolder?>() {
        private var books: List<Book>? = null
        init {
            this.books = books
        }
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): BookHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.list_item_book, parent, false)
            return BookHolder(view)
        }
        override fun onBindViewHolder(holder: BookHolder,
                                      position: Int) {
            val book = books!![position]
            holder.bindBook(book)
        }
        override fun getItemCount(): Int {
            return books!!.size
        }
    }
}