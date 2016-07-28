package com.thoughtworks.ketsu.util;

import com.google.gson.Gson;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;

public class AppUtils {

  public static DBObject toDBObject(Object pojo) {
    String json = new Gson().toJson(pojo);
    return (DBObject) JSON.parse(json);
  }

  public static Object fromDBObject(DBObject dbObj, Class clazz) {
    ObjectId id = (ObjectId)dbObj.get("_id");
    String x = id.toHexString();
    String s = id.toString();
    dbObj.put("_id", id);
    String json = dbObj.toString();


    return new Gson().fromJson(json, clazz);
  }
}