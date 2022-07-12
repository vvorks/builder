package com.github.vvorks.builder.client.test;

import com.github.vvorks.builder.client.common.ui.UiNode;
import com.github.vvorks.builder.client.common.ui.UiStyle;
import com.github.vvorks.builder.client.ui.BuilderStyles;
import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.util.Test;
import com.github.vvorks.builder.common.util.TestCase;

public class StyleTest implements TestCase {

	@Test
	public void basicTest() {

		UiNode enableNode = new UiNode() {
			public boolean isEnable() {return true;}
			public boolean isFocus() {return false;}
			public boolean inFocus() {return false;}
			public boolean hasFocus() {return false;}
		};
		UiStyle enableStyle = BuilderStyles.FIELD.getEffectiveStyle(enableNode);
		Asserts.checkEquals("image/rrect-bw.png", enableStyle.getBorderImage());

		UiNode disableNode = new UiNode() {
			public boolean isEnable() {return false;}
			public boolean isFocus() {return false;}
			public boolean inFocus() {return false;}
			public boolean hasFocus() {return false;}
		};
		UiStyle disableStyle = BuilderStyles.FIELD.getEffectiveStyle(disableNode);
		Asserts.checkEquals("image/rrect-bs.png", disableStyle.getBorderImage());

		UiNode focusNode = new UiNode() {
			public boolean isEnable() {return true;}
			public boolean isFocus() {return true;}
			public boolean inFocus() {return true;}
			public boolean hasFocus() {return true;}
		};
		UiStyle focusStyle = BuilderStyles.FIELD.getEffectiveStyle(focusNode);
		Asserts.checkEquals("image/rrect-yw.png", focusStyle.getBorderImage());
	}
}
