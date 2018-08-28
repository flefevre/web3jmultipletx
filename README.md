#Steps to reproduce

-create the web3j layer for a smart contract

'''
cd web3jmultipletxsmartcontract
mvn clean install
'''

-import the project in our favoite IDE, you should see the web3j layer in target/main/java

- start a ethereum private network based on the following work [1]
- account with password set to node01account

'''
cd ethereum-docker
docker-compose up -d
'''

- in your ide execute a basic main class localized in web3jmultipletxsmartcontract/src/main/java/org/github/web3j/web3jmultipletxsmartcontract/MultipleCreation.java

#Questions

- how to setup the private genesis files to have a developer instance with multipel block creation per secunds and low cost in transaction
- gasLimit
- gasprice 4000000000 
- targetgaslimit 4712388


https://hudsonjameson.com/2017-06-27-accounts-transactions-gas-ethereum/
--targetgaslimit Target gas limit sets the artificial target gas floor for the blocks to mine (default: “4712388”) --gasprice Minimal gas price to accept for mining a transactions (default: “20000000000”). Note: gasprice is listed in wei.

#References

[1]: https://github.com/Capgemini-AIE/ethereum-docker 