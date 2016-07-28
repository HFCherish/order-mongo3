package com.thoughtworks.ketsu.web.jersey;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class Routes {

    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI productUrl(String prodId) {
        return URI.create(String.format(baseUri + "products/" + prodId));
    }

    public URI userUrl(String userId) {
        return URI.create(String.format(baseUri + "users/" + userId));
    }

    public URI orderUrl(String userId, String orderId) {
        return URI.create(String.format(baseUri + "users/" + userId + "/orders/" + orderId));
    }

    public URI paymentUrl(String userId, String orderId) {
        return URI.create(String.format(baseUri + "users/" + userId + "/orders/" + orderId + "/payment"));
    }
}
