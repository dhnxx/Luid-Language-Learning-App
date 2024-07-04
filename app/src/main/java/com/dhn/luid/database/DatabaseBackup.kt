package com.dhn.luid.database
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class DatabaseBackup() {

    fun backup(context: Context, uid: String) {
        val originalDatabasePath = context.getDatabasePath("LuidDB.db").absolutePath
        val originalDatabaseFile = File(originalDatabasePath)

        // Copy the original database file to a new location
        val backupDir = context.cacheDir // Choose a suitable backup directory
        val backupFile = File(backupDir, "LuidDB_backup.db")
        originalDatabaseFile.copyTo(backupFile, overwrite = true)

        // Rename the copied backup file
        val renamedBackupFile = File(backupDir, "user_$uid.db")
        backupFile.renameTo(renamedBackupFile)

        // Upload the renamed backup file to Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference
        val backupRef = storageRef.child("backups/user_$uid/${renamedBackupFile.name}")
        val uploadTask = backupRef.putFile(Uri.fromFile(renamedBackupFile))

        uploadTask.addOnSuccessListener {
            // Backup upload successful
            // Toast.makeText(context, "Backup uploaded successfully.", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { exception ->
            // Backup upload failed
            // Toast.makeText(context, "Backup upload failed.", Toast.LENGTH_SHORT).show()
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