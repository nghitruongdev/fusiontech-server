package com.vnco.fusiontech.user.web.rest.controller;

import com.vnco.fusiontech.user.entity.Role;
import com.vnco.fusiontech.user.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class RoleRestController {
    //TODO: validate role when user add more role like for example: user -> must be ROLE_USER,
    final
    RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/role")
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role newRole = roleService.createRole(role);
        return new ResponseEntity<>(newRole, HttpStatus.CREATED);
    }

    @PutMapping("/role/{oldRoleName}/rename/{newRoleName}")
    public Role updateRole(@PathVariable("oldRoleName") String oldRoleName,
                           @PathVariable("newRoleName") String newRoleName) {
        return roleService.updateRole(oldRoleName, newRoleName.toUpperCase());
    }

    @DeleteMapping("/role/{roleName}")
    public void deleteRole(@PathVariable Role roleName) {
        roleService.deleteRole(roleName);
    }
}
