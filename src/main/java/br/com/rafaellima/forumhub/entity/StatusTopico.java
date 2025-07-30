package br.com.rafaellima.forumhub.entity;

import lombok.Getter;

@Getter
public enum StatusTopico {
      NAO_RESPONDIDO("Não Respondido"),
      NAO_SOLUCIONADO("Não Solucionado"),
      SOLUCIONADO("Solucionado"),
      FECHADO("Fechado");

      private final String descricao;
      StatusTopico(String descricao) {
            this.descricao = descricao;
      }

   @Override
      public String toString() {
         return descricao;
      }


}
