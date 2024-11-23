package com.example.myapplication

import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.content.Context

class ShopBuy {
    companion object {
        // Show product dialog with price
        fun showProductDialog(
            context: Context, // 改為 Context
            productName: String,
            productDescription: String,
            productImageRes: Int,
            productPrice: Int // 新增金額參數
        ) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogLayout = inflater.inflate(R.layout.activity_shop_details, null)

            builder.setView(dialogLayout)

            val imageView = dialogLayout.findViewById<ImageView>(R.id.product_image)
            val nameView = dialogLayout.findViewById<TextView>(R.id.product_name)
            val descriptionView = dialogLayout.findViewById<TextView>(R.id.product_description)
            val priceView = dialogLayout.findViewById<TextView>(R.id.product_price) // 用來顯示金額的 TextView

            imageView.setImageResource(productImageRes)
            nameView.text = productName
            descriptionView.text = productDescription
            priceView.text = "NT$ ${productPrice}" // 設置金額

            val dialog = builder.create()

            dialogLayout.findViewById<Button>(R.id.button_buy).setOnClickListener {
                dialog.dismiss()
            }
            dialogLayout.findViewById<Button>(R.id.button_cancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        // Show product end dialog without price
        fun showProductEnd(
            context: Context, // 改為 Context
            productName: String,
            productDescription: String,
            productImageRes: Int,
            productPrice: Int
        ) {
            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            val dialogLayout = inflater.inflate(R.layout.activity_shop_end, null)

            builder.setView(dialogLayout)

            val imageView = dialogLayout.findViewById<ImageView>(R.id.product_image)
            val nameView = dialogLayout.findViewById<TextView>(R.id.product_name)
            val descriptionView = dialogLayout.findViewById<TextView>(R.id.product_description)
            val priceView = dialogLayout.findViewById<TextView>(R.id.product_price) // 用來顯示金額的 TextView

            imageView.setImageResource(productImageRes)
            nameView.text = productName
            descriptionView.text = productDescription
            priceView.text = "NT$ ${productPrice}"

            val dialog = builder.create()

            dialog.show()
        }
    }
}
