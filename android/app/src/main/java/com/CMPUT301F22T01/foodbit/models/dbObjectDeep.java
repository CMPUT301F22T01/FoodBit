package com.CMPUT301F22T01.foodbit.models;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public interface dbObjectDeep {
    dbObjectDeep createFromDoc(QueryDocumentSnapshot t);
}
