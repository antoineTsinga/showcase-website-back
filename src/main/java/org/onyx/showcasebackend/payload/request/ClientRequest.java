package org.onyx.showcasebackend.payload.request;

import java.util.Collection;

public class ClientRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Long tel;
    private String avatar;
    private String email;
    private String password;
    private Collection<Long> orders;
}
