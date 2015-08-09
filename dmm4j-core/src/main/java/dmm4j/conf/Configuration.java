/*
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

package dmm4j.conf;

import java.util.Properties;

import dmm4j.http.HttpClientConfiguration;

/**
 * @author nikuyoshi
 */
public interface Configuration extends java.io.Serializable {

  boolean isDebugEnabled();

  boolean isApplicationOnlyAuthEnabled();

  String getUser();

  String getPassword();

  // methods for HttpClientConfiguration
  HttpClientConfiguration getHttpClientConfiguration();

  int getHttpStreamingReadTimeout();

  // oauth related setter/getters

  String getOAuthConsumerKey();

  String getOAuthConsumerSecret();

  String getOAuthAccessToken();

  String getOAuthAccessTokenSecret();

  String getOAuth2TokenType();

  String getOAuth2AccessToken();

  String getOAuth2Scope();

  String getRestBaseURL();

  String getUploadBaseURL();

  String getStreamBaseURL();

  String getOAuthRequestTokenURL();

  String getOAuthAuthorizationURL();

  String getOAuthAccessTokenURL();

  String getOAuthAuthenticationURL();

  String getOAuth2TokenURL();

  String getOAuth2InvalidateTokenURL();

  String getUserStreamBaseURL();

  String getSiteStreamBaseURL();

  boolean isIncludeMyRetweetEnabled();

  boolean isJSONStoreEnabled();

  boolean isMBeanEnabled();

  boolean isUserStreamRepliesAllEnabled();

  boolean isUserStreamWithFollowingsEnabled();

  boolean isStallWarningsEnabled();

  String getMediaProvider();

  String getMediaProviderAPIKey();

  Properties getMediaProviderParameters();

  int getAsyncNumThreads();

  long getContributingTo();

  String getDispatcherImpl();

  String getLoggerFactory();

  boolean isIncludeEntitiesEnabled();

  boolean isTrimUserEnabled();

  boolean isDaemonEnabled();
}
