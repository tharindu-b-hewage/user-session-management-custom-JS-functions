/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.custom.conditional.auth.functions.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticatedUser;
import org.wso2.carbon.identity.application.authentication.framework.exception.UserSessionException;
import org.wso2.carbon.identity.custom.conditional.auth.functions.user.internal.UserFunctionsServiceHolder;

public class KillUserSessionsImpl implements KillUserSessions {

    private static final Log LOG = LogFactory.getLog(KillUserSessions.class);

    public boolean killUserSessions(JsAuthenticatedUser user) {

        String tenantDomain = user.getWrapped().getTenantDomain();
        String userStoreDomain = user.getWrapped().getUserStoreDomain();
        String username = user.getWrapped().getUserName();
        try {
            UserFunctionsServiceHolder.getInstance().getUserSessionManagementService()
                    .terminateSessionsOfUser(username, userStoreDomain, tenantDomain);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Killed all the sessions of the user: " + username);
            }
            return true;
        } catch (UserSessionException e) {
            LOG.error("Error occurred while retrieving the UserID: ", e);
        }
        return false;
    }

}
