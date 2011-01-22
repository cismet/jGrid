package de.loganmobile.grid.iphoto;

public class DummyModelObject implements IPhotoModelObject {

	private float fraction;
	
	@Override
	public Object getValueForFraction() {
		return (int)(fraction * 100) + "";
	}

	@Override
	public void setFraction(float fraction) {
		this.fraction = fraction;
	}

	@Override
	public String toString() {
		return getValueForFraction().toString();
	}
}
