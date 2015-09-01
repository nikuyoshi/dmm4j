package dmm4j.http;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import dmm4j.DmmException;
import dmm4j.Version;
import dmm4j.log.Logger;

public abstract class HttpClientBase implements HttpClient, Serializable {
  private static final Logger logger = Logger.getLogger(HttpClientBase.class);
  private static final long serialVersionUID = -8016974810651763053L;
  protected final HttpClientConfiguration CONF;

  private final Map<String, String> requestHeaders;

  public HttpClientBase(HttpClientConfiguration conf) {
    this.CONF = conf;
    requestHeaders = new HashMap<String, String>();
    requestHeaders.put("X-Dmm-Client-Version", Version.getVersion());
    requestHeaders.put("X-Dmm-Client-URL", "http://Dmm4j.org/en/Dmm4j-" + Version.getVersion()
        + ".xml");
    requestHeaders.put("X-Dmm-Client", "Dmm4J");
    requestHeaders.put("User-Agent", "Dmm4j http://Dmm4j.org/ /" + Version.getVersion());
    if (conf.isGZIPEnabled()) {
      requestHeaders.put("Accept-Encoding", "gzip");
    }
  }

  protected boolean isProxyConfigured() {
    return CONF.getHttpProxyHost() != null && !CONF.getHttpProxyHost().equals("");
  }

  public void write(DataOutputStream out, String outStr) throws IOException {
    out.writeBytes(outStr);
    logger.debug(outStr);
  }

  public Map<String, String> getRequestHeaders() {
    return requestHeaders;
  }

  public void addDefaultRequestHeader(String name, String value) {
    requestHeaders.put(name, value);
  }

  public final HttpResponse request(HttpRequest req) throws DmmException {
    return handleRequest(req);
  }

  abstract HttpResponse handleRequest(HttpRequest req) throws DmmException;

  public HttpResponse get(String url) throws DmmException {
    return request(new HttpRequest(RequestMethod.GET, url, null, this.requestHeaders));
  }

  public HttpResponse post(String url) throws DmmException {
    return request(new HttpRequest(RequestMethod.POST, url, null, this.requestHeaders));
  }

  public HttpResponse delete(String url) throws DmmException {
    return request(new HttpRequest(RequestMethod.DELETE, url, null, this.requestHeaders));
  }

  public HttpResponse head(String url) throws DmmException {
    return request(new HttpRequest(RequestMethod.HEAD, url, null, this.requestHeaders));
  }

  public HttpResponse put(String url) throws DmmException {
    return request(new HttpRequest(RequestMethod.PUT, url, null, this.requestHeaders));
  }


}
