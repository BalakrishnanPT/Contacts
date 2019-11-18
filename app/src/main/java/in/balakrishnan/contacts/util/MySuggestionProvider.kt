package `in`.balakrishnan.contacts.util

import android.content.SearchRecentSuggestionsProvider
import android.database.Cursor
import android.net.Uri

class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }


    companion object {
        const val AUTHORITY = "in.balakrishnan.contacts.MySuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}