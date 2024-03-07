package ru.rsue.rubanova

import BookDepository.SingleFragmentActivity
import androidx.fragment.app.Fragment

class BookListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment = BookListFragment()
}