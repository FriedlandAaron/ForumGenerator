package Domain_layer.ForumComponent;

@SuppressWarnings("serial")
public class MemberType  implements java.io.Serializable {
	
	private String _typeName;
	
	public String get_typeName() {
		return _typeName;
	}

	public MemberType(String typeName) {
		_typeName = typeName; 
	}

}
