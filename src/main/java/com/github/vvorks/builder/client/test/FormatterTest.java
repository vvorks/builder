package com.github.vvorks.builder.client.test;

import java.util.Date;

import com.github.vvorks.builder.client.common.ui.Metrics;
import com.github.vvorks.builder.common.lang.Strings;
import com.github.vvorks.builder.common.text.Formatter;
import com.github.vvorks.builder.common.util.Test;
import com.github.vvorks.builder.common.util.TestCase;

public class FormatterTest implements TestCase {

	private void checkFormat(StringBuilder errors, String e, String f, Object v) {
		String r = Formatter.format(f, v);
		if (!e.equals(r)) {
			errors.append(Strings.sprintf("%s: [%s] != [%s]\n", f, e, r));
		}
	}

	private void throwIfErrorOccurs(String errors) {
		if (!errors.isEmpty()) {
			throw new AssertionError(errors);
		}
	}

	private void assumeLocale(String loc) {
		String locale = Metrics.get().getLocale();
		if (!locale.startsWith(loc)) {
			throw new UnsupportedOperationException();
		}
	}
	private Date date(int year, int month, int day) {
		return time(year, month, day, 0, 0, 0, 0);
	}

	private Date time(int year, int month, int day, int hour, int minute, int second, int msec) {
		@SuppressWarnings("deprecation")
		Date temp = new Date(year - 1900, month - 1, day, hour, minute, second);
		return new Date(temp.getTime() + msec);
	}

	@Test
	@SuppressWarnings("all")
	public void testBinary() {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		checkFormat(sb, "   1"   , "%4b" , 1);
		checkFormat(sb, "0010"   , "%04b", 2);
		checkFormat(sb, "11  "   , "%-4b", 3);
		checkFormat(sb, "100"    , "%b",   4);
		checkFormat(sb, "101"    , "%b", "5");
		checkFormat(sb, "110"    , "%b", "6.2");
		checkFormat(sb, "111"    , "%b", 7.12);
		checkFormat(sb, "1000000", "%b", '@');
		checkFormat(sb, Long.toBinaryString(now.getTime()), "%b", now);
		checkFormat(sb, "0", "%b", null);
		throwIfErrorOccurs(sb.toString());
	}

	@Test
	@SuppressWarnings("all")
	public void testOctal() {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		checkFormat(sb, "   1", "%4o" , 1);
		checkFormat(sb, "0002", "%04o", 2);
		checkFormat(sb, "3   ", "%-4o", 3);
		checkFormat(sb, "4"   , "%o",   4);
		checkFormat(sb, "5"   , "%o", "5");
		checkFormat(sb, "6"   , "%o", "6.2");
		checkFormat(sb, "7"   , "%o", 7.12);
		checkFormat(sb, "100" , "%o", '@');
		checkFormat(sb, Long.toOctalString(now.getTime()), "%o", now);
		checkFormat(sb, "0", "%o", null);
		throwIfErrorOccurs(sb.toString());
	}

	@Test
	@SuppressWarnings("all")
	public void testDecimal() {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		checkFormat(sb, "   1", "%4d" , 1);
		checkFormat(sb, "0002", "%04d", 2);
		checkFormat(sb, "3   ", "%-4d", 3);
		checkFormat(sb, "4"   , "%d",   4);
		checkFormat(sb, "5"   , "%d", "5");
		checkFormat(sb, "6.2" , "%d", "6.2");
		checkFormat(sb, "7.12", "%d", 7.12);
		checkFormat(sb, "64"  , "%d", '@');
		checkFormat(sb, Long.toString(now.getTime()), "%d", now);
		checkFormat(sb, "0"   , "%d", null);
		throwIfErrorOccurs(sb.toString());
	}

	@Test
	@SuppressWarnings("all")
	public void testHexaDecimal() {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		checkFormat(sb, "   1", "%4x" , 1);
		checkFormat(sb, "0002", "%04x", 2);
		checkFormat(sb, "3   ", "%-4x", 3);
		checkFormat(sb, "4"   , "%x",   4);
		checkFormat(sb, "5"   , "%x", "5");
		checkFormat(sb, "6"   , "%x", "6.2");
		checkFormat(sb, "7"   , "%x", 7.12);
		checkFormat(sb, "40"  , "%x", '@');
		checkFormat(sb, Long.toHexString(now.getTime()), "%x", now);
		checkFormat(sb, "0"   , "%x", null);
		throwIfErrorOccurs(sb.toString());
	}

	@Test
	@SuppressWarnings("all")
	public void testDecimalJa() {
		assumeLocale("ja");
		StringBuilder sb = new StringBuilder();
		checkFormat(sb,"1,234,567",			"%,7d" , +1234567);
		checkFormat(sb,"1,234,567",			"%,07d", +1234567);
		checkFormat(sb,"1,234,567",			"%,-7d", +1234567);
		checkFormat(sb,"-1,234,567",		"%,7d" , -1234567);
		checkFormat(sb,"-1,234,567",		"%,07d", -1234567);
		checkFormat(sb,"-1,234,567",		"%,-7d", -1234567);
		checkFormat(sb," +1,234,567",		"%,+8d" , +1234567);
		checkFormat(sb,"+01,234,567",		"%,+08d", +1234567);
		checkFormat(sb,"+1,234,567 ",		"%-,+8d", +1234567);
		checkFormat(sb," -1,234,567",		"%,+8d" , -1234567);
		checkFormat(sb,"-01,234,567",		"%,+08d", -1234567);
		checkFormat(sb,"-1,234,567 ",		"%-,+8d", -1234567);
		checkFormat(sb,"  ??1,234,567",		"%, ??8d" , +1234567);
		checkFormat(sb," ??01,234,567",		"%, ??08d", +1234567);
		checkFormat(sb," ??1,234,567 ",		"%-, ??8d", +1234567);
		checkFormat(sb," -??1,234,567",		"%, ??8d" , -1234567);
		checkFormat(sb,"-??01,234,567",		"%, ??08d", -1234567);
		checkFormat(sb,"-??1,234,567 ",		"%-, ??8d", -1234567);
		checkFormat(sb,"  $1,234,567.12",	"%, $8d" , +1234567.123);
		checkFormat(sb," $01,234,567.46",	"%, $08d", +1234567.456);
		checkFormat(sb," $1,234,567.00 ",	"%-, $8d", +1234567);
		checkFormat(sb," -$1,234,567.00",	"%, $8d" , -1234567);
		checkFormat(sb,"-$01,234,567.00",	"%, $08d", -1234567);
		checkFormat(sb,"-$1,234,567.00 ",	"%-, $8d", -1234567);
		checkFormat(sb,"  ??1,234,567",		"%, ??8d" , +1234567);
		checkFormat(sb," ??01,234,567",		"%, ??08d", +1234567);
		checkFormat(sb," ??1,234,567 ",		"%-, ??8d", +1234567);
		checkFormat(sb," -??1,234,567",		"%, ??8d" , -1234567);
		checkFormat(sb,"-??01,234,567",		"%, ??08d", -1234567);
		checkFormat(sb,"-??1,234,567 ",		"%-, ??8d", -1234567);
		checkFormat(sb,"  ???1,234,567.12",	"%, ???8d" , +1234567.123);
		checkFormat(sb," ???01,234,567.46",	"%, ???08d", +1234567.456);
		checkFormat(sb," ???1,234,567.00 ",	"%-, ???8d", +1234567);
		checkFormat(sb," -???1,234,567.00",	"%, ???8d" , -1234567);
		checkFormat(sb,"-???01,234,567.00",	"%, ???08d", -1234567);
		checkFormat(sb,"-???1,234,567.00 ",	"%-, ???8d", -1234567);
		checkFormat(sb,"  ??1,234,567.12",	"%, ??8d" , +1234567.123);
		checkFormat(sb," ??01,234,567.46",	"%, ??08d", +1234567.456);
		checkFormat(sb," ??1,234,567.00 ",	"%-, ??8d", +1234567);
		checkFormat(sb," -??1,234,567.00",	"%, ??8d" , -1234567);
		checkFormat(sb,"-??01,234,567.00",	"%, ??08d", -1234567);
		checkFormat(sb,"-??1,234,567.00 ",	"%-, ??8d", -1234567);
		checkFormat(sb,"+1,234,567.123",	"%,+7.3d" , +1234567.1234);
		checkFormat(sb,"+1,234,567.568",	"%,+07.3d", +1234567.5678);
		checkFormat(sb,"+1,234,567.123",	"%-,+7.3d", +1234567.1234);
		checkFormat(sb,"-1,234,567.568",	"%,+7.3d" , -1234567.5678);
		checkFormat(sb,"-1,234,567.123",	"%,+07.3d", -1234567.1234);
		checkFormat(sb,"-1,234,567.568",	"%-,+7.3d", -1234567.5678);
		checkFormat(sb," +1,234,567.123",	"%,+8.3d" , +1234567.1234);
		checkFormat(sb,"+01,234,567.568",	"%,+08.3d", +1234567.5678);
		checkFormat(sb,"+1,234,567.123 ",	"%-,+8.3d", +1234567.1234);
		checkFormat(sb," -1,234,567.568",	"%,+8.3d" , -1234567.5678);
		checkFormat(sb,"-01,234,567.123",	"%,+08.3d", -1234567.1234);
		checkFormat(sb,"-1,234,567.568 ",	"%-,+8.3d", -1234567.5678);
		throwIfErrorOccurs(sb.toString());
	}

	@Test
	@SuppressWarnings("all")
	public void testString() {
		StringBuilder sb = new StringBuilder();
		checkFormat(sb, "   1", "%4s" , "1");
		checkFormat(sb, "   2", "%04s", "2");
		checkFormat(sb, "3   ", "%-4s", "3");
		checkFormat(sb, "4"   , "%s",   "4");
		checkFormat(sb, "5"   , "%s",   5);
		checkFormat(sb, "6.2" , "%s",   "6.2");
		checkFormat(sb, "7.12", "%s",   7.12);
		checkFormat(sb, "@"   , "%s",   '@');
		checkFormat(sb, "null", "%s",   null);
		checkFormat(sb, "abcHELLOdef",  "abc%sdef", "HELLO");
		checkFormat(sb, "123-4567",     "%3s-%4s",  "1234567");
		checkFormat(sb, "123-45",       "%3s-%4s",  "12345");
		checkFormat(sb, "123",          "%3s-%4s",  "123");
		throwIfErrorOccurs(sb.toString());
	}

	@Test
	@SuppressWarnings("all")
	public void testChar() {
		StringBuilder sb = new StringBuilder();
		checkFormat(sb, "abc@def",  "abc%cdef" , '@');
		checkFormat(sb, "abc???def", "abc%cdef" , 0x3042);
		throwIfErrorOccurs(sb.toString());
	}

	@Test
	@SuppressWarnings("all")
	public void testDateTimeJa() {
		assumeLocale("ja");
		StringBuilder sb = new StringBuilder();
		Date d = time(2020, 2, 3, 14, 5, 6, 789);
		checkFormat(sb, "2020/02/03(???)",						"%Y/%02m/%02D(%A)",			d);
		checkFormat(sb, "2020???2???3???(???)",						"%Y???%m???%D???(%+A)",			d);
		checkFormat(sb, "R02.02.03(???)",							"%G%#02Y.%02m.%02D(%A)",	d);
		checkFormat(sb, "??????2???2???3???(?????????)",					"%+G%#Y???%m???%D???(%++A)",		d);
		checkFormat(sb, "14:5:6",								"%H:%M:%S",					d);
		checkFormat(sb, "14:05:06" ,							"%02H:%02M:%02S",			d);
		checkFormat(sb, "PM 02:05:06",							"%P %02H:%02M:%02S",		d);
		checkFormat(sb, "?????? 02:05:06",							"%+P %02H:%02M:%02S",		d);
		checkFormat(sb, "??? 02:05:06",							"%++P %02H:%02M:%02S",		d);
		checkFormat(sb, "??? 02:05:06",							"%+++P %02H:%02M:%02S",		d);
		checkFormat(sb, "  14???   5???   6???", 					"%4H???%4M???%4S???", 			d);
		checkFormat(sb, "14:05:06.7",							"%02H:%02M:%02S.%1L",		d);
		checkFormat(sb, "14:05:06.78",							"%02H:%02M:%02S.%2L",		d);
		checkFormat(sb, "14:05:06.789",							"%02H:%02M:%02S.%3L",		d);
		checkFormat(sb, "14:05:06.789+09:00" ,					"%02H:%02M:%02S.%3L%Z",		d);
		checkFormat(sb, "14:05:06.789JST" ,						"%02H:%02M:%02S.%3L%+Z",	d);
		checkFormat(sb, "14:05:06.789 ???????????????",					"%02H:%02M:%02S.%3L %++Z",	d);

		checkFormat(sb, "2020-02-03T14:05:06.789+09:00" ,		"%04Y-%02m-%02DT%02H:%02M:%02S.%3L%Z", d);

		checkFormat(sb, "2020/02/03", 							"%F" ,						d);
		checkFormat(sb, "14:05", 								"%T" ,						d);
		checkFormat(sb, "2020/02/03 14:05", 					"%F %T",					d);
		checkFormat(sb, "2020/02/03 14:05:06", 					"%+F %+T",					d);
		checkFormat(sb, "2020???2???3??? 14:05:06 JST", 				"%++F %++T",				d);
		checkFormat(sb, "2020???2???3???????????? 14???05???06??? ???????????????", 	"%+++F %+++T",				d);
		checkFormat(sb, "R2/2/3 14:05", 						"%#F %T",					d);
		checkFormat(sb, "??????2???2???3??? 14:05:06",	 				"%#+F %+T",					d);
		checkFormat(sb, "??????2???2???3??? 14:05:06 JST",				"%#++F %++T",				d);
		checkFormat(sb, "??????2???2???3???????????? 14???05???06??? ???????????????",	"%#+++F %+++T",				d);

		checkFormat(sb, "??????4???9???7???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(1868, 9, 7)	);
		checkFormat(sb, "????????????9???8???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(1868, 9, 8)	);
		checkFormat(sb, "??????45???7???29???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(1912, 7,29)	);
		checkFormat(sb, "????????????7???30???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(1912, 7,30)	);
		checkFormat(sb, "??????15???12???24???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(1926,12,24)	);
		checkFormat(sb, "????????????12???25???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(1926,12,25)	);
		checkFormat(sb, "??????64???1???7???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(1989, 1, 7)	);
		checkFormat(sb, "????????????1???8???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(1989, 1, 8)	);
		checkFormat(sb, "??????31???4???30???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(2019, 4,30)	);
		checkFormat(sb, "????????????5???1???(?????????)",	"%+G%#Y???%m???%D???(%++A)",	date(2019, 5, 1)	);

		throwIfErrorOccurs(sb.toString());
	}

}
