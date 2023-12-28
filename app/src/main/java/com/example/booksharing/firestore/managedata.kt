package com.example.booksharing.firestore
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class managedata {
    //本を追加
    fun registbook(isbn:String,tag1:String,tag2:String,tag3:String,tag4:String,tag5:String,db:FirebaseFirestore){
        val book= hashMapOf(
            "isbooked" to false,
            "borrower" to "",
            "isbn" to isbn,
            "tag1" to tag1,
            "tag2" to tag2,
            "tag3" to tag3,
            "tag4" to tag4,
            "tag5" to tag5
        )

        //ここは後で修正
        db.collection("orgname").document("ownisbn")
            //非同期処理に成功したときの処理
            .set(book).addOnSuccessListener {
                //Log.d()
            }
            //非同期処理に失敗したときの処理
            .addOnFailureListener {
                e -> //Log.w()
            }
    }
    //データを取得(タグ検索利用)
    suspend fun getbookbytag(db:FirebaseFirestore,tag:String):List<detaildata>{
        val booklist= mutableListOf<detaildata>()
        try{
            //val result=db.collection().whereEqualTo().get().await()

        }catch(e:Exception){}
        return booklist
    }
    //本を削除
    fun deletebook(db:FirebaseFirestore,collection:String, document:String,detail:detaildata){
        val bookRef=db.collection(collection).document(document)
        bookRef.delete()
            .addOnSuccessListener{}
            .addOnFailureListener{}
    }
}