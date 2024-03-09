package ru.rsue.rubanova

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class BookPagerActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var books: List<Book>

    companion object {
        private const val EXTRA_BOOK_ID =
            "ru.rsue.rubanova.bookdepository.book_id"
        fun newIntent(packageContext: Context?, bookId: UUID?) = Intent(
            packageContext,
            BookPagerActivity::class.java
        ).apply {
            putExtra(EXTRA_BOOK_ID, bookId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_pager)
        val bookId = intent
            .getSerializableExtra(EXTRA_BOOK_ID) as UUID?
        viewPager = findViewById(R.id.activity_book_pager_view_pager)
        viewPager.adapter = ViewPagerAdapter(this)

        books = BookLab.get(this).books
        for (i in books.indices)
            if (books[i].id == bookId) {
                viewPager.currentItem = i
                break
            }
    }

    private class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        private val books: List<Book> =
            BookLab.get(fragmentActivity).books
        override fun getItemCount() = books.size
        override fun createFragment(position: Int) =
            BookFragment.newInstance(books[position].id)
    }

}