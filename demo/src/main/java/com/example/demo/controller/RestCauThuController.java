package com.example.demo.controller;

import com.example.demo.Validate.CauThuValidate;
import com.example.demo.dto.CauThuDTO;
import com.example.demo.entity.CauThu;
import com.example.demo.repository.IDoiTuyenRepository;
import com.example.demo.service.ICauThuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/api/cauthu")
public class RestCauThuController {
    @Autowired
    ICauThuService cauThuService;
    @Autowired
    IDoiTuyenRepository doiTuyenRepository;
    @GetMapping("")
    public ResponseEntity<List<CauThu>> findAll(@RequestParam(name = "page",
            required = false,
            defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<CauThu> cauThu = cauThuService.findAll(pageable);
        if(cauThu.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cauThu.getContent(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CauThu> details(@PathVariable(name = "id") int id) {
      CauThu cauThu=cauThuService.findCauThuByCauId(id);
      if(cauThu==null){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(cauThu, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@Validated @RequestBody CauThuDTO cauThuDTO, BindingResult bindingResult) {
        CauThuValidate validate=new CauThuValidate();
        validate.validate(cauThuDTO,bindingResult);
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new LinkedHashMap<>();
            bindingResult.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        CauThu cauThu1=new CauThu();
        BeanUtils.copyProperties(cauThuDTO,cauThu1);
        cauThuService.addCauThu(cauThu1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@Validated @RequestBody CauThuDTO cauThuDTO, BindingResult bindingResult,
    @PathVariable(name = "id") int id) {
        CauThu cauThu=cauThuService.findCauThuByCauId(id);
        CauThuValidate validate=new CauThuValidate();
        validate.validate(cauThuDTO,bindingResult);
        if(bindingResult.hasErrors()){
            Map<String, String> errors = new LinkedHashMap<>();
            bindingResult.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        BeanUtils.copyProperties(cauThuDTO,cauThu);
        cauThuService.addCauThu(cauThu);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
