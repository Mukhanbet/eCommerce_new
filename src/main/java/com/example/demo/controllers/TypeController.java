package com.example.demo.controllers;

import com.example.demo.dto.type.TypeRequest;
import com.example.demo.dto.type.TypeResponse;
import com.example.demo.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/type")
public class TypeController {
    private final TypeService typeService;

    @GetMapping("/getAll")
    public List<TypeResponse> getAll() {
        return typeService.getAll();
    }

    @GetMapping("/findByName/{name}")
    public TypeResponse findByName(@PathVariable String name) {
        return typeService.findByName(name);
    }

    @PutMapping("/updateByName/{name}")
    public void updateByName(@PathVariable String name,@RequestBody TypeRequest typeRequest) {
        typeService.updateByName(name, typeRequest);
    }

    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable String name) {
        typeService.deleteByName(name);
    }

    @PostMapping("/create")
    public void create(@RequestBody TypeRequest typeRequest) {
        typeService.create(typeRequest);
    }
}
