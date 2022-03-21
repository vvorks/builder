package com.github.vvorks.builder.client.test;

import java.util.Date;

import com.github.vvorks.builder.common.logging.Logger;
import com.github.vvorks.builder.common.text.Formatter;
import com.github.vvorks.builder.common.util.Test;
import com.github.vvorks.builder.common.util.TestCase;

public class FormatterTest implements TestCase {

	private static final Logger LOGGER = Logger.createLogger(FormatterTest.class);

	private void checkEquals(int no, CharSequence e, CharSequence r) {
		if (!e.equals(r)) {
			LOGGER.error("%2d: [%s] != [%s]", no, e, r);
		}
	}

	@Test
	public void testBinary() {
		checkEquals( 1, "   1"   , Formatter.format("%4b" , 1));
		checkEquals( 2, "0010"   , Formatter.format("%04b", 2));
		checkEquals( 3, "11  "   , Formatter.format("%-4b", 3));
		checkEquals( 4, "100"    , Formatter.format("%b",   4));
		checkEquals( 5, "101"    , Formatter.format("%b", "5"));
		checkEquals( 6, "110"    , Formatter.format("%b", "6.2"));
		checkEquals( 7, "111"    , Formatter.format("%b", 7.12));
		checkEquals( 8, "1000000", Formatter.format("%b", '@'));
		Date now = new Date();
		checkEquals( 9, Long.toBinaryString(now.getTime()), Formatter.format("%b", now));
		checkEquals(10, "0", Formatter.format("%b", null));
	}

	@Test
	public void testOctal() {
		checkEquals( 1, "   1", Formatter.format("%4o" , 1));
		checkEquals( 2, "0002", Formatter.format("%04o", 2));
		checkEquals( 3, "3   ", Formatter.format("%-4o", 3));
		checkEquals( 4, "4"   , Formatter.format("%o",   4));
		checkEquals( 5, "5"   , Formatter.format("%o", "5"));
		checkEquals( 6, "6"   , Formatter.format("%o", "6.2"));
		checkEquals( 7, "7"   , Formatter.format("%o", 7.12));
		checkEquals( 8, "100" , Formatter.format("%o", '@'));
		Date now = new Date();
		checkEquals( 9, Long.toOctalString(now.getTime()), Formatter.format("%o", now));
		checkEquals(10, "0", Formatter.format("%o", null));
	}

	@Test
	public void testDecimal() {
		checkEquals( 1, "   1", Formatter.format("%4d" , 1));
		checkEquals( 2, "0002", Formatter.format("%04d", 2));
		checkEquals( 3, "3   ", Formatter.format("%-4d", 3));

		checkEquals(11, "1,234,567", Formatter.format("%,7d" , +1234567));
		checkEquals(12, "1,234,567", Formatter.format("%,07d", +1234567));
		checkEquals(13, "1,234,567", Formatter.format("%,-7d", +1234567));
		checkEquals(14, "-1,234,567", Formatter.format("%,7d" , -1234567));
		checkEquals(15, "-1,234,567", Formatter.format("%,07d", -1234567));
		checkEquals(16, "-1,234,567", Formatter.format("%,-7d", -1234567));

		checkEquals(21, " +1,234,567", Formatter.format("%,+8d" , +1234567));
		checkEquals(22, "+01,234,567", Formatter.format("%,+08d", +1234567));
		checkEquals(23, "+1,234,567 ", Formatter.format("%-,+8d", +1234567));
		checkEquals(24, " -1,234,567", Formatter.format("%,+8d" , -1234567));
		checkEquals(25, "-01,234,567", Formatter.format("%,+08d", -1234567));
		checkEquals(26, "-1,234,567 ", Formatter.format("%-,+8d", -1234567));

		//locale ja前提
		checkEquals(31, " +¥1,234,567", Formatter.format("%,+¤8d" , +1234567));
		checkEquals(32, "+¥01,234,567", Formatter.format("%,+¤08d", +1234567));
		checkEquals(33, "+¥1,234,567 ", Formatter.format("%-,+¤8d", +1234567));
		checkEquals(34, " -¥1,234,567", Formatter.format("%,+¤8d" , -1234567));
		checkEquals(35, "-¥01,234,567", Formatter.format("%,+¤08d", -1234567));
		checkEquals(36, "-¥1,234,567 ", Formatter.format("%-,+¤8d", -1234567));

		//locale ja前提
		checkEquals(41, "  ¥1,234,567", Formatter.format("%, ¤8d" , +1234567));
		checkEquals(42, " ¥01,234,567", Formatter.format("%, ¤08d", +1234567));
		checkEquals(43, " ¥1,234,567 ", Formatter.format("%-, ¤8d", +1234567));
		checkEquals(44, " -¥1,234,567", Formatter.format("%, ¤8d" , -1234567));
		checkEquals(45, "-¥01,234,567", Formatter.format("%, ¤08d", -1234567));
		checkEquals(46, "-¥1,234,567 ", Formatter.format("%-, ¤8d", -1234567));

		//locale 不問
		checkEquals(51, "  ¥1,234,567", Formatter.format("%, ¥8d" , +1234567));
		checkEquals(52, " ¥01,234,567", Formatter.format("%, ¥08d", +1234567));
		checkEquals(53, " ¥1,234,567 ", Formatter.format("%-, ¥8d", +1234567));
		checkEquals(54, " -¥1,234,567", Formatter.format("%, ¥8d" , -1234567));
		checkEquals(55, "-¥01,234,567", Formatter.format("%, ¥08d", -1234567));
		checkEquals(56, "-¥1,234,567 ", Formatter.format("%-, ¥8d", -1234567));

		checkEquals(61, "+1,234,567.123", Formatter.format("%,+7.3d" , +1234567.1234));
		checkEquals(62, "+1,234,567.568", Formatter.format("%,+07.3d", +1234567.5678));
		checkEquals(63, "+1,234,567.123", Formatter.format("%-,+7.3d", +1234567.1234));
		checkEquals(64, "-1,234,567.568", Formatter.format("%,+7.3d" , -1234567.5678));
		checkEquals(65, "-1,234,567.123", Formatter.format("%,+07.3d", -1234567.1234));
		checkEquals(66, "-1,234,567.568", Formatter.format("%-,+7.3d", -1234567.5678));

		checkEquals(71, " +1,234,567.123", Formatter.format("%,+8.3d" , +1234567.1234));
		checkEquals(72, "+01,234,567.568", Formatter.format("%,+08.3d", +1234567.5678));
		checkEquals(73, "+1,234,567.123 ", Formatter.format("%-,+8.3d", +1234567.1234));
		checkEquals(74, " -1,234,567.568", Formatter.format("%,+8.3d" , -1234567.5678));
		checkEquals(75, "-01,234,567.123", Formatter.format("%,+08.3d", -1234567.1234));
		checkEquals(76, "-1,234,567.568 ", Formatter.format("%-,+8.3d", -1234567.5678));
	}

	@Test
	public void testHexaDecimal() {
		checkEquals( 1, "   1", Formatter.format("%4x" , 1));
		checkEquals( 2, "0002", Formatter.format("%04x", 2));
		checkEquals( 3, "3   ", Formatter.format("%-4x", 3));
		checkEquals( 4, "4"   , Formatter.format("%x",   4));
		checkEquals( 5, "5"   , Formatter.format("%x", "5"));
		checkEquals( 6, "6"   , Formatter.format("%x", "6.2"));
		checkEquals( 7, "7"   , Formatter.format("%x", 7.12));
		checkEquals( 8, "40"  , Formatter.format("%x", '@'));
		Date now = new Date();
		checkEquals( 9, Long.toHexString(now.getTime()), Formatter.format("%x", now));
		checkEquals(10, "0", Formatter.format("%x", null));
	}

	@Test
	public void testString() {
		checkEquals( 1, "   1", Formatter.format("%4s" , "1"));
		checkEquals( 2, "   2", Formatter.format("%04s", "2"));
		checkEquals( 3, "3   ", Formatter.format("%-4s", "3"));
		checkEquals( 4, "4"   , Formatter.format("%s",   "4"));
		checkEquals( 5, "5"   , Formatter.format("%s", 5));
		checkEquals( 6, "6.2" , Formatter.format("%s", "6.2"));
		checkEquals( 7, "7.12", Formatter.format("%s", 7.12));
		checkEquals( 8, "@"   , Formatter.format("%s", '@'));
		checkEquals( 9, "null", Formatter.format("%s", null));
	}

	@Test
	public void testStrings() {
		checkEquals( 1, "abcHELLOdef", Formatter.format("abc%sdef" , "HELLO"));
		checkEquals( 2, "123-4567", Formatter.format("%3s-%4s" , "1234567"));
		checkEquals( 3, "123-45", Formatter.format("%3s-%4s" , "12345"));
		checkEquals( 4, "123", Formatter.format("%3s-%4s" , "123"));
	}

	@Test
	public void testChar() {
		checkEquals( 1, "abc@def", Formatter.format("abc%cdef" , '@'));
		checkEquals( 2, "abcあdef", Formatter.format("abc%cdef" , 0x3042));
	}

}
