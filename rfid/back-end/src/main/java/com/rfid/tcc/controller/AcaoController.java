package com.rfid.tcc.controller;

import com.rfid.tcc.model.Acao;
import com.rfid.tcc.service.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Acao> salvarArquivo(@RequestParam("file")MultipartFile file){
        if(file == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Acao acao = acaoService.salvarAcao(file);
        return new ResponseEntity<>(acao, HttpStatus.OK);
    }
}
