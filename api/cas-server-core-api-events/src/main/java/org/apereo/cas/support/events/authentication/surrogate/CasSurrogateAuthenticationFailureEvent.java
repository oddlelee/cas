package org.apereo.cas.support.events.authentication.surrogate;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.support.events.AbstractCasEvent;
import lombok.Getter;

/**
 * This is {@link CasSurrogateAuthenticationFailureEvent}.
 *
 * @author Misagh Moayyed
 * @since 5.2.0
 */
@Slf4j
@ToString(callSuper = true)
@Getter
public class CasSurrogateAuthenticationFailureEvent extends AbstractCasEvent {

    private static final long serialVersionUID = 8059647975948452375L;

    private final Principal principal;

    private final String surrogate;

    /**
     * Instantiates a new Abstract cas sso event.
     *
     * @param source    the source
     * @param principal the principal
     * @param surrogate the surrogate
     */
    public CasSurrogateAuthenticationFailureEvent(final Object source, final Principal principal, final String surrogate) {
        super(source);
        this.principal = principal;
        this.surrogate = surrogate;
    }

    public Principal getPrincipal() {
        return principal;
    }
}
