package com.example.razorpayintegration

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.razorpayintegration.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject


class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {

    private lateinit var _binding: ActivityMainBinding
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btn1.setOnClickListener {
            startPayment(100)
        }

        binding.btn2.setOnClickListener {
            startPayment(500)
        }

        binding.btn2.setOnClickListener {
            startPayment(1000)
        }
    }

    private fun startPayment(payment: Int) {
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID("Your Api Id here")


        try {
            val options = JSONObject()
            options.put("name", "Sourabh bhatt")
            options.put("description", "Service Charge")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://images.pexels.com/photos/45201/kitty-cat-kitten-pet-45201.jpeg?cs=srgb&dl=pexels-pixabay-45201.jpg&fm=jpg")
            options.put("theme.color", "#000000");
            options.put("currency", "INR");
            var total = payment.toDouble()
            total *= 100
            options.put("amount", total)

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val preFill = JSONObject()
            preFill.put("email", " ")
            preFill.put("contact", " ")

            options.put("prefill", preFill)

            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }


    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this,"Congrats",Toast.LENGTH_LONG).show()
        Checkout.clearUserData(this)
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {

    }
}