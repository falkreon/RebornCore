package reborncore.mcmultipart.property;

import net.minecraftforge.common.property.IUnlistedProperty;
import reborncore.mcmultipart.multipart.PartState;

import java.util.List;

public class PropertyMultipartStates implements IUnlistedProperty<List<PartState>> {

	private String name;

	public PropertyMultipartStates(String name) {

		this.name = name;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public boolean isValid(List<PartState> value) {

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<List<PartState>> getType() {

		return (Class<List<PartState>>) (Class<?>) List.class;
	}

	@Override
	public String valueToString(List<PartState> value) {

		return "";
	}

}
