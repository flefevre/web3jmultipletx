package org.github.web3j.web3jmultipletxsmartcontract;	

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
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
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

public class MultipleCreationFastTransactionManager {
	private static Logger logger = LoggerFactory.getLogger(MultipleCreationFastTransactionManager.class);

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

		long pollingInterval = 10;
		FastRawTransactionManager fastRawTxMgr = new FastRawTransactionManager(adminWeb3j, credentials, new PollingTransactionReceiptProcessor(adminWeb3j, pollingInterval, 15));
		System.out.println("fastRawTxMgr.getCurrentNonce()="+fastRawTxMgr.getCurrentNonce());


		Date start = new Date();
		SimpleStorage x=null;
		try {
			logger.info(start.getTime()+"\tDeploy first time with send: ");
			byte[] _name = SmartContractUtil.stringToByte("tot"+((new Date()).getTime()));
			x = SimpleStorage.deploy(adminWeb3j, fastRawTxMgr, SmartContractUtil.GAS_PRICE, SmartContractUtil.GAS_LIMIT, _name).send();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date done = new Date();
		if(x!=null) {
			System.out.println(done.getTime()+"\tA:"+x.getTransactionReceipt().get().getBlockNumber()+"\t"+ x.getContractAddress());
		}
		else {
			System.err.println("Contract is null");
		}


		System.out.println("Waiting");
		try {
			TimeUnit.SECONDS.sleep(120);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static byte[] stringToByte(String string) {
		byte[] byteValue = string.getBytes();
		byte[] byteValueLen32 = new byte[32];
		System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
		return byteValueLen32;
	}

}
