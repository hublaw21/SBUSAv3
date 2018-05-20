package com.example.khubbart.mysbusaappv3;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ElementDao {

    @Insert
    void insertAllElements(Element element);

    @Delete
    void delete(Element element);

    @Query("DELETE FROM element_table")
    void deleteAllElements();

    @Query("SELECT * FROM element_table ORDER BY elementID ASC")  //original working
        //@Query("SELECT * from element_table ORDER BY element ASC")  //original working
        // @Query("SELECT * from element_table ORDER BY elementID ASC") // first multi-field attempt
        //LiveData<List<Element>> getAllElements();
    List<Element> getAllElements();

    @Query("SELECT COUNT(*) FROM element_table")
    int rowCount();
    /*
    @Query("SELECT COUNT(*) FROM element_table WHERE elementID LIKE elementID")
    Element findElement(String elementID);
    */
}

