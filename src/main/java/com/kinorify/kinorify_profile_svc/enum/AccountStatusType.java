package com.kinorify.kinorify_profile_svc.enums;

import lombok.Getter;

@Getter
public enum AccountStatusType {

    ACTIVE((short) 1),
    DISABLED((short) 2),
    DELETED((short) 3);

    private final short id;

    AccountStatusType(short id) {
        this.id = id;
    }
}
