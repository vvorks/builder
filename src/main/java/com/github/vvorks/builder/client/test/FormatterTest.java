package com.github.vvorks.builder.client.test;

import java.util.Date;

import com.github.vvorks.builder.common.lang.Asserts;
import com.github.vvorks.builder.common.text.Formatter;
import com.github.vvorks.builder.common.util.Test;
import com.github.vvorks.builder.common.util.TestCase;

public class FormatterTest implements TestCase {

	@Test
	public void testBinary() {
		Asserts.checkEquals("   1"   , Formatter.format("%4b" , 1));
		Asserts.checkEquals("0010"   , Formatter.format("%04b", 2));
		Asserts.checkEquals("11  "   , Formatter.format("%-4b", 3));
		Asserts.checkEquals("100"    , Formatter.format("%b",   4));
		Asserts.checkEquals("101"    , Formatter.format("%b", "5"));
		Asserts.checkEquals("110"    , Formatter.format("%b", "6.2"));
		Asserts.checkEquals("111"    , Formatter.format("%b", 7.12));
		Asserts.checkEquals("1000000", Formatter.format("%b", '@'));
		Date now = new Date();
		Asserts.checkEquals(Long.toBinaryString(now.getTime()), Formatter.format("%b", now));
		Asserts.checkEquals("0", Formatter.format("%b", null));
	}

	@Test
	public void testOctal() {
		Asserts.checkEquals("   1", Formatter.format("%4o" , 1));
		Asserts.checkEquals("0002", Formatter.format("%04o", 2));
		Asserts.checkEquals("3   ", Formatter.format("%-4o", 3));
		Asserts.checkEquals("4"   , Formatter.format("%o",   4));
		Asserts.checkEquals("5"   , Formatter.format("%o", "5"));
		Asserts.checkEquals("6"   , Formatter.format("%o", "6.2"));
		Asserts.checkEquals("7"   , Formatter.format("%o", 7.12));
		Asserts.checkEquals("100" , Formatter.format("%o", '@'));
		Date now = new Date();
		Asserts.checkEquals(Long.toOctalString(now.getTime()), Formatter.format("%o", now));
		Asserts.checkEquals("0", Formatter.format("%o", null));
	}

	@Test
	public void testDecimal() {
		Asserts.checkEquals("   1", Formatter.format("%4d" , 1));
		Asserts.checkEquals("0002", Formatter.format("%04d", 2));
		Asserts.checkEquals("3   ", Formatter.format("%-4d", 3));

		Asserts.checkEquals("1,234,567", Formatter.format("%,7d" , +1234567));
		Asserts.checkEquals("1,234,567", Formatter.format("%,07d", +1234567));
		Asserts.checkEquals("1,234,567", Formatter.format("%,-7d", +1234567));
		Asserts.checkEquals("-1,234,567", Formatter.format("%,7d" , -1234567));
		Asserts.checkEquals("-1,234,567", Formatter.format("%,07d", -1234567));
		Asserts.checkEquals("-1,234,567", Formatter.format("%,-7d", -1234567));

		Asserts.checkEquals("+1,234,567", Formatter.format("%,+7d" , +1234567));
		Asserts.checkEquals("+1,234,567", Formatter.format("%,+07d", +1234567));
		Asserts.checkEquals("+1,234,567", Formatter.format("%-,+7d", +1234567));
		Asserts.checkEquals("-1,234,567", Formatter.format("%,+7d" , -1234567));
		Asserts.checkEquals("-1,234,567", Formatter.format("%,+07d", -1234567));
		Asserts.checkEquals("-1,234,567", Formatter.format("%-,+7d", -1234567));

		//locale ja前提
		Asserts.checkEquals(" +¥1,234,567", Formatter.format("%,+¤8d" , +1234567));
		Asserts.checkEquals("+¥01,234,567", Formatter.format("%,+¤08d", +1234567));
		Asserts.checkEquals("+¥1,234,567 ", Formatter.format("%-,+¤8d", +1234567));
		Asserts.checkEquals(" -¥1,234,567", Formatter.format("%,+¤8d" , -1234567));
		Asserts.checkEquals("-¥01,234,567", Formatter.format("%,+¤08d", -1234567));
		Asserts.checkEquals("-¥1,234,567 ", Formatter.format("%-,+¤8d", -1234567));

		//locale ja前提
		Asserts.checkEquals("  ¥1,234,567", Formatter.format("%, ¤8d" , +1234567));
		Asserts.checkEquals(" ¥01,234,567", Formatter.format("%, ¤08d", +1234567));
		Asserts.checkEquals(" ¥1,234,567 ", Formatter.format("%-, ¤8d", +1234567));
		Asserts.checkEquals(" -¥1,234,567", Formatter.format("%, ¤8d" , -1234567));
		Asserts.checkEquals("-¥01,234,567", Formatter.format("%, ¤08d", -1234567));
		Asserts.checkEquals("-¥1,234,567 ", Formatter.format("%-, ¤8d", -1234567));

		//locale 不問
		Asserts.checkEquals("  ¥1,234,567", Formatter.format("%, ¥8d" , +1234567));
		Asserts.checkEquals(" ¥01,234,567", Formatter.format("%, ¥08d", +1234567));
		Asserts.checkEquals(" ¥1,234,567 ", Formatter.format("%-, ¥8d", +1234567));
		Asserts.checkEquals(" -¥1,234,567", Formatter.format("%, ¥8d" , -1234567));
		Asserts.checkEquals("-¥01,234,567", Formatter.format("%, ¥08d", -1234567));
		Asserts.checkEquals("-¥1,234,567 ", Formatter.format("%-, ¥8d", -1234567));

		Asserts.checkEquals("+1,234,567.123", Formatter.format("%,+7.3d" , +1234567.1234));
		Asserts.checkEquals("+1,234,567.568", Formatter.format("%,+07.3d", +1234567.5678));
		Asserts.checkEquals("+1,234,567.123", Formatter.format("%-,+7.3d", +1234567.1234));
		Asserts.checkEquals("-1,234,567.568", Formatter.format("%,+7.3d" , -1234567.5678));
		Asserts.checkEquals("-1,234,567.123", Formatter.format("%,+07.3d", -1234567.1234));
		Asserts.checkEquals("-1,234,567.568", Formatter.format("%-,+7.3d", -1234567.5678));
	}

	@Test
	public void testHexaDecimal() {
		Asserts.checkEquals("   1", Formatter.format("%4x" , 1));
		Asserts.checkEquals("0002", Formatter.format("%04x", 2));
		Asserts.checkEquals("3   ", Formatter.format("%-4x", 3));
		Asserts.checkEquals("4"   , Formatter.format("%x",   4));
		Asserts.checkEquals("5"   , Formatter.format("%x", "5"));
		Asserts.checkEquals("6"   , Formatter.format("%x", "6.2"));
		Asserts.checkEquals("7"   , Formatter.format("%x", 7.12));
		Asserts.checkEquals("40"  , Formatter.format("%x", '@'));
		Date now = new Date();
		Asserts.checkEquals(Long.toHexString(now.getTime()), Formatter.format("%x", now));
		Asserts.checkEquals("0", Formatter.format("%x", null));
	}

	@Test
	public void testString() {
		Asserts.checkEquals("   1", Formatter.format("%4s" , "1"));
		Asserts.checkEquals("   2", Formatter.format("%04s", "2"));
		Asserts.checkEquals("3   ", Formatter.format("%-4s", "3"));
		Asserts.checkEquals("4"   , Formatter.format("%s",   "4"));
		Asserts.checkEquals("5"   , Formatter.format("%s", 5));
		Asserts.checkEquals("6.2" , Formatter.format("%s", "6.2"));
		Asserts.checkEquals("7.12", Formatter.format("%s", 7.12));
		Asserts.checkEquals("@"   , Formatter.format("%s", '@'));
		Asserts.checkEquals("null", Formatter.format("%s", null));
	}

	@Test
	public void testStrings() {
		Asserts.checkEquals("abcHELLOdef", Formatter.format("abc%sdef" , "HELLO"));
		Asserts.checkEquals("123-4567", Formatter.format("%3s-%4s" , "1234567"));
		Asserts.checkEquals("123-45", Formatter.format("%3s-%4s" , "12345"));
		Asserts.checkEquals("123", Formatter.format("%3s-%4s" , "123"));
	}

	@Test
	public void testChar() {
		Asserts.checkEquals("abc@def", Formatter.format("abc%cdef" , '@'));
		Asserts.checkEquals("abcあdef", Formatter.format("abc%cdef" , 0x3042));
	}


}
