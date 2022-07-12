/*
 * Apache License v2.0
 */
package com.github.vvorks.builder.client.ui;

import com.github.vvorks.builder.client.common.ui.UiStyle;

public class BuilderStyles {

	public static final UiStyle FIELD = UiStyle.newBuilder("field")
				.condition("DEFAULT")
				.textColor("black")
				.backgroundColor("transparent")
				.borderLeft("4px")
				.borderTop("4px")
				.borderRight("4px")
				.borderBottom("4px")
				.borderColor("black")
				.borderImage("image/rrect-bw.png")
				.fontSize("12pt")
				.textAlign("left")
				.verticalAlign("middle")
				.build();
	public static final UiStyle FIELD_CLICKING = UiStyle.newBuilder("fieldClicking")
				.condition("CLICKING")
				.parent(FIELD)
				.borderImage("image/rrect-yp.png")
				.build();
	public static final UiStyle FIELD_DISABLE = UiStyle.newBuilder("fieldDisable")
				.condition("DISABLE")
				.parent(FIELD)
				.borderImage("image/rrect-bs.png")
				.build();
	public static final UiStyle FIELD_FOCUS = UiStyle.newBuilder("fieldFocus")
				.condition("FOCUS")
				.parent(FIELD)
				.borderImage("image/rrect-yw.png")
				.build();
	public static final UiStyle GROUP = UiStyle.newBuilder("group")
				.condition("DEFAULT")
				.backgroundColor("#CC808080")
				.borderLeft("0px")
				.borderTop("0px")
				.borderRight("0px")
				.borderBottom("0px")
				.build();
	public static final UiStyle GROUP_SPLITTER = UiStyle.newBuilder("groupSplitter")
				.condition("NAMED", "splitter")
				.parent(GROUP)
				.backgroundColor("silver")
				.borderLeft("1px")
				.borderTop("1px")
				.borderRight("1px")
				.borderBottom("1px")
				.borderColor("pink")
				.build();
	public static final UiStyle SB = UiStyle.newBuilder("sb")
				.condition("DEFAULT")
				.backgroundColor("gray")
				.borderLeft("2px")
				.borderTop("2px")
				.borderRight("2px")
				.borderBottom("2px")
				.borderColor("pink")
				.build();
	public static final UiStyle SB_INNER = UiStyle.newBuilder("sbInner")
				.condition("NAMED", "thumb")
				.parent(SB)
				.backgroundColor("white")
				.borderLeft("0px")
				.borderTop("0px")
				.borderRight("0px")
				.borderBottom("0px")
				.build();
	public static final UiStyle SB_FOCUS = UiStyle.newBuilder("sbFocus")
				.condition("FOCUS")
				.parent(SB_INNER)
				.backgroundColor("orange")
				.build();
	public static final UiStyle TRANSPARENT = UiStyle.newBuilder("transparent")
				.condition("DEFAULT")
				.textColor("transparent")
				.backgroundColor("transparent")
				.borderLeft("0px")
				.borderTop("0px")
				.borderRight("0px")
				.borderBottom("0px")
				.build();

}
