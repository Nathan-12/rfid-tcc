package com.rfid.tcc.service;

import org.springframework.web.multipart.MultipartFile;

public interface AcaoService {


    void salvarArquivo(MultipartFile foto, Integer codigo);

    void salvar(String diretorio, MultipartFile arquivo, Integer codigorfid);

    String analisar(String caminho);

}
