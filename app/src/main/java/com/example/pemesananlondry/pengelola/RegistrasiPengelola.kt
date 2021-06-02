package com.example.pemesananlondry.pengelola

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pemesananlondry.MainActivity
import com.example.pemesananlondry.R
import com.example.pemesananlondry.pemesan.Pemesantest
import com.example.pemesananlondry.utill.App
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registrasi.*
import kotlinx.android.synthetic.main.activity_registrasi.et_email_anggota
import kotlinx.android.synthetic.main.activity_registrasipengelola.*
import kotlinx.android.synthetic.main.activity_registrasipengelola.et_email_pengelola
import kotlinx.android.synthetic.main.activity_registrasipengelola.et_passwd_pengelola
import kotlin.math.log

class RegistrasiPengelola : AppCompatActivity() {

     lateinit var ref : DatabaseReference
     lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasipengelola)
        auth = FirebaseAuth.getInstance()
        val app = App();

        ref = FirebaseDatabase.getInstance().getReference("Pengelola")

        btn_daftarpengelolaaa.setOnClickListener {
            registrasi()
        }
    }

    private fun registrasi() {

        //Proses Valiidasi
        if (et_email_pengelola.text.toString().isEmpty()) {
            et_email_pengelola.error = "Masukkan Email"
            et_email_pengelola.requestFocus()
            return

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(et_email_pengelola.text.toString()).matches()) {
            et_email_pengelola.error = "Masukkan Email Valid"
            et_email_pengelola.requestFocus()
        }
        if (et_nama_pengelola.text.toString().isEmpty()) {
            et_nama_pengelola.error = "Masukkan Nama"
            et_nama_pengelola.requestFocus()
            return
        }
        if (et_alamat_pengelola.text.toString().isEmpty()) {
            et_alamat_pengelola.error = "Masukkan Alamat"
            et_alamat_pengelola.requestFocus()
            return
        }
        if (et_passwd_pengelola.text.toString().isEmpty()) {
            et_passwd_pengelola.error = "Masukkan Password"
            et_passwd_pengelola.requestFocus()
            return
        }
        val email = et_email_pengelola.text.toString().trim()
        val pass = et_passwd_pengelola.text.toString().trim()

        Log.d("test",email)
        Log.d("test1",et_passwd_pengelola.text.toString())
        Log.d("test2",et_alamat_pengelola.text.toString())
        Log.d("test3",et_nama_pengelola.text.toString())

        auth.createUserWithEmailAndPassword(
                email, pass
        ).addOnCompleteListener(this@RegistrasiPengelola) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.sendEmailVerification()
                        ?.addOnCompleteListener { //task ->
                            Log.d("test",user.toString())
                            if (task.isSuccessful) {
                                simpanFirebase()
                                Toast.makeText(
                                        baseContext,
                                        "Silahkan Cek Email",
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
            } else {
                Toast.makeText(
                        baseContext, "Ulang Lagi",
                        Toast.LENGTH_SHORT
                ).show()
            }
            //Firebase database
        }
    }
    private fun simpanFirebase() {
        val nama = et_nama_registrasi.text.toString().trim()
        val email = et_email_anggota.text.toString().trim()
        val password = et_passwd_anggota.text.toString().trim()
        val alamat = et_alamat_registrasi.text.toString().trim()
        val user = Pemesantest(nama,email, password, alamat)

        //this.ref = FirebaseDatabase.getInstance().getReference("Anggota")
        ref.child(email).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
            Log.d("test", ref.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}