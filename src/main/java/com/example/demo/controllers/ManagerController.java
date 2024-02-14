package com.example.demo.controllers;

import com.example.demo.dto.manager.ManagerRequest;
import com.example.demo.dto.manager.ManagerResponse;
import com.example.demo.service.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping("/getAll")
    public List<ManagerResponse> getAll() {
        return managerService.getAll();
    }

    @GetMapping("/findByEmail/{email}")
    public ManagerResponse findByEmail(@PathVariable String email) {
        return managerService.findByEmail(email);
    }

    @PutMapping("/updateByEmail/{email}")
    public void updateByEmail(@PathVariable String email,@RequestBody ManagerRequest managerRequest) {
        managerService.updateByEmail(email, managerRequest);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public void deleteByEmail(@PathVariable String email) {
        managerService.deleteByEmail(email);
    }
}
