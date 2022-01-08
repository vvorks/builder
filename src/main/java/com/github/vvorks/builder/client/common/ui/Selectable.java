package com.github.vvorks.builder.client.common.ui;

public interface Selectable {

	public interface Listener {
		public void onSelected(int index);
		public void onDeselected(int index);
	}

	public void addSelectableListener(Selectable.Listener listener);
	public void removeSelectableListener(Selectable.Listener listener);
	public boolean isSelected(int index);
	public boolean select(int index);
	public boolean deselect(int index);

}
