package com.quran.labs.androidquran.util;

import com.quran.labs.androidquran.common.TranslationItem;
import com.quran.labs.androidquran.data.Constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

public class TranslationUtils {

   public static String getDefaultTranslation(Context context,
                                       List<TranslationItem> items) {
     final TranslationItem item = getDefaultTranslationItem(context, items);
     return item == null ? null : item.filename;
   }

  public static TranslationItem getDefaultTranslationItem(Context context,
      List<TranslationItem> items){
      if (items == null || items.size() == 0){ return null; }
      SharedPreferences prefs =
              PreferenceManager.getDefaultSharedPreferences(context);
      final String db =
          prefs.getString(Constants.PREF_ACTIVE_TRANSLATION, null);

      TranslationItem result = null;
      boolean changed = false;
      if (db == null){
         changed = true;
         result = items.get(0);
      }
      else {
         boolean found = false;
         for (TranslationItem item : items){
            if (item.filename.equals(db)){
               found = true;
               result = item;
               break;
            }
         }

         if (!found){
            changed = true;
            result = items.get(0);
         }
      }

      if (changed && result != null){
         prefs.edit().putString(
             Constants.PREF_ACTIVE_TRANSLATION, result.filename)
                 .commit();
      }

      return result;
   }
}
