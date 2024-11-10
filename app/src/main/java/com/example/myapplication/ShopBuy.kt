package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ShopBuy {
    companion object {
        fun showProductDialog(context: Context, productName: String, productDescription: String, productImageRes: Int) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogLayout = inflater.inflate(R.layout.activity_shop_details, null)

            builder.setView(dialogLayout)

            val imageView = dialogLayout.findViewById<ImageView>(R.id.product_image)
            val nameView = dialogLayout.findViewById<TextView>(R.id.product_name)
            val descriptionView = dialogLayout.findViewById<TextView>(R.id.product_description)

            imageView.setImageResource(productImageRes)
            nameView.text = productName
            descriptionView.text = productDescription

            val dialog = builder.create()
            dialogLayout.findViewById<Button>(R.id.button_buy).setOnClickListener {
                dialog.dismiss()
            }
            dialogLayout.findViewById<Button>(R.id.button_cancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}
