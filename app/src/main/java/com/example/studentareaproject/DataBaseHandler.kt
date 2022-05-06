package com.example.studentareaproject


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyBD"
val TABLE_NAME = "users"
val COL_NAME = "username"
val COL_PASS = "password"
val COL_ID = "student_id"

val COL_ID_P = "id"
val COL_DISCIPLINE = "discipline"
val COL_CLASS_DAY = "class_day"
val COL_START_AT = "start_at"
val COL_END_AT = "end_at"
val COL_STUDENT_ID = "student_id"
val TABLE_NAME_SCHEDULE = "student_schedule"

val COL_IS_PRESENT = "is_present"
val TABLE_NAME_PRESENT = "student_present"


class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 6){
    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME +" VARCHAR(256)," +
                COL_PASS +" VARCHAR(256))";
        db?.execSQL(createUsersTable)

        val createStudentTable = "CREATE TABLE " + TABLE_NAME_SCHEDULE +" (" +
                COL_ID_P + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_DISCIPLINE + " varchar(255)," +
                COL_CLASS_DAY + " INTEGER," +
                COL_START_AT + " varchar(255)," +
                COL_END_AT + " varchar(255)," +
                COL_STUDENT_ID + " int)";
        db!!.execSQL(createStudentTable)

        val createStudentPresent = "CREATE TABLE " + TABLE_NAME_PRESENT +" (" +
            COL_ID_P + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_DISCIPLINE + " varchar(255)," +
            COL_CLASS_DAY + " varchar(255)," +
            COL_IS_PRESENT + " bool)";
        db!!.execSQL(createStudentPresent)

        val insertUser = "INSERT INTO users VALUES (1,\"admin\", \"admin\");"
        db?.execSQL(insertUser)

        val insertSchedule_1 = "INSERT INTO student_schedule VALUES (1, \"Programação para dispositivos moveis\", \"4\", \"19:10:00\", \"22:00:00\", 1);"
        val insertSchedule_2 = "INSERT INTO student_schedule VALUES (2, \"Linguagens formais e automatos\", \"6\", \"19:10:00\", \"22:00:00\", 1);"
        val insertSchedule_3 = "INSERT INTO student_schedule VALUES (3, \"Trabalho de graduacao interdisciplinar i\", \"2\", \"19:10:00\", \"20:20:00\", 1);"
        val insertSchedule_4 = "INSERT INTO student_schedule VALUES (4, \"Fundamentos de inteligência artificial\", \"5\", \"19:10:00\", \"22:00:00\", 1);"
        db?.execSQL(insertSchedule_1)
        db?.execSQL(insertSchedule_2)
        db?.execSQL(insertSchedule_3)
        db?.execSQL(insertSchedule_4)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME");
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_SCHEDULE");
        onCreate(db);
    }

    fun insertData(user : User){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, user.username)
        cv.put(COL_PASS, user.password)
        var result = db.insert(TABLE_NAME, null,cv)
        if(result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }
    /// O read data agora deve só fazer a pesquisa de um usuário e não de todos. O problema do login é pq ele não chega até o final do loop.
    fun readData() : MutableList<User>{
        var list : MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var user = User()
                user.id = result.getString(0).toInt()
                user.username = result.getString(1).toString()
                user.password = result.getString(2).toString()
                list.add(user)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    fun getSchedule(class_day : Int) : MutableList<Schedule>{
        var list : MutableList<Schedule> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM student_schedule INNER JOIN users ON student_schedule.student_id = users.student_id where student_schedule.student_id = 1 and class_day = $class_day;"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var schedule = Schedule()
                schedule.id = result.getString(0).toInt()
                schedule.discipline = result.getString(1).toString()
                schedule.class_day = result.getString(2).toString()
                schedule.start_at = result.getString(3).toString()
                schedule.end_at = result.getString(4).toString()
                schedule.student_id = result.getString(5).toInt()
                list.add(schedule)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

//    fun getUser(user : User){
//        val cv = ContentValues()
//        cv.put(COL_NAME, user.username)
//        userAuthData = 0
//        val db = this.readableDatabase
//        val query = "select * from " + TABLE_NAME + " where username == " + ""
//        val result = db.rawQuery(query, null)
//    }


}