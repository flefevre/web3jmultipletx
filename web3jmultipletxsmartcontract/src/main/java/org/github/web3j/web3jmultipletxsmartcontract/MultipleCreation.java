package org.github.web3j.web3jmultipletxsmartcontract;	

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class MultipleCreation {
	private static Logger logger = LoggerFactory.getLogger(MultipleCreation.class);
	
	public static final int COUNT = 10;  // don't set too high if using a real Ethereum network
	public static final long POLLING_FREQUENCY = 15000;
	public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = 40;
	public static BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000L);
	//public static BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
	public static BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

	public static void main(String[] args) throws IOException {
		Web3j web3 = Web3j.build(new HttpService());
		Credentials credentials2 = null;
		try {
			credentials2 = WalletUtils.loadCredentials("node01account", "/volatile/home/fl218080/gits/fildariane/digitalworld/dwethereum/dwethereumdockermultiplenodes/files/keystore/UTC--2017-06-05T15-25-11.024429180Z--4e5d555f2188e97c8c5f372ccba78b4b8fe9edbc");
		} catch (CipherException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		long pollingInterval = 10;
		//FastRawTransactionManager fastRawTxMgr =new FastRawTransactionManager(adminWeb3j, credentials, new PollingTransactionReceiptProcessor(adminWeb3j, pollingInterval, 15));

		Date start = new Date();
		SimpleStorage x=null;
		try {
			System.out.println(start.getTime()+"\tDeploy first time with send: ");
			x = SimpleStorage.deploy(web3, credentials2, GAS_PRICE, GAS_LIMIT).send();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date done = new Date();
		System.out.println(done.getTime()+"\tA:"+x.getTransactionReceipt().get().getBlockNumber()+"\t"+ x.getContractAddress());

		System.out.println(done.getTime()+"\tDeploy secund time with sendAsync");
		CompletableFuture<SimpleStorage> b = SimpleStorage.deploy(web3, credentials2, GAS_PRICE, GAS_LIMIT).sendAsync();
		b.thenAccept(trCommand -> {
			try {
				Date doneB = new Date();
				System.out.println(doneB.getTime()+"\tB:"+b.get().getTransactionReceipt().get().getBlockNumber()+"\t"+ b.get().getContractAddress());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Date done3 = new Date();
		System.out.println(done3.getTime()+"\tDeploy third time with sendAsync");
		CompletableFuture<SimpleStorage> c = SimpleStorage.deploy(web3, credentials2, GAS_PRICE, GAS_LIMIT).sendAsync();
		c.thenAccept(trCommand -> {
			try {
				Date doneC = new Date();
				System.out.println(doneC.getTime()+"\tC:"+c.get().getTransactionReceipt().get().getBlockNumber()+"\t"+ c.get().getContractAddress());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Date done4 = new Date();
		System.out.println(done4.getTime()+"\tWaiting");
		try {
			TimeUnit.SECONDS.sleep(120);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date done5 = new Date();
		System.out.println(done4.getTime()+"\tEnd");
	}



}
