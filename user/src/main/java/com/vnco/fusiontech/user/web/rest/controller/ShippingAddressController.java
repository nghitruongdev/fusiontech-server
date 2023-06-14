package com.vnco.fusiontech.user.web.rest.controller;

import com.vnco.fusiontech.user.repository.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
public class ShippingAddressController {
    private final ShippingAddressRepository addressRepository;
}
