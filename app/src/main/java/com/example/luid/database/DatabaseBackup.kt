package com.example.luid.database
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class DatabaseBackup() {

    fun backup(context: Context, uid: String){
        val databasePath = context.getDatabasePath("LuidDB.db").absolutePath
        val backupFile = File(databasePath)
        val renamedBackupFile = File(context.cacheDir, "user_$uid.db")
        backupFile.renameTo(renamedBackupFile)

        val storageRef = FirebaseStorage.getInstance().reference
        val backupRef = storageRef.child("backups/user_$uid/user_$uid.db")
        val uploadTask = backupRef.putFile(Uri.fromFile(renamedBackupFile))

        uploadTask.addOnSuccessListener {
          //  Toast.makeText(context, "uploadTask successful..", Toast.LENGTH_SHORT)
        }.addOnFailureListener { exception ->
           // Toast.makeText(context, "uploadTask failed..", Toast.LENGTH_SHORT)
//
        }
    }
    fun restore(context: Context, uid: String) {
        val databasePath = context.getDatabasePath("LuidDB.db").absolutePath
        val absolutePath = File(databasePath)

        val storageRef = FirebaseStorage.getInstance().reference
        val backupRef = storageRef.child("backups/user_$uid/user_$uid.db")
        val localFile = File(context.cacheDir, "user_$uid.db")
        val renamedFile = File(context.cacheDir, "LuidDB.db")

        val downloadTask = backupRef.getFile(localFile)

        downloadTask.addOnSuccessListener {
            // Rename the file
            if (localFile.renameTo(renamedFile)) {
                // File renamed successfully
               // Toast.makeText(context, "File renamed successfully", Toast.LENGTH_SHORT).show()

                // Replace the existing database file with the restored file
                if (absolutePath.exists()) {
                    absolutePath.delete()
                }
                renamedFile.renameTo(absolutePath)

                // Use the restored database (replaced file) here
                // ...

            } else {
                // Failed to rename the file
              //  Toast.makeText(context, "Failed to rename the file", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
           // Toast.makeText(context, "Download failed: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }


}