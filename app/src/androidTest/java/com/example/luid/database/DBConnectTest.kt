import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import com.example.luid.database.DBConnect
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DBConnectTest {

    private lateinit var dbConnect: DBConnect
    private lateinit var db: SQLiteDatabase

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        dbConnect = DBConnect(context)
        db = dbConnect.writableDatabase
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun onCreate_databaseCreated() {
        // Verify that the database is created
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)
        assertTrue(cursor.moveToFirst())
        cursor.close()
    }

    @Test
    fun onCreate_tablesCreated() {
        // Verify that the tables are created
        val tableNames = arrayOf(
            DBConnect.questions_tb,
            DBConnect.user_records_tb,
            DBConnect.achievements_tb
        )
        for (tableName in tableNames) {
            val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'", null)
            assertTrue(cursor.moveToFirst())
            cursor.close()
        }
    }

    @Test
    fun onUpgrade_tablesDroppedAndCreated() {
        // Perform an upgrade by calling onUpgrade
        val oldVersion = DBConnect.DB_V
        val newVersion = oldVersion + 1
        dbConnect.onUpgrade(db, oldVersion, newVersion)

        // Verify that the tables are dropped and created again
        val tableNames = arrayOf(
            DBConnect.questions_tb,
            DBConnect.user_records_tb,
            DBConnect.achievements_tb
        )
        for (tableName in tableNames) {
            val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'", null)
            assertTrue(cursor.moveToFirst())
            cursor.close()
        }
    }
}
