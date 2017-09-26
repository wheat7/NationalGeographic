package com.wheat7.nationalgeographic.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.wheat7.nationalgeographic.data.model.Detail
import com.wheat7.nationalgeographic.data.model.Picture
import java.io.Serializable

/**
 * Created by wheat7 on 2017/9/25.
 * https://github.com/wheat7
 */
/**
 * counttotal : 12
 * picture : [{"id":"14457","albumid":"1601","title":"〈寻找真正的维京人〉精选","content":"波兰的维京人重演者穿上盔甲，准备上演近身战斗。维京人的残暴并非浪得虚名：斯堪地那维亚男孩从小就接受作战训练，并且在社会制约下习于血腥暴力。 ","url":"http://pic01.bdatu.com/Upload/picimg/1496230162.jpg","size":"254945","addtime":"2017-05-31 19:29:25","author":"David Guttenfelder","thumb":"http://pic01.bdatu.com/Upload/picimg/1496230162.jpg","encoded":"1","weburl":"http://","type":"pic","yourshotlink":"","copyright":"","pmd5":"9f4c2c9dd41064b5bf5bd467bac8956e","sort":"14457"}]
 */
object Collects : Serializable {

    private var databaseHelper: DatabaseHelper? = null
    private var db: SQLiteDatabase? = null

//    private var counttotal: String? = "0"
    private var pictures: MutableList<Picture>? = null

    private fun initCollects(context : Context): MutableList<Picture> {
        databaseHelper = DatabaseHelper(context,
                "NationalGeographic.db", null, 1)
        db = databaseHelper!!.writableDatabase
        pictures = ArrayList<Picture>()
        val cursor: Cursor = db!!.query("Collects", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                var pic: Picture = Picture()
                pic.id = cursor.getString(cursor.getColumnIndex("id"))
                pic.albumid = cursor.getString(cursor.getColumnIndex("albumid"))
                pic.title = cursor.getString(cursor.getColumnIndex("title"))
                pic.content = cursor.getString(cursor.getColumnIndex("content"))
                pic.url = cursor.getString(cursor.getColumnIndex("url"))
                pic.size = cursor.getString(cursor.getColumnIndex("size"))
                pic.addtime = cursor.getString(cursor.getColumnIndex("addtime"))
                pic.author = cursor.getString(cursor.getColumnIndex("author"))
                pic.thumb = cursor.getString(cursor.getColumnIndex("thumb"))
                pic.encoded = cursor.getString(cursor.getColumnIndex("encoded"))
                pic.weburl = cursor.getString(cursor.getColumnIndex("weburl"))
                pic.type = cursor.getString(cursor.getColumnIndex("type"))
                pic.yourshotlink = cursor.getString(cursor.getColumnIndex("yourshotlink"))
                pic.copyright = cursor.getString(cursor.getColumnIndex("copyright"))
                pic.pmd5 = cursor.getString(cursor.getColumnIndex("pmd5"))
                pic.sort = cursor.getString(cursor.getColumnIndex("sort"))
                pictures?.add(pic)
            } while (cursor.moveToNext());
        }
        cursor.close()
//        counttotal = pictures?.size.toString()
        pictures?.reverse()
        return pictures!!
    }

    fun collectItem(pic: Picture) {
        pictures?.add(0 , pic)
        val values: ContentValues = ContentValues();
        values.put("id", pic.id)
        values.put("albumid", pic.albumid)
        values.put("title", pic.title)
        values.put("content", pic.content)
        values.put("url", pic.url)
        values.put("size", pic.size)
        values.put("addtime", pic.addtime)
        values.put("author", pic.author)
        values.put("thumb", pic.thumb)
        values.put("encoded", pic.encoded)
        values.put("weburl", pic.weburl)
        values.put("type", pic.type)
        values.put("yourshotlink", pic.yourshotlink)
        values.put("copyright", pic.copyright)
        values.put("pmd5", pic.pmd5)
        values.put("sort", pic.sort)
        db!!.insert("Collects", null, values)
//        counttotal = pictures?.size.toString()
    }

    fun deleteItem(id: String?) {
        db!!.delete("Collects", "id=?", arrayOf(id))
        var tempItem: Picture? = null
        for (pic: Picture in pictures!!) {
            if (pic.id.equals(id)) {
                tempItem = pic
            }
        }
        if (tempItem != null) {
            pictures?.remove(tempItem)
//            counttotal = pictures?.size.toString()
        }
    }

    fun isCollect(pic: Picture): Boolean {
        for (item: Picture in pictures!!) {
            if (item.id .equals(pic.id)) {
                return true
            }
        }
        return false
    }

    fun getCollect(context: Context): MutableList<Picture> {
        if (pictures == null) {
            return initCollects(context)
        }
        return pictures!!
    }

    fun getDetail(): Detail {
        val detail: Detail = Detail()
        detail.counttotal = pictures?.size.toString()
        detail.picture = pictures
        return detail
    }

    fun destroy() {
//        db!!.close()
//        pictures = null
//        counttotal = null
    }

}

