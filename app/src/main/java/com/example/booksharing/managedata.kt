package com.example.booksharing.firestore
import android.content.Context
import android.util.Log
import android.widget.Toast
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
    //データを取得(タグ検索利用)のこりはここの実装
    suspend fun getbookbytag(db:FirebaseFirestore,tag:String):List<dataforapi>{
        val booklist= mutableListOf<dataforapi>()
        try{
            val result=db.collection("C0de").whereEqualTo().get().await()
            for (document in result){
                booklist.add(dataforapi(
                    detaildata()
                ))
            }

        }catch(e:Exception){
            Log.d("methodtest","error occured: ${e.message} ${e.cause}")
        }
        return booklist
    }
    //本を削除
    fun deletebook(db:FirebaseFirestore,collection:String, document:String,detail:detaildata,context:Context){
        val bookRef=db.collection(collection).document(document)
        bookRef.delete()
            .addOnSuccessListener{
                Toast.makeText(context,"Successflly deleted!",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                e ->Toast.makeText(context,"failed to delete",Toast.LENGTH_SHORT).show()
                Log.d("methodTest", "error ocurred: ${e.message} ${e.cause}")
            }
    }
}