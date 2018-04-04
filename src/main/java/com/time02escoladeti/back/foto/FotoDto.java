package com.time02escoladeti.back.foto;

public class FotoDto {
    private FotoId fotoId;
    private String nomeFoto;
    private String urlFoto;

    public FotoDto(FotoId fotoId, String nomeFoto, String urlFoto) {
        this.fotoId = fotoId;
        this.nomeFoto = nomeFoto;
        this.urlFoto = urlFoto;
    }

    public FotoId getFotoId() {
        return fotoId;
    }

    public String getNomeFoto() {
        return nomeFoto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }
}
