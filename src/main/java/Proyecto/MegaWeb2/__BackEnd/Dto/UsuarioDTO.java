package Proyecto.MegaWeb2.__BackEnd.Dto;

import java.time.LocalDateTime;
import java.util.Date;

public class UsuarioDTO {
    private Integer id;
    private String nombres;
    private String username;
    private String apellidos;
    private String email;
    private String idGoogle;
    private int idRol;
    private Integer suscripcion;
    private String tema;
    private String jtable;
    private String imagen;
    private String passwordHash;
    private String passwordEncode;
    private Date expirationToken;
  private boolean twoFactorEnabled;
    private String secret2FA;

    // Getters y setters
    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String getSecret2FA() {
        return secret2FA;
    }

    public void setSecret2FA(String secret2FA) {
        this.secret2FA = secret2FA;
    }
    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombre) {
        this.nombres = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApellidos() {

        return apellidos;
    }

    public void setApellidos(String apellidos) {

        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdGoogle() {
        return idGoogle;
    }
    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public int getRol() {
        return idRol;
    }
    public void setRol(int rol) {
        this.idRol = rol;
    }

    public int getSuscripcion() {
        return suscripcion;
    }
    public void setSuscripcion(Integer suscripcion) {
        this.suscripcion = suscripcion;
    }

    public String getTema() {
        return tema;
    }
    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getJTable() {
        return jtable;
    }
    public void setJTable(String jtable) {
        this.jtable = jtable;
    }

    public String setImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    // Getter y Setter para el password hash
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getExpirationToken() {
        return expirationToken;
    }

    public void setExpirationToken(Date expirationToken) {
        this.expirationToken = expirationToken;
    }

    public String getPasswordEncode() {
        return passwordEncode;
    }

    public void setPasswordEncode(String passwordEncode) {
        this.passwordEncode = passwordEncode;
    }
}
