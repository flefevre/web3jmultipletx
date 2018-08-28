package org.github.web3j.web3jmultipletxsmartcontract;

import java.math.BigInteger;

public class SmartContractUtil {
	
	public static BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
	public static BigInteger GAS_LIMIT = BigInteger.valueOf(400_300L);

	public final static String PASSWORD = new String("node01account");
	public final static String BC_GETH_ADMINWALLET= "src/main/resources/keystore/UTC--2017-06-05T15-25-01.575856240Z--d0f98f1ea8406c9ddb222144de6f8a9e464941da";

	public static byte[] stringToByte(String string) {
		byte[] byteValue = string.getBytes();
		byte[] byteValueLen32 = new byte[32];
		System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
		return byteValueLen32;
	}

}
