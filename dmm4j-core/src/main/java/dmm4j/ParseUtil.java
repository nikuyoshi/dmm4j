/*
 * Copyright 2015 Hiroki Uchida
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package dmm4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import dmm4j.json.JSONException;
import dmm4j.json.JSONObject;

/**
 * A tiny parse utility class.
 */
final class ParseUtil {
  private ParseUtil() {
    // should never be instantiated
    throw new AssertionError();
  }


  public static String getRawString(String name, JSONObject json) {
    try {
      if (json.isNull(name)) {
        return null;
      } else {
        return json.getString(name);
      }
    } catch (JSONException jsone) {
      return null;
    }
  }

  static String getURLDecodedString(String name, JSONObject json) {
    String returnValue = getRawString(name, json);
    if (returnValue != null) {
      try {
        returnValue = URLDecoder.decode(returnValue, "UTF-8");
      } catch (UnsupportedEncodingException ignore) {
      }
    }
    return returnValue;
  }

  public static Date parseTrendsDate(String asOfStr) throws DmmException {
    Date parsed;
    switch (asOfStr.length()) {
      case 10:
        parsed = new Date(Long.parseLong(asOfStr) * 1000);
        break;
      case 20:
        parsed = getDate(asOfStr, "yyyy-MM-dd'T'HH:mm:ss'Z'");
        break;
      default:
        parsed = getDate(asOfStr, "EEE, d MMM yyyy HH:mm:ss z");
    }
    return parsed;
  }

  private final static Map<String, LinkedBlockingQueue<SimpleDateFormat>> formatMapQueue =
      new HashMap<String, LinkedBlockingQueue<SimpleDateFormat>>();

  public static Date getDate(String dateString, String format) throws DmmException {
    LinkedBlockingQueue<SimpleDateFormat> simpleDateFormats = formatMapQueue.get(format);
    if (simpleDateFormats == null) {
      simpleDateFormats = new LinkedBlockingQueue<SimpleDateFormat>();
      formatMapQueue.put(format, simpleDateFormats);
    }
    SimpleDateFormat sdf = simpleDateFormats.poll();
    if (null == sdf) {
      sdf = new SimpleDateFormat(format, Locale.US);
      sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
    }
    try {
      return sdf.parse(dateString);
    } catch (ParseException pe) {
      throw new DmmException("Unexpected date format(" + dateString + ") returned from dmm.com", pe);
    } finally {
      try {
        simpleDateFormats.put(sdf);
      } catch (InterruptedException ignore) {
        // the size of LinkedBlockingQueue is Integer.MAX by default.
        // there is no need to concern about this situation
      }
    }
  }

  public static int getInt(String name, JSONObject json) {
    return getInt(getRawString(name, json));
  }

  public static int getInt(String str) {
    if (null == str || "".equals(str) || "null".equals(str)) {
      return -1;
    } else {
      try {
        return Integer.valueOf(str);
      } catch (NumberFormatException nfe) {
        // workaround for the API side issue http://issue.dmm4j.org/youtrack/issue/TFJ-484
        return -1;
      }
    }
  }

  public static long getLong(String name, JSONObject json) {
    return getLong(getRawString(name, json));
  }

  public static long getLong(String str) {
    if (null == str || "".equals(str) || "null".equals(str)) {
      return -1;
    } else {
      // some count over 100 will be expressed as "100+"
      if (str.endsWith("+")) {
        str = str.substring(0, str.length() - 1);
        return Long.valueOf(str) + 1;
      }
      return Long.valueOf(str);
    }
  }

  public static double getDouble(String name, JSONObject json) {
    String str2 = getRawString(name, json);
    if (null == str2 || "".equals(str2) || "null".equals(str2)) {
      return -1;
    } else {
      return Double.valueOf(str2);
    }
  }

  public static boolean getBoolean(String name, JSONObject json) {
    String str = getRawString(name, json);
    if (null == str || "null".equals(str)) {
      return false;
    }
    return Boolean.valueOf(str);
  }
}
