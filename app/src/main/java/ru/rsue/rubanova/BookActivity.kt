package ru.rsue.rubanova

import BookDepository.SingleFragmentActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*

class BookActivity : SingleFragmentActivity() {
    companion object {
        private val EXTRA_BOOK_ID = "ru.rsue.android.bookdepository.book_id"
        fun newIntent(packageContext: Context?, bookId: UUID?): Intent? {
            val intent = Intent(packageContext, BookActivity::class.java)
            intent.putExtra(EXTRA_BOOK_ID, bookId)
            return intent
        }
    }

    override fun createFragment() : BookFragment{
        val bookId = intent
            .getSerializableExtra(EXTRA_BOOK_ID) as UUID?
        return BookFragment.newInstance(bookId)
    }
}