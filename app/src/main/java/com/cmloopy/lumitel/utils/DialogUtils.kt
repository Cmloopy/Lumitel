package com.cmloopy.lumitel.utils

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.cmloopy.lumitel.R
import com.cmloopy.lumitel.views.CreateChannelActivity
import com.google.android.material.button.MaterialButton

object DialogUtils {
    private var progressDialog: AlertDialog? = null
    fun showSuccessDialog(context: Context, message: String , onDismiss: () -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_success, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)
            .create()
        dialog.setOnShowListener {
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialogView.findViewById<TextView>(R.id.txt_title_noti_success).text = message
        dialogView.findViewById<Button>(R.id.btn_dismiss_success).setOnClickListener {
            dialog.dismiss()
            onDismiss()
        }
        dialog.show()
    }

    fun showFailDialog(context: Context, message: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_failed, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)
            .create()
        dialog.setOnShowListener {
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialogView.findViewById<TextView>(R.id.txt_title_noti_failed).text = message
        dialogView.findViewById<Button>(R.id.btn_dismiss_failed).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showProgressDialog(context: Context, onDismiss: () -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_waiting, null)
        progressDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        progressDialog?.setOnShowListener {
            progressDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        dialogView.findViewById<Button>(R.id.btn_cancel_create).setOnClickListener {
            progressDialog?.dismiss()
            onDismiss()
        }
        progressDialog?.show()
    }

    fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
    fun channelIsNotExistDialog(context: Context, msisdn: String?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_noti_nochannel, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.btn_accept_create).setOnClickListener {
            val intent = Intent(context, CreateChannelActivity::class.java)
            intent.putExtra("msisdn", msisdn)
            context.startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()
    }
    fun notiDialog(context: Context){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_noti_waiting_approve, null)
        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.findViewById<MaterialButton>(R.id.btn_dismiss_warn).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}