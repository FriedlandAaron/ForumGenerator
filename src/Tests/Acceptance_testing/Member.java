package Tests.Acceptance_testing;

public class Member{
		private String name;
		private String password;
		private MemberType type;
	public Member(String name, String password, MemberType type) {
		this.name = name;
		this.password = password;
		this.type = type;
	}

	public String get_name() {
		return name;
	}

	public String get_password() {

		return password;
	}
	public MemberType get_Type() {

		return type;
	}

}
