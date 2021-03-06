package org.apereo.cas.web.flow.login;

import lombok.extern.slf4j.Slf4j;
import org.apereo.cas.services.ServicesManager;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * This is {@link InitializeLoginAction}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@Slf4j
public class InitializeLoginAction extends AbstractAction {



    /** The services manager with access to the registry. **/
    protected ServicesManager servicesManager;

    public InitializeLoginAction(final ServicesManager servicesManager) {
        this.servicesManager = servicesManager;
    }

    @Override
    protected Event doExecute(final RequestContext requestContext) throws Exception {
        LOGGER.debug("Initialized login sequence");
        return success();
    }
}
