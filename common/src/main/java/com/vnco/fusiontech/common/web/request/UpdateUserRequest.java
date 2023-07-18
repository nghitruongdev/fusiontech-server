package com.vnco.fusiontech.common.web.request;

import com.vnco.fusiontech.common.utils.FirebaseUtils;

public record UpdateUserRequest(
        String firstName,
        String lastName,
        String fullName,
        String password,
        String email,
        String phoneNumber,
        String photoUrl) {
    public UpdateUserRequest {
        if (phoneNumber != null) {
            phoneNumber = FirebaseUtils.convertToE164Format(phoneNumber);
        }
        if (fullName == null) {
            fullName = FirebaseUtils.composeFullName(firstName, lastName);
        }
    }
}
// export interface IUser {
// id?: number;
// firebaseUid: string;
// name?: string;
// email?: string;
// phoneNumber?: string;
// photoUrl?: string;
// dateOfBirth?: Date;
// gender?: boolean;
// defaultAddress?: ShippingAddress;
// _links: _links;
// }
