package com.example.myapplication

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    view.load(Uri.parse(url))
}