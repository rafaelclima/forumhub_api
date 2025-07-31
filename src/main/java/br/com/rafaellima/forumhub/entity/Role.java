package br.com.rafaellima.forumhub.entity;

public enum Role {
   ADMIN,
   USER;

   public String getNome() {
      return this.name();
   }
   public static Role fromString(String roleName) {
      try {
         return Role.valueOf(roleName.toUpperCase());
      } catch (IllegalArgumentException e) {
         throw new IllegalArgumentException("Role '" + roleName + "' is not valid.");
      }
   }
}
