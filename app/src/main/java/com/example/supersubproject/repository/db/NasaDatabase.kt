package com.example.supersubproject.repository.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.supersubproject.model.NasaResponseItem


//adding annotation for our databse entities and db version.
@Database(entities = [NasaResponseItem::class], version = 1)
abstract class NasaDatabase : RoomDatabase() {
    //below line is to create abstract variable for dao.
    abstract fun Dao(): Dao

    //we are creating an async task class to perform task in background.
    private class PopulateDbAsyncTask(instance: NasaDatabase?) : AsyncTask<Void?, Void?, Void?>() {

        init {
            val dao = instance?.Dao()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            return null
        }
    }

    companion object {
        //below line is to create instance for our databse class.
        private var instance: NasaDatabase? = null

        //on below line we are getting instance for our database.
         @Synchronized
        fun getInstance(context: Context): NasaDatabase? {
            //below line is to check if the instance is null or not.
            if (instance == null) {
                //if the instance is null we are creating a new instance
                instance =
                        //for creating a instance for our database we are creating a database builder and passing our database class with our database name.
                    Room.databaseBuilder(
                        context.applicationContext,
                        NasaDatabase::class.java, "nasa_database"
                    ) //below line is use to add fall back to destructive migration to our database.
                        .fallbackToDestructiveMigration() //below line is to add callback to our database.
                        .addCallback(roomCallback) //below line is to build our database.
                        .build()
            }
            //after creating an instance we are returning our instance
            return instance
        }

        //below line is to create a callback for our room database.
        private val roomCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                //this method is called when database is created and below line is to populate our data.
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }
}