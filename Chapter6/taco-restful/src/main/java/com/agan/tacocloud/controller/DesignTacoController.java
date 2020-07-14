package com.agan.tacocloud.controller;

import java.util.*;

import javax.validation.Valid;

import com.agan.tacocloud.common.ResponseMessage;
import com.agan.tacocloud.dao.TacoRepository;
import com.agan.tacocloud.po.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/design", produces = "application/json")   //只处理头文件包含application/json的
@CrossOrigin(origins = "*")         //允许跨域请求
public class DesignTacoController {

    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recent")
    public ResponseMessage recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return ResponseMessage.success(tacoRepo.findAll(page).getContent());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepo.findById(id);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco processDesign(@Valid @RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

}
