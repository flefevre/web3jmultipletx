package org.github.web3j.web3jmultipletxsmartcontract;	

import java.io.File;
import java.io.IOException;
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
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

public class MultipleCreationRawTransactionManager {
	private static Logger logger = LoggerFactory.getLogger(MultipleCreationRawTransactionManager.class);

	
	public static void main(String[] args) throws IOException {

		Admin adminWeb3j = Admin.build(new HttpService());
		Web3ClientVersion web3ClientVersion;
		try {
			web3ClientVersion = adminWeb3j.web3ClientVersion().sendAsync().get();
			String clientVersion = web3ClientVersion.getWeb3ClientVersion();
			logger.info("clientVersion: "+clientVersion);
		} catch (InterruptedException | ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Web3j web3 = Web3j.build(new HttpService());
		//By default load the Admin credentials to be able to deploy contracts if necessary
		Credentials credentials = null;
		try {
			credentials = WalletUtils.loadCredentials(SmartContractUtil.PASSWORD, new File(SmartContractUtil.BC_GETH_ADMINWALLET));
		} catch (IOException e) {
			logger.error("credentials "+e.getMessage());
			e.printStackTrace();
		} catch (CipherException e) {
			logger.error("credentials "+e.getMessage());
			e.printStackTrace();
		}

		RawTransactionManager transactionManager = new RawTransactionManager(adminWeb3j, credentials);
		
		byte[] _name = SmartContractUtil.stringToByte("tot"+((new Date()).getTime()));
		Date start = new Date();
		SimpleStorage x=null;
		try {
			logger.info(start.getTime()+"\tDeploy first time with send: ");
			x = SimpleStorage.deploy(adminWeb3j, transactionManager, SmartContractUtil.GAS_PRICE, SmartContractUtil.GAS_LIMIT, _name).send();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date done = new Date();
		System.out.println(done.getTime()+"\tA:"+x.getTransactionReceipt().get().getBlockNumber()+"\t"+ x.getContractAddress());

		_name = SmartContractUtil.stringToByte("tot"+((new Date()).getTime()));

		System.out.println(done.getTime()+"\tDeploy secund time with sendAsync");
		CompletableFuture<SimpleStorage> b = SimpleStorage.deploy(adminWeb3j, transactionManager, SmartContractUtil.GAS_PRICE, SmartContractUtil.GAS_LIMIT, _name).sendAsync();
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
		_name = SmartContractUtil.stringToByte("tot"+((new Date()).getTime()));
		CompletableFuture<SimpleStorage> c = SimpleStorage.deploy(adminWeb3j, credentials, SmartContractUtil.GAS_PRICE, SmartContractUtil.GAS_LIMIT, _name).sendAsync();
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

		System.out.println("Waiting");
		try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			SimpleStorage simpleStorageB = b.get();
			System.out.println(simpleStorageB.getTransactionReceipt().get().getBlockNumber()+"\t"+ simpleStorageB.getTransactionReceipt().get().getContractAddress());
			SimpleStorage simpleStorageC = c.get();
			System.out.println(simpleStorageC.getTransactionReceipt().get().getBlockNumber()+"\t"+ simpleStorageC.getTransactionReceipt().get().getContractAddress());
			
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ending");
	}



}
