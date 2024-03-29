package com.vnco.fusiontech.user.web.rest.request;

import com.vnco.fusiontech.common.constraint.NullOrNotBlank;
import com.vnco.fusiontech.user.entity.User.Gender;
import com.vnco.fusiontech.user.entity.roles.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

import java.util.Date;
import java.util.List;

public record UserRequest(
        String firstName,
        String lastName,
        @NotBlank (groups = {OnRegister.class, OnCreate.class})
        @Pattern (
                regexp = "^0\\d{9}$", message = "Số điện thoại không đúng định dạng")
        String phoneNumber,
        @NotBlank (
                groups = {OnRegister.class, OnCreate.class}
        ) @Email String email,
        Gender gender,
        Date dateOfBirth,
        @NullOrNotBlank String image,
        @NotBlank (groups = {OnRegister.class}) @Null (groups = {OnCreate.class, OnUpdate.class}) String password,
        List<Roles> roles){

  public interface OnRegister {

  }

  public interface OnCreate {
  }

  public interface OnUpdate {
  }
}
