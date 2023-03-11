package com.fgm.laundrynfo.data.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend inline fun Query.awaitQueryValue(): DataSnapshot =
    suspendCancellableCoroutine { continuation ->
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resumeWith(Result.success(snapshot))
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(Throwable(error.message))
            }
        })

    }