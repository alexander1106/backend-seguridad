package Proyecto.MegaWeb2.__BackEnd.Dto;

import java.time.LocalDateTime;
import java.util.Date;

public class ResponseLoginDto {
	private String token;
	private Date expiration;
	private Integer isFirstAuthGoogle;
	private String username;
	private boolean require2FA;

	public boolean isRequire2FA() {
		return require2FA;
	}

	public void setRequire2FA(boolean require2FA) {
		this.require2FA = require2FA;
	}
	public Date getExpiration() {
		return expiration;
	}

	public String getToken() {
		return token;
	}

	public Integer getIsFirstAuthGoogle() {
		return isFirstAuthGoogle;
	}

	public String getUsername() {
		return username;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setIsFirstAuthGoogle(Integer isFirstAuthGoogle) {
		this.isFirstAuthGoogle = isFirstAuthGoogle;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
