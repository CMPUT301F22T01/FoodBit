package com.CMPUT301F22T01.foodbit.models;

import com.google.firebase.firestore.QueryDocumentSnapshot;

/**
 * Give classes ability to customize how they are rebuilt from Database documents within DatabaseController.
 */
public interface dbObjectCustom {
    dbObjectCustom createFromDocCustom(QueryDocumentSnapshot t);
}
