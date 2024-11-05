package com.br.prefeitura.enums;

public enum TipoNota {
    NF_E,
    NFS_E,
    NFC_E,
    CT_E,
    MDF_E;

    @Override
    public String toString() {
        return this.name().replace("_", "-");
    }

}
