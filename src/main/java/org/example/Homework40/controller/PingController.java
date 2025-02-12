package org.example.Homework40.controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ping")
public class PingController {
    @GetMapping
    public String ping() { return "OK"; }
}
