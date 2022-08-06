package com.github.vvorks.builder.client.common.ui;

import java.util.HashMap;
import java.util.Map;

import com.github.vvorks.builder.shared.common.lang.Strings;

/**
 * 色情報操作用クラス
 */
public class Colors {

	/** 明色化、暗色化の比率 */
	private static final int RATIO = 70;

	public static final int ALICE_BLUE = 0xFFF0F8FF;
	public static final int ANTIQUE_WHITE = 0xFFFAEBD7;
	public static final int AQUA = 0xFF00FFFF;
	public static final int AQUAMARINE = 0xFF7FFFD4;
	public static final int AZURE = 0xFFF0FFFF;
	public static final int BEIGE = 0xFFF5F5DC;
	public static final int BISQUE = 0xFFFFE4C4;
	public static final int BLACK = 0xFF000000;
	public static final int BLANCHED_ALMOND = 0xFFFFEBCD;
	public static final int BLUE = 0xFF0000FF;
	public static final int BLUE_VIOLET = 0xFF8A2BE2;
	public static final int BROWN = 0xFFA52A2A;
	public static final int BURLY_WOOD = 0xFFDEB887;
	public static final int CADET_BLUE = 0xFF5F9EA0;
	public static final int CHARTREUSE = 0xFF7FFF00;
	public static final int CHOCOLATE = 0xFFD2691E;
	public static final int CORAL = 0xFFFF7F50;
	public static final int CORNFLOWER_BLUE = 0xFF6495ED;
	public static final int CORNSILK = 0xFFFFF8DC;
	public static final int CRIMSON = 0xFFDC143C;
	public static final int CYAN = 0xFF00FFFF;
	public static final int DARK_BLUE = 0xFF00008B;
	public static final int DARK_CYAN = 0xFF008B8B;
	public static final int DARK_GOLDEN_ROD = 0xFFB8860B;
	public static final int DARK_GRAY = 0xFFA9A9A9;
	public static final int DARK_GREY = 0xFFA9A9A9;
	public static final int DARK_GREEN = 0xFF006400;
	public static final int DARK_KHAKI = 0xFFBDB76B;
	public static final int DARK_MAGENTA = 0xFF8B008B;
	public static final int DARK_OLIVE_GREEN = 0xFF556B2F;
	public static final int DARKORANGE = 0xFFFF8C00;
	public static final int DARK_ORCHID = 0xFF9932CC;
	public static final int DARK_RED = 0xFF8B0000;
	public static final int DARK_SALMON = 0xFFE9967A;
	public static final int DARK_SEA_GREEN = 0xFF8FBC8F;
	public static final int DARK_SLATE_BLUE = 0xFF483D8B;
	public static final int DARK_SLATE_GRAY = 0xFF2F4F4F;
	public static final int DARK_SLATE_GREY = 0xFF2F4F4F;
	public static final int DARK_TURQUOISE = 0xFF00CED1;
	public static final int DARK_VIOLET = 0xFF9400D3;
	public static final int DEEP_PINK = 0xFFFF1493;
	public static final int DEEP_SKY_BLUE = 0xFF00BFFF;
	public static final int DIM_GRAY = 0xFF696969;
	public static final int DIM_GREY = 0xFF696969;
	public static final int DODGER_BLUE = 0xFF1E90FF;
	public static final int FIRE_BRICK = 0xFFB22222;
	public static final int FLORAL_WHITE = 0xFFFFFAF0;
	public static final int FOREST_GREEN = 0xFF228B22;
	public static final int FUCHSIA = 0xFFFF00FF;
	public static final int GAINSBORO = 0xFFDCDCDC;
	public static final int GHOST_WHITE = 0xFFF8F8FF;
	public static final int GOLD = 0xFFFFD700;
	public static final int GOLDEN_ROD = 0xFFDAA520;
	public static final int GRAY = 0xFF808080;
	public static final int GREY = 0xFF808080;
	public static final int GREEN = 0xFF008000;
	public static final int GREEN_YELLOW = 0xFFADFF2F;
	public static final int HONEY_DEW = 0xFFF0FFF0;
	public static final int HOT_PINK = 0xFFFF69B4;
	public static final int INDIAN_RED = 0xFFCD5C5C;
	public static final int INDIGO = 0xFF4B0082;
	public static final int IVORY = 0xFFFFFFF0;
	public static final int KHAKI = 0xFFF0E68C;
	public static final int LAVENDER = 0xFFE6E6FA;
	public static final int LAVENDER_BLUSH = 0xFFFFF0F5;
	public static final int LAWN_GREEN = 0xFF7CFC00;
	public static final int LEMON_CHIFFON = 0xFFFFFACD;
	public static final int LIGHT_BLUE = 0xFFADD8E6;
	public static final int LIGHT_CORAL = 0xFFF08080;
	public static final int LIGHT_CYAN = 0xFFE0FFFF;
	public static final int LIGHT_GOLDEN_ROD_YELLOW = 0xFFFAFAD2;
	public static final int LIGHT_GRAY = 0xFFD3D3D3;
	public static final int LIGHT_GREY = 0xFFD3D3D3;
	public static final int LIGHT_GREEN = 0xFF90EE90;
	public static final int LIGHT_PINK = 0xFFFFB6C1;
	public static final int LIGHT_SALMON = 0xFFFFA07A;
	public static final int LIGHT_SEA_GREEN = 0xFF20B2AA;
	public static final int LIGHT_SKY_BLUE = 0xFF87CEFA;
	public static final int LIGHT_SLATE_GRAY = 0xFF778899;
	public static final int LIGHT_SLATE_GREY = 0xFF778899;
	public static final int LIGHT_STEEL_BLUE = 0xFFB0C4DE;
	public static final int LIGHT_YELLOW = 0xFFFFFFE0;
	public static final int LIME = 0xFF00FF00;
	public static final int LIME_GREEN = 0xFF32CD32;
	public static final int LINEN = 0xFFFAF0E6;
	public static final int MAGENTA = 0xFFFF00FF;
	public static final int MAROON = 0xFF800000;
	public static final int MEDIUM_AQUA_MARINE = 0xFF66CDAA;
	public static final int MEDIUM_BLUE = 0xFF0000CD;
	public static final int MEDIUM_ORCHID = 0xFFBA55D3;
	public static final int MEDIUM_PURPLE = 0xFF9370D8;
	public static final int MEDIUM_SEA_GREEN = 0xFF3CB371;
	public static final int MEDIUM_SLATE_BLUE = 0xFF7B68EE;
	public static final int MEDIUM_SPRING_GREEN = 0xFF00FA9A;
	public static final int MEDIUM_TURQUOISE = 0xFF48D1CC;
	public static final int MEDIUM_VIOLET_RED = 0xFFC71585;
	public static final int MIDNIGHT_BLUE = 0xFF191970;
	public static final int MINT_CREAM = 0xFFF5FFFA;
	public static final int MISTY_ROSE = 0xFFFFE4E1;
	public static final int MOCCASIN = 0xFFFFE4B5;
	public static final int NAVAJO_WHITE = 0xFFFFDEAD;
	public static final int NAVY = 0xFF000080;
	public static final int OLD_LACE = 0xFFFDF5E6;
	public static final int OLIVE = 0xFF808000;
	public static final int OLIVE_DRAB = 0xFF6B8E23;
	public static final int ORANGE = 0xFFFFA500;
	public static final int ORANGE_RED = 0xFFFF4500;
	public static final int ORCHID = 0xFFDA70D6;
	public static final int PALE_GOLDEN_ROD = 0xFFEEE8AA;
	public static final int PALE_GREEN = 0xFF98FB98;
	public static final int PALE_TURQUOISE = 0xFFAFEEEE;
	public static final int PALE_VIOLET_RED = 0xFFD87093;
	public static final int PAPAYA_WHIP = 0xFFFFEFD5;
	public static final int PEACH_PUFF = 0xFFFFDAB9;
	public static final int PERU = 0xFFCD853F;
	public static final int PINK = 0xFFFFC0CB;
	public static final int PLUM = 0xFFDDA0DD;
	public static final int POWDER_BLUE = 0xFFB0E0E6;
	public static final int PURPLE = 0xFF800080;
	public static final int RED = 0xFFFF0000;
	public static final int ROSY_BROWN = 0xFFBC8F8F;
	public static final int ROYAL_BLUE = 0xFF4169E1;
	public static final int SADDLE_BROWN = 0xFF8B4513;
	public static final int SALMON = 0xFFFA8072;
	public static final int SANDY_BROWN = 0xFFF4A460;
	public static final int SEA_GREEN = 0xFF2E8B57;
	public static final int SEA_SHELL = 0xFFFFF5EE;
	public static final int SIENNA = 0xFFA0522D;
	public static final int SILVER = 0xFFC0C0C0;
	public static final int SKY_BLUE = 0xFF87CEEB;
	public static final int SLATE_BLUE = 0xFF6A5ACD;
	public static final int SLATE_GRAY = 0xFF708090;
	public static final int SLATE_GREY = 0xFF708090;
	public static final int SNOW = 0xFFFFFAFA;
	public static final int SPRING_GREEN = 0xFF00FF7F;
	public static final int STEEL_BLUE = 0xFF4682B4;
	public static final int TAN = 0xFFD2B48C;
	public static final int TEAL = 0xFF008080;
	public static final int THISTLE = 0xFFD8BFD8;
	public static final int TOMATO = 0xFFFF6347;
	public static final int TURQUOISE = 0xFF40E0D0;
	public static final int VIOLET = 0xFFEE82EE;
	public static final int WHEAT = 0xFFF5DEB3;
	public static final int WHITE = 0xFFFFFFFF;
	public static final int WHITE_SMOKE = 0xFFF5F5F5;
	public static final int YELLOW = 0xFFFFFF00;
	public static final int YELLOW_GREEN = 0xFF9ACD32;

	/** 透明色 */
	public static final int TRANSPARENT = 0x00000000;

	/** CSS色名マップ */
	private static final Map<String, Integer> COLOR_NAME_MAP;
	static {
		COLOR_NAME_MAP = new HashMap<>();
		Map<String, Integer> map = COLOR_NAME_MAP;
		map.put("aliceblue"             ,ALICE_BLUE              );
		map.put("antiquewhite"          ,ANTIQUE_WHITE           );
		map.put("aqua"                  ,AQUA                    );
		map.put("aquamarine"            ,AQUAMARINE              );
		map.put("azure"                 ,AZURE                   );
		map.put("beige"                 ,BEIGE                   );
		map.put("bisque"                ,BISQUE                  );
		map.put("black"                 ,BLACK                   );
		map.put("blanchedalmond"        ,BLANCHED_ALMOND         );
		map.put("blue"                  ,BLUE                    );
		map.put("blueviolet"            ,BLUE_VIOLET             );
		map.put("brown"                 ,BROWN                   );
		map.put("burlywood"             ,BURLY_WOOD              );
		map.put("cadetblue"             ,CADET_BLUE              );
		map.put("chartreuse"            ,CHARTREUSE              );
		map.put("chocolate"             ,CHOCOLATE               );
		map.put("coral"                 ,CORAL                   );
		map.put("cornflowerblue"        ,CORNFLOWER_BLUE         );
		map.put("cornsilk"              ,CORNSILK                );
		map.put("crimson"               ,CRIMSON                 );
		map.put("cyan"                  ,CYAN                    );
		map.put("darkblue"              ,DARK_BLUE               );
		map.put("darkcyan"              ,DARK_CYAN               );
		map.put("darkgoldenrod"         ,DARK_GOLDEN_ROD         );
		map.put("darkgray"              ,DARK_GRAY               );
		map.put("darkgrey"              ,DARK_GREY               );
		map.put("darkgreen"             ,DARK_GREEN              );
		map.put("darkkhaki"             ,DARK_KHAKI              );
		map.put("darkmagenta"           ,DARK_MAGENTA            );
		map.put("darkolivegreen"        ,DARK_OLIVE_GREEN        );
		map.put("darkorange"            ,DARKORANGE              );
		map.put("darkorchid"            ,DARK_ORCHID             );
		map.put("darkred"               ,DARK_RED                );
		map.put("darksalmon"            ,DARK_SALMON             );
		map.put("darkseagreen"          ,DARK_SEA_GREEN          );
		map.put("darkslateblue"         ,DARK_SLATE_BLUE         );
		map.put("darkslategray"         ,DARK_SLATE_GRAY         );
		map.put("darkslategrey"         ,DARK_SLATE_GREY         );
		map.put("darkturquoise"         ,DARK_TURQUOISE          );
		map.put("darkviolet"            ,DARK_VIOLET             );
		map.put("deeppink"              ,DEEP_PINK               );
		map.put("deepskyblue"           ,DEEP_SKY_BLUE           );
		map.put("dimgray"               ,DIM_GRAY                );
		map.put("dimgrey"               ,DIM_GREY                );
		map.put("dodgerblue"            ,DODGER_BLUE             );
		map.put("firebrick"             ,FIRE_BRICK              );
		map.put("floralwhite"           ,FLORAL_WHITE            );
		map.put("forestgreen"           ,FOREST_GREEN            );
		map.put("fuchsia"               ,FUCHSIA                 );
		map.put("gainsboro"             ,GAINSBORO               );
		map.put("ghostwhite"            ,GHOST_WHITE             );
		map.put("gold"                  ,GOLD                    );
		map.put("goldenrod"             ,GOLDEN_ROD              );
		map.put("gray"                  ,GRAY                    );
		map.put("grey"                  ,GREY                    );
		map.put("green"                 ,GREEN                   );
		map.put("greenyellow"           ,GREEN_YELLOW            );
		map.put("honeydew"              ,HONEY_DEW               );
		map.put("hotpink"               ,HOT_PINK                );
		map.put("indianred"             ,INDIAN_RED              );
		map.put("indigo"                ,INDIGO                  );
		map.put("ivory"                 ,IVORY                   );
		map.put("khaki"                 ,KHAKI                   );
		map.put("lavender"              ,LAVENDER                );
		map.put("lavenderblush"         ,LAVENDER_BLUSH          );
		map.put("lawngreen"             ,LAWN_GREEN              );
		map.put("lemonchiffon"          ,LEMON_CHIFFON           );
		map.put("lightblue"             ,LIGHT_BLUE              );
		map.put("lightcoral"            ,LIGHT_CORAL             );
		map.put("lightcyan"             ,LIGHT_CYAN              );
		map.put("lightgoldenrodyellow"  ,LIGHT_GOLDEN_ROD_YELLOW );
		map.put("lightgray"             ,LIGHT_GRAY              );
		map.put("lightgrey"             ,LIGHT_GREY              );
		map.put("lightgreen"            ,LIGHT_GREEN             );
		map.put("lightpink"             ,LIGHT_PINK              );
		map.put("lightsalmon"           ,LIGHT_SALMON            );
		map.put("lightseagreen"         ,LIGHT_SEA_GREEN         );
		map.put("lightskyblue"          ,LIGHT_SKY_BLUE          );
		map.put("lightslategray"        ,LIGHT_SLATE_GRAY        );
		map.put("lightslategrey"        ,LIGHT_SLATE_GREY        );
		map.put("lightsteelblue"        ,LIGHT_STEEL_BLUE        );
		map.put("lightyellow"           ,LIGHT_YELLOW            );
		map.put("lime"                  ,LIME                    );
		map.put("limegreen"             ,LIME_GREEN              );
		map.put("linen"                 ,LINEN                   );
		map.put("magenta"               ,MAGENTA                 );
		map.put("maroon"                ,MAROON                  );
		map.put("mediumaquamarine"      ,MEDIUM_AQUA_MARINE      );
		map.put("mediumblue"            ,MEDIUM_BLUE             );
		map.put("mediumorchid"          ,MEDIUM_ORCHID           );
		map.put("mediumpurple"          ,MEDIUM_PURPLE           );
		map.put("mediumseagreen"        ,MEDIUM_SEA_GREEN        );
		map.put("mediumslateblue"       ,MEDIUM_SLATE_BLUE       );
		map.put("mediumspringgreen"     ,MEDIUM_SPRING_GREEN     );
		map.put("mediumturquoise"       ,MEDIUM_TURQUOISE        );
		map.put("mediumvioletred"       ,MEDIUM_VIOLET_RED       );
		map.put("midnightblue"          ,MIDNIGHT_BLUE           );
		map.put("mintcream"             ,MINT_CREAM              );
		map.put("mistyrose"             ,MISTY_ROSE              );
		map.put("moccasin"              ,MOCCASIN                );
		map.put("navajowhite"           ,NAVAJO_WHITE            );
		map.put("navy"                  ,NAVY                    );
		map.put("oldlace"               ,OLD_LACE                );
		map.put("olive"                 ,OLIVE                   );
		map.put("olivedrab"             ,OLIVE_DRAB              );
		map.put("orange"                ,ORANGE                  );
		map.put("orangered"             ,ORANGE_RED              );
		map.put("orchid"                ,ORCHID                  );
		map.put("palegoldenrod"         ,PALE_GOLDEN_ROD         );
		map.put("palegreen"             ,PALE_GREEN              );
		map.put("paleturquoise"         ,PALE_TURQUOISE          );
		map.put("palevioletred"         ,PALE_VIOLET_RED         );
		map.put("papayawhip"            ,PAPAYA_WHIP             );
		map.put("peachpuff"             ,PEACH_PUFF              );
		map.put("peru"                  ,PERU                    );
		map.put("pink"                  ,PINK                    );
		map.put("plum"                  ,PLUM                    );
		map.put("powderblue"            ,POWDER_BLUE             );
		map.put("purple"                ,PURPLE                  );
		map.put("red"                   ,RED                     );
		map.put("rosybrown"             ,ROSY_BROWN              );
		map.put("royalblue"             ,ROYAL_BLUE              );
		map.put("saddlebrown"           ,SADDLE_BROWN            );
		map.put("salmon"                ,SALMON                  );
		map.put("sandybrown"            ,SANDY_BROWN             );
		map.put("seagreen"              ,SEA_GREEN               );
		map.put("seashell"              ,SEA_SHELL               );
		map.put("sienna"                ,SIENNA                  );
		map.put("silver"                ,SILVER                  );
		map.put("skyblue"               ,SKY_BLUE                );
		map.put("slateblue"             ,SLATE_BLUE              );
		map.put("slategray"             ,SLATE_GRAY              );
		map.put("slategrey"             ,SLATE_GREY              );
		map.put("snow"                  ,SNOW                    );
		map.put("springgreen"           ,SPRING_GREEN            );
		map.put("steelblue"             ,STEEL_BLUE              );
		map.put("tan"                   ,TAN                     );
		map.put("teal"                  ,TEAL                    );
		map.put("thistle"               ,THISTLE                 );
		map.put("tomato"                ,TOMATO                  );
		map.put("turquoise"             ,TURQUOISE               );
		map.put("violet"                ,VIOLET                  );
		map.put("wheat"                 ,WHEAT                   );
		map.put("white"                 ,WHITE                   );
		map.put("whitesmoke"            ,WHITE_SMOKE             );
		map.put("yellow"                ,YELLOW                  );
		map.put("yellowgreen"           ,YELLOW_GREEN            );
		map.put("transparent"           ,TRANSPARENT             );
	}

	/**
	 * このクラスはインスタンス化しない
	 */
	private Colors() {
	}

	/**
	 * RGBから色値を取得する
	 *
	 * @param r
	 * 		赤成分
	 * @param g
	 * 		緑成分
	 * @param b
	 * 		青成分
	 * @return
	 * 		色値
	 */
	public static int fromRGB(int r, int g, int b) {
		return	( ( ((r) << 16) & 0x00FF0000)
				| ( ((g) <<  8) & 0x0000FF00)
				| ( ((b) <<  0) & 0x000000FF)
				| (               0xFF000000));
	}

	/**
	 * RGB及び不透明度から色値を取得する
	 *
	 * @param r
	 * 		赤成分
	 * @param g
	 * 		緑成分
	 * @param b
	 * 		青成分
	 * @param a
	 * 		不透明度（0.0：透明～1.0不透明）
	 * @return
	 * 		色値
	 */
	public static int fromRGBA(int r, int g, int b, double a) {
		return	( ( ((r                 ) << 16) & 0x00FF0000)
				| ( ((g                 ) <<  8) & 0x0000FF00)
				| ( ((b                 ) <<  0) & 0x000000FF)
				| ( (((int)(255.0 * (a))) << 24) & 0xFF000000));
	}

	/**
	 * 色値から赤成分を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		赤成分
	 */
	public static int getRValue(int rgba) {
		return ((rgba >> 16) & 0xFF);
	}

	/**
	 * 色値から緑成分を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		緑成分
	 */
	public static int getGValue(int rgba) {
		return ((rgba >> 8) & 0xFF);
	}

	/**
	 * 色値から青成分を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		青成分
	 */
	public static int getBValue(int rgba) {
		return rgba & 0xFF;
	}

	/**
	 * 色値から不透明度を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		不透明度（0.0：透明～1.0不透明）
	 */
	public static double getAValue(int rgba) {
		return ((rgba >> 24) & 0xFF)/255.0;
	}

	/**
	 * 不透明か否かを取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		不透明の場合、真
	 */
	public static boolean isOpaque(int rgba) {
		return (((rgba >> 24) & 0xFF) == 0xFF);
	}

	/**
	 * 透明か否かを取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		透明の場合、真
	 */
	public static boolean isTransparent(int rgba) {
		return (((rgba >> 24) & 0xFF) == 0x00);
	}

	/**
	 * YUV値からRGB値を取得する
	 *
	 * @param y
	 * 		輝度成分
	 * @param u
	 * 		青色成分の差分
	 * @param v
	 * 		赤色成分の差分
	 * @return
	 * 		色値
	 */
	public static final int fromYUV(int y, int u, int v) {
		int r = (int)(+1.000*y + 0.000*u + 1.402*v);
		int g = (int)(+1.000*y - 0.344*u - 0.714*v);
		int b = (int)(+1.000*y + 1.772*u + 0.000*v);
		return fromRGB(r, g, b);
	}

	/**
	 * 色値から輝度成分を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		輝度成分
	 */
	public static int getYValue(int rgba) {
		int r = getRValue(rgba);
		int g = getGValue(rgba);
		int b = getBValue(rgba);
		return ((int)(+0.299*r+0.587*g+0.114*b));
	}

	/**
	 * 色値から青色成分の差分を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		青色成分の差分
	 */
	public static int getUValue(int rgba) {
		int r = getRValue(rgba);
		int g = getGValue(rgba);
		int b = getBValue(rgba);
		return ((int)(-0.169*r-0.331*g+0.500*b));
	}

	/**
	 * 色値から赤色成分の差分を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		赤色成分の差分
	 */
	public static int getVValue(int rgba) {
		int r = getRValue(rgba);
		int g = getGValue(rgba);
		int b = getBValue(rgba);
		return ((int)(+0.500*r-0.419*g-0.081*b));
	}

	/**
	 * 暗色化した色値を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		暗色化した値
	 */
	public static int getDarker(int rgba) {
		int r = getRValue(rgba);
		int g = getGValue(rgba);
		int b = getBValue(rgba);
		double a = getAValue(rgba);
		r = r * RATIO / 100;
		g = g * RATIO / 100;
		b = b * RATIO / 100;
		return fromRGBA(r, g, b, a);
	}

	/**
	 * 明色化した値を取得する
	 *
	 * @param rgba
	 * 		色値
	 * @return
	 * 		明色化した値
	 */
	public static int getBrighter(int rgba) {
		int r = getRValue(rgba);
		int g = getGValue(rgba);
		int b = getBValue(rgba);
		double a = getAValue(rgba);
		int i = 100 / (100 - RATIO);
		if (r == 0 && g == 0 && b == 0) {
			r = g = b = i;
	    } else {
			if ( 0 < r && r < i ) {
				r = i;
			}
			if ( 0 < g && g < i ) {
				g = i;
			}
			if ( 0 < b && b < i ) {
				b = i;
			}
			r = r * 100 / RATIO;
			g = g * 100 / RATIO;
			b = b * 100 / RATIO;
			r = Math.min(r, 255);
			g = Math.min(g, 255);
			b = Math.min(b, 255);
	    }
		return fromRGBA(r, g, b, a);
	}

	/**
	 * 色値を得る
	 *
	 * @param str
	 * 		色を表現した文字列
	 * 		"#AaRrGgBb"	32ビットカラー（α値は、0x00：透明、0xFF：不透明とする）
	 *		"#RrGgBb"	２４ビットカラー（α値は常に不透明とする）
	 *		"#RGB"		２４ビットカラー（短縮指定）
	 *		"<色名>"		CSS色名によるRGB値参照
	 * @return
	 * 		色値
	 */
	public static int parse(String str) {
		int c = BLACK;
		int r;
		int g;
		int b;
		int a;
		if (str.charAt(0) == '#') {
			str = str.substring(1);
			switch (str.length()) {
			case 8:
				r = Integer.parseInt(str.substring(2,4), 16);
				g = Integer.parseInt(str.substring(4,6), 16);
				b = Integer.parseInt(str.substring(6,8), 16);
				a = Integer.parseInt(str.substring(0,2), 16);
				break;
			case 6:
				r = Integer.parseInt(str.substring(0,2), 16);
				g = Integer.parseInt(str.substring(2,4), 16);
				b = Integer.parseInt(str.substring(4,6), 16);
				a = 0xFF;
				break;
			case 3:
				r = Integer.parseInt(str.substring(0,1), 16);
				g = Integer.parseInt(str.substring(1,2), 16);
				b = Integer.parseInt(str.substring(2,3), 16);
				r = r * 16 + r;
				g = g * 16 + g;
				b = b * 16 + b;
				a = 0xFF;
				break;
			default:
				r = g = b = 0;
				a = 0xFF;
			}
			c =	( ((r << 16) & 0x00FF0000)
				| ((g <<  8) & 0x0000FF00)
				| ((b <<  0) & 0x000000FF)
				| ((a << 24) & 0xFF000000));
		} else {
			Integer value = COLOR_NAME_MAP.get(str);
			if (value != null) {
				c = value;
			}
		}
		return c;
	}

	public static String toCssColor(int c) {
		if (isOpaque(c)) {
			return Strings.sprintf("#%06x", c & 0xFFFFFF);
		}
		return Strings.sprintf("rgba(%d,%d,%d,%g)",
				getRValue(c),
				getGValue(c),
				getBValue(c),
				getAValue(c));
	}

}
