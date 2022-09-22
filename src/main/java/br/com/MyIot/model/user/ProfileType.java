package br.com.MyIot.model.user;

public enum ProfileType {

	ADMIN(1, "ROLE_ADMIN"), GOLD_USER(2, "ROLE_GOLD_USER"), SILVER_USER(3, "ROLE_SILVER_USER");

	private long cod;
	private String description;

	private ProfileType(long cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public long getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}

	public static ProfileType toEnum(String description) {
		if (description == null) {
			return null;
		}
		for (ProfileType x : ProfileType.values()) {
			if (description.equals(x.getDescription())) {
				return x;
			}

		}
		throw new IllegalArgumentException("Perfil inválido");
	}

}
