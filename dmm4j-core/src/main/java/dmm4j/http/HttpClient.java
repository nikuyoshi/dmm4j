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

package dmm4j.http;


import java.util.Map;

import dmm4j.DmmException;

/**
 * A utility class to handle HTTP request/response.
 *
 */
public interface HttpClient {

  void addDefaultRequestHeader(String name, String value);

  Map<String, String> getRequestHeaders();

  HttpResponse request(HttpRequest req) throws DmmException;

  HttpResponse get(String url) throws DmmException;

  HttpResponse post(String url) throws DmmException;

  HttpResponse delete(String url) throws DmmException;

  HttpResponse head(String url) throws DmmException;

  HttpResponse put(String url) throws DmmException;
}
