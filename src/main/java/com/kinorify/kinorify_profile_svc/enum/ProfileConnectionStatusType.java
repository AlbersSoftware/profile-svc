package com.kinorify.kinorify_profile_svc.enums;

import lombok.Getter;

@Getter
public enum ProfileConnectionStatusType {

    PENDING((short) 1),
    ACCEPTED((short) 2),
    DECLINED((short) 3),
    BLOCKED((short) 4),
    REMOVED((short) 5);

    private final short id;

    ProfileConnectionStatusType(short id) {
        this.id = id;
    }
}
