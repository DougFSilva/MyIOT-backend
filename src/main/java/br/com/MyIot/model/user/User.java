package br.com.MyIot.model.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A Classe <b>User</b> define um usuário do sistema 
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	private String id;

	private Email email;

	private String name;

	private String password;

	private String clientMqttPassword;

	private boolean approvedRegistration;

	private List<Profile> profiles = new ArrayList<>();

	public User(String id, String emailAddress, String name, String password, String clientMqttPassword,
			List<Profile> profiles) {
		this.id = id;
		setEmail(emailAddress);
		this.name = name;
		this.password = password;
		this.clientMqttPassword = clientMqttPassword;
		this.approvedRegistration = false;
		this.profiles = profiles;
	}
	
	public User(String emailAddress, String name, String password, String clientMqttPassword,
			List<Profile> profiles) {
		setEmail(emailAddress);
		this.name = name;
		this.password = password;
		this.clientMqttPassword = clientMqttPassword;
		this.approvedRegistration = false;
		this.profiles = profiles;
	}

	public User(String emailAddress, String name, String password, List<Profile> profiles) {
		setEmail(emailAddress);
		this.name = name;
		this.password = password;
		this.approvedRegistration = false;
		this.profiles = profiles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(String address) {
		this.email = new Email(address);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientMqttPassword() {
		return clientMqttPassword;
	}

	public void setClientMqttPassword(String clientMqttPassword) {
		this.clientMqttPassword = clientMqttPassword;
	}

	public boolean isApprovedRegistration() {
		return approvedRegistration;
	}

	public void setApprovedRegistration(boolean approvedRegistration) {
		this.approvedRegistration = approvedRegistration;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password
				+ ", clientMqttPassword=" + clientMqttPassword + ", approvedRegistration=" + approvedRegistration
				+ ", profiles=" + profiles + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return profiles;
	}

	@Override
	public String getUsername() {
		return email.getAddress();
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

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	

}
