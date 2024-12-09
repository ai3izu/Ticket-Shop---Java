package org.db.hibernate;

import lombok.Getter;
import lombok.Setter;

public class UserSession {
    @Setter
    @Getter
    private static Integer loggedInUserId;
}
