package com.example.myapplication

import com.drake.brv.utils.BRV
import org.strongswan.android.logic.StrongSwanApplication

class MyApplication : StrongSwanApplication() {

    override fun onCreate() {
        super.onCreate()
        BRV.modelId = BR.m
    }
}