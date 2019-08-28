package com.rfid.tcc.controller;

import com.rfid.tcc.service.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/libras")
public class AcaoController {

    @Autowired
    private AcaoService acaoService;

    @PostMapping
    public void upload(@RequestParam MultipartFile file, @RequestParam Integer codigorfid) {
        acaoService.salvarArquivo(file, codigorfid);
    }
}
