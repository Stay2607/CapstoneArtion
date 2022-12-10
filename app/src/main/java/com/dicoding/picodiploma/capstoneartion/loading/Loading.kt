package com.dicoding.picodiploma.capstoneartion.loading

import android.view.View
import com.google.android.material.progressindicator.CircularProgressIndicator

class Loading {
    fun showLoading(isLoading: Boolean, progressBar: CircularProgressIndicator) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}