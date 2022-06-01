package com.hoaxify.entity;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.hoaxify.constraint.UniqueUserName;
import com.hoaxify.token.Token;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails  {
	private static final long serialVersionUID = -8421768845853099274L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@UniqueUserName(message="{hoaxify.validation.constraints.Uniqueusername.username.message}")
	@NotNull(message="{hoaxify.validation.constraints.NotNull.username.message}")
	@Size(message="{hoaxify.validation.constraints.Size.username.message}", min=4,max=200)
	
	private String username;
	
	@NotNull(message="{hoaxify.validation.constraints.NotNull.displayname.message}")
	@Size(message="{hoaxify.validation.constraints.Size.displayname.message}",min=4,max=200)
	private String displayName;
	
	
	private String image;
	
	@NotNull(message="{hoaxify.validation.constraints.NotNull.password.message}")
	@Size(message="{hoaxify.validation.constraints.Size.password.message}",min=8,max=200)
	@Pattern(regexp= "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message="{hoaxify.validation.constraints.Pattern.password.message}")
	private String password;
	
	@OneToMany(mappedBy="user",cascade=CascadeType.REMOVE)
	private List<Hoax> hoax;
	@OneToMany(mappedBy="user",cascade=CascadeType.REMOVE)
	private List<Token> token;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	@Override
	public boolean isEnabled() {
		
		return true;
	}

	
	
}
